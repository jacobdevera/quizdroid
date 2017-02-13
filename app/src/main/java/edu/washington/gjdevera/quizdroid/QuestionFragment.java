package edu.washington.gjdevera.quizdroid;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.TextView;

public class QuestionFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public static final String ARG_QUESTION_NUMBER = "questionNumber";

    private int mQuestionNumber;

    public QuestionFragment() {
        // required empty constructor
    }

    public static QuestionFragment newInstance(int questionNumber) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_NUMBER, questionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestionNumber = getArguments().getInt(ARG_QUESTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        QuizActivity activity = (QuizActivity) getActivity();
        Question mQuestion = ((QuizApp) activity.getApplication()).getRepository().getAllTopics()
                .get(activity.getTopicNumber())
                .getQuestions().get(mQuestionNumber);
        TextView tvQuestion = (TextView) getView().findViewById(R.id.question);
        tvQuestion.setText(mQuestion.getText());

        // instantiate radio group and set its listener
        RadioGroup radioGroup = (RadioGroup) getView().findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(activity);
        RadioGroup.LayoutParams params =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        String[] answers = mQuestion.getAnswers();
        for (int i = 0; i < answers.length; i++) {
            RadioButton button = new RadioButton(getActivity());
            button.setText(answers[i]);
            radioGroup.addView(button, params);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
