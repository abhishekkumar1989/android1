package com.abhishek_k.dictionarylearner;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

public abstract class SingleFragmentActivity extends Activity {

    private static final String LOG_TAG = SingleFragmentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate() called");
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, createFragment())
                    .commit();

        }
    }

    public abstract Fragment createFragment();

}
