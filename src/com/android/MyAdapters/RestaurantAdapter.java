package com.android.MyAdapters;

import java.util.ArrayList;

import com.android.Models.RestaurantModel;
import com.android.ZomatoApplication.R;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class RestaurantAdapter extends ArrayAdapter<RestaurantModel>{
	Activity context;
	ArrayList<RestaurantModel> restaurantArray;

	public RestaurantAdapter(Activity context,ArrayList<RestaurantModel> restaurantArray) {
		super(context,R.layout.list_item,R.id.text,restaurantArray);
		this.context=context;
		this.restaurantArray=restaurantArray;
		// TODO Auto-generated constructor stub
	}

	@Override
	public RestaurantModel getItem(int position) {
		return restaurantArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return restaurantArray.size();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		RestaurantModel c=restaurantArray.get(position);
		if(convertView==null){
			LayoutInflater inflater=context.getLayoutInflater();
			convertView=inflater.inflate(R.layout.customrestaurants,null);

			holder = new ViewHolder();

			holder.name = (TextView) convertView.findViewById(R.id.textName);
			holder.address = (TextView) convertView.findViewById(R.id.textAddress);
			holder.cuisines = (TextView) convertView.findViewById(R.id.textCuisines);
			holder.rating=(RatingBar)convertView.findViewById(R.id.rating);

			convertView.setTag(holder);
		}
		else {
			holder=(ViewHolder)convertView.getTag();
		}
		holder.name.setText(c.getName());
		holder.address.setText(c.getAddress());
		holder.cuisines.setText(c.getCuisines());
		holder.rating.setRating(c.getRating());


		return convertView;
	}

	static class ViewHolder {
		TextView name;
		TextView address;
		TextView cuisines;
		RatingBar rating;

	}



}
