package com.example.classtracker;

import java.util.ArrayList;
import java.util.Calendar;

import com.broadcast.AlarmReceiver;
import com.database.objects.SingletonDatabaseControl;
import com.database.objects.SubjectObject;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AddSubject extends Activity {
	
//	private static int MINSBEFORE = -20;
	private static ArrayList<PendingIntent> alarms = new ArrayList<PendingIntent>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_subject);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_subject, menu);
		return true;
	}
	
	public static void removeAlarm(int index){
		if(index < alarms.size()){
			PendingIntent intent = alarms.get(index);
			intent.cancel();
			alarms.remove(intent);
		}
	}
	
	public void saveToDatabase(View view){
		EditText classNameView = (EditText) findViewById(R.id.EditTextClassName);
		String className = classNameView.getText().toString();
		
		EditText subjectNameView = (EditText)findViewById(R.id.EditTextSubjectName);
		String subjectName = subjectNameView.getText().toString();
		
		EditText startTimeView = (EditText)findViewById(R.id.EditTextStartTime);
		String startTime = startTimeView.getText().toString();
		
		EditText latitudeView = (EditText)findViewById(R.id.EditTextLatitude);
		String latitudeStr = latitudeView.getText().toString();
		double latitude = Double.parseDouble(latitudeStr);
		
		EditText longitudeView = (EditText)findViewById(R.id.EditTextLongitude);
		String longitudeStr = longitudeView.getText().toString();
		double longitude = Double.parseDouble(longitudeStr);
		
		SubjectObject currentObj = SingletonDatabaseControl.getInstance(this).getSource().createSubjectObject(subjectName, className, startTime, latitude, longitude);
		String time = currentObj.getStartTime();
		Double latitudeFromObj = currentObj.getLatitude();
		Double longitudeFromObj = currentObj.getLongitude();
				
		Intent intent = new Intent(this, AlarmReceiver.class);
		intent.putExtra("latitude", latitudeFromObj.toString());
		intent.putExtra("longitude", longitudeFromObj.toString());
		Log.e("latitude Passed", latitudeFromObj.toString());
		Log.e("longitude Passed", longitudeFromObj.toString());
		//Getting time constraints
		Integer hours = Integer.parseInt(time.split(":")[0].trim());
		
		Integer mins = Integer.parseInt(time.split(":")[1].trim());
		
		Log.e("hours Passed", hours.toString());
		intent.putExtra("hour", hours.toString());
		Log.e("mins Passed", mins.toString());
		intent.putExtra("min", mins.toString());
		
		Time setTime = new Time();
		setTime.setToNow();
		Calendar time1 = Calendar.getInstance();
		Log.e("TimeNow", time1.getTime().toString());
		time1.set(time1.get(Calendar.YEAR), time1.get(Calendar.MONTH), time1.get(Calendar.DATE), hours, mins);
		
/*//		time1.set(Calendar.MINUTE, mins);
		
		Calendar time2 = Calendar.getInstance();
		time2.set(Calendar.HOUR, hours);
		time2.set(Calendar.MINUTE, mins);
		time2.add(Calendar.MINUTE, MINSBEFORE);*/
		
		Log.e("time", time1.getTime().toString());
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarms.add(pendingIntent);
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
		
		Intent mainActivityIntent = new Intent(this, MainActivity.class);
		startActivity(mainActivityIntent);
		
		
	}

}
