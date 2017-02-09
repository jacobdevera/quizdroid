package edu.washington.gjdevera.quizdroid;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob Devera on 2/8/2017.
 */

public class TopicMemoryRepository implements TopicRepository {
    public static TopicMemoryRepository instance = new TopicMemoryRepository();
    private List<Topic> topics = new ArrayList<>();

    public TopicMemoryRepository() {
        // initialize topics
        topics.add(new Topic("Math", "Filler shortDesc", "Filler longDesc"));
        topics.add(new Topic("Physics", "Filler shortDesc", "Filler longDesc"));
        topics.add(new Topic("Marvel Super Heroes", "Filler shortDesc", "Filler longDesc"));
        for (int i = 0; i < 3; i++) {
            topics.get(i).getQuestions().add(new Question("Filler question " + (i + 1),
                    new String[]{"Filler choice 1", "Filler choice 2", "Filler choice 3", "Filler choice 4"},
                    0));
        }
        // log all topics and their questions
        Log.d(QuizApp.TAG, topics.toString());
    }

    public TopicMemoryRepository getInstance() {
        return instance;
    }

    public List<Topic> getAllTopics() {
        return topics;
    }
}
