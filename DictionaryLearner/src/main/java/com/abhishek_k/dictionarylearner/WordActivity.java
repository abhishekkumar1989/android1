package com.abhishek_k.dictionarylearner;

import android.app.Fragment;

import com.abhishek_k.dictionarylearner.model.Word;

public class WordActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        String word = (String) getIntent().getSerializableExtra(WordFragment.EXTRA_WORDKEY);
        String meaning = (String) getIntent().getSerializableExtra(WordFragment.EXTRA_MEANINGKEY);
        return WordFragment.newInstance(new Word(word, meaning));
    }
}
