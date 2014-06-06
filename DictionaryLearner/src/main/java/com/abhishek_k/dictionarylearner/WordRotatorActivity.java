package com.abhishek_k.dictionarylearner;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;


public class WordRotatorActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getIntent().hasExtra("quizCompletedNotify")) {
            Intent intent = new Intent(this, QuizCompletionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("report", getIntent().getSerializableExtra("report"));
            intent.putExtras(bundle);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public Fragment createFragment() {
        return new WordsRotatorFragment();
    }

}
