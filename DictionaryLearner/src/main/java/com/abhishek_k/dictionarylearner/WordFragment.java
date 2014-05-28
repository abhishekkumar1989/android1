package com.abhishek_k.dictionarylearner;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abhishek_k.dictionarylearner.model.Word;
import com.abhishek_k.dictionarylearner.service.WordHandlerService;

public class WordFragment extends Fragment {

    private EditText wordText;
    private EditText meaningText;
    private Button updateWordButton;
    private Button deleteWordButton;
    private Word word = new Word();
    private static final String LOG_TAG = "WordFragment";
    public static final String EXTRA_WORDKEY = "com.abhishek_k.dictionarylearner.wordkey";
    public static final String EXTRA_WORD = "com.abhishek_k.dictionarylearner.word";
    public static final String EXTRA_MEANINGKEY = "com.abhishek_k.dictionarylearner.meaningkey";
    private WordHandlerService handlerService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreate() called");
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            word = (Word) arguments.getSerializable(EXTRA_WORD);
        }
    }

    @Deprecated
    public WordFragment newInstance(String wordName) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_WORDKEY, wordName);
        WordFragment fragment = new WordFragment();
        word.setWord(wordName);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static WordFragment newInstance(Word word) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_WORD, word);
        WordFragment fragment = new WordFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        wordText = (EditText) view.findViewById(R.id.word_name);
        meaningText = (EditText) view.findViewById(R.id.word_meaning);
        updateWordButton = (Button) view.findViewById(R.id.word_updateButton);
        deleteWordButton = (Button) view.findViewById(R.id.word_deleteButton);
        wordText.setText(word.getWord());
        meaningText.setText(word.getMeaning());
        wordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getActivity().setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        updateWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordName = wordText.getText().toString();
                String wordMeaning = meaningText.getText().toString();
                if (wordName.equals("") || wordMeaning.equals("")) {
                    Toast.makeText(getActivity(), R.string.warning_empty_fields, Toast.LENGTH_SHORT).show();
                    return;
                }
                handlerService.addOrUpdate(new Word(wordName, wordMeaning));
            }
        });

        deleteWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordName = wordText.getText().toString();
                String wordMeaning = meaningText.getText().toString();
                if (wordName.equals("")) {
                    Toast.makeText(getActivity(), R.string.warning_empty_word_to_delete, Toast.LENGTH_SHORT).show();
                    return;
                }
                handlerService.remove(new Word(wordName, wordMeaning));
            }
        });

        handlerService = WordHandlerService.get(getActivity());
        handlerService.addAdderHandler(new Handler());
        handlerService.registerAdderCb(new WordHandlerService.Callback() {
            @Override
            public void handleResponse(Word word) {
                if (word == null) {
                    Toast.makeText(getActivity(), R.string.failed_activity_message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), R.string.success_activity_message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        setHasOptionsMenu(true);
        if(NavUtils.getParentActivityName(getActivity()) != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
