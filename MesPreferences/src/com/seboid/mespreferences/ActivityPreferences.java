package com.seboid.mespreferences;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class ActivityPreferences extends PreferenceActivity implements
		SharedPreferences.OnSharedPreferenceChangeListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		PreferenceManager.getDefaultSharedPreferences(this)
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
		Toast.makeText(ActivityPreferences.this,"pref changed!! ("+key+")"
		,Toast.LENGTH_LONG).show();

		// alarme dans 3 minutes
		ajouteAlarme(1*60*1000L);
//		if (key.equals("autoupdate") || key.equals("intervallemobile")
//				|| key.equals("intervallewifi")) {
//			updateAlarm();
//			return;
//		}
//		if (key.equals("savetime")) {
//		}
	}

	// interval <0 ==> enleve l'alarme
	private void ajouteAlarme(long interval) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent,
//				PendingIntent.FLAG_UPDATE_CURRENT);

		PendingIntent pendingIntent=PendingIntent.getActivity(this, 1234, intent,  PendingIntent.FLAG_UPDATE_CURRENT );
		
		long currentTimeMillis = System.currentTimeMillis();
		// long nextUpdateTimeMillis = currentTimeMillis + 20 *
		// DateUtils.MINUTE_IN_MILLIS;

		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		if (interval > 0) {
			alarmManager.set(AlarmManager.RTC,
					currentTimeMillis+interval, pendingIntent);
			Toast.makeText(ActivityPreferences.this,
					"alarm dans " + interval / 60 / 1000 + "min",
					Toast.LENGTH_LONG).show();
		} else {
			// enleve l'arlarme
			alarmManager.cancel(pendingIntent);
			Toast.makeText(ActivityPreferences.this, "alarm cancel",
					Toast.LENGTH_LONG).show();
		}
	}
	

}
