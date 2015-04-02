package com.bitcamp.benjamin.geoquiz_lab;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends ActionBarActivity {

    private final String TAG = "QuizActivity";

    public static final String INTENT_KEY_CORRECT_ANSWERS = "bitcamp.geoquiz.correct_answers";
    public static final String INTENT_KEY_TOTAL_QUESTIONS = "bitcamp.geqoquiz.total_questions";
    public static final String INTENT_KEY_CURRENT_QUESTION = "bitcamp.geoquiz.current_question";
    public static final String INTENT_KEY_USERNAME = "bitcamp.geoquiz.username";

    private static final String STATE_CURRENT_INDEX = "bitcamp.geoquiz.current_index";
    private static final String STATE_CORRECT_ANSWERS = "bitcamp.geoquiz.correct_answers";
    private static final int INPUT_REQUEST_CODE = 200;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mStatsButton;
    private Button mSetNameButton;


    private TextView mQuestionText;


    private int mCurrentIndex = 0;
    private int mCorrectAnswers = 0;
    private Question[] mQuestions = new Question[]{
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, false),
            new Question(R.string.question_3, true)
    };

    private String mUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(STATE_CURRENT_INDEX);
            mCorrectAnswers = savedInstanceState.getInt(STATE_CORRECT_ANSWERS);
        }

        Log.d(TAG, "onCreate started");


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mStatsButton = (Button) findViewById(R.id.stats_button);
        mStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statsIntent = new Intent(QuizActivity.this, StatsActivity.class);
                statsIntent.putExtra(INTENT_KEY_TOTAL_QUESTIONS, mQuestions.length);
                statsIntent.putExtra(INTENT_KEY_CORRECT_ANSWERS, mCorrectAnswers);
                statsIntent.putExtra(INTENT_KEY_CURRENT_QUESTION, mCurrentIndex+1);
                startActivity(statsIntent);
            }
        });

        mSetNameButton = (Button) findViewById(R.id.set_name_button);
        mSetNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inputIntent = new Intent(QuizActivity.this, InputActivity.class);
                inputIntent.putExtra(INTENT_KEY_USERNAME, mUsername);
                startActivityForResult(inputIntent, INPUT_REQUEST_CODE);
            }
        });

        mQuestionText = (TextView) findViewById(R.id.question_text);
        updateQuestionText();
    }

    private void checkAnswer(boolean userChoice) {
        int toastText;

        if (userChoice == mQuestions[mCurrentIndex].isAnswer()) {
            toastText = R.string.correct;
            mCorrectAnswers++;
        } else {
            toastText = R.string.incorrect;
        }
        mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
        updateQuestionText();
        Toast.makeText(QuizActivity.this, toastText, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestionText() {

        if(mCurrentIndex == 0)
            mCorrectAnswers = 0;
        String questionText;
        if(mUsername != null)
            questionText = mUsername +", " + getString(mQuestions[mCurrentIndex].getQuestionId());
        else
            questionText = getString(mQuestions[mCurrentIndex].getQuestionId());
        mQuestionText.setText(questionText);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Activity result " + requestCode + " " + resultCode);
        if(requestCode == INPUT_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                mUsername = data.getStringExtra(INTENT_KEY_USERNAME);
                updateQuestionText();
            }
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause started");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume started");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy started");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outputState) {
        super.onSaveInstanceState(outputState);
        outputState.putInt(STATE_CURRENT_INDEX, mCurrentIndex);
        outputState.putInt(STATE_CORRECT_ANSWERS, mCorrectAnswers);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle inputState){
        super.onRestoreInstanceState(inputState);
        mCurrentIndex = inputState.getInt(STATE_CURRENT_INDEX);
        mCorrectAnswers = inputState.getInt(STATE_CORRECT_ANSWERS);
        Log.i(TAG, "onRestoreInstanceState");
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
