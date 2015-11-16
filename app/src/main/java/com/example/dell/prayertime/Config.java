package com.example.dell.prayertime;

/**
 * Created by dell on 9/7/2015.
 */
public class Config {

    public static int MAX_CITY_RESULT = 20;
    //shared preference ID,s for prayer time

    public static final String FAJAR_SP="fajar";
    public static final String ZUHAR_SP="zuhar";
    public static final String ASAR_SP ="asar";
    public static final String MAGRIB_SP="magrib";
    public static final String ISHA_SP="isha";

    public static boolean isOnCall;

    // PENDING Intent IDs for prayer time
    public static final int FAJAR_PI=1;
    public static final int ZUHAR_PI=2;
    public static final int ASAR_PI=3;
    public static final int MAGRIB_PI=4;
    public static final int ISHA_PI=5;

}
