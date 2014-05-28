package com.abhishek_k.dictionarylearner;

import android.app.Fragment;

public class QuizActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new QuizFragment();
    }
}
