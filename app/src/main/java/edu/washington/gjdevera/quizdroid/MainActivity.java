package edu.washington.gjdevera.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    public final static String EXTRA_TOPIC = "edu.washington.gjdevera.quizdroid.TOPIC";
    public static final String EXTRA_QUESTION_NUMBER = "edu.washington.gjdevera.quizdroid.QUESTION_NUMBER";
    public static final String EXTRA_CORRECT_TOTAL = "edu.washington.gjdevera.quizdroid.CORRECT_TOTAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] topics = getResources().getStringArray(R.array.topics);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, topics);

        final ListView listView = (ListView) findViewById(R.id.topic_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                intent.putExtra(EXTRA_TOPIC, position);
                startActivity(intent);
            }
        });
    }
}
