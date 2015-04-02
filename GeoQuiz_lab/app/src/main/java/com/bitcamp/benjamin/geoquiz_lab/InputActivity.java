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
import android.widget.EditText;
import android.widget.TextView;


public class InputActivity extends ActionBarActivity {

    private final String TAG = "QuizActivity";

    private EditText mUsernameInput;
    private Button mSaveButton;
    private Button mCancelButton;

    private String mUsername = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        mUsernameInput = (EditText) findViewById(R.id.name_input);
        mSaveButton = (Button) findViewById(R.id.save_button);
        mCancelButton = (Button) findViewById(R.id.cancel_button);

        mUsername = getIntent().getStringExtra(QuizActivity.INTENT_KEY_USERNAME);
        if(mUsername != null){
            mUsernameInput.setText(mUsername);
        }

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnInput = new Intent(InputActivity.this, QuizActivity.class);
                returnInput.putExtra(QuizActivity.INTENT_KEY_USERNAME, mUsernameInput.getText().toString());
                setResult(RESULT_OK, returnInput);
                finish();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnInput = new Intent(InputActivity.this, QuizActivity.class);
               setResult(RESULT_CANCELED, returnInput);
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_name, menu);
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
