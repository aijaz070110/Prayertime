package com.example.dell.prayertime.farsitel.qiblacompass.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by dell on 9/16/2015.
 */
public class AutoPlayerForPrayTime {
    String fileName;
    Context contex;
    MediaPlayer mp;

    public AutoPlayerForPrayTime( Context context) {

        contex = context;
//        playAudio();
    }

    public void playAudio(String name) {
        fileName = name;
        mp = new MediaPlayer();
        try {
            AssetFileDescriptor descriptor = contex.getAssets()
                    .openFd(fileName);

            mp.setDataSource(descriptor.getFileDescriptor(),
                    descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            mp.prepare();
//            mp.setLooping(false);
            mp.start();
            mp.setVolume(3, 3);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Stop Audio
    public void stop() {
        mp.stop();
    }
}