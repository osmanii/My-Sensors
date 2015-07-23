package com.asolab.osmani.mysesnsors;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AboutSensor extends Activity {
	
	String [] sensor_description = 
		{
			"Measures the acceleration force in m/s2 that is applied to a device on all three physical axes (x, y, and z), including the force of gravity.",
			"Measures the ambient room temperature in degrees Celsius (°C).",
			"Measures the force of gravity in m/s2 that is applied to a device on all three physical axes (x, y, z).",
			"Measures a device's rate of rotation in rad/s around each of the three physical axes (x, y, and z).",
			"Measures the ambient light level (illumination) in lx.",
			"Measures the acceleration force in m/s2 that is applied to a device on all three physical axes (x, y, and z), excluding the force of gravity.",
			"Measures the ambient geomagnetic field for all three physical axes (x, y, z) in micro Tesla.",
			"Measures degrees of rotation that a device makes around all three physical axes (x, y, z).",
			"Measures the ambient air pressure in hPa or mbar.",
			"Measures the proximity of an object in cm relative to the view screen of a device.",
			"Measures the relative ambient humidity in percent (%).",
			"Measures the orientation of a device by providing the three elements of the device's rotation vector.",
			"Measures the temperature of the device in degrees Celsius (°C). "
		};
	String [] sensor_uses = 
		{
			"Motion detection (shake, tilt, etc.).",
			"Monitoring air temperatures.",
			"Motion detection (shake, tilt, etc.).",
			"Rotation detection (spin, turn, etc.).",
			"Controlling screen brightness.",
			"Monitoring acceleration along a single axis.",
			"Creating a compass.",
			"Determining device position.",
			"Monitoring air pressure changes.",
			"Phone position during a call. This sensor is typically used to determine whether a handset is being held up to a person's ear",
			"Monitoring dewpoint, absolute, and relative humidity.",
			"Motion detection and rotation detection.",
			"Monitoring temperatures."
		};
			
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_sensor); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_about_sensor, menu);
		return true;
	}

}
