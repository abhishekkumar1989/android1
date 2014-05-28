package com.abhishek_k.dictionarylearner;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
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
    private WordHandlerService handlerService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        questionText = (TextView) view.findViewById(R.id.quiz_question_text);
        optionsRadio = (RadioGroup) view.findViewById(R.id.quiz_question_radioOptions);
        optionsRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                submitAnswerButton.setEnabled(true);
            }
        });
        submitAnswerButton = (Button) view.findViewById(R.id.quiz_submit_answer_button);
        submitAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = optionsRadio.getCheckedRadioButtonId();
                RadioButton checkedButton = (RadioButton) optionsRadio.getChildAt(checkedRadioButtonId - 1);
                String userAnswer = checkedButton.getText().toString();
                if (userAnswer.equals(question.getAnswer())) {
                    Toast.makeText(getActivity(), "Correct :)", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Failed :(", Toast.LENGTH_LONG).show();
                }
//                nextQuestion();
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
        handlerService.quizQuestion(1);
        return view;
    }

    private void setQuiz() {
        if (question == null) {
            Toast.makeText(getActivity(), "Some issue or you completed the quiz", Toast.LENGTH_LONG).show();
            return;
        }
        questionText.setText(question.getQuestion());
        ((RadioButton) optionsRadio.getChildAt(0)).setText(question.getOption1());
        ((RadioButton) optionsRadio.getChildAt(1)).setText(question.getOption2());
        ((RadioButton) optionsRadio.getChildAt(2)).setText(question.getOption3());
        ((RadioButton) optionsRadio.getChildAt(3)).setText(question.getOption4());
    }


}
