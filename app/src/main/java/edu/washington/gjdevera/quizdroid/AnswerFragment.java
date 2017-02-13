package edu.washington.gjdevera.quizdroid;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AnswerFragment extends Fragment {
    private QuestionFragment.OnFragmentInteractionListener mListener;

    public static final String ARG_CORRECT_TOTAL = "correctTotal";

    private int mQuestionNumber;
    private int mCorrectTotal;

    public AnswerFragment() {
        // required empty constructor
    }

    public static AnswerFragment newInstance(int questionNumber, int correctTotal) {
        AnswerFragment fragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putInt(QuestionFragment.ARG_QUESTION_NUMBER, questionNumber);
        args.putInt(ARG_CORRECT_TOTAL, correctTotal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestionNumber = getArguments().getInt(QuestionFragment.ARG_QUESTION_NUMBER);
            mCorrectTotal = getArguments().getInt(ARG_CORRECT_TOTAL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_answer, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        QuizApp mApplication = (QuizApp) getActivity().getApplication();
        Question mQuestion = mApplication.getRepository().getAllTopics()
                .get(((QuizActivity) getActivity()).getTopicNumber())
                .getQuestions().get(mQuestionNumber);
        TextView yourAnswerText = (TextView) getActivity().findViewById(R.id.your_answer);
        // get answer submitted on previous question, set text
        yourAnswerText.setText(String.format(getString(R.string.str_your_answer),
                ((QuizActivity) getActivity()).getYourAnswer()));

        TextView correctText = (TextView) getActivity().findViewById(R.id.correct_answer);
        correctText.setText(String.format(getString(R.string.str_correct_answer),
                mQuestion.getAnswers()[mQuestion.getCorrect()]));

        TextView ratioText = (TextView) getActivity().findViewById(R.id.ratio);
        ratioText.setText(String.format(getString(R.string.str_ratio), mCorrectTotal,
                mQuestionNumber + 1));

        // if the last question, change "Next" button to "Finish"
        final Button next = (Button) getActivity().findViewById(R.id.btn_main);
        Topic topic = mApplication.getRepository().getAllTopics()
                .get(((QuizActivity) getActivity()).getTopicNumber());
        final Boolean lastQuestion =
                (mQuestionNumber + 1 >= topic.getQuestions().size());
        if (lastQuestion) {
            next.setText(getString(R.string.str_finish));
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
