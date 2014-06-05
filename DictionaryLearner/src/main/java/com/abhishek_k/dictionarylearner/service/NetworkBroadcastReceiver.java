package com.abhishek_k.dictionarylearner.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;

import com.abhishek_k.dictionarylearner.model.QuizQuestion;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkBroadcastReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = NetworkBroadcastReceiver.class.getSimpleName();

    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if (!action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Log.d(LOG_TAG, "Received broadcast for action: " + action);
            return;
        }

        boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        if (noConnectivity) {
            Log.d(LOG_TAG, "Connectivity is lost...");
            return;
        }
        Log.i(LOG_TAG, "Network is available to use");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // TODO: too many logs
                    QuizQuestion question = SQLiteService.get(context).getLastQuizQuestion();
                    URL url = new URL("http://talk.to/api/history/status?version=" + Build.VERSION.SDK_INT + "&_id=" + question.getQuestionId());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    InputStream inputStream = connection.getInputStream();
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Log.e(LOG_TAG, "Network called failed with non 200 code: " + connection.getResponseCode());
                        return;
                    }
                    int bytesRead;
                    byte[] buffer = new byte[1024];
                    while ((bytesRead = inputStream.read(buffer)) > 0) {
                        os.write(buffer, 0, bytesRead);
                    }
                    os.close();
                    Log.i(LOG_TAG, "Received: " + os.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

}
