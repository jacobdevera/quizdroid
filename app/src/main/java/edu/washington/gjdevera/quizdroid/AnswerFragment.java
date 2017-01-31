package edu.washington.gjdevera.quizdroid;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AnswerFragment extends Fragment {
    private int questionNumber;
    private int correctTotal;

    public AnswerFragment() {
        // required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        TextView yourAnswerText = (TextView) getView().findViewById(R.id.your_answer);
        yourAnswerText.setText(String.format(getString(R.string.str_your_answer),
                ((QuizActivity) getActivity()).getYourAnswer()));

        String[] correctAnswers = getResources().getStringArray(R.array.answers);
        String[] choices = getResources().getStringArray(R.array.choices);
        TextView correctText = (TextView) getView().findViewById(R.id.correct_answer);
        correctText.setText(String.format(getString(R.string.str_correct_answer),
                choices[Integer.parseInt(correctAnswers[questionNumber])]));

        TextView ratioText = (TextView) getView().findViewById(R.id.ratio);
        ratioText.setText(String.format(getString(R.string.str_ratio), correctTotal,
                questionNumber + 1));

        final Button next = (Button) getView().findViewById(R.id.btn_next);
        final Boolean lastQuestion =
                (questionNumber + 1 >= getResources().getStringArray(R.array.questions).length);
        if (lastQuestion) {
            next.setText(getString(R.string.str_finish));
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to next question
                if (!lastQuestion) {
                    //
                } else {
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}
