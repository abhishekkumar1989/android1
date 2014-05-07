package com.abhishek_k.testapp2.app1.dictionary;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhishek_k.testapp2.app1.R;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DisplayWordFragment extends Fragment {

    private DictionarySQLHelper sqlHelper;
    private ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);
    private TextView keyText;
    private TextView meaningText;

    private void startExecutor() {
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                changeView();
            }
        }, 1000, 4000, TimeUnit.MILLISECONDS);
    }

    private void changeView() {
        final Word word = sqlHelper.readWord();
        if (word != null) {
            Log.d(DisplayWordFragment.class.getName(), "Attempting for changing the view to, " + word.toString());
            if (keyText != null && meaningText != null) {
                keyText.post(new Runnable() {
                    @Override
                    public void run() {
                        keyText.setText(word.getWord());
                        meaningText.setText(word.getMeaning());
                    }
                });
                Log.d(DisplayWordFragment.class.getName(), "Changed the display view, " + word.toString());
            }
        } else {
            Log.d(DisplayWordFragment.class.getName(), "Nothing to display");
            if (keyText != null) {
                keyText.post(new Runnable() {
                    @Override
                    public void run() {
                        keyText.setText("No words added, add new words :)");
                    }
                });
            }
        }
    }

    public static DisplayWordFragment init() {
        DisplayWordFragment fragment = new DisplayWordFragment();
        fragment.startExecutor();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DisplayWordFragment fragment = (DisplayWordFragment) getFragmentManager().getFragments().get(0);
        View viewLayout = inflater.inflate(R.layout.display_words, container, false);
        fragment.keyText = (TextView) viewLayout.findViewById(R.id.keytext);
        fragment.meaningText = (TextView) viewLayout.findViewById(R.id.meaningtext);
        Context context = viewLayout.getContext();
        sqlHelper = new DictionarySQLHelper(context);
        View tv = viewLayout.findViewById(R.id.text);
        ((TextView) tv).setText("Displaying keyword");
        return viewLayout;
    }

}
