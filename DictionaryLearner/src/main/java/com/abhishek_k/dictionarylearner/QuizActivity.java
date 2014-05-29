package com.abhishek_k.dictionarylearner;

import android.app.Fragment;

import static com.abhishek_k.dictionarylearner.QuizFragment.EXTRA_USER_NAME;

public class QuizActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        String username = getIntent().getStringExtra(EXTRA_USER_NAME);
        return QuizFragment.newInstance(username);
    }

}
