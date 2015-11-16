package com.example.dell.prayertime;

import android.content.ClipData;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity {
    ImageButton namaz;
    ImageButton qibla;
    ImageButton location, aboutt;
    private InterstitialAd mInterstitialAd;
    AdView mAdView ;

    // define the display assembly compass picture
    private ImageView image;

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;

    TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        creatInterstitialAdd();

        // our compass image
        image = (ImageView) findViewById(com.example.dell.prayertime.R.id.imageViewCompass);
        ////////Admob
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setActivated(true);
        /// end admob

        // TextView that will tell the user what degree is he heading
        //tvHeading = (TextView) findViewById(R.id.subHeadingTv);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        namaz = (ImageButton) findViewById(R.id.namaz);
        qibla = (ImageButton) findViewById(R.id.qibla);
        location = (ImageButton) findViewById(R.id.locationBtn);
        aboutt = (ImageButton) findViewById(R.id.about);
        namaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, namaz.class);
                startActivity(intent);


            }
        });
        qibla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, qibla.class);
                startActivity(intent);


            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, location.class);
                startActivity(intent);


            }
        });

        aboutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, about_activity.class);
                startActivity(intent);


            }

        });


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        // for the system's orientation sensor registered listeners
//        mSensorManager.registerListener(this,
//                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//                SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        // to stop the listener and save battery
//        mSensorManager.unregisterListener(this);
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//
//        // get the angle around the z-axis rotated
//        float degree = Math.round(event.values[0]);
//
//        //tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
//
//        // create a rotation animation (reverse turn degree degrees)
//        RotateAnimation ra = new RotateAnimation(
//                currentDegree,
//                -degree,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF,
//                0.5f);
//
//        // how long the animation will take place
//        ra.setDuration(210);
//
//        // set the animation after the end of the reservation status
//        ra.setFillAfter(true);
//
//        // Start the animation
////        image.startAnimation(ra);
//        currentDegree = -degree;
//
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        // not in use
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        ClipData.Item item1;
        ClipData.Item itrm2;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.item1:
                Toast.makeText(this, "Item1 Here", Toast.LENGTH_LONG).show();

                break;
            case R.id.item2:
                Toast.makeText(this, "Item2 Here", Toast.LENGTH_LONG).show();

            case R.id.action_settings:
                Toast.makeText(this, "seeting  Here", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, setting.class);
                this.startActivity(intent);
            default:

                return super.onOptionsItemSelected(item);
        }
        return true;

    }
    private void creatInterstitialAdd()
    {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
//                mNextLevelButton.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
//                mNextLevelButton.setEnabled(true);
//                finish();
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
//                goToNextLevel();
                finish();
            }
        });

        loadInterstitial();
    }
    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }


    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        showInterstitial();
    }



}

