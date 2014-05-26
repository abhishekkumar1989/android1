package com.abhishek_k.dictionarylearner;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhishek_k.dictionarylearner.model.Word;
import com.abhishek_k.dictionarylearner.service.WordHandlerService;

public class WordsRotatorFragment extends Fragment {

    private TextView wordView;
    private TextView meaningView;
    private WordHandlerService handlerThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_rotator, container, false);
        wordView = (TextView) view.findViewById(R.id.word_viewer);
        meaningView = (TextView) view.findViewById(R.id.meaning_viewer);
        wordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        handlerThread = WordHandlerService.get(getActivity());
        handlerThread.addRotatorHandler(new Handler());
        handlerThread.registerDisplayCb(new WordHandlerService.Callback() {
            @Override
            public void handleResponse(Word word) {
                updateWord(word);
            }
        });
        wordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WordActivity.class);
                intent.putExtra(WordFragment.EXTRA_WORDKEY, wordView.getText().toString());
                intent.putExtra(WordFragment.EXTRA_MEANINGKEY, meaningView.getText().toString());
                startActivity(intent);
            }
        });
        return view;
    }

    private void updateWord(Word word) {
        if (word == null) {
            wordView.setText("Nothing to display");
            return;
        }
        wordView.setText(word.getWord());
        meaningView.setText(word.getMeaning());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerThread.destroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dictionary, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_word:
                Intent intent = new Intent(getActivity(), WordActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
