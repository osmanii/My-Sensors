package com.asolab.osmani.mysesnsors;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;


public class GraphActivity extends Activity implements OnClickListener, SensorEventListener
{
	private SensorManager mSensorManager = null;
	private GraphicalView mChartView;
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    XYSeries series1 = new XYSeries("A-x");
    XYSeries series2 = new XYSeries("A-y");
    XYSeries series3 = new XYSeries("A-z");
    
    private final int history_size = 100;

	private Sensor mSensor;
	
    String selected_sensor;
	int [] sensor_index;
	String [] sensor_name;
	String graphTitle = "Nothing";

    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);                
        setContentView(R.layout.sensor_graph);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        
		Intent myIntent = getIntent();
		Bundle b = myIntent.getExtras();
		
		selected_sensor = b.getString("sensor");

		sensor_index = getResources().getIntArray(R.array.sensor_index);
		sensor_name = getResources().getStringArray(R.array.object_names);

		setTitle(sensor_name[Integer.parseInt(selected_sensor)] + " Graph");
		
		mSensor = mSensorManager.getDefaultSensor(sensor_index[Integer.parseInt(selected_sensor)]);

		graphSettings();
    }
    
    @SuppressLint("NewApi")
	protected void onResume() 
    {
    	  super.onResume();
    	  if (mChartView == null) 
    	  {
    		setRenderer();
    	    LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
    	    mChartView = ChartFactory.getLineChartView(this, getDataset(), renderer);
    	    
    	    if( Build.VERSION.SDK_INT > 10) 
    	    {
    	    	layout.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    	    }
    	    else layout.addView(mChartView, 0);
    	  } 
    	  else 
    	  {
    	    mChartView.repaint();
    	  }
  	    mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }
    
    @Override
    protected void onStop() 
    {
	    // Unregister the listener
	    mSensorManager.unregisterListener(this);
	    super.onStop();
    }
    
    public XYMultipleSeriesDataset getDataset()
    {
    	dataset.addSeries(series1);
    	dataset.addSeries(series2);
    	dataset.addSeries(series3);
    	
    	return dataset;
    }
    
    private void setRenderer() 
    {
        renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(30);
        renderer.setChartTitleTextSize(30);
        renderer.setLabelsTextSize(20);
        renderer.setLegendTextSize(20);
        renderer.setPointSize(5f);       
        renderer.setMargins(new int[] { 20, 30, 15, 0 });
        
        XYSeriesRenderer r1 = new XYSeriesRenderer();
        r1.setColor(Color.GREEN);
        r1.setPointStyle(PointStyle.CIRCLE);
        r1.setFillBelowLine(false);
        r1.setFillPoints(true);
        renderer.addSeriesRenderer(r1);
        
        XYSeriesRenderer r2 = new XYSeriesRenderer();
        r2.setColor(Color.BLUE);
        r2.setPointStyle(PointStyle.CIRCLE);
        r2.setFillBelowLine(false);
        r2.setFillPoints(true);
        renderer.addSeriesRenderer(r2);

        XYSeriesRenderer r3 = new XYSeriesRenderer();
        r3.setColor(Color.RED);
        r3.setPointStyle(PointStyle.CIRCLE);
        r3.setFillBelowLine(false);
        r3.setFillPoints(true);
        renderer.addSeriesRenderer(r3);

        
        setChartSettings(renderer);
    }
    
    private void setChartSettings(XYMultipleSeriesRenderer renderer) 
    {
        renderer.setChartTitle(graphTitle);
        renderer.setXTitle("Time");
        renderer.setYTitle("Sensor values");
        renderer.setXLabelsColor(Color.BLACK);
        renderer.setBackgroundColor(Color.WHITE);
        renderer.setApplyBackgroundColor(true);
        renderer.setRange(new double[] {0,6,-70,40});
     //   renderer.setRange(new double[] {20,20,30,10});
        renderer.setFitLegend(false);
        renderer.setAxesColor(Color.BLACK);
        renderer.setShowGrid(true);
        renderer.setXAxisMin(0);
        renderer.setXAxisMax(history_size);
        renderer.setXLabels(history_size/5);
        renderer.setYLabels(20);
        //renderer.setYLabels(20);
        renderer.setZoomEnabled(false);
        renderer.setMarginsColor(Color.WHITE);
    }

	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		
//		if(v == btn)
//		{
//		}
		
	}
    
	public void addData(double x, double y, double z)
	{
		if(series1.getItemCount() < history_size)
		{
			series1.add(series1.getItemCount(), x);
			series2.add(series2.getItemCount(), y);
			series3.add(series3.getItemCount(), z);
		}
		else
		{
			for(int i = 0; i<history_size-1; i++)
			{
				series1.add(i, series1.getY(i+1));				
				series2.add(i, series2.getY(i+1));	
				series3.add(i, series3.getY(i+1));
			}
			series1.add(history_size-1,x);
			series2.add(history_size-1,y);
			series3.add(history_size-1,z);
		}
		
	}
	
	
    // This method will update the UI on new sensor events
    public void onSensorChanged(SensorEvent sensorEvent) 
    {
    	
    	synchronized (this) 
    	{    		    		
			addData(sensorEvent.values[0],sensorEvent.values[1], sensorEvent.values[2]);
    		mChartView.repaint();
    	}
    }

	public void onAccuracyChanged(Sensor sensor, int accuracy) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void graphSettings()
	{
        

		switch(sensor_index[Integer.parseInt(selected_sensor)])
		{
		/* Accelerometer */
		case 1:	
			graphTitle = "Time vs 3-Axis Acceleration";
	        renderer.setYAxisMax(mSensor.getMaximumRange());
	        renderer.setYAxisMin(-1*mSensor.getMaximumRange());
			break;
		
		/* Magnetic Field */
		case 2: 
			graphTitle = "Time vs 3-Axis Magnetic Field";
	        renderer.setYAxisMax(mSensor.getMaximumRange());
	        renderer.setYAxisMin(-1*mSensor.getMaximumRange());
			break;
		
		/* Orientation */
		case 3:
			graphTitle = "Time vs 3-Axis Orientation Vector";
	        renderer.setYAxisMax(mSensor.getMaximumRange());
	        renderer.setYAxisMin(-1*mSensor.getMaximumRange());
			break;
			
		/* Gyroscope */		
		case 4:
			graphTitle = "Time vs 3-Axis Rate of Rotation";
	        renderer.setYAxisMax(mSensor.getMaximumRange());
	        renderer.setYAxisMin(-1*mSensor.getMaximumRange());
			break;
			
		/* Light */			
		case 5:
			graphTitle = "Time vs Light Intensity";
	        renderer.setYAxisMax(mSensor.getMaximumRange());
	        renderer.setYAxisMin(-1*mSensor.getMaximumRange());
			break;
			
		/* Pressure */			
		case 6:
			graphTitle = "Time vs Pressure";
	        renderer.setYAxisMax(mSensor.getMaximumRange());
	        renderer.setYAxisMin(-1*mSensor.getMaximumRange());
			break;
			
		/* Temperature */			
		case 7:
			graphTitle = "Time vs Temperature";
	        renderer.setYAxisMax(mSensor.getMaximumRange());
	        renderer.setYAxisMin(-1*mSensor.getMaximumRange());
			break;
			
		/* Proximity */			
		case 8:
			graphTitle = "Time vs Proximity";
	        renderer.setYAxisMax(mSensor.getMaximumRange());
	        renderer.setYAxisMin(-1*mSensor.getMaximumRange());
			break;
			
		/* Gravity */			
		case 9:
			graphTitle = "Time vs 3-Axis Gravity";
	        renderer.setYAxisMax(mSensor.getMaximumRange());
	        renderer.setYAxisMin(-1*mSensor.getMaximumRange());
			break;
			
		/* Linear Acceleration */			
		case 10:
			graphTitle = "Time vs 3-Axis Linear Acceleration";
	        renderer.setYAxisMax(mSensor.getMaximumRange());
	        renderer.setYAxisMin(-1*mSensor.getMaximumRange());
			break;
			
		/* Rotation Vector */			
		case 11:
			graphTitle = "Time vs 3-Axis Rotation";
	        renderer.setYAxisMax(mSensor.getMaximumRange());
	        renderer.setYAxisMin(-1*mSensor.getMaximumRange());
			break;
			
		/* Relative Humidity */			
		case 12:
			graphTitle = "Time vs Humidity";
	        renderer.setYAxisMax(mSensor.getMaximumRange());
	        renderer.setYAxisMin(-1*mSensor.getMaximumRange());
			break;
		/* Ambient Temperature */			
		case 13:
			graphTitle = "Time vs Temperature";
	        renderer.setYAxisMax(mSensor.getMaximumRange());
	        renderer.setYAxisMin(-1*mSensor.getMaximumRange());
			break;
		case 14:
			break;
		
		}
	}
}