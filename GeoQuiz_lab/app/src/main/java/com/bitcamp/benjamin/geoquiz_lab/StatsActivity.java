package com.bitcamp.benjamin.geoquiz_lab;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;


public class StatsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent quizActivity = getIntent();

        int answeredQuestions = quizActivity.getIntExtra(QuizActivity.INTENT_KEY_CURRENT_QUESTION, 0);
        int correctAnswers = quizActivity.getIntExtra(QuizActivity.INTENT_KEY_CORRECT_ANSWERS, 0);
        int totalQuestions = quizActivity.getIntExtra(QuizActivity.INTENT_KEY_TOTAL_QUESTIONS, 0);

        int leftToAnswer = totalQuestions - answeredQuestions;
        double correctPercentage;
        correctPercentage = answeredQuestions > 0 ? (double)(correctAnswers)/answeredQuestions : 0;
        correctPercentage *= 100;


        TextView intentMessage = (TextView) findViewById(R.id.questions_left_text);
        intentMessage.setText(String.format("You have %d questions left. %.2f %% of your answers are correct", leftToAnswer, correctPercentage));

        ProgressBar questionProgress = (ProgressBar) findViewById(R.id.question_progress_bar);
        questionProgress.setMax(totalQuestions);
        questionProgress.setProgress(answeredQuestions);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
