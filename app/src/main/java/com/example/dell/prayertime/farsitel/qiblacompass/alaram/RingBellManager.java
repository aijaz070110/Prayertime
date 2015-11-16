package com.example.dell.prayertime.farsitel.qiblacompass.alaram;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


import com.example.dell.prayertime.Config;


public enum RingBellManager
{
	INSTANCE;
	private Timer timer;
	private Activity mActivity;
	private RingBellStateListener mRingBellStateListener;
	public static final long TIME_OPTION_DELAY_1 = 5000;
	public static final long TIME_OPTION_DELAY_2 = 30000;
	public static final long TIME_OPTION_DELAY_3 = 60000;

	public void init(Activity activity, RingBellStateListener ringBellStateListener)
	{
		mActivity = activity;
		mRingBellStateListener = ringBellStateListener;
		SoundManager.init(activity);
	}

	public void action1()
	{
		if (SoundManager.getSharedInstance().getSound(0).isPlaying())
		{
			stop();
		}
		else
		{
			schedule();
		}
	}

	public void stop()
	{
		SoundManager.getSharedInstance().stopSound(0);
	}

	public synchronized void schedule()
	{
		schedule(2000);
	}

	public synchronized void schedule(long delay)
	{
		if (SoundManager.getSharedInstance().getSound(0).isPlaying())
		{
			stop();
		}
		TimerTask task = new TimerTask()
		{
			public void run()
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
					mActivity.startActivity(new Intent(mActivity, FakeCallActivity.class));
				}
			}
		};

		if (timer != null)
		{
			timer.cancel();
		}
		timer = new Timer();
		timer.schedule(task, delay);

		if (mRingBellStateListener != null)
		{
			mRingBellStateListener.onActivate();
		}
	}

	public void dismissSheduled(Context mContext)
	{
		if (timer != null)
		{
			timer.cancel();
		}
		if (SoundManager.getSharedInstance().getSound(0).isPlaying())
		{
			stop();
		}
		if (mRingBellStateListener != null)
		{
			mRingBellStateListener.onDissmiss();
		}

		if (new SharedPrefSettingsUtil().getSharedPrefValue(mContext, "isRingActive") != null)
		{
			new SharedPrefSettingsUtil().removeSharedPrefValue(mContext, "isRingActive");
		}

	}



	public void setAlaram(Context context)
	{
		new SharedPrefSettingsUtil().saveSharedPrefValue(context, "isRingActive", "isRingActive");
//		sheduleRingBell(mActivity, 5,19); //5 seconds

	}

	public static String convertTimeToRepresentation(long millis, String secondsFormat, String minuteFormat)
	{
		long minutes = TimeUnit.MILLISECONDS.toSeconds(millis) / 60;
		long remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(millis) - minutes * 60;
		if (minutes == 0)
		{
			return String.format("%d " + secondsFormat, remainingSeconds);
		}
		else if (minutes != 0 && remainingSeconds == 0)
		{
			return String.format("%d " + minuteFormat, minutes);
		}
		return String.format("%d " + minuteFormat + ", %d " + secondsFormat, minutes, remainingSeconds);
	}

	public static interface RingBellStateListener
	{
		public void onActivate();

		public void onDissmiss();
	}
	private PendingIntent pendingIntent;
	
	public void sheduleRingBell(Context context,int hour, int minutes, String namazTimStrKey, int intentID )
	{
		if(mRingBellStateListener!=null)
			mRingBellStateListener.onActivate();

		Intent myIntent = new Intent(mActivity, MyAlarm.class);
		pendingIntent = PendingIntent.getService(context, intentID, myIntent, 0);


		SharedPrefSettingsUtil sharedPrefSettingsUtil = new SharedPrefSettingsUtil();

		String namazSPTime = sharedPrefSettingsUtil.getSharedPrefValue(context,namazTimStrKey);
		String  timePassed= ""+hour+":"+minutes;
		if(namazSPTime!=null )
		{
			if(timePassed.equals(namazSPTime))
			{
				pendingIntent.cancel();
			}
		}

		sharedPrefSettingsUtil.saveSharedPrefValue(context,namazTimStrKey,timePassed);


		

		Calendar calendar = Calendar.getInstance();
//		AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");//ALARM_SERVICE
//
//
//		calendar.setTimeInMillis(System.currentTimeMillis());
//		calendar.add(Calendar.SECOND, seconds);
//		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


		////////////

		AlarmManager manager = (AlarmManager) context.getSystemService("alarm");
		int interval = 24*60*60*1000;

        /* Set the alarm to start at 10:30 AM */
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minutes);

        /* Repeating on every 20 minutes interval */
		manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				1000 * 60 * 20, pendingIntent);
		/////////////////


	}
	
	public void disableRingBell(Context context)
	{
		if(mRingBellStateListener!=null)
			mRingBellStateListener.onDissmiss();
		
		if (SoundManager.getSharedInstance().getSound(0).isPlaying())
		{
			stop();
		}
		if (mRingBellStateListener != null)
		{
			mRingBellStateListener.onDissmiss();
		}

		if (new SharedPrefSettingsUtil().getSharedPrefValue(context, "isRingActive") != null)
		{
			new SharedPrefSettingsUtil().removeSharedPrefValue(context, "isRingActive");
		}
		
		AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
		if(pendingIntent!=null)
		alarmManager.cancel(pendingIntent);
	}


}
