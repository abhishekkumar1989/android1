package com.abhishek_k.testapp2.app1.async;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.abhishek_k.testapp2.app1.MainActivity;

import java.util.Arrays;

public class NetworkCall extends AsyncTask<String, Integer, String> {

    private MainActivity activity;

    public NetworkCall(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public String doInBackground(String... urls) {
        for (String url : urls) {
//            try {
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpResponse response = httpClient.execute(new HttpGet(url));
//                StatusLine statusLine = response.getStatusLine();
//                Log.d("network-call", "Connections Successful: " + statusLine.getStatusCode());
//                return (statusLine.getStatusCode() == HttpStatus.SC_OK) ? "200" : "OTHER";
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return (false) ? "200" : "OTHER";
        }
        return "Failed";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d("network-call", "On Progress Update: : " + Arrays.toString(values));
        Toast.makeText(activity, values.toString(), Toast.LENGTH_LONG).show();
        //  invoked on the UI thread
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("network-call", "Post execute string: " + s);
        Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
        super.onPostExecute(s);
//        activity.runOnUiThread();
    }

}
