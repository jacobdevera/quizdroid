package edu.washington.gjdevera.quizdroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob Devera on 2/8/2017.
 */

public class Topic {
    private String title;
    private String shortDesc;
    private String longDesc;
    private List<Question> questions = new ArrayList<>();

    public Topic(String title, String shortDesc, String longDesc) {
        this.title = title;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "title='" + title + '\'' +
                ", questions=" + questions +
                '}';
    }
}