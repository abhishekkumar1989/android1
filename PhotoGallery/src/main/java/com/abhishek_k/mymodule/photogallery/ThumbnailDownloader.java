package com.abhishek_k.mymodule.photogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThumbnailDownloader<T> extends HandlerThread {

    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    private Callback<T> cb;
    private Handler mHandler;
    private Handler mResponseHandler;
    private Map<T, String> requestMap = Collections.synchronizedMap(new HashMap<T, String>());
    private Map<String, Bitmap> imageCache;

    public ThumbnailDownloader(Handler responseHandler) {
        super(TAG);
        imageCache = new HashMap<String, Bitmap>();
        mResponseHandler = responseHandler;
    }

    public void setListener(Callback<T> cb) {
        this.cb = cb;
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    T token = (T) msg.obj;
                    Log.i(TAG, "Got a request for url: " + requestMap.get(token));
                    handleRequest(token);
                }
            }
        };
    }

    private void handleRequest(final T token) {
        try {
            final String url = requestMap.get(token);
            if (url == null)
                return;
            final Bitmap bitmap;
            if (imageCache.containsKey(url)) {
                Log.d(TAG, "Fetching image from cache for url: " + url);
                bitmap = imageCache.get(url);
            } else {
                byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
                bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
                imageCache.put(url, bitmap);
                Log.i(TAG, "Bitmap fetched for url: " + url);
            }
            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (requestMap.get(token) != url) return;
                    requestMap.remove(token);
                    cb.handleResponse(token, bitmap);
                }
            });
        } catch (IOException ioe) {
            Log.e(TAG, "Error downloading image", ioe);
        }
    }

    public void queueThumbnail(T token, String url) {
        Log.i(TAG, "Got an URL: " + url);
        requestMap.put(token, url);
        mHandler.obtainMessage(MESSAGE_DOWNLOAD, token).sendToTarget();
    }

    public interface Callback<T> {
        public void handleResponse(T token, Bitmap thumbnail);
    }

    public void clearQueue() {
        mHandler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }

}
