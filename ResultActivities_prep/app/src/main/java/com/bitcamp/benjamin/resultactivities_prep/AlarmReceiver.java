package com.bitcamp.benjamin.resultactivities_prep;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startMain = new Intent(context, ControlsActivity.class);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startMain);
        Toast.makeText(context, new Date().toString(), Toast.LENGTH_LONG).show();
    }
}
