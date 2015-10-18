package com.tulu.vnpetrolstation.receiver;

import java.util.Date;

import com.tulu.vnpetrolstation.common.VNPetrolStation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class LocationObserver extends Service implements LocationListener {
	public static final int UPDATE_DISTANCE = 10;
	public static final int REFRESH_TIME = 60000;
	public static final int SLEEP_TIME = 600000;

	ScreenOffReceiver so;
	KeepAliveThread kat;
	LocationManager locationManager;

	private class KeepAliveThread {
		AlarmManager alm;
		PendingIntent pi;
		int refreshTime;

		public KeepAliveThread() {
			this.refreshTime = REFRESH_TIME;

			alm = (AlarmManager) LocationObserver.this.getSystemService(Context.ALARM_SERVICE);
			Intent i = new Intent(LocationObserver.this, LocationObserver.class);
			pi = PendingIntent.getService(LocationObserver.this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);

			alm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), this.refreshTime, pi);
		}

		private void pause() {
			alm.cancel(pi);

			refreshTime = SLEEP_TIME;
			alm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), refreshTime, pi);
		}

		private void resume() {
			alm.cancel(pi);
			refreshTime = REFRESH_TIME;
			alm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), refreshTime, pi);
		}
	}

	private class ScreenOffReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (locationManager == null)
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (locationManager != null)
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, REFRESH_TIME, UPDATE_DISTANCE, this);

		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		kat = new KeepAliveThread();
		so = new ScreenOffReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
		intentFilter.addAction(Intent.ACTION_TIME_TICK);
		registerReceiver(so, intentFilter);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (locationManager != null)
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, REFRESH_TIME, UPDATE_DISTANCE, this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (so != null)
			unregisterReceiver(so);

		startService(new Intent(this, LocationObserver.class));
	}

	@Override
	public void onLocationChanged(Location location) {
		VNPetrolStation nvn = VNPetrolStation.getInstance();
		nvn.setLastGPS((float) location.getLatitude(), (float) location.getLongitude());
		Date now = new Date();
		nvn.setLastGPSUpdateTime(now.getTime());
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
