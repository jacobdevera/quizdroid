package edu.washington.gjdevera.quizdroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob Devera on 2/8/2017.
 */

public class TopicRepository {
    private static TopicRepository instance = new TopicRepository();
    private List<Topic> topics = new ArrayList<Topic>();

    public static TopicRepository getInstance() {
        return instance;
    }

    public List<Topic> getAllTopics() {
        return topics;
    }
}
