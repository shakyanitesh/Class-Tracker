package com.example.classtracker;

import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmActivity extends Activity {

	private MediaPlayer player;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		
		
		//Intent
		Intent intent= this.getIntent();
		Double latitude = Double.parseDouble(intent.getStringExtra("latitude"));
		Double longitude = Double.parseDouble(intent.getStringExtra("longitude"));

		//Creating location manager
		LocationManager locationManager;
		String svcName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(svcName);
		
		
		
		Location oldLocation = locationManager.getLastKnownLocation("network");
		oldLocation.setLatitude(latitude);
		oldLocation.setLongitude(longitude);
		
		Location newLocation = locationManager.getLastKnownLocation("network");
		
		float distance = oldLocation.distanceTo(newLocation);
		TextView distanceTextView = (TextView)findViewById(R.id.distanceView);
		distanceTextView.setText("You are " + distance + "m from your destinataion");
		if(distance > 20){
			Log.e("Sound", "here");
			player = MediaPlayer.create(this, R.raw.caralarm);
			player.setLooping(false);
			player.start();
		}
		Toast.makeText(this, "Alarm System", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_alarm, menu);
		return true;
	}
	
	public void stop(View view){
		if(player != null){
			if(player.isPlaying()){
				player.stop();
			}
		}
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		
	}

}
