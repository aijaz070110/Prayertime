package com.example.dell.prayertime;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.prayertime.farsitel.qiblacompass.alaram.RingBellManager;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by dell on 9/6/2015.
 */
public class namaz extends Activity {
    AppCompatAutoCompleteTextView edtcity;
    WebApiAsynTask prayersAsyncTask;
    WebApiAsynTask webApiAsynTask;
    String locationUrl = "http://ip-api.com/json";
    String Url = "http://praytime.info/services.php";
    Context mcontext;
    private PendingIntent pendingIntent;
    MediaPlayer mp=null ;

    String country, city, regionName, countryCode;
    String fajar, Zohar, Asar, Magrib, Isha, Sunrise, Sunset;
    double lat, lng;
    TextView fajarTv, zuharTv, AsarTv, magribTv, IshaTv, dateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namaz);

        RingBellManager.INSTANCE.init(namaz.this, new RingBellManager.RingBellStateListener() {

            @Override
            public void onDissmiss() {

            }

            @Override
            public void onActivate() {
            }
        });


        dateTv = (TextView) findViewById(R.id.dateTv);
        fajarTv = (TextView) findViewById(R.id.fajarTv);
        zuharTv = (TextView) findViewById(R.id.zuharTv);
        AsarTv = (TextView) findViewById(R.id.AsarTv);
        magribTv = (TextView) findViewById(R.id.magribTv);
        IshaTv = (TextView) findViewById(R.id.IshaTv);

        final int FOR_HOURS = 3600000;
        final int FOR_MIN = 60000;

        /* Retrieve a PendingIntent that will perform a broadcast */
//        Intent alarmIntent = new Intent(namaz.this, Alarm.class);
//        pendingIntent = PendingIntent.getBroadcast(namaz.this, 0, alarmIntent, 0);


        ((ImageButton) findViewById(R.id.ring)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                setAlaramTime(fajar,Config.FAJAR_SP, Config.FAJAR_PI);
//                startAt10();
            }
        });
        ((ImageButton) findViewById(R.id.ringzohar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startAlaram();

               // RingBellManager.INSTANCE.sheduleRingBell(namaz.this,12,55,Config.ZUHAR_SP,Config.ZUHAR_PI);
                setAlaramTime(Zohar, Config.ZUHAR_SP, Config.ZUHAR_PI);
//                startAt10();
            }
        });
        ((ImageButton) findViewById(R.id.ringasar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startAlaram();
               // RingBellManager.INSTANCE.sheduleRingBell(namaz.this, 3,28,Config.ASAR_SP,Config.ASAR_PI);
                setAlaramTime(Asar,Config.ASAR_SP , Config.ASAR_PI);
//                startAt10();
            }
        });
        ((ImageButton) findViewById(R.id.ringmagrib)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startAlaram();
                //RingBellManager.INSTANCE.sheduleRingBell(namaz.this, 4,45,Config.MAGRIB_SP,Config.MAGRIB_PI);
                setAlaramTime(Magrib,Config.MAGRIB_SP , Config.MAGRIB_PI);
//                startAt10();
            }
        });
        ((ImageButton) findViewById(R.id.ringisha)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startAlaram();
                //RingBellManager.INSTANCE.sheduleRingBell(namaz.this, 8,28,Config.ISHA_SP,Config.ISHA_PI);
                setAlaramTime(Isha, Config.ISHA_SP, Config.ISHA_PI);
//                startAt10();
            }
        });



        /////
        webApiAsynTask = new WebApiAsynTask(namaz.this, true, locationUrl, null, new JSONResponseCallback() {
            @Override
            public void onCompleteResponse(JSONObject jsonResponse) {
                city = jsonResponse.optString("city");
                country = jsonResponse.optString("country");
                countryCode = jsonResponse.optString("countryCode");
                lat = jsonResponse.optDouble("lat");
                lng = jsonResponse.optDouble("lon");

                int gmtOffset = (TimeZone.getDefault().getRawOffset()) / (1000 * 60);
//                       Toast.makeText(namaz.this, "GMT Value in minutes==>"+gmtOffset, Toast.LENGTH_SHORT).show();
//                       Log.e("GMT Value in minutes==>","GMT Value in minutes==>"+gmtOffset);


                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat Sdf = new SimpleDateFormat("yyyy-MM-dd");
                //SimpleDateFormat Sdf = new SimpleDateFormat("dd-MM-yyyy");
                String currentDate = Sdf.format(calendar.getTime());
                dateTv.setText("Date  " + currentDate);

                Date date = new Date();


                String dateStrFormatted = Sdf.format(date);
                String dateString[] = dateStrFormatted.split("-");
                String year = dateString[0];
                String month = dateString[1];
                final String dayName = dateString[2];


                final String prayerTimeUrl = "http://praytime.info/getprayertimes.php?lat=" + lat + "&lon=" + lng + "&gmt=" + gmtOffset +
                        "&m=" + month + "&y=" + year;
                //http://praytime.info/getprayertimes.php?lat=31.488800&lon=74.368599&gmt=300&m=9&y=2015&school=0

                prayersAsyncTask = new WebApiAsynTask(namaz.this, true, prayerTimeUrl, null, new JSONResponseCallback() {

                    @Override
                    public void onCompleteResponse(JSONObject result) {
                        // Now write parser to parse json result of prayers.


                        JSONObject prayerTimeObj = result.optJSONObject("" + dayName);

                        fajar = prayerTimeObj.optString("Fajr");
                        Zohar = prayerTimeObj.optString("Dhuhr");
                        Asar = prayerTimeObj.optString("Asr");
                        Magrib = prayerTimeObj.optString("Maghrib");
                        Isha = prayerTimeObj.optString("Isha");
                        Sunrise = prayerTimeObj.optString("Sunrise");
                        Sunset = prayerTimeObj.optString("Sunset");

//                               Toast.makeText(namaz.this, "" + prayerTimeObj.toString(), Toast.LENGTH_LONG).show();
                        fajarTv.setText("Fajar" + convertTimeToAmPm(fajar));
                        zuharTv.setText("Zohar" + convertTimeToAmPm(Zohar));
                        AsarTv.setText("Asar" + convertTimeToAmPm(Asar));
                        magribTv.setText("Magrib" + convertTimeToAmPm(Magrib));
                        IshaTv.setText("Isha" + convertTimeToAmPm(Isha));



                    }

                });
                prayersAsyncTask.execute();

//

            }
        });
        webApiAsynTask.execute();
        //////
    }


    public String convertTimeToAmPm(String inputTimeStr) {
        DateFormat df = new SimpleDateFormat("HH:mm ");
        String[] timeStr = inputTimeStr.split(":");
        int hours = Integer.parseInt(timeStr[0]);
        int minute = Integer.parseInt(timeStr[1]);

        String amPmString = "";
        if (hours >= 12) {
            if (hours > 12)
                amPmString = "" + (hours - 12) + ":" + minute + " PM";
            else
                amPmString = "" + (hours) + ":" + minute + " PM";
        } else {
            amPmString = "" + (hours) + ":" + minute + " AM";
        }

        return amPmString;
    }


//    public void startAlaram() {
////        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
////        int interval = 8000;
////
////        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
////        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
//
//
//        /////////
//
//        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
//        PendingIntent pendingIntent=PendingIntent.getBroadcast(this, 0, new Intent(this, Alarm.class), 0);
//        Calendar calendar=Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.add(Calendar.SECOND, 2);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
//
//        //////////////
//    }

//    public void cancel() {
//        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        manager.cancel(pendingIntent);
//        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
//    }
//
//    public void startAt10(int hours, int minutes ) {
//        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        int interval = 1000 * 60 * 20;
//
//        /* Set the alarm to start at 10:30 AM */
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 10);
//        calendar.set(Calendar.MINUTE, 30);
//
//        /* Repeating on every 20 minutes interval */
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                1000 * 60 * 20, pendingIntent);
//    }
//    private void playSound(final Context context, Uri alert) {
//
//
//        Thread background = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    mp.start();
//
//                } catch (Throwable t) {
//                    Log.i("Animation", "Thread  exception " + t);
//                }
//            }
//        });
//        background.start();
//    }

    public void setAlaramTime(String timeStrInput, String configNamazSPKey, int configNamazPI)
    {
        if(timeStrInput!=null) {
            String[] timeStr = timeStrInput.split(":");
            int hours = Integer.parseInt(timeStr[0]);
            int minute = Integer.parseInt(timeStr[1]);

            RingBellManager.INSTANCE.sheduleRingBell(namaz.this, hours, minute, configNamazSPKey, configNamazPI);
        }
    }
    protected void onDestroy() {
        super.onDestroy();

        if(mp!=null)
        mp.stop();
    }
}
