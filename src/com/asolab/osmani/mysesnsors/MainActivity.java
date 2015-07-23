package com.asolab.osmani.mysesnsors;

import java.util.HashMap;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;



import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity 
{

    ListView list;
    String[] objects;
    TypedArray icons;
    Context mContext;
    Resources resources;
    
    int [] sensor_index;
    String [] sensor_name;
    static HashMap<String, Boolean> myMap;
    
    private SensorManager mSensorManager;    
    
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		sensor_index = getResources().getIntArray(R.array.sensor_index);
		sensor_name = getResources().getStringArray(R.array.sensor_name);
		
		myMap = new HashMap<String, Boolean>();
		
		int i = 0;
		
		Sensor s;

		while( i < sensor_index.length)
		{
			if( (s = mSensorManager.getDefaultSensor(sensor_index[i])) != null)
			{
				myMap.put(sensor_index[i] + "", true);
				Log.d("<<x_x>>", s.getName() + " available");
			}
			else
			{
				myMap.put(sensor_index[i] + "", false);
				Log.d("<<x_x>>",  sensor_name[i] + " Not available" );					
			}
			i++;
		}
				
        list = (ListView)findViewById(android.R.id.list);
        
        mContext = getApplicationContext();
    	resources = mContext.getResources();

    	objects = resources.getStringArray(R.array.object_names);
    	icons = resources.obtainTypedArray(R.array.object_icons);
    	
    		    	
    	setListAdapter(new ListAdapter(mContext, R.layout.list_view_item, objects, icons));
    	
    	/*AdView adView;
		AdRequest adRequest = new AdRequest();
		adView= (AdView)findViewById(R.id.adView);
		adView.loadAd(adRequest);*/
    	
    	AdView adView = (AdView)this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}		
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() 
	{
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		Intent i;
	    // Handle item selection
	    switch (item.getItemId()) 
	    {
	        case R.id.menu_settings:
	            i = new Intent(this, SettingsActivity.class);
	            startActivity(i);
	            return true;
	            
	        case R.id.menu_data_collect:
	            i = new Intent(this, DataCollectionActivity.class);
	            startActivity(i);
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override 
    public void onListItemClick(ListView listView, View view, int position, long id) 
	{
        // Do something when a list item is clicked
		
		//Toast.makeText(getApplicationContext(), "Hi there!", Toast.LENGTH_SHORT).show();
		Log.d("<<x_x>>", "id >> " + position);
		
		if(myMap.get(sensor_index[position] + ""))
		{
			Intent i = new Intent(this, SensorActivity.class);
			i.putExtra("sensor", position + "");
			startActivity(i);
		}
		else
			Toast.makeText(getApplicationContext(), "Sorry! This sensor is not available in your phone.", Toast.LENGTH_SHORT).show();
    }
}



//while( i < sensors.length )
//{
//	Log.d("<<x_x>>", "i>> " + i + "<-->" + sensors[i]);
//	switch(sensors[i])
//	{
//	case 1:
//		if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
//		{
//			myMap.put(sensors[i] + "", true);
//			Log.d("<<x_x>>", "Accelerometer available");
//		}
//		else
//		{
//			myMap.put(sensors[i] + "", false);
//			Log.d("<<x_x>>", "Accelerometer Not available" );					
//		}
//		break;
//	case 2:
//		if(mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null)
//		{
//			myMap.put(sensors[i] + "", true);
//			Log.d("<<x_x>>", "Magnetic Field sensor available");
//		}
//		else
//		{
//			myMap.put(sensors[i] + "", false);
//			Log.d("<<x_x>>", "Magnetic Field sensor Not available" );					
//		}
//		break;
//	case 3:
//		if(mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null)
//		{
//			myMap.put(sensors[i] + "", true);
//			Log.d("<<x_x>>", "Orientation sensor available");
//		}
//		else
//		{
//			myMap.put(sensors[i] + "", false);
//			Log.d("<<x_x>>", "Orientation sensor Not available" );					
//		}
//		break;
//	case 4:
//		if(mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null)
//		{
//			myMap.put(sensors[i] + "", true);
//			Log.d("<<x_x>>", "Gyroscope available");
//		}
//		else
//		{
//			myMap.put(sensors[i] + "", false);
//			Log.d("<<x_x>>", "Gyroscope Not available" );					
//		}
//		break;
//	case 5:
//		if(mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
//		{
//			myMap.put(sensors[i] + "", true);
//			Log.d("<<x_x>>", "Light sensor available");
//		}
//		else
//		{
//			myMap.put(sensors[i] + "", false);
//			Log.d("<<x_x>>", "Light sensor Not available" );					
//		}
//		break;
//	case 6:
//		if(mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null)
//		{
//			myMap.put(sensors[i] + "", true);
//			Log.d("<<x_x>>", "Barometer(pressure) available");
//		}
//		else
//		{
//			myMap.put(sensors[i] + "", false);
//			Log.d("<<x_x>>", "Barometer(pressure) Not available" );					
//		}
//		break;
//	case 7:
//		if(mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null)
//		{
//			myMap.put(sensors[i] + "", true);
//			Log.d("<<x_x>>", "Temperature sensor available");
//		}
//		else
//		{
//			myMap.put(sensors[i] + "", false);
//			Log.d("<<x_x>>", "Temperature sensor Not available" );					
//		}
//		break;
//	case 8:
//		if(mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
//		{
//			myMap.put(sensors[i] + "", true);
//			Log.d("<<x_x>>", "Proximity sensor available");
//		}
//		else
//		{
//			myMap.put(sensors[i] + "", false);
//			Log.d("<<x_x>>", "Proximity sensor Not available" );					
//		}
//		break;
//	case 9:
//		if(mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null)
//		{
//			myMap.put(sensors[i] + "", true);
//			Log.d("<<x_x>>", "Gravity sensor available");
//		}
//		else
//		{
//			myMap.put(sensors[i] + "", false);
//			Log.d("<<x_x>>", "Gravity sensor Not available" );					
//		}
//		break;
//	case 10:
//		if(mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null)
//		{
//			myMap.put(sensors[i] + "", true);
//			Log.d("<<x_x>>", "Linear Accelerometer available");
//		}
//		else
//		{
//			myMap.put(sensors[i] + "", false);
//			Log.d("<<x_x>>", "Linear Accelerometer Not available" );					
//		}
//		break;
//	case 11:
//		if(mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null)
//		{
//			myMap.put(sensors[i] + "", true);
//			Log.d("<<x_x>>", "Rotation Vector sensor available");
//		}
//		else
//		{
//			myMap.put(sensors[i] + "", false);
//			Log.d("<<x_x>>", "Rotation Vector sensor Not available" );					
//		}
//		break;
//	case 12:
//		if(mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null)
//		{
//			myMap.put(sensors[i] + "", true);
//			Log.d("<<x_x>>", "Humidity sensor available");
//		}
//		else
//		{
//			myMap.put(sensors[i] + "", false);
//			Log.d("<<x_x>>", "Humidity sensor Not available" );					
//		}
//		break;
//	case 13:
//		if(mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
//		{
//			myMap.put(sensors[i] + "", true);
//			Log.d("<<x_x>>", "Ambient Temperature sensor available");
//		}
//		else
//		{
//			myMap.put(sensors[i] + "", false);
//			Log.d("<<x_x>>", "Ambient Temperature sensor Not available" );					
//		}
//		break;
//	default:
//		break;
//
//	}
//	
//	i++;
//}
