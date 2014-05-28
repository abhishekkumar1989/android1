package com.abhishek_k.dictionarylearner;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class QuizCompletionFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.quiz_completed_message);
        if(NavUtils.getParentActivityName(getActivity()) != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return inflater.inflate(R.layout.fragment_quiz_completion, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateHome() {
        if (NavUtils.getParentActivityName(getActivity()) != null) {
            NavUtils.navigateUpFromSameTask(getActivity());
        }
    }

}
