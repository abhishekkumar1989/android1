package com.abhishek_k.dictionarylearner;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek_k.dictionarylearner.model.QuizQuestion;
import com.abhishek_k.dictionarylearner.service.WordHandlerService;

public class QuizFragment extends Fragment {

    private TextView questionText;
    private RadioGroup optionsRadio;
    private Button submitAnswerButton;
    private Button nextQuestionButton;
    private QuizQuestion question;
    private int currentIndex = 0;
    private WordHandlerService handlerService;
    private static final String LOG_TAG = QuizFragment.class.getSimpleName();
    private static final String CURRENT_QUES_INDEX = "current_quiz_index";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(CURRENT_QUES_INDEX, 0);
        }
        getActivity().setTitle(R.string.play_quiz);
        Log.d(LOG_TAG, "onCreate() currentIndex is: " + currentIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        questionText = (TextView) view.findViewById(R.id.quiz_question_text);
        optionsRadio = (RadioGroup) view.findViewById(R.id.quiz_question_radioOptions);
        optionsRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (question != null) {
                    submitAnswerButton.setEnabled(true);
                }
            }
        });
        submitAnswerButton = (Button) view.findViewById(R.id.quiz_submit_answer_button);
        submitAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = optionsRadio.getCheckedRadioButtonId();
                RadioButton checkedButton = (RadioButton) optionsRadio.findViewById(checkedRadioButtonId);
                String userAnswer = checkedButton.getText().toString();
                if (userAnswer.equals(question.getAnswer())) {
                    Toast.makeText(getActivity(), "Correct :)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Failed, Correct answer is: " + question.getAnswer(), Toast.LENGTH_SHORT).show();
                }
                getAndSetNextQuestion();
            }
        });
        handlerService = WordHandlerService.get(getActivity());
        handlerService.quizQuestionHandler(new Handler());
        handlerService.registerQuizCb(new WordHandlerService.QuizCallback() {
            @Override
            public void handleResponse(QuizQuestion question) {
                QuizFragment.this.question = question;
                setQuiz();
            }
        });
        nextQuestionButton = (Button) view.findViewById(R.id.quiz_next_question_button);
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndSetNextQuestion();
            }
        });
        handlerService.quizQuestion(currentIndex);

        if (NavUtils.getParentActivityName(getActivity()) != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        return view;
    }

    private void getAndSetNextQuestion() {
        Log.d(LOG_TAG, "Get and set next question, currentIndex: " + currentIndex);
        optionsRadio.clearCheck();
        submitAnswerButton.setEnabled(false);
        if (question != null) {
            int currentQuestionId = question.getQuestionId();
            handlerService.quizQuestion(currentQuestionId);
        }
    }

    private void setQuiz() {
        if (question == null) {
            Log.d(LOG_TAG, "Setting question: null");
            Toast.makeText(getActivity(), "You completed the quiz", Toast.LENGTH_LONG).show();
            nextQuestionButton.setEnabled(false);
            Intent intent = new Intent(getActivity(), QuizCompletionActivity.class);
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Setting question: " + question.getQuestionId());
            currentIndex = question.getQuestionId();
            questionText.setText(question.getQuestion());
            ((RadioButton) optionsRadio.getChildAt(0)).setText(question.getOption1());
            ((RadioButton) optionsRadio.getChildAt(1)).setText(question.getOption2());
            ((RadioButton) optionsRadio.getChildAt(2)).setText(question.getOption3());
            ((RadioButton) optionsRadio.getChildAt(3)).setText(question.getOption4());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(LOG_TAG, "onSaveInstanceState() called");
        outState.putInt(CURRENT_QUES_INDEX, currentIndex);
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
