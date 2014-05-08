package com.abhishek_k.testapp2.app1.dic1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abhishek_k.testapp2.app1.R;
import com.abhishek_k.testapp2.app1.dictionary.Word;

public class WordAdderFragment extends Fragment {

    private WordAdderListener adderListener;

    public static WordAdderFragment init() {
        return new WordAdderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layoutView = inflater.inflate(R.layout.addword, container, false);
        Button addButton = (Button) layoutView.findViewById(R.id.addword);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWord();
            }
        });
        return layoutView;
    }

    public void addWord() {
        FragmentActivity activity = getActivity();
        EditText keyView = (EditText) activity.findViewById(R.id.key);
        EditText meaningView = (EditText) activity.findViewById(R.id.meaning);

        String key = keyView.getText().toString();
        String meaning = meaningView.getText().toString();

        if (key.equals("") || meaning.equals("")) {
            Toast.makeText(activity, "Fields can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Word insertedWord = adderListener.onWordAdd(new Word(key, meaning));
        keyView.setText("");
        meaningView.setText("");
        if (insertedWord == null) {
            Toast.makeText(activity, "word already exist [ " + key + " ]", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "Word inserted [ " + insertedWord.toString() + " ]", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof WordAdderListener)
            adderListener = (WordAdderListener) activity;
        else
            throw new ClassCastException(activity.toString() + " must implement WordAdderFragment.WordAdderListener");
    }

    protected interface WordAdderListener {
        public Word onWordAdd(Word word);
    }

}
