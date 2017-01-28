package edu.washington.gjdevera.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class QuestionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        int quizNumber = intent.getIntExtra(TopicOverviewActivity.EXTRA_OVERVIEW, 0);
        TextView textView = new TextView(this);
        textView.setTextSize(40);

    }
}
