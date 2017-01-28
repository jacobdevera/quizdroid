package edu.washington.gjdevera.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TopicOverviewActivity extends Activity {
    public final static String EXTRA_OVERVIEW = "com.example.quizdroid.OVERVIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_overview);
        String[] descriptions = getResources().getStringArray(R.array.descriptions);

        Intent intent = getIntent();
        final int quizNumber = intent.getIntExtra(MainActivity.EXTRA_TOPIC, 0);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(descriptions[quizNumber]);

        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_topic_overview);
        layout.addView(textView, 0);

        final Button button = (Button) findViewById(R.id.btn_next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TopicOverviewActivity.class);
                intent.putExtra(EXTRA_OVERVIEW, quizNumber);
                startActivity(intent);
            }
        });
    }
}
