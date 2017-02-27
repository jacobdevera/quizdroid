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

public class QuizActivity extends Activity implements TopicOverviewFragment.OnFragmentInteractionListener, RadioGroup.OnCheckedChangeListener {
    private int topicNumber;
    private int questionNumber = -1; // first question = 0, increment by one when instantiating
    private int correctTotal = 0;
    private String yourAnswer;
    private boolean isFirstFragmentOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // get intent from previous activity, choosing the topic
        Intent intent = getIntent();
        topicNumber = intent.getIntExtra(MainActivity.EXTRA_TOPIC, 0);
        final TextView headerText = (TextView) findViewById(R.id.header);

        changeFragment(TopicOverviewFragment.newInstance(topicNumber)); // display topic overview
        final Topic topic = ((QuizApp) getApplication()).getRepository().getAllTopics().get(topicNumber);
        headerText.setText(topic.getTitle());
        headerText.setCompoundDrawablesWithIntrinsicBounds(topic.getIcon(), 0, 0, 0);
        headerText.setCompoundDrawablePadding(12);

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
                    button.setText(getString(R.string.str_next));
                    Boolean isCorrect = (((RadioButton) radioGroup.getChildAt(topic.getQuestions()
                            .get(questionNumber).getCorrect())).isChecked());
                    if (isCorrect) {
                        correctTotal++;
                        headerText.setText(getString(R.string.str_header_correct));
                    } else {
                        headerText.setText(getString(R.string.str_header_wrong));
                    }
                    // go to next question
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
                        // go back to topic list
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

    public int getTopicNumber() {
        return topicNumber;
    }
    public String getYourAnswer() {
        return yourAnswer;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // get radio button that was checked and make the submit button visible
        Button submit = (Button) findViewById(R.id.btn_main);
        int radioButtonID = group.getCheckedRadioButtonId();
        View radioButton = group.findViewById(radioButtonID);
        yourAnswer = ((RadioButton) radioButton).getText().toString();
        submit.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // mandatory method
    }
}
