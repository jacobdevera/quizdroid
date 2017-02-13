package edu.washington.gjdevera.quizdroid;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob Devera on 2/8/2017.
 */

public class TopicMemoryRepository implements TopicRepository {
    private List<Topic> topics = new ArrayList<>();

    public TopicMemoryRepository() {
        // initialize hard-coded topics
        topics.add(new Topic("Math", "Filler shortDesc for Math", "Filler longDesc for Math"));
        topics.add(new Topic("Physics", "Filler shortDesc for Physics", "Filler longDesc for Physics"));
        topics.add(new Topic("Marvel Super Heroes", "Filler shortDesc for Marvel Super Heroes", "Filler longDesc for Marvel Super Heroes"));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                topics.get(i).getQuestions().add(new Question("Filler question " + (j + 1) + " for " +
                        topics.get(i).getTitle(),
                        new String[]{"Filler choice 1", "Filler choice 2", "Filler choice 3", "Filler choice 4"},
                        0));
            }
        }
        // log all topics and their questions
        Log.d(QuizApp.TAG, topics.toString());
    }

    public List<Topic> getAllTopics() {
        return topics;
    }

    @Override
    public String toString() {
        return "TopicMemoryRepository{" +
                "topics=" + topics +
                '}';
    }
}
