package com.database.objects;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SubjectDataSource {
	
	
	private SQLiteOpenHelper dbHelper;
	private SQLiteDatabase database;
	private String[] allColumns = { SubjectSQLiteHelper.COLUMN_ID, SubjectSQLiteHelper.COLUMN_NAME, SubjectSQLiteHelper.COLUMN_CLASSNAME, SubjectSQLiteHelper.COLUMN_STARTTIME, SubjectSQLiteHelper.COLUMN_LATITUDE, SubjectSQLiteHelper.COLUMN_LONGITUDE};
	
	public SubjectDataSource(Context context){
		dbHelper = new SubjectSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	private SubjectObject cursorToSubject(Cursor cursor) {
		String names ="";
		for(String columnName : cursor.getColumnNames() ){
			names += columnName + " ";
		}
		Log.e("cursor", names);
		SubjectObject subject = new SubjectObject();
		subject.setId(cursor.getLong(0));
		subject.setName(cursor.getString(1));
		subject.setClassName(cursor.getString(2));
		subject.setStartTime(cursor.getString(3));
		subject.setLatitude(cursor.getDouble(4));
		subject.setLongitude(cursor.getDouble(5));
		return subject;
	}
	
	public SubjectObject createSubjectObject(String subjectName, String className, String time, Double latitude, Double longitude){
		//Adding subject in database
		ContentValues contentValues = new ContentValues();
		contentValues.put(SubjectSQLiteHelper.COLUMN_NAME, subjectName);
		contentValues.put(SubjectSQLiteHelper.COLUMN_CLASSNAME, className);
		contentValues.put(SubjectSQLiteHelper.COLUMN_STARTTIME, time);
		contentValues.put(SubjectSQLiteHelper.COLUMN_LATITUDE, latitude);
		contentValues.put(SubjectSQLiteHelper.COLUMN_LONGITUDE, longitude);
		
		//Insert Id
		long insertId = database.insert(SubjectSQLiteHelper.TABLE_NAME, null,
				contentValues);
		
		Cursor cursor = database.query(SubjectSQLiteHelper.TABLE_NAME,
		        allColumns, SubjectSQLiteHelper.COLUMN_ID + " = " + insertId, null,
		        null, null, null);
		cursor.moveToFirst();
		SubjectObject subObj = cursorToSubject(cursor);
		cursor.close();
		return subObj;
		
	}
	
	public void deleteSubject(SubjectObject subject) {
	    long id = subject.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(SubjectSQLiteHelper.TABLE_NAME, SubjectSQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<SubjectObject> getAllLocations() {
	    List<SubjectObject> subjects = new ArrayList<SubjectObject>();

	    Cursor cursor = database.query(SubjectSQLiteHelper.TABLE_NAME,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      SubjectObject location = cursorToSubject(cursor);
	      subjects.add(location);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return subjects;
	  }
	

}
