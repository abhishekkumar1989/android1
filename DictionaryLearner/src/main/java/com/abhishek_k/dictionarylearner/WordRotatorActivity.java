package com.abhishek_k.dictionarylearner;

import android.app.Fragment;


public class WordRotatorActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new WordsRotatorFragment();
    }

}
