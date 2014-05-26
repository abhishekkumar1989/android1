package com.abhishek_k.dictionarylearner.model;

import java.io.Serializable;

public class Word implements Serializable {

    public static final String WORD_KEY = "_id";
    public static final String MEANING_KEY = "meaning";
    private String word;
    private String meaning;

    public Word() {
    }

    public Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

}
