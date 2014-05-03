package com.abhishek_k.testapp2.app1;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProgressTestActivity extends Activity {
    private ProgressBar progress;
    private TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);
        progress = (ProgressBar) findViewById(R.id.progressBar1);
        text = (TextView) findViewById(R.id.textView1);
    }

    public void startProgress(View view) {

//        Handler handler = new Handler();
//        handler.hasMessages()

        Handler handler = getWindow().getDecorView().getHandler();

        // do something long
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    final int value = i;
                    doFakeWork();
                    text.post(new Runnable() {
                        @Override
                        public void run() {
                            text.setText("Updating");
                            progress.setProgress(value);
                            if (value == 10) {
                                showCompleteToaster();
                                text.setText("");
                            }
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }

    private void showCompleteToaster() {
        Toast.makeText(this, "Update Complete", Toast.LENGTH_LONG).show();
    }

    // Simulating something timeconsuming
    private void doFakeWork() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}