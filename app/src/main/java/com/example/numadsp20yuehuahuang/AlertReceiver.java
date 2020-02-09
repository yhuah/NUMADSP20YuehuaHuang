package com.example.numadsp20yuehuahuang;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
//reference https://www.journaldev.com/27681/android-alarmmanager-broadcast-receiver-and-service
public class AlertReceiver extends BroadcastReceiver {
    private static final String TAG = "AlertReceiver:wakeuptag";

    @Override
    public void onReceive(Context context, Intent intent){

       // PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
       // wl.acquire(10 * 1000L);
        Date currentTime = Calendar.getInstance().getTime();
        Toast.makeText(context, currentTime.toString(), Toast.LENGTH_LONG).show();
        Log.d(TAG, "working");

       // wl.release();

    }
    public void setAlarm(Context context){
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent (context, AlertReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 60,1000*60, pi); // Millisec * Second * Minute
        Toast.makeText(context, "Alarm Started", Toast.LENGTH_LONG).show();


    }
    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(sender);
            Toast.makeText(context, "Alarm Canceled", Toast.LENGTH_LONG).show();
        }
    }





}
