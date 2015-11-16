package com.example.dell.prayertime.farsitel.qiblacompass.alaram;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.dell.prayertime.Config;
import com.example.dell.prayertime.R;

import java.util.ArrayList;

public class SoundManager {

//	private SoundPool mSoundPool;
	ArrayList<Integer> soundPool;
	ArrayList<MediaPlayer> players;

//	private AudioManager mAudioManager;
	private Context mContext;

	private static SoundManager soundManager;

	private SoundManager(Context context) {
		mContext = context;
		soundPool = new ArrayList<Integer>();
		players = new ArrayList<MediaPlayer>();

	}

	public static void init(Context context) {
		soundManager = new SoundManager(context);

		//SoundManager.getSharedInstance().addSound(R.raw.ring);
		SoundManager.getSharedInstance().addSound(R.raw.azan);
		SoundManager.getSharedInstance().getSound(0).setLooping(true);

	}

	public static SoundManager getSharedInstance() {

		return soundManager;
	}

	public void addSound(int SoundID) {
		soundPool.add(SoundID);
		MediaPlayer media = new MediaPlayer();
		media = MediaPlayer.create(mContext, SoundID);
		players.add(media);
		 Log.e("end", "" + SoundID);

	}

	public void stopSound(int index) {

//		int soundid = soundPool.get(index);

		if (players.get(index) != null) {
			
			if(players.get(index).isPlaying())
			{
				players.get(index).pause();
				
			}
			// Log.i("end", "Sound is PAUSED:"+index);
		}
	}

	public void pauseSound(int id) {

		try {
//			int soundid = soundPool.get(id);
			if (players.get(id) != null) {

				if (players.get(id).isPlaying()) {
					players.get(id).pause();
					// Log.i("end", "Sound is PAUSED:"+id);
				}
			} else {
				// Log.i("stop", "Failed:"+id);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}

	}

	public void playSound(int index) {

		{
			MediaPlayer player;
			if (players.get(index) != null) {
				players.get(index).start();
			} else {
				try {
					player = new MediaPlayer();
					int ind = soundPool.get(index);
					player = MediaPlayer.create(mContext, ind);
					// if(AppConsts.isLogEnabled) Log.e("end", "Recreating sound" + soundPool.get(index));
					try {
						players.set(index, player);
						players.get(index).start();
					} catch (Exception e) {
						try {
							player.prepare();
						} catch (IllegalStateException e1) {

							// e1.printStackTrace();
						}
						Log.i("end", "ERROR Creating Sound:" + index);
						if (players.get(index) == null)
							// Log.i("end", "Cannot Initialize Sound:" + index);
							e.getStackTrace();
					}
				} catch (Exception e) {

				}

			}
		}
	}

	public void releaseSound(int key) {
		if (players.get(key) != null) {
			players.get(key).stop();
			players.get(key).release();
			players.set(key, null);

		}
	}

	public void Release() {

		try {
			for (int i = 0; i < soundPool.size(); i++) {

				if (players.get(i) != null)
					players.get(i).release();
				players.set(i, null);

			}
		} catch (Exception e) {
			// new Utility().handleException(e);
		}

	}

	public MediaPlayer getSound(int key) {
		if (key < players.size())
			return players.get(key);
		else
			return null;
	}

	public void PlayAlaramSound()
	{
		if (Config.isOnCall)
		{
			Config.isOnCall = false;
		}
		else
		{

			if (!SoundManager.getSharedInstance().getSound(0).isPlaying())
			{
				SoundManager.getSharedInstance().playSound(0);
			}
		}
	}

	public int getSoundPoolSize() {
		return soundPool.size();
	}
}