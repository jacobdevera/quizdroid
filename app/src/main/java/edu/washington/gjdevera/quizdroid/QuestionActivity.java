package edu.washington.gjdevera.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuestionActivity extends Activity {
    private int correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        correct = 0;
        Intent intent = getIntent();
        // get question number based on previous question number
        int questionNumber = intent.getIntExtra(TopicOverviewActivity.EXTRA_OVERVIEW, 0);
        TextView question = (TextView) findViewById(R.id.question);
        question.setText(getResources().getStringArray(R.array.questions)[questionNumber]);
        question.setTextSize(40);

        RadioButton radio1 = (RadioButton) findViewById(R.id.radio_1);
        RadioButton radio2 = (RadioButton) findViewById(R.id.radio_2);
        RadioButton radio3 = (RadioButton) findViewById(R.id.radio_3);
        RadioButton radio4 = (RadioButton) findViewById(R.id.radio_4);
        String[] radioArray = getResources().getStringArray(R.array.choices);
        radio1.setText(radioArray[0]);
        radio2.setText(radioArray[1]);
        radio3.setText(radioArray[2]);
        radio4.setText(radioArray[3]);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        Button next = (Button) findViewById(R.id.btn_next);
        next.setVisibility(View.VISIBLE);
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_1:
                if (checked)

                    break;
            case R.id.radio_2:
                if (checked)

                    break;
        }
    }
}
