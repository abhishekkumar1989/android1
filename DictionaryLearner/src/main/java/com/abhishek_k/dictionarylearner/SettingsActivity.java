package com.abhishek_k.dictionarylearner;

import android.app.Fragment;

public class SettingsActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new SettingsFragment();
    }

}
