package com.abhishek_k.mymodule.photogallery;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThumbnailDownloader<T> extends HandlerThread {

    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    Handler mHandler;
    Map<T, String> requestMap = Collections.synchronizedMap(new HashMap<T, String>());

    public ThumbnailDownloader() {
        super(TAG);
    }

    public void queueThumbnail(T token, String url) {
        Log.i(TAG, "Got an URL: " + url);
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    T token = (T) msg.obj;
                    Log.i(TAG, "Got a request for url: " + requestMap.get(token));
//                    handleRequest(token);
                }
            }
        };
    }


}
