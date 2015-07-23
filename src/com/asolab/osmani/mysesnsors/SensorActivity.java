package com.asolab.osmani.mysesnsors;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
//import au.com.bytecode.opencsv.CSVWriter;

public class SensorActivity extends Activity implements SensorEventListener, OnClickListener{

	private SensorManager mSensorManager;
	private Sensor mSensor;
	//private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private TextView textView4;
	private TextView textView5;
	private TextView textView6;
	private TextView textView7;
	private TextView textView8;
	private TextView textView9;
	private TextView textView10;
	private TextView textView11;
	private TextView textView12;
	private TextView textView13;
	private TextView textView14;
	private TextView textView15;
	private TextView textView16;
	private TextView textView17;
	private TextView textView18;
	private TextView textView19;
	
	private Button btn1;
	private Button btn2;
	private Button btn3;
	
	int [] sensor_index;
	String[] sensor_name;
	String sensor_details = "";
	String selected_sensor;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_details);
		
		
		//textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);
		textView4 = (TextView) findViewById(R.id.textView4);
		textView5 = (TextView) findViewById(R.id.textView5);
		textView6 = (TextView) findViewById(R.id.textView6);
		textView7 = (TextView) findViewById(R.id.textView7);
		textView8 = (TextView) findViewById(R.id.textView8);
		textView9 = (TextView) findViewById(R.id.textView9);
		textView10 = (TextView) findViewById(R.id.textView10);
		textView11 = (TextView) findViewById(R.id.textView11);
		textView12 = (TextView) findViewById(R.id.textView12);
		//textView13 = (TextView) findViewById(R.id.textView13);
		textView14 = (TextView) findViewById(R.id.textView14);
		textView15 = (TextView) findViewById(R.id.textView15);
		textView16 = (TextView) findViewById(R.id.textView16);
		textView17 = (TextView) findViewById(R.id.textView17);
		textView18 = (TextView) findViewById(R.id.textView18);
		textView19 = (TextView) findViewById(R.id.textView19);
		
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		Intent myIntent = getIntent();
		Bundle b = myIntent.getExtras();
		
		selected_sensor = b.getString("sensor");
		
		
		sensor_index = getResources().getIntArray(R.array.sensor_index);
		sensor_name = getResources().getStringArray(R.array.object_names);

		Log.d("<x_x>", "position @ SensorActivity : " + selected_sensor);
		Log.d("<x_x>", "sensor @ SensorActivity : " + sensor_index[Integer.parseInt(selected_sensor)]);
		
		
		mSensor = mSensorManager.getDefaultSensor(sensor_index[Integer.parseInt(selected_sensor)]);
		setTitle(sensor_name[Integer.parseInt(selected_sensor)]);
		
		textView2.setText(" Vendor");
		textView4.setText(" Maximum Range");
		textView6.setText(" Power Consumption");
		textView8.setText(" Resolution");
		textView10.setText(" Version");
		
		textView3.setText(": " + mSensor.getVendor());
		textView5.setText(": " + mSensor.getMaximumRange());
		textView7.setText(": " + mSensor.getPower() + " mili Amp");
		textView9.setText(": " + mSensor.getResolution());
		textView11.setText(": " + mSensor.getVersion());

		
		setSensorValueLabel();
		
		/*AdView adView;
		AdRequest adRequest = new AdRequest();
		adView= (AdView)findViewById(R.id.adView);
		adView.loadAd(adRequest);*/
		
		AdView adView = (AdView)this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		
//		sensor_details += "Vendor: " + mSensor.getVendor() + "\n";
//		sensor_details += "Max Range: " + mSensor.getMaximumRange() + "\n";
//		sensor_details += "Power: " + mSensor.getPower() + "mA\n";
//		//sensor_details += "Min Delay: " + mSensor.getMinDelay() + "\n";
//		sensor_details += "Resolution: " + mSensor.getResolution() + "\n";
//		sensor_details += "Version: " + mSensor.getVersion() + "\n";
//		sensor_details += "Type: " + mSensor.getType() + "\n";
//		
//		textView2.setText(sensor_details);
	}
	
	public void setSensorValueLabel()
	{
			
		switch(sensor_index[Integer.parseInt(selected_sensor)])
		{
		/* Accelerometer */
		case 1:	
			
			textView12.setText("Accelerometer");
			
			textView14.setText(" Acceleration along x-axis");
			textView16.setText(" Acceleration along y-axis");
			textView18.setText(" Acceleration along z-axis");
			
			break;
			/* Magnetic Field */		
		case 2: 
			textView12.setText("Magnatic Field");

			textView14.setText(" Magnetic Field x-axis");
			textView16.setText(" Magnetic Field y-axis");
			textView18.setText(" Magnetic Field z-axis");

			break;
		
			/* Orientation */
		case 3:

			textView12.setText("Orientation");

			textView14.setText(" Azimuth, \u03B8 around z-axis");
			textView16.setText(" Pitch, \u03B8 around x-axis");
			textView18.setText(" Roll, \u03B8 around y-axis");

			break;
			
		/* Gyroscope */		
		case 4:
			textView12.setText("Gyrsocope");			
			
			textView14.setText(" Rate of Rotation x-axis");
			textView16.setText(" Rate of Rotation y-axis");
			textView18.setText(" Rate of Rotation z-axis");
			
			break;
			
		/* Light */			
		case 5:
			textView12.setText("Light ");

			textView14.setText(" Light Intensity");
			
			break;
			
			/* Pressure */			
		case 6:
			textView12.setText("Pressure");

			textView14.setText(" Pressure");
			
			break;
			
			/* Temperature */			
		case 7:
			textView12.setText("Temperature");
			
			textView14.setText(" Temperature");
			
			break;
			
			/* Proximity */			
		case 8:
			textView12.setText("Proximity");

			textView14.setText(" Distance from object ");
			

			break;
			
			/* Gravity */			
		case 9:
			textView12.setText("Gravity");			
			
			textView14.setText(" Gravity along x-axis");
			textView16.setText(" Gravity along y-axis");
			textView18.setText(" Gravity along z-axis");
			
			break;
			
			/* Linear Acceleration */			
		case 10:
			textView12.setText("Linear Acceleration");

			textView14.setText(" Lin Acceleration x-axis");
			textView16.setText(" Lin Acceleration y-axis");
			textView18.setText(" Lin Acceleration z-axis");

			break;
			
		/* Rotation Vector */			
		case 11:
			textView12.setText("Rotation Vector");

			textView14.setText(" Rotation along x-axis");
			textView16.setText(" Rotation along y-axis");
			textView18.setText(" Rotation along z-axis");

			break;
			
		/* Relative Humidity */		
		case 12:
			textView12.setText("Humidity");
			textView14.setText(" Humidity ");
			
			break;
		
			/* Ambient Temperature */
		case 13:
			textView12.setText("Temperature");

			textView14.setText(" Temperature ");

			break;
			
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public final void onAccuracyChanged(Sensor sensor, int accuracy) 
	{
	    // Do something here if sensor accuracy changes.
	}

	  @Override
	  public final void onSensorChanged(SensorEvent event) 
	  {
	    
		  
			switch(sensor_index[Integer.parseInt(selected_sensor)])
			{
			/* Accelerometer */
			case 1:	
				textView15.setText(": " + event.values[0]);
				textView17.setText(": " + event.values[1]);
				textView19.setText(": " + event.values[2]);
				
				break;
				/* Magnetic Field */
			case 2: 
				textView15.setText(": " + event.values[0]);
				textView17.setText(": " + event.values[1]);
				textView19.setText(": " + event.values[2]);
				
				break;
			
				/* Orientation */			
			case 3:
				textView15.setText(": " + event.values[0]);
				textView17.setText(": " + event.values[1]);
				textView19.setText(": " + event.values[2]);
				
				break;
				
			/* Gyroscope */		
			case 4:
				textView15.setText(": " + event.values[0]);
				textView17.setText(": " + event.values[1]);
				textView19.setText(": " + event.values[2]);
				
				break;
				
			/* Light */			
			case 5:
				textView15.setText(": " + event.values[0]);
				
				break;
				
				/* Pressure */			
			case 6:
				textView15.setText(": " + event.values[0]);

				break;
				
				/* Temperature */			
			case 7:
				textView15.setText(": " + event.values[0]);

				break;
				
				/* Proximity */				
			case 8:
				textView15.setText(": " + event.values[0]);
				
				break;
				
				/* Gravity */			
			case 9:
				textView15.setText(": " + event.values[0]);
				textView17.setText(": " + event.values[1]);
				textView19.setText(": " + event.values[2]);
				
				break;
				
				/* Linear Acceleration */
			case 10:
				textView15.setText(": " + event.values[0]);
				textView17.setText(": " + event.values[1]);
				textView19.setText(": " + event.values[2]);
				
				break;
				
				/* Rotation Vector */			
			case 11:
				textView15.setText(": " + event.values[0]);
				textView17.setText(": " + event.values[1]);
				textView19.setText(": " + event.values[2]);

				break;				
				
			/* Relative Humidity */			
			case 12:
				textView15.setText(": " + event.values[0]);
				
				break;

			/* Ambient Temperature */
			case 13:
				textView15.setText(": " + event.values[0]);
				
				break;
				
			default:
				break;
			
			}

		  
		  //textView3.setText(str);
	  }

	  @Override
	  protected void onResume() {
	    super.onResume();
	    mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    mSensorManager.unregisterListener(this);
	  }
	  
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			
			Log.d("<x_x>", "Button Clicked!");
			
			if(v == btn3)
			{
				Intent i_graph = new Intent(this, GraphActivity.class);
				i_graph.putExtra("sensor", selected_sensor);
				startActivity(i_graph);				
			}
			
		}
		
	/*	private void storeSensorData()
		{
	        try
	        {
	        //	Log.d("", getApplicationContext().getFilesDir().getAbsolutePath() + "/rssiData.txt");
	        //	Log.d("", Environment.getDataDirectory().getPath() + "/rssiData.txt");
	            Writer output = null;
	            File file = new File("/sdcard/rssiData.txt");
	          // File file = new File(Environment.getDataDirectory().getPath() + "/rssiData.txt");
	        }
	        catch (Exception ex) 
	        {
	           ex.printStackTrace();
	        }

			
		    File myFile;  
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		    String TimeStampDB = sdf.format(cal.getTime()); 

		    CSVWriter writer = null;
		    try 
		    {
		        writer = new CSVWriter(new FileWriter("/sdcard/sensor_data_"+TimeStampDB+".csv"), ',');
		        String[] entries = "first#second#third".split("#"); // array of your values
		        writer.writeNext(entries);  
		        writer.close();
		    } 
		    catch (IOException e)
		    {
		        //error
		    }
		}

*/
}
