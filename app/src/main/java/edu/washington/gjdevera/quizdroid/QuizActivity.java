package edu.washington.gjdevera.quizdroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizActivity extends Activity {
    private int questionNumber = -1; // first question = 0, increment by one when instantiating
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
        final TextView headerText = (TextView) findViewById(R.id.header);
        final TextView descText = new TextView(this);
        final TextView numberText = new TextView(this);
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
                Boolean lastQuestion =
                        (questionNumber + 1 >=
                                getResources().getStringArray(R.array.questions).length);

                if (questionNumber == -1) { // remove topic overview
                    ((ViewGroup) numberText.getParent()).removeView(numberText);
                    ((ViewGroup) descText.getParent()).removeView(descText);
                }

                if (isFirstFragmentOn) {
                    // Display answer fragment
                    RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
                    String[] correctAnswers = getResources().getStringArray(R.array.answers);
                    button.setText(getString(R.string.str_next));
                    Boolean isCorrect = (((RadioButton) radioGroup.getChildAt(Integer
                            .parseInt(correctAnswers[questionNumber]))).isChecked());
                    if (isCorrect) {
                        correctTotal++;
                        headerText.setText(getString(R.string.str_header_correct));
                    } else {
                        headerText.setText(getString(R.string.str_header_wrong));
                    }
                    isFirstFragmentOn = false;
                    fragToDisplay = AnswerFragment.newInstance(questionNumber, correctTotal);
                } else {
                    // Display question fragment
                    if (!lastQuestion) {
                        questionNumber++;
                        isFirstFragmentOn = true;
                        button.setVisibility(View.GONE);
                        button.setText(getString(R.string.str_submit));
                        headerText.setText(getString(R.string.str_topic_name));
                        fragToDisplay = QuestionFragment.newInstance(questionNumber);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
                if (fragToDisplay != null) {
                    FragmentTransaction tx = getFragmentManager().beginTransaction();
                    tx.replace(R.id.fragment_placeholder, fragToDisplay);
                    tx.commit();
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        final Button submit = (Button) findViewById(R.id.btn_main);
        yourAnswer = ((RadioButton) view).getText().toString();
        /*int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);*/

        submit.setVisibility(View.VISIBLE);
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
