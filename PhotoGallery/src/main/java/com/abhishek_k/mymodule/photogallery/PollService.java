package com.abhishek_k.mymodule.photogallery;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class PollService extends IntentService {

    private static final String TAG = PollService.class.getSimpleName();

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Received an intent: " + intent);
    }



}
