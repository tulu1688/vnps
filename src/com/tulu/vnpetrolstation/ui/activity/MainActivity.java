package com.tulu.vnpetrolstation.ui.activity;

import com.tulu.vnpetrolstation.R;
import com.tulu.vnpetrolstation.receiver.LocationObserver;
import com.tulu.vnpetrolstation.remote.PoiConnector;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends BaseAdActivity implements LocationListener {
	private static String TAG = MainActivity.class.getSimpleName();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_poi);

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LocationObserver.REFRESH_TIME, LocationObserver.UPDATE_DISTANCE,
//				MainActivity.this);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LocationObserver.REFRESH_TIME, LocationObserver.UPDATE_DISTANCE,
				MainActivity.this);

	}

	protected void onDestroy() {
		super.onDestroy();
	}

	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	protected void initElements() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setupListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.v(TAG, "On location update");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				PoiConnector.getPois();
			}
		});
		thread.start();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}
}