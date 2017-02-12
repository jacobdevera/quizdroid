package edu.washington.gjdevera.quizdroid;

import android.util.Log;

/**
 * Created by Jacob Devera on 2/8/2017.
 */

public class QuizApp extends android.app.Application {
    private static QuizApp instance;
    private static TopicMemoryRepository repository;
    public static final String TAG = "QuizApp";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        repository = new TopicMemoryRepository();
        Log.d(TAG, "QuizApp class loaded");
        getRepository();
    }

    public static QuizApp getInstance() {
        if (instance == null) {
            instance = new QuizApp();
        }
        return instance;
    }

    public TopicMemoryRepository getRepository() {
        return repository;
    }
}
