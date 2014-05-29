package com.abhishek_k.dictionarylearner;

import android.app.Fragment;

public class QuizCompletionActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new QuizCompletionFragment();
    }

}
