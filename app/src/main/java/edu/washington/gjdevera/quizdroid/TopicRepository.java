package edu.washington.gjdevera.quizdroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob Devera on 2/8/2017.
 */

public interface TopicRepository<E> {
    List<Topic> getAllTopics();
}
