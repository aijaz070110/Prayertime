package com.example.dell.prayertime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import com.example.dell.prayertime.farsitel.qiblacompass.util.AutoPlayerForPrayTime;

import java.util.Calendar;

/**
 * Created by dell on 9/15/2015.
 */
public class Alarm extends BroadcastReceiver {
    AutoPlayerForPrayTime  audioAutoPlayerForPrayTime;
    @Override
    public void onReceive(Context context, Intent intent) {

        // For our recurring task, we'll just display a message
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();

        // Call the play sound method here

        audioAutoPlayerForPrayTime  = new AutoPlayerForPrayTime(context);

        audioAutoPlayerForPrayTime.playAudio("azan.mp3");

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();


    }


    }
