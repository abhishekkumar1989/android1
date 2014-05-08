package com.abhishek_k.testapp2.app1.dic1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhishek_k.testapp2.app1.R;
import com.abhishek_k.testapp2.app1.dictionary.Word;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static com.abhishek_k.testapp2.app1.dic1.WordAdderFragment.WordAdderListener;
import static com.abhishek_k.testapp2.app1.helper.AppConstant.Dic1.FIRST_DELAY_TIME;
import static com.abhishek_k.testapp2.app1.helper.AppConstant.Dic1.WORDS_DIFF_TIME;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class WordDisplayFragment extends Fragment {

    private WordDisplayListener displayListener;
    private ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);
    private TextView keyText;
    private TextView meaningText;

    private void startExecutor() {
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                showNewWord();
            }
        }, FIRST_DELAY_TIME, WORDS_DIFF_TIME, MILLISECONDS);
    }

    private void showNewWord() {
        final Word word = displayListener.getRandomWord();

        if (word == null) {
            Log.d(WordDisplayFragment.class.getName(), "Nothing to display");
            keyText.post(new Runnable() {
                @Override
                public void run() {
                    keyText.setText("No words added, add new words :)");
                }
            });
            return;
        }

        Log.d(WordDisplayFragment.class.getName(), "Attempting for changing the view to, " + word.toString());
        keyText.post(new Runnable() {
            @Override
            public void run() {
                keyText.setText(word.getWord());
                meaningText.setText(word.getMeaning());
            }
        });
        Log.d(WordDisplayFragment.class.getName(), "Changed the display view, " + word.toString());
    }

    public static WordDisplayFragment init() {
        WordDisplayFragment fragment = new WordDisplayFragment();
        fragment.startExecutor();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WordDisplayFragment fragment = (WordDisplayFragment) getFragmentManager().getFragments().get(0);
        View viewLayout = inflater.inflate(R.layout.display_words, container, false);
        fragment.keyText = (TextView) viewLayout.findViewById(R.id.keytext);
        fragment.meaningText = (TextView) viewLayout.findViewById(R.id.meaningtext);
        return viewLayout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof WordAdderListener)
            displayListener = (WordDisplayListener) activity;
        else
            throw new ClassCastException(activity.toString() + " must implement WordDisplayFragment.WordDisplayListener");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        displayListener = null;
    }

    protected interface WordDisplayListener {
        public Word getRandomWord();
    }

}
