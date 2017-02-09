package edu.washington.gjdevera.quizdroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob Devera on 2/8/2017.
 */

public class TopicMemoryRepository implements TopicRepository {
    public static TopicMemoryRepository instance = new TopicMemoryRepository();
    private List<Topic> topics = new ArrayList<Topic>();

    public TopicMemoryRepository() {
        // initialize topics

    }

    public TopicMemoryRepository getInstance() {
        return instance;
    }

    public List<Topic> getAllTopics() {
        return topics;
    }
}
