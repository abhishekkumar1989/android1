package com.abhishek_k.remotecontrol;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Window;


public class RemoteControlActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return new RemoteControlFragment();
    }


}
