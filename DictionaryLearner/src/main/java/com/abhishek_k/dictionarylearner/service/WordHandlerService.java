package com.abhishek_k.dictionarylearner.service;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.abhishek_k.dictionarylearner.model.Word;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WordHandlerService {

    private Handler wordDisplayHandler;
    private Handler wordUpdateHandler;
    private Callback displayCb;
    private Callback adderCb;
    private Context context;
    private static final String LOG_TAG = WordHandlerService.class.getSimpleName();
    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
    private static WordHandlerService handlerService;

    public static WordHandlerService get(Context context) {
        if(handlerService == null) {
            handlerService = new WordHandlerService(context);
        }
        return handlerService;
    }

    private WordHandlerService(Context context) {
        this.context = context;
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "Changing the word now...");
                handleRandomWord();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    public void addRotatorHandler(Handler handler) {
        this.wordDisplayHandler = handler;
    }

    public void addAdderHandler(Handler handler) {
        this.wordUpdateHandler = handler;
    }

    private void handleRandomWord() {
        final Word word = SQLiteService.get(context).randomWord();
        wordDisplayHandler.post(new Runnable() {
            @Override
            public void run() {
                displayCb.handleResponse(word);
            }
        });
    }

    public void addOrUpdate(final Word word) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final boolean isInserted = SQLiteService.get(context).addOrUpdate(word);
                wordUpdateHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adderCb.handleResponse(isInserted ? word : null);
                    }
                });
            }
        });
    }

    public void remove(final Word word) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final boolean isDeleted = SQLiteService.get(context).deleteWord(word.getWord());
                wordUpdateHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adderCb.handleResponse(isDeleted ? word : null);
                    }
                });
            }
        });
    }

    public void registerAdderCb(Callback cb) {
        this.adderCb = cb;
    }

    public void registerDisplayCb(Callback cb) {
        this.displayCb = cb;
    }

    public void destroy() {
        executorService.shutdownNow();
    }

    public interface Callback {
        public void handleResponse(Word word);
    }

}
