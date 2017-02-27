package edu.washington.gjdevera.quizdroid;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "MainActivity";
    public final static String EXTRA_TOPIC = "edu.washington.gjdevera.quizdroid.TOPIC";

    private ProgressDialog pDialog;
    private PendingIntent pendingIntent;
    private static MainActivity instance;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // get interval to update JSON from preferences

        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int interval = Integer.parseInt(prefs.getString("sync_frequency", "180")) * 1000 * 60;
        Log.i(TAG, "Interval (milliseconds): " + interval);
        final AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        boolean alarmUp = (PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null);//just changed the flag
        Log.d(TAG, "alarm is " + (alarmUp ? "" : "not") + "up");
        if (!alarmUp)
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

        // reset the alarm every time the sync frequency preference changes
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if (key.equals("sync_frequency")) {
                    int interval = Integer.parseInt(prefs.getString("sync_frequency", "180")) * 1000 * 60;
                    Log.i(TAG, "Interval (milliseconds): " + interval);
                    manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
                }
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(listener);

        // fetch and parse JSON
        start();
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void start() {
        new GetTopics().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    public class GetTopics extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Fetching topics...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // let repository fetch JSON topics in the background
            ((QuizApp) getApplication()).getRepository().initializeRepo(MainActivity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (isAirplaneModeOn(MainActivity.this)) {
                // prompt user to go to settings to turn off airplane mode
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainActivity.this);

                alertDialogBuilder.setTitle(MainActivity.this.getString(R.string.airplane_mode_title))
                        .setMessage(MainActivity.this.getString(R.string.airplane_mode_dialog))
                        .setCancelable(true)
                        .setPositiveButton(MainActivity.this.getString(R.string.airplane_mode_positive),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // go to settings
                                        MainActivity.this.startActivityForResult(new Intent
                                                (android.provider.Settings.ACTION_SETTINGS), 0);
                                    }
                                })
                        .setNegativeButton(MainActivity.this.getString(R.string.airplane_mode_negative),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                }).create().show();
            }
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            // put parsed JSON into RecyclerView
            final List<Topic> topics = ((QuizApp) getApplication()).getRepository().getAllTopics();

            // instantiate RecyclerView
            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            mRecyclerView.setAdapter(new RecyclerView.Adapter<TopicViewHolder>() {

                @Override
                public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View v = LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.topic_row,
                            parent,
                            false);
                    return new TopicViewHolder(v);
                }

                @Override
                public void onBindViewHolder(TopicViewHolder vh, int position) {
                    // get TextViews and replace with the title and short description
                    TextView tv = (TextView) vh.itemView.findViewById(R.id.text1);
                    tv.setText(topics.get(position).getTitle());
                    tv.setCompoundDrawablesWithIntrinsicBounds(topics.get(position).getIcon(), 0, 0, 0);
                    tv.setCompoundDrawablePadding(12);
                    tv = (TextView) vh.itemView.findViewById(R.id.text2);
                    tv.setText(topics.get(position).getShortDesc());
                }

                @Override
                public int getItemCount() {
                    return topics.size();
                }
            });
            // success toast
            if (topics.size() > 0) {
                Toast.makeText(MainActivity.this, getString(R.string.json_success), Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }

    private class TopicViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TopicViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
            intent.putExtra(EXTRA_TOPIC, getAdapterPosition());
            startActivity(intent);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_preferences:
                // go to preferences activity
                startActivity(new Intent(getApplicationContext(), PreferencesActivity.class));
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private boolean isAirplaneModeOn(Context context) {
        return Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }
}
