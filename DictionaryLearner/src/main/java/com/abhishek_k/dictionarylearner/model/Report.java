package com.abhishek_k.dictionarylearner.model;

import java.io.Serializable;

public class Report implements Serializable{

    private String userName;
    private int correct, wrong, unAnswered;

    public Report(String userName) {
        this.userName = userName;
    }

    public void incAnswered() {
        correct++;
    }

    public void incWrong() {
        wrong++;
    }

    public void incUnAnswered() {
        unAnswered++;
    }

    public String getUserName() {
        return userName;
    }

    public int getCorrect() {
        return correct;
    }

    public int getWrong() {
        return wrong;
    }

    public int getUnAnswered() {
        return unAnswered;
    }
}
