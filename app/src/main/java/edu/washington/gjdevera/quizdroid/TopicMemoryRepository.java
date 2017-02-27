package edu.washington.gjdevera.quizdroid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob Devera on 2/8/2017.
 */

public class TopicMemoryRepository implements TopicRepository {
    public final static String TAG = "TopicMemoryRepository";
    private List<Topic> topics = new ArrayList<>();

    public void initializeRepo(final Activity activity) {
        topics.clear();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        String url = prefs.getString("json_url", null);
        Log.d(TAG, "URL " + url);
        if (url == null || url == "") { // if blank in preferences, reset to default
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("json_url", "https://tednewardsandbox.site44.com/questions.json");
            editor.commit();
            url = "https://tednewardsandbox.site44.com/questions.json";
        }

        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        String jsonStr;
        if (!isConnected) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Snackbar.make(activity.findViewById(R.id.coordinatorLayout),
                            R.string.connection_error, Snackbar.LENGTH_LONG)
                    .setAction(R.string.snackbar_retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.getInstance().start();
                        }
                    })
                    .show();
                }
            });
        } else {
            HttpHandler sh = new HttpHandler();
            // making a request to url and getting response
            jsonStr = sh.makeServiceCall(url);
            Log.i(TAG, "Response from url: " + jsonStr);
            // saving response to JSON file
            if (jsonStr != null) {
                MyJSON.saveData(activity, jsonStr);
            } else {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(activity.findViewById(R.id.coordinatorLayout),
                                R.string.json_error, Snackbar.LENGTH_LONG)
                                .setAction(R.string.snackbar_retry, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        MainActivity.getInstance().start();
                                    }
                                })
                                .show();
                    }
                });
            }
        }
        // read from file, whether it was downloaded from server or used as a fallback
        jsonStr = MyJSON.getData(activity);
        if (jsonStr != null) {
            try {
                // Getting JSON Array node
                JSONArray topics = new JSONArray(jsonStr);

                // looping through topics
                for (int i = 0; i < topics.length(); i++) {
                    JSONObject topic = topics.getJSONObject(i);

                    String title = topic.getString("title");
                    String desc = topic.getString("desc");
                    Topic newTopic = new Topic(R.drawable.ic_library_books, title, desc, desc);

                    // looping through questions in the current topic
                    JSONArray questions = topic.getJSONArray("questions");
                    for (int j = 0; j < questions.length(); j++) {
                        JSONObject question = questions.getJSONObject(j);
                        String text = question.getString("text");
                        int answer = Integer.parseInt(question.getString("answer"));

                        // looping through answers in the current question
                        JSONArray answers = question.getJSONArray("answers");
                        int lengthAnswers = answers.length();
                        String[] answersArray = new String[lengthAnswers];

                        // add new question to the topic and update it with its answers
                        newTopic.getQuestions().add(new Question(text, answersArray, answer - 1));
                        for (int k = 0; k < answers.length(); k++) {
                            answersArray[k] = answers.getString(k);
                            newTopic.getQuestions().get(j).setAnswers(answersArray);
                        }
                    }
                    // add new topic to the repository
                    ((QuizApp) activity.getApplication()).getRepository().getAllTopics().add(newTopic);
                }
                // log all topics
                Log.d(QuizApp.TAG, topics.toString());
            } catch (final JSONException e) {
                Log.e(TAG, "JSON parsing error: " + e.getMessage());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "JSON parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else if (isConnected) {
            Log.e(TAG, "Couldn't get JSON from server.");
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Snackbar.make(activity.findViewById(R.id.coordinatorLayout),
                            R.string.json_error_no_file, Snackbar.LENGTH_LONG)
                            .setAction(R.string.snackbar_retry, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    MainActivity.getInstance().start();
                                }
                            })
                            .show();
                }
            });
        }
    }

    public List<Topic> getAllTopics() {
        return topics;
    }

    @Override
    public String toString() {
        return "TopicMemoryRepository{" +
                "topics=" + topics +
                '}';
    }
}
