package edu.washington.gjdevera.quizdroid;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by Jacob Devera on 2/8/2017.
 */

public class QuizApp extends android.app.Application {
    private static final String TAG = "QuizApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "QuizApp class loaded");
    }
}
