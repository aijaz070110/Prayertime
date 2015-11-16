package com.example.dell.prayertime.farsitel.qiblacompass.alaram;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefSettingsUtil
{
	static String PREFS_NAME = "pref";

	public String getSharedPrefValue(Context mContext, String sharedPrefTitle)
	{
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
		return settings.getString(sharedPrefTitle, null);
	}

	public void saveSharedPrefValue(Context mContext, String sharedPrefTitle, String sharedPrefValue)
	{
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
		final Editor editor = settings.edit();
		editor.putString(sharedPrefTitle, sharedPrefValue);
		editor.commit();
	}

	public void removeSharedPrefValue(Context mContext, String sharedPrefTitle)
	{
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
		Editor editor = settings.edit();
		editor.remove(sharedPrefTitle);
		editor.commit();
	}
	
	public void clearSharedPref(Context mContext)
	{
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
		Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}

}
