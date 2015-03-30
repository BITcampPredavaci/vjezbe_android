package com.bitcamp.benjamin.geoquiz_lab;

/**
 * Created by benjamin on 30/03/15.
 */
public class Question {

    private int mQuestionId;
    private boolean mAnswer;

    public Question(int questionId, boolean answer) {
        mQuestionId = questionId;
        mAnswer = answer;
    }

    public int getQuestionId() {
        return mQuestionId;
    }

    public boolean isAnswer() {
        return mAnswer;
    }
}
