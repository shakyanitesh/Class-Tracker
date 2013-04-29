package com.example.classtracker;

import java.util.List;

import com.database.objects.SingletonDatabaseControl;
import com.database.objects.SubjectObject;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

//This activity displays all the subject added
public class MainActivity extends ListActivity {

	private List<SubjectObject> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//Instantiating database control to be same over all the different contexts
		SingletonDatabaseControl databaseControl = SingletonDatabaseControl.getInstance(this);
		
		//Getting all location
		list = databaseControl.getSource().getAllLocations();
		String[] activityList = new String[list.size()+1];
		activityList[0] = "Add Subject";
		int i = 1;
		for(SubjectObject object : list){
			activityList[i] = object.toString();
			i++;
		}
		this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, activityList));
//		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public void onListItemClick(ListView l, View view, int position, long id){
		String object = ((TextView) view).getText().toString();
		if(object.equals("Add Subject")){
			Intent intent = new Intent(this, AddSubject.class);
			startActivity(intent);
		} else {
			Log.e("position", ((Integer)position).toString());
			AddSubject.removeAlarm(position-1);
			SubjectObject subject = list.get(position-1);
			SingletonDatabaseControl.getInstance(this).getSource().deleteSubject(subject);
			
			finish();
			startActivity(getIntent());
		}
	}
	
	public void openAdd(View view){
		
	}

}
