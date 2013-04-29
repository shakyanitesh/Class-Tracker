package com.broadcast;

import com.example.classtracker.AlarmActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String latitude = intent.getStringExtra("latitude");
		Log.e("latitude Received", latitude.toString() );
		String longitude = intent.getStringExtra("longitude");
		Log.e("longitude Received", longitude.toString() );
		String minute = intent.getStringExtra("min");
		Log.e("mins Received", minute.toString() );
		String hourFromIntent = intent.getStringExtra("hour");
		Log.e("hours Received", hourFromIntent.toString());


		Intent nextIntent = new Intent(context, AlarmActivity.class);
		nextIntent.putExtra("latitude", latitude);
		nextIntent.putExtra("longitude", longitude);
//		intent.setClassName("com.test", "com.test.MainActivity");
        nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(nextIntent);
		
	}

}
