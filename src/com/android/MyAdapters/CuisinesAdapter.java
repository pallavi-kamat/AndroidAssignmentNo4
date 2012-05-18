package com.android.MyAdapters;

import java.util.ArrayList;

import com.android.Models.CuisinesModel;
import com.android.ZomatoApplication.*;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CuisinesAdapter extends ArrayAdapter<CuisinesModel> {
	Activity context;
	ArrayList<CuisinesModel> cuisineArray;
	
	public CuisinesAdapter(Activity context,ArrayList<CuisinesModel> cuisineArray) {
		super(context,R.layout.list_item,R.id.text,cuisineArray);
		this.context=context;
		this.cuisineArray=cuisineArray;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public CuisinesModel getItem(int position) {
		return cuisineArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		CuisinesModel c=cuisineArray.get(position);
		if(convertView==null){
			LayoutInflater inflater=context.getLayoutInflater();
			convertView=inflater.inflate(R.layout.customcuisines,null);
			
			holder = new ViewHolder();
			
			holder.name = (TextView) convertView
					.findViewById(R.id.textName);
	
			
			convertView.setTag(holder);
		}
		else {
			holder=(ViewHolder)convertView.getTag();
		}
	//	text=(TextView)layout.findViewById(R.id.text);
	//	text.setText(info.getiName()+"\t"+info.getiAge());
		
	
		holder.name.setText(c.getName());
	
		
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView name;		
	}
	


}
