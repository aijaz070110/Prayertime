package com.example.dell.prayertime;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import javax.security.auth.callback.Callback;

/**
 * Created by dell on 9/10/2015.
 */
public interface JSONResponseCallback {

    public void onCompleteResponse(JSONObject jsonResponse);



}

//
//    public class interface implements JSONResponseCallback {
//
//        @Override
//        public void onCompleteResponse(JSONObject result) {
//            // do something with result here!
//
//
//            URL url = null;
//            try
//
//            {
//                url = new URL("http://praytime.info/getprayertimes.php?lat=31.488800&lon=74.368599");
//            } catch (
//                    MalformedURLException e
//                    )
//
//            {
//                e.printStackTrace();
//
//
//
//            }
//        }
//    }