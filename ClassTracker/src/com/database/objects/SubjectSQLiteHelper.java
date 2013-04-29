package com.database.objects;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SubjectSQLiteHelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "tempTestSubject1.db";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "tempSubject1";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_CLASSNAME= "className";
	public static final String COLUMN_LATITUDE= "latitude";
	public static final String COLUMN_LONGITUDE= "longitude";
	public static final String COLUMN_STARTTIME = "startTime";
	public static final String COLUMN_ALIAS = "alias";
	public static final String COLUMN_ID = "_id";
	
	private static final String DATABASE_CREATE = "create table " + TABLE_NAME + "(" + COLUMN_ID
			+ " integer primary key autoincrement, "
			+ COLUMN_NAME + " text not null, "
			+ COLUMN_CLASSNAME + " text not null, "
			+ COLUMN_STARTTIME + " text not null," 
			+ COLUMN_LATITUDE + " double not null, "
			+ COLUMN_LONGITUDE + " double  not null);";
	
	public SubjectSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVer, int newVer) {
		Log.w(SubjectSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVer + " to "
						+ newVer + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(database);
	}
}
