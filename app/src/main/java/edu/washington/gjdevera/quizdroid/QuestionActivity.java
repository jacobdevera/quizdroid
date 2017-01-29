package edu.washington.gjdevera.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuestionActivity extends Activity {
    private int questionNumber;
    private int correctTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Bundle bundle = getIntent().getExtras();
        // get question number based on previous question number
        questionNumber = bundle.getInt("questionNumber");
        correctTotal = bundle.getInt("correctTotal");
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
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        final String yourAnswer = ((RadioButton) view).getText().toString();
        final Button submit = (Button) findViewById(R.id.btn_submit);

        /*int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);*/

        submit.setVisibility(View.VISIBLE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if correct answer selected
                String[] correctAnswers = getResources().getStringArray(R.array.answers);
                Boolean isCorrect = (((RadioButton) radioGroup.getChildAt(Integer
                        .parseInt(correctAnswers[questionNumber]))).isChecked());
                // go to answer page
                Intent intent = new Intent(getApplicationContext(), AnswerActivity.class);
                intent.putExtra("yourAnswer", yourAnswer);
                intent.putExtra("questionNumber", questionNumber);
                intent.putExtra("isCorrect", isCorrect);
                intent.putExtra("correctTotal", correctTotal);
                startActivity(intent);
            }
        });
    }
}
