package com.abhishek_k.dictionarylearner;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhishek_k.dictionarylearner.model.Report;

import static java.lang.String.valueOf;

public class QuizCompletionFragment extends Fragment {

    private Report report;
    private TextView userMessageView;
    private TextView totalAnsweredView;
    private TextView totalWrongAnsweredView;
    private TextView totalUnAnsweredView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        report = (Report) getActivity().getIntent().getSerializableExtra("report");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_completion, container, false);

        userMessageView = (TextView) view.findViewById(R.id.user_message);
        totalAnsweredView = (TextView) view.findViewById(R.id.user_answered_text);
        totalWrongAnsweredView = (TextView) view.findViewById(R.id.user_wrongly_answered_text);
        totalUnAnsweredView = (TextView) view.findViewById(R.id.user_unanswered_text);

        if(report != null) {
            userMessageView.setVisibility(View.VISIBLE);
            totalAnsweredView.setVisibility(View.VISIBLE);
            totalWrongAnsweredView.setVisibility(View.VISIBLE);
            totalUnAnsweredView.setVisibility(View.VISIBLE);

            totalAnsweredView.append(valueOf(report.getCorrect()));
            totalWrongAnsweredView.append(valueOf(report.getWrong()));
            totalUnAnsweredView.append(valueOf(report.getUnAnswered()));
        }

        getActivity().setTitle(R.string.quiz_completed_message);
        if(NavUtils.getParentActivityName(getActivity()) != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return view;
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
