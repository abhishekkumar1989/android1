package com.abhishek_k.nerdlauncher;

import android.app.Fragment;


public class NerdLauncherActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new NerdLauncherFragment();
    }
}
