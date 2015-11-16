package com.example.dell.prayertime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class setting extends AppCompatActivity {
    //TextView city;
    AppCompatAutoCompleteTextView edtcity;
    WebApiAsynTask prayersAsyncTask;
    WebApiAsynTask webApiAsynTask;
    String locationUrl = "http://ip-api.com/json";
    String Url = "http://praytime.info/services.php";

    Button cityBtn;

    String country, city, regionName, countryCode;
    String fajar, Zohar, Asar, Magrib, Isha,Sunrise,Sunset;
    double lat, lng;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        cityBtn = (Button) findViewById(R.id.city);
        cityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /////
                webApiAsynTask = new WebApiAsynTask(setting.this, true, locationUrl, null, new JSONResponseCallback() {
                    @Override
                    public void onCompleteResponse(JSONObject jsonResponse) {
                        city = jsonResponse.optString("city");
                        country = jsonResponse.optString("country");
                        countryCode = jsonResponse.optString("countryCode");
                        lat = jsonResponse.optDouble("lat");
                        lng = jsonResponse.optDouble("lon");

                        final String prayerTimeUrl = "http://praytime.info/getprayertimes.php?lat=" + lat + "&lon=" + lng;

                        prayersAsyncTask = new WebApiAsynTask(setting.this, true, prayerTimeUrl, null, new JSONResponseCallback() {

                            @Override
                            public void onCompleteResponse(JSONObject result) {
                                // Now write parser to parse json result of prayers.
                                Calendar calendar = Calendar.getInstance();


                                SimpleDateFormat Sdf = new SimpleDateFormat("dd");
                                String currentDate = Sdf.format(calendar.getTime());
                                Date date = new Date();

                                String dayName = Sdf.format(date);
                                // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
//

                                JSONObject prayerTimeObj = result.optJSONObject("" + dayName);

                                Toast.makeText(setting.this, "" + prayerTimeObj.toString(), Toast.LENGTH_LONG).show();

                                System.out.println("" + prayerTimeObj.toString());
                                // Log.e("Prayer time resposne is: ",prayerTimeObj.toString());
//

                                fajar = prayerTimeObj.optString("Fajar");
                                Zohar = prayerTimeObj.optString("Zohar");
                                Asar = prayerTimeObj.optString("Asar");
                                Magrib = prayerTimeObj.optString("Magrib");
                                Isha = prayerTimeObj.optString("Isha");
                                Sunrise = prayerTimeObj.optString("Sunrise");
                                Sunset = prayerTimeObj.optString("Sunset");



                                // Now write parser to parse json result of prayers.


                            }

                        });
                        prayersAsyncTask.execute();


                    }



                });
                webApiAsynTask.execute();
                //////
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(setting.this, "" + data.getStringExtra("CityName"), Toast.LENGTH_SHORT).show();

        CityResult cityResult = (CityResult) data.getSerializableExtra("cityResult");


    }
}