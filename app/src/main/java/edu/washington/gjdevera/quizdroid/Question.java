package edu.washington.gjdevera.quizdroid;

/**
 * Created by Jacob Devera on 2/8/2017.
 */

public class Question {
    private String text;
    private String[] answers;
    private int correct;

    public Question(String text, String[] answers, int correct) {
        this.text = text;
        this.answers = answers;
        this.correct = correct;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public String getText() {
        return text;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrect() {
        return correct;
    }
}
