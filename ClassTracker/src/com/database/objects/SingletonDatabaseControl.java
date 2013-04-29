package com.database.objects;

import android.content.Context;

public class SingletonDatabaseControl {
	
	private static SingletonDatabaseControl instance;
	
	private SubjectDataSource databaseSource;
	
	public static SingletonDatabaseControl getInstance(Context context){
		if(instance == null){
			instance = new SingletonDatabaseControl(context);
		}
		return instance;
		
	}
	
	private SingletonDatabaseControl(Context context){
		databaseSource = new SubjectDataSource(context);
		databaseSource.open();
		
	}

	public SubjectDataSource getSource(){
		return databaseSource;
	}
}
