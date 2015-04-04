package com.bitcamp.benjamin.resultactivities_prep;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class ControlsActivity extends ActionBarActivity {

    private int REQUEST_CAMERA = 5;
    private static int REQUEST_ALARM = 7;

    private Button mCameraButton;
    private Button mAlarmButton;

    private ImageView mPhotoView;
    private TimePicker mTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);

        mCameraButton = (Button) findViewById(R.id.photo_button);
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                }
            }
        });

        mPhotoView = (ImageView) findViewById(R.id.photo_view);

        mTimePicker = (TimePicker) findViewById(R.id.alarm_time);
        mTimePicker.setIs24HourView(true);

        mAlarmButton = (Button) findViewById(R.id.alarm_button);
        mAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar alarmTime = Calendar.getInstance();
                alarmTime.set(Calendar.HOUR_OF_DAY, mTimePicker.getCurrentHour());
                alarmTime.set(Calendar.MINUTE, mTimePicker.getCurrentMinute());

                Intent innerIntent = new Intent("bitcamp.preparation.AlarmSet");
                PendingIntent wrapIntent = PendingIntent
                        .getBroadcast(ControlsActivity.this,
                                ControlsActivity.REQUEST_ALARM,
                                innerIntent, 0);

                AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmMgr.set(AlarmManager.RTC, alarmTime.getTimeInMillis(), wrapIntent);
                Toast.makeText(ControlsActivity.this, "Alarm Set", Toast.LENGTH_SHORT).show();
               
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CAMERA && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mPhotoView.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_controls, menu);
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
