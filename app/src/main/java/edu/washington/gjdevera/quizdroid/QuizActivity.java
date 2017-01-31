package edu.washington.gjdevera.quizdroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizActivity extends Activity {
    private int questionNumber = 0;
    private int correctTotal = 0;
    private String yourAnswer;
    private boolean isCorrect;
    private boolean isFirstFragmentOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

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

        final Button button = (Button) findViewById(R.id.btn_main);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragToDisplay = null;

                if (isFirstFragmentOn) {
                    // Display second
                    isFirstFragmentOn = false;
                    fragToDisplay = new AnswerFragment();
                }
                else {
                    // Display first
                    isFirstFragmentOn = true;
                    button.setText(getString(R.string.str_submit));
                    button.setVisibility(View.GONE);
                    fragToDisplay = QuestionFragment.newInstance(0, 0);
                }

                FragmentTransaction tx = getFragmentManager().beginTransaction();
                tx.replace(R.id.fragment_placeholder, fragToDisplay);
                tx.commit();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        final String yourAnswer = ((RadioButton) view).getText().toString();
        final Button submit = (Button) findViewById(R.id.btn_main);

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
                setCorrect(isCorrect);
                // go to next question
                setQuestionNumber(questionNumber + 1);
            }
        });
    }


    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int n) {
        questionNumber = n;
    }

    public int getCorrectTotal() {
        return correctTotal;
    }

    public void setCorrectTotal(int n) {
        correctTotal = n;
    }

    public String getYourAnswer() {
        return yourAnswer;
    }

    public void setYourAnswer(String s) {
        yourAnswer = s;
    }
    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct){
        isCorrect = correct;
    }
}
