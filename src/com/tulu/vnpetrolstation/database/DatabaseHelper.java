package com.tulu.vnpetrolstation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tulu.vnpetrolstation.BuildConfig;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = DatabaseHelper.class.getSimpleName();
	public static final String TB_EXAM_INFO = "CREATE table exam_file_info ("
			+ "_id integer primary key autoincrement,"
			+ "answered_questions integer,"
			+ "unanswered_questions integer,"
			+ "total_questions integer,"
			+ "exam_name text,"
			+ "last_read long,"
			+ "first_read long,"
			+ "exam_duration long,"
			+ "learn_duration long)";
	public static final String TB_QUESTION_ANSWER = "CREATE table question_answer ("
			+ "_id integer primary key autoincrement,"
			+ "exam_id integer,"
			+ "last_answer_time long,"
			+ "corect_answer int,"
			+ "seleted_answer int)";

	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "tulu.quiz.ccna.db";
	public static final String TABLE_EXAM_FILE_INFO = "exam_file_info";
	public static final String TABLE_QUESTION_ANSWER = "question_answer";

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	/**
	 * Table created here
	 */
	public void onCreate(SQLiteDatabase db) {
		if (BuildConfig.DEBUG) {
			Log.d(TAG, "DB created");
		}
		createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (BuildConfig.DEBUG) {
			Log.d(TAG, "DB upgrade from " + oldVersion + "to " + newVersion);
		}
		switch (oldVersion) {
		case 4:
		case 5:
			break;
		default:
			break;
		}
	}

	private void createTable(SQLiteDatabase db) {
		// Create data table
		db.execSQL(TB_EXAM_INFO);
		db.execSQL(TB_QUESTION_ANSWER);
	}
}