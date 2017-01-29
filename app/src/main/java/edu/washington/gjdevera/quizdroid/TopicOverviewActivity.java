package edu.washington.gjdevera.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TopicOverviewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_overview);

        Intent intent = getIntent();
        final int quizNumber = intent.getIntExtra(MainActivity.EXTRA_TOPIC, 0);
        TextView descText = new TextView(this);
        TextView numberText = new TextView(this);
        descText.setTextSize(20);
        numberText.setTextSize(20);

        numberText.setText(getString(R.string.number_of_questions));
        descText.setText(getString(R.string.description));

        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_topic_overview);
        layout.addView(descText, 1);
        layout.addView(numberText, 2);

        final Button button = (Button) findViewById(R.id.btn_begin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                intent.putExtra("questionNumber", 0); // 0 = start on first question
                intent.putExtra("correctTotal", 0);
                startActivity(intent);
                finish();
            }
        });
    }
}
