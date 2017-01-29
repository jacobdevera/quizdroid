package edu.washington.gjdevera.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnswerActivity extends Activity {
    private int questionNumber;
    private int correctTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Bundle bundle = getIntent().getExtras();
        String yourAnswer = bundle.getString("yourAnswer");
        questionNumber = bundle.getInt("questionNumber");
        Boolean isCorrect = bundle.getBoolean("isCorrect");
        correctTotal = bundle.getInt("correctTotal");
        if (isCorrect) correctTotal++;
        TextView yourAnswerText = (TextView) findViewById(R.id.your_answer);
        yourAnswerText.setText(String.format(getString(R.string.str_your_answer), yourAnswer));

        String[] correctAnswers = getResources().getStringArray(R.array.answers);
        String[] choices = getResources().getStringArray(R.array.choices);
        TextView correctText = (TextView) findViewById(R.id.correct_answer);
        correctText.setText(String.format(getString(R.string.str_correct_answer),
                choices[Integer.parseInt(correctAnswers[questionNumber])]));

        TextView ratioText = (TextView) findViewById(R.id.ratio);
        ratioText.setText(String.format(getString(R.string.str_ratio), correctTotal, questionNumber + 1));

        final Button next = (Button) findViewById(R.id.btn_next);
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
                    Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                    intent.putExtra("questionNumber", questionNumber + 1);
                    intent.putExtra("correctTotal", correctTotal);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                finish();
            }
        });
    }
}
