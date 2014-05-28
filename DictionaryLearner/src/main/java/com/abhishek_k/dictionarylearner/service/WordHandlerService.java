package com.abhishek_k.dictionarylearner.service;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.abhishek_k.dictionarylearner.model.QuizQuestion;
import com.abhishek_k.dictionarylearner.model.Word;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WordHandlerService {

    private Handler wordDisplayHandler;
    private Handler wordUpdateHandler;
    private Handler quizQuestionHandler;
    private Callback displayCb;
    private Callback adderCb;
    private QuizCallback quizCb;
    private Context context;
    private static final String LOG_TAG = WordHandlerService.class.getSimpleName();
    private ScheduledExecutorService executorService;
    private static WordHandlerService handlerService;
    private static long rotateMillis = 2000;

    public static WordHandlerService get(Context context) {
        if(handlerService == null) {
            handlerService = new WordHandlerService(context);
        }
        return handlerService;
    }

    private WordHandlerService(Context context) {
        this.context = context;
        startExecutor();
    }

    private void startExecutor() {
        executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                handleRandomWord();
            }
        }, 0, rotateMillis, TimeUnit.MILLISECONDS);
    }

    public void modifyScheduler(long newRotateMillis) {
        rotateMillis = newRotateMillis;
        executorService.shutdownNow();
        startExecutor();
    }

    public void addRotatorHandler(Handler handler) {
        this.wordDisplayHandler = handler;
    }

    public void addAdderHandler(Handler handler) {
        this.wordUpdateHandler = handler;
    }

    public void quizQuestionHandler(Handler handler) {
        this.quizQuestionHandler = handler;
    }

    private void handleRandomWord() {
        final Word word = SQLiteService.get(context).randomWord();
        Log.d(LOG_TAG, "Changing the word now to: " + word.getWord());
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

    public void quizQuestion(final int id) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final QuizQuestion question = SQLiteService.get(context).getQuestion(id);
                quizQuestionHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        quizCb.handleResponse(question);
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

    public void registerQuizCb(QuizCallback cb) {
        this.quizCb = cb;
    }

    public void registerDisplayCb(Callback cb) {
        this.displayCb = cb;
    }

    public void destroy() {
        Log.i(LOG_TAG, "Destroying WordHandlerService");
        handlerService = null;
        executorService.shutdownNow();
    }

    public interface Callback {
        public void handleResponse(Word word);
    }

    public interface QuizCallback {
        public void handleResponse(QuizQuestion question);
    }

}
