package edu.washington.gjdevera.quizdroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizActivity extends Activity implements TopicOverviewFragment.OnFragmentInteractionListener {
    private Topic topic;
    private int topicNumber;
    private int questionNumber = -1; // first question = 0, increment by one when instantiating
    private int correctTotal = 0;
    private String yourAnswer;
    private boolean isFirstFragmentOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Intent intent = getIntent();
        topicNumber = intent.getIntExtra(MainActivity.EXTRA_TOPIC, 0);
        final TextView headerText = (TextView) findViewById(R.id.header);
        QuizApp mApplication = (QuizApp) getApplication();

        changeFragment(TopicOverviewFragment.newInstance(topicNumber)); // display topic overview
        topic = mApplication.getRepository().getAllTopics().get(topicNumber);
        headerText.setText(topic.getTitle());

        final Button button = (Button) findViewById(R.id.btn_main);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragToDisplay = null;
                Boolean lastQuestion =
                        (questionNumber + 1 >=
                                topic.getQuestions().size());

                if (isFirstFragmentOn) {
                    // Display answer fragment
                    RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
                    String[] correctAnswers = getResources().getStringArray(R.array.answers);
                    button.setText(getString(R.string.str_next));
                    Boolean isCorrect = (((RadioButton) radioGroup.getChildAt(topic.getQuestions()
                            .get(questionNumber).getCorrect())).isChecked());
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
                        headerText.setText(topic.getTitle());
                        fragToDisplay = QuestionFragment.newInstance(questionNumber);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
                if (fragToDisplay != null) {
                    changeFragment(fragToDisplay);
                }
            }
        });
    }

    private void changeFragment(Fragment fragToDisplay) {
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.setCustomAnimations(R.animator.fade_enter, R.animator.slide_out_right);
        tx.replace(R.id.fragment_placeholder, fragToDisplay);
        tx.commit();
    }

    public void onRadioButtonClicked(View view) {
        final Button submit = (Button) findViewById(R.id.btn_main);
        yourAnswer = ((RadioButton) view).getText().toString();
        /*int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);*/

        submit.setVisibility(View.VISIBLE);
    }

    public int getTopicNumber() {
        return topicNumber;
    }
    public String getYourAnswer() {
        return yourAnswer;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // mandatory method
    }
}
