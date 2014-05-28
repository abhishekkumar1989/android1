package com.abhishek_k.dictionarylearner.model;

public class QuizQuestion {

    public static final String QUESTION_ID =  "_id";
    public static final String QUESTION_KEY = "question";
    public static final String ANSWER_OPTION1_KEY = "option1";
    public static final String ANSWER_OPTION2_KEY = "option2";
    public static final String ANSWER_OPTION3_KEY = "option3";
    public static final String ANSWER_OPTION4_KEY = "option4";
    public static final String ANSWER_KEY = "answer";

    private int questionId;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;

    public int getQuestionId() {
        return questionId;
    }

    public static QuizQuestion builder() {
        return new QuizQuestion();
    }

    public QuizQuestion id(int questionId) {
        this.questionId = questionId;
        return this;
    }

    public String getQuestion() {
        return question;
    }

    public QuizQuestion question(String question) {
        this.question = question;
        return this;
    }

    public String getOption1() {
        return option1;
    }

    public QuizQuestion option1(String option1) {
        this.option1 = option1;
        return this;
    }

    public String getOption2() {
        return option2;
    }

    public QuizQuestion option2(String option2) {
        this.option2 = option2;
        return this;
    }

    public String getOption3() {
        return option3;
    }

    public QuizQuestion option3(String option3) {
        this.option3 = option3;
        return this;
    }

    public String getOption4() {
        return option4;
    }

    public QuizQuestion option4(String option4) {
        this.option4 = option4;
        return this;
    }

    public String getAnswer() {
        return answer;
    }

    public QuizQuestion answer(String answer) {
        this.answer = answer;
        return this;
    }

}
