package edu.washington.gjdevera.quizdroid;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Pratik.
 */
public class MyJSON {

    static String fileName = "questions.json";

    public static void saveData(Context context, String mJsonResponse) {
        try {
            FileWriter file = new FileWriter(context.getFilesDir().getPath() + "/" + fileName);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.e("TAG", "Error in writing JSON: " + e.getLocalizedMessage());
        }
    }

    public static String getData(Context context) {
        try {
            File f = new File(context.getFilesDir().getPath() + "/" + fileName);
            //check whether file exists
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            Log.e("TAG", "Error in reading JSON: " + e.getLocalizedMessage());
            return null;
        }
    }
}
