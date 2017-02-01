package edu.washington.gjdevera.quizdroid;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
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
        TextView question = (TextView) getView().findViewById(R.id.question);
        question.setText(getResources().getStringArray(R.array.questions)[mQuestionNumber]);

        RadioButton radio1 = (RadioButton) getView().findViewById(R.id.radio_1);
        RadioButton radio2 = (RadioButton) getView().findViewById(R.id.radio_2);
        RadioButton radio3 = (RadioButton) getView().findViewById(R.id.radio_3);
        RadioButton radio4 = (RadioButton) getView().findViewById(R.id.radio_4);
        String[] radioArray = getResources().getStringArray(R.array.choices);
        radio1.setText(radioArray[0]);
        radio2.setText(radioArray[1]);
        radio3.setText(radioArray[2]);
        radio4.setText(radioArray[3]);
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
