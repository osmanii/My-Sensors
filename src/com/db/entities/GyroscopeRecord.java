package com.db.entities;

import com.orm.SugarRecord;

public class GyroscopeRecord extends SugarRecord<GyroscopeRecord> {

	public String X;
	public String Y;
	public String Z;
	
	public GyroscopeRecord(){
		
	}
	
	public GyroscopeRecord(String x, String y, String z) {
		
		X = x;
		Y = y;
		Z = z;
	}
	
	
	
}
