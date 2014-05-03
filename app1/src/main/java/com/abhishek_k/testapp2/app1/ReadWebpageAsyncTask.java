package com.abhishek_k.testapp2.app1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadWebpageAsyncTask extends Activity {
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network);
        textView = (TextView) findViewById(R.id.TextView01);
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.readWebpage:
                EditText editText = (EditText) findViewById(R.id.editText);
                DownloadWebPageTask task = new DownloadWebPageTask();
                String text = editText.getText().toString();
                if (!(text.equals(""))) {
                    task.execute(new String[]{text});
                } else {
                    Toast.makeText(this, "Enter some website", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.clearWebpage:
                TextView textView = (TextView) findViewById(R.id.TextView01);
                textView.setText("PlaceHolder");
                break;
        }


    }
}