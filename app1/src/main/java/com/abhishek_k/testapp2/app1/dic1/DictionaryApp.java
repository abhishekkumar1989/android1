package com.abhishek_k.testapp2.app1.dic1;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.abhishek_k.testapp2.app1.R;
import com.abhishek_k.testapp2.app1.dictionary.DictionarySQLHelper;
import com.abhishek_k.testapp2.app1.dictionary.Word;

import static com.abhishek_k.testapp2.app1.dic1.WordAdderFragment.WordAdderListener;
import static com.abhishek_k.testapp2.app1.dic1.WordDisplayFragment.WordDisplayListener;

public class DictionaryApp extends FragmentActivity implements WordAdderListener, WordDisplayListener {

    private static final int NUM_TABS = 2;
    private ViewPager pager;
    private DictionarySQLHelper sqlHelper;

    @TargetApi(11)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);
        sqlHelper = new DictionarySQLHelper(this);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            LayoutTransition lt = new LayoutTransition();
            lt.enableTransitionType(LayoutTransition.CHANGING);
            pager.setLayoutTransition(lt);
        }
        pager.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.View);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });
        button = (Button) findViewById(R.id.Add);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pager.setCurrentItem(NUM_TABS - 1);
            }
        });
//        button = (Button) findViewById(R.id.change_fragment);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.add(WordDisplayFragment.init(), "display1");
//                ft.commit();
//                fm.findFragmentByTag("display1");
//            }
//        });
    }

    @Override
    public Word onWordAdd(Word word) {
        return sqlHelper.addWord(word);
    }

    @Override
    public Word getRandomWord() {
        return sqlHelper.readWord();
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_TABS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return WordDisplayFragment.init();
                case 1:
                    return WordAdderFragment.init();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqlHelper.close();
    }
}
