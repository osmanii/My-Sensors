package com.asolab.osmani.mysesnsors;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import jxl.*; 
import jxl.write.*;
import jxl.write.Number;

public class DataCollectionActivity extends Activity implements OnClickListener, SensorEventListener
{
	
	private TextView clock;
	private TextView monitoredSensors;
	private Button start;
	private Button stop;
	
	Sensor [] mSensors;
    int [] sensor_index;
    String [] sensor_name;

	private String listOfSensorsMonitored;
    
	private boolean collectData, fileWrite;	
	
    final int MSG_START_TIMER = 0;
    final int MSG_STOP_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;

    StopWatch timer = new StopWatch();
    final int REFRESH_RATE = 100;

    private String fileName;
    
	private SensorManager mSensorManager = null;

	
	WritableWorkbook workbook;
	WritableSheet sheetAcc;
	WritableSheet sheetMag;
	WritableSheet sheetOrn;
	WritableSheet sheetGyr;
	WritableSheet sheetLit;
	WritableSheet sheetPrs;
	WritableSheet sheetTemp_1;
	WritableSheet sheetPrx;
	WritableSheet sheetGrv;
	WritableSheet sheetLAcc;
	WritableSheet sheetRVct;
	WritableSheet sheetHum;	
	WritableSheet sheetTemp_2;
	
	int [] countIndex;
	
	String [] worksheetNames = {"Accelerometer", "Magnetic Field", "Orientation", "Gyroscope" ,
			"Light", "Pressure", "Temperature", "Proximity", "Gravity", "Linear Acceleration",
			"Rotation Vector", "Humidity", "Temperature"};
	
	
	Handler mHandler = new Handler()
    {
    	long millis;
    	String time;
        @Override
        public void handleMessage(Message msg) 
        {
            super.handleMessage(msg);
            switch (msg.what) 
            {
            case MSG_START_TIMER:
                timer.start(); //start timer
                mHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
                break;

            case MSG_UPDATE_TIMER:
            	millis = timer.getElapsedTime();
            	clock.setText(""+ String.format("%d:%d",millis/ (60 * 1000), TimeUnit.MILLISECONDS.toSeconds(millis) - 60*(millis/ (60 * 1000)) ));
                mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER,REFRESH_RATE); //text view is updated every second, though the timer is still running
                break;                                  
            case MSG_STOP_TIMER:
                mHandler.removeMessages(MSG_UPDATE_TIMER); // no more updates.
                timer.stop();//stop timer
                time = ""+ String.format("%d:%d",millis/ (60 * 1000), TimeUnit.MILLISECONDS.toSeconds(millis) - 60*(millis/ (60 * 1000)) );
            	millis = timer.getElapsedTime();
            	clock.setText(time);
                break;
            default:
                break;
            }
        }
    };
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_collection);
		
        clock = (TextView)findViewById(R.id.clock);
        monitoredSensors = (TextView)findViewById(R.id.monitoredSensors);
        start = (Button)findViewById(R.id.start);
        start.setOnClickListener(this);
        stop = (Button)findViewById(R.id.stop); 
        stop.setOnClickListener(this);
        
        collectData = false;
        fileWrite = true;
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		mSensors = new Sensor[13];
		
		sensor_index = getResources().getIntArray(R.array.sensor_index);
		sensor_name = getResources().getStringArray(R.array.object_names);

		int i = 0;
		
		while( i < sensor_index.length)
		{
			mSensors[i] = mSensorManager.getDefaultSensor(sensor_index[i]);
			i++;
		}


		
	}

    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
    	
		int i = 0;
		listOfSensorsMonitored = "List of Sensors currently monitored...\n\n";
		while( i < sensor_index.length)
		{
			if(mSensors[i]!=null)
			{
				mSensorManager.registerListener(this, mSensors[i], SensorManager.SENSOR_DELAY_NORMAL);
				listOfSensorsMonitored += sensor_name[i] + "\n"; 
			}
			
			i++;
		}
		
		monitoredSensors.setText(listOfSensorsMonitored);
		
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
	    // Unregister the listener
	    mSensorManager.unregisterListener(this);
	    
	    if(!fileWrite)
		try
		{
				workbook.write();
				workbook.close();					
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		super.onStop();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_data_collection, menu);
		return true;
	}
	
	
	public void initializeExcelFile()
	{
		int i;
		
		i = 0;
		
		countIndex = new int[13];
		
		while(i<sensor_index.length)
		{
			countIndex[i++] = 1;
		}
		
		try
        {		
		    fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
			
//			workbook = Workbook.createWorkbook(new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + fileName + ".xls"));
			workbook = Workbook.createWorkbook(new File("/sdcard/" + fileName + ".xls"));			
			
			if(workbook==null)
				Log.d("<x_x>", "Workbook is null.....");
			
//			workbook = Workbook.createWorkbook(new File("/sdcard/rssiData.xls"));

			Log.d("<x_x>", getApplicationContext().getFilesDir().getAbsolutePath() + "/" + fileName + ".xls");
			Log.d("<x_x>", getApplicationContext().getFilesDir().getAbsolutePath() + "/rssiData.txt");
	        Log.d("<x_x>", Environment.getDataDirectory().getPath() + "/rssiData.txt");
	        Log.d("<x_x>", Environment.getExternalStorageDirectory() + "/rssiData.txt");
	        //  File file = new File("/sdcard/rssiData.txt");
	        //  File file = new File(Environment.getDataDirectory().getPath() + "/rssiData.txt");
						
//            File fileSystemRoot = Environment.getExternalStorageDirectory();            
			
/*	        <item>TYPE_ACCELROMETER</item>
	        <item>TYPE_AMBIENT_TEMPERATURE</item>
	        <item>TYPE_GRAVITY</item>
	        <item>TYPE_GYROSCOPE</item>
	        <item>TYPE_LIGHT</item>
	        <item>TYPE_LINEAR_ACCELERATION</item>
	        <item>TYPE_MAGNETIC_FIELD</item>
	        <item>TYPE_ORIENTATION</item>
	        <item>TYPE_PRESSURE</item>
	        <item>TYPE_PROXIMITY</item>
	        <item>TYPE_RELATIVE_HUMIDITY</item>
	        <item>TYPE_ROTATION_VECTOR</item>
			<item>TYPE_TEMPERATURE</item>                
*/
	        
			i = 0;
			if(mSensors[0]!=null)
			{
				sheetAcc = workbook.createSheet(worksheetNames[0], i++);
				Label label = new Label(0, 0, "X"); 
				sheetAcc.addCell(label);
				label = new Label(1, 0, "Y"); 
				sheetAcc.addCell(label);
				label = new Label(2, 0, "Z"); 
				sheetAcc.addCell(label);
			}
			if(mSensors[1]!=null)
			{
				sheetTemp_1 = workbook.createSheet(worksheetNames[6], i++);
				Label label = new Label(0, 0, "X"); 
				sheetTemp_1.addCell(label);

			}
			if(mSensors[2]!=null)
			{
				sheetGrv = workbook.createSheet(worksheetNames[8], i++);
				Label label = new Label(0, 0, "X"); 
				sheetGrv.addCell(label);
				label = new Label(1, 0, "Y"); 
				sheetGrv.addCell(label);
				label = new Label(2, 0, "Z"); 
				sheetGrv.addCell(label);

			}
			if(mSensors[3]!=null)
			{
				sheetGyr = workbook.createSheet(worksheetNames[3], i++);
				Label label = new Label(0, 0, "X"); 
				sheetGyr.addCell(label);
				label = new Label(1, 0, "Y"); 
				sheetGyr.addCell(label);
				label = new Label(2, 0, "Z"); 
				sheetGyr.addCell(label);
			}
			if(mSensors[4]!=null)
			{
				sheetLit = workbook.createSheet(worksheetNames[4], i++);
				Label label = new Label(0, 0, "X"); 
				sheetLit.addCell(label);
			}
			if(mSensors[5]!=null)
			{
				sheetLAcc = workbook.createSheet(worksheetNames[9], i++);
				Label label = new Label(0, 0, "X"); 
				sheetLAcc.addCell(label);
				label = new Label(1, 0, "Y"); 
				sheetLAcc.addCell(label);
				label = new Label(2, 0, "Z"); 
				sheetLAcc.addCell(label);
			}
			if(mSensors[6]!=null)
			{
				sheetMag = workbook.createSheet(worksheetNames[1], i++);
				Label label = new Label(0, 0, "X"); 
				sheetMag.addCell(label);
				label = new Label(1, 0, "Y"); 
				sheetMag.addCell(label);
				label = new Label(2, 0, "Z"); 
				sheetMag.addCell(label);				

			}
			if(mSensors[7]!=null)
			{
				sheetOrn = workbook.createSheet(worksheetNames[2], i++);
				Label label = new Label(0, 0, "X"); 
				sheetOrn.addCell(label);
				label = new Label(1, 0, "Y"); 
				sheetOrn.addCell(label);
				label = new Label(2, 0, "Z"); 
				sheetOrn.addCell(label);				

			}
			if(mSensors[8]!=null)
			{
				sheetPrs = workbook.createSheet(worksheetNames[5], i++);
				Label label = new Label(0, 0, "X"); 
				sheetPrs.addCell(label);
				
			}
			if(mSensors[9]!=null)
			{
				sheetPrx = workbook.createSheet(worksheetNames[7], i++);
				Label label = new Label(0, 0, "X"); 
				sheetPrx.addCell(label);
				
			}
			if(mSensors[10]!=null)
			{
				sheetHum = workbook.createSheet(worksheetNames[11], i++);
				Label label = new Label(0, 0, "X"); 
				sheetHum.addCell(label);
			}
			if(mSensors[11]!=null)
			{
				sheetRVct = workbook.createSheet(worksheetNames[10], i++);
				Label label = new Label(0, 0, "X"); 
				sheetRVct.addCell(label);
				label = new Label(1, 0, "Y"); 
				sheetRVct.addCell(label);
				label = new Label(2, 0, "Z"); 
				sheetRVct.addCell(label);				
			}
			if(mSensors[12]!=null)
			{
				sheetTemp_2 = workbook.createSheet(worksheetNames[12], i++);
				Label label = new Label(0, 0, "X"); 
				sheetTemp_2.addCell(label);
			}			
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        } 
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if (v == start) 
		{
			collectData = true;
			fileWrite = false;
			initializeExcelFile();
			mHandler.sendEmptyMessage(MSG_START_TIMER);			
		}
		else if(v == stop)
		{
			collectData = false;
		
			mHandler.sendEmptyMessage(MSG_STOP_TIMER);
			try
			{
					workbook.write();
					workbook.close();	
					fileWrite = true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		// TODO Auto-generated method stub
		if(!collectData)
			return;

		int sensorType = event.sensor.getType();
		Number number;
		
		//listOfLists.get(sensorType).add();
		try
		{
		
			switch(sensorType)
			{		
				/* Accelerometer */
				case 1:	
					number = new Number(0, countIndex[0], event.values[0]); 
					sheetAcc.addCell(number);
					number = new Number(1, countIndex[0], event.values[1]); 
					sheetAcc.addCell(number);
					number = new Number(2, countIndex[0], event.values[2]); 
					sheetAcc.addCell(number);
					
					countIndex[0]++;

					break;
				
				/* Magnetic Field */
				case 2:									
					number = new Number(0, countIndex[1], event.values[0]); 
					sheetMag.addCell(number);
					number = new Number(1, countIndex[1], event.values[1]); 
					sheetMag.addCell(number);
					number = new Number(2, countIndex[1], event.values[2]); 
					sheetMag.addCell(number);
	
					countIndex[1]++;
	
					break;
				
				/* Orientation */
				case 3:
					number = new Number(0, countIndex[2], event.values[0]); 
					sheetOrn.addCell(number);
					number = new Number(1, countIndex[2], event.values[1]); 
					sheetOrn.addCell(number);
					number = new Number(2, countIndex[2], event.values[2]); 
					sheetOrn.addCell(number);
	
					countIndex[2]++;
	
					break;
					
				/* Gyroscope */		
				case 4:
					number = new Number(0, countIndex[3], event.values[0]); 
					sheetGyr.addCell(number);
					number = new Number(1, countIndex[3], event.values[1]); 
					sheetGyr.addCell(number);
					number = new Number(2, countIndex[3], event.values[2]); 
					sheetGyr.addCell(number);
	
					countIndex[3]++;
	
					break;
					
				/* Light */			
				case 5:
					number = new Number(0, countIndex[4], event.values[0]); 
					sheetLit.addCell(number);
	
					countIndex[4]++;
	
					break;
					
				/* Pressure */			
				case 6:
					number = new Number(0, countIndex[5], event.values[0]); 
					sheetPrs.addCell(number);
	
					countIndex[5]++;
	
					break;
					
				/* Temperature */			
				case 7:
					number = new Number(0, countIndex[6], event.values[0]); 
					sheetTemp_1.addCell(number);
	
					countIndex[6]++;
	
					break;
					
				/* Proximity */			
				case 8:
					number = new Number(0, countIndex[7], event.values[0]); 
					sheetPrx.addCell(number);
	
					countIndex[7]++;
	
					break;
					
				/* Gravity */			
				case 9:
					number = new Number(0, countIndex[8], event.values[0]); 
					sheetGrv.addCell(number);
					number = new Number(1, countIndex[8], event.values[1]); 
					sheetGrv.addCell(number);
					number = new Number(2, countIndex[8], event.values[2]); 
					sheetGrv.addCell(number);
	
					countIndex[8]++;
	
					break;
					
				/* Linear Acceleration */			
				case 10:
					number = new Number(0, countIndex[9], event.values[0]); 
					sheetLAcc.addCell(number);
					number = new Number(1, countIndex[9], event.values[1]); 
					sheetLAcc.addCell(number);
					number = new Number(2, countIndex[9], event.values[2]); 
					sheetLAcc.addCell(number);
	
					countIndex[9]++;
	
					break;
					
				/* Rotation Vector */			
				case 11:
					number = new Number(0, countIndex[10], event.values[0]); 
					sheetRVct.addCell(number);
					number = new Number(1, countIndex[10], event.values[1]); 
					sheetRVct.addCell(number);
					number = new Number(2, countIndex[10], event.values[2]); 
					sheetRVct.addCell(number);
	
					countIndex[10]++;
	
					break;
					
				/* Relative Humidity */			
				case 12:
					number = new Number(0, countIndex[11], event.values[0]); 
					sheetHum.addCell(number);
	
					countIndex[11]++;
	
					break;
				/* Ambient Temperature */			
				case 13:
					number = new Number(0, countIndex[12], event.values[0]); 
					sheetTemp_2.addCell(number);
	
					countIndex[12]++;
	
					break;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
	}


}
