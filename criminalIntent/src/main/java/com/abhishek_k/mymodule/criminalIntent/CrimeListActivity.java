package com.abhishek_k.mymodule.criminalIntent;

import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity {

    private static final String TAG = "CrimeListActivity";

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

}
