package com.asolab.osmani.mysesnsors;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<String> 
{

	private LayoutInflater mInflater;
	
	private String[] mStrings;
	private TypedArray mIcons;
	
	private int mViewResourceId;
	
	int [] sensor_index;
	
	public ListAdapter(Context ctx, int viewResourceId, String[] strings, TypedArray icons) 
	{
		super(ctx, viewResourceId, strings);
		
		sensor_index = ctx.getResources().getIntArray(R.array.sensor_index);
		
		mInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		mStrings = strings;
		mIcons = icons;
		
		mViewResourceId = viewResourceId;
	}

	@Override
	public int getCount() 
	{
		return mStrings.length;
	}

	@Override
	public String getItem(int position) 
	{
		return mStrings[position];
	}

	@Override
	public long getItemId(int position) 
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView = mInflater.inflate(mViewResourceId, null);
		
		Log.d("<x_x>", "" + position);
		
		ImageView iv;
		if(MainActivity.myMap.get(sensor_index[position] + "") == true)
		{
			iv = (ImageView)convertView.findViewById(R.id.imageView1);
			iv.setImageResource(R.drawable.av);
		}
		else
		{
			iv = (ImageView)convertView.findViewById(R.id.imageView1);
			iv.setImageResource(R.drawable.nav);			
		}

		
		TextView tv = (TextView)convertView.findViewById(R.id.textView1);
		tv.setText(mStrings[position]);
		
		return convertView;
	}
}