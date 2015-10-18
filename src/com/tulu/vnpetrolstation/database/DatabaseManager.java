package com.tulu.vnpetrolstation.database;

import com.tulu.vnpetrolstation.common.VNPetrolStation;

public class DatabaseManager {
	private static final int COLUM_REMARK = 1;
	private static final String TAG = DatabaseManager.class.getSimpleName();
	private static final boolean DEBUG = false;

	private DatabaseHelper mHelper = null;
	private static volatile DatabaseManager _instance;

	public synchronized final static DatabaseManager instance() {
		if (null == _instance) {
			_instance = new DatabaseManager();
		}
		return _instance;
	}

	public DatabaseManager() {
		mHelper = new DatabaseHelper(VNPetrolStation.getInstance());
	}

	public void deleteDatabase() {
		VNPetrolStation.getInstance().deleteDatabase(DatabaseHelper.DB_NAME);
	}

}