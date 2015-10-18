package com.tulu.vnpetrolstation.common;

import com.tulu.vnpetrolstation.model.GPSPoint;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class VNPetrolStation extends Application {
	private static VNPetrolStation _instance = null;
	public static final String BACKUP_FOLDER = "backup_folder";
	public static final String IS_FIRST_TIME = "is_first_time";
	public static final String DEFAUL_EXAM_QUIZ_NO = "default_exam_quiz_no";

	public static final String LAST_GPS_UPDATE = "last_gps_update_time";
	public static final String LAST_GPS_LNG = "last_gps_lon";
	public static final String LAST_GPS_LAT = "last_gps_lat";
	public static final String POI_CATEGORIES = "poi_categories";

	private SharedPreferences prefs;

	@Override
	public void onCreate() {
		super.onCreate();
		_instance = this;
		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	}

	public static VNPetrolStation getInstance() {
		if (_instance == null)
			throw new IllegalStateException("Application not created yet!");
		return _instance;
	}

	public void setBackupFolder(String folder) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(BACKUP_FOLDER, folder);
		editor.commit();
	}

	public String getBackupFolder() {
		return prefs.getString(BACKUP_FOLDER, null);
	}

	public String getBackupFolder(String defaultFolder) {
		return prefs.getString(BACKUP_FOLDER, defaultFolder);
	}

	public void setFirstTime(boolean isFirstime) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(IS_FIRST_TIME, isFirstime);
		editor.commit();
	}

	public boolean isFirstTime() {
		return prefs.getBoolean(IS_FIRST_TIME, true);
	}

	public void setLastGPSUpdateTime(long time) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong(LAST_GPS_UPDATE, time);
		editor.commit();
	}

	public long getLastGPSUpdateTime() {
		return prefs.getLong(LAST_GPS_UPDATE, 0);
	}

	public void setLastGPS(float lat, float lng) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putFloat(LAST_GPS_LAT, lat);
		editor.putFloat(LAST_GPS_LNG, lng);
		editor.commit();
	}

	public GPSPoint getLastGPSPoint() {
		return new GPSPoint(prefs.getFloat(LAST_GPS_LAT, 0), prefs.getFloat(LAST_GPS_LNG, 0));
	}

	public void setFavoriteCategory(String categories) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(POI_CATEGORIES, categories);
		editor.commit();
	}

	public String getFavoriteCategory() {
		return prefs.getString(POI_CATEGORIES, "");
	}

}
