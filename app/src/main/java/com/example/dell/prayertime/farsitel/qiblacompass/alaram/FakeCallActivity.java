package com.example.dell.prayertime.farsitel.qiblacompass.alaram;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.dell.prayertime.Config;
import com.example.dell.prayertime.R;


public class FakeCallActivity extends Activity
{

	Toast myToast;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		 Log.d("LIFECYCLE", "FakeCallActivity, onCreate");
		context = this;

		setContentView(R.layout.fake_call_activity_layout);
		Config.isOnCall = false;
		SoundManager.init(this);
		playSound();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		 Log.d("LIFECYCLE", "FakeCallActivity, onTouchEvent");

		
		if (new SharedPrefSettingsUtil().getSharedPrefValue(context, "isRingActive") != null)
		{
			new SharedPrefSettingsUtil().removeSharedPrefValue(context, "isRingActive");
		}
		RingBellManager.INSTANCE.disableRingBell(context);
		RingBellManager.INSTANCE.dismissSheduled(context);
		
			finish();

		return super.onTouchEvent(event);
	}

	@Override
	public void onBackPressed()
	{
		 Log.d("LIFECYCLE", "FakeCallActivity, onBackPressed");
	}

	@Override
	protected void onStart()
	{
		Config.isOnCall = false;
		super.onStart();
	}

	public void playSound()
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

}
