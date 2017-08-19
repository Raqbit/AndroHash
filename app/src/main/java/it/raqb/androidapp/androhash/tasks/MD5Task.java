package it.raqb.androidapp.androhash.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

/**
 * Created by ramon on 6-7-17.
 */

public class MD5Task extends AsyncTask<File, Void, String> {

    private OnHashingTaskCompleted listener;

    public MD5Task(OnHashingTaskCompleted listener){
        this.listener = listener;
    }

    public static final String TAG = "MD5-Task";

    @Override
    protected String doInBackground(File... files) {
        return calculateMD5(files[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        listener.onTaskCompleted(TAG, s);
    }

    public static String calculateMD5(File file) {
        HashCode hash;
        try {
             hash = Files.asByteSource(file).hash(Hashing.md5());
        } catch (IOException e) {
            Log.e(TAG, "An error occurred while trying to read and hash the imput file");
            e.printStackTrace();
            return null;
        }
        return hash.toString();
    }
}
