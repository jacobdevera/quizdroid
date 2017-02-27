package edu.washington.gjdevera.quizdroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    private RecyclerView mRecyclerView;
    private ProgressDialog pDialog;
    private PendingIntent pendingIntent;
    private Context mContext;
    private static MainActivity instance;

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
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

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
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            // put parsed JSON into RecyclerView
            final List<Topic> topics = ((QuizApp) getApplication()).getRepository().getAllTopics();

            // instantiate RecyclerView
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
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
                    if (topics.size() > 0) {
                        Toast.makeText(MainActivity.this, getString(R.string.json_success), Toast.LENGTH_SHORT)
                                .show();
                    }
                }

                @Override
                public int getItemCount() {
                    return topics.size();
                }
            });
        }

    }

    private class TopicViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TopicViewHolder(View v) {
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
}
