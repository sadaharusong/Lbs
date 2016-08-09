package com.example.lbs;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.col.a;
import com.amap.api.col.v;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Text;
import com.amap.api.maps.model.TextOptions;
import com.amap.api.services.core.LatLonPoint;


public class MarkerDo implements OnMarkerClickListener,OnInfoWindowClickListener
{
	private MarkerOptions markerOption;
	private AMap aMap;
	Context context;
	boolean play_flag = false;
	private LatLng position;
	com.amap.api.maps.model.Marker marker_library,marker_restaurant,marker_hall,
									marker_gym,marker_apartment,marker_apollo ;
	String apollo_id,apartment_id,gym_id,hall_id,restaurant_id,library_id;
	

	public String getApollo_id()
	{
		return apollo_id;
	}

	public String getApartment_id()
	{
		return apartment_id;
	}

	public String getGym_id()
	{
		return gym_id;
	}

	public String getHall_id()
	{
		return hall_id;
	}

	public String getRestaurant_id()
	{
		return restaurant_id;
	}

	public String getLibrary_id()
	{
		return library_id;
	}

	public void doMarker(AMap aMap,Context context)
	{
		this.aMap = aMap;
		this.context = context;
		aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
		aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
		//aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
		
		marker_library = aMap.addMarker(new MarkerOptions()
		.title("点击听解说")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.sound))
        .position(new LatLng(32.1118772,118.93179238))
        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
		.decodeResource(context.getResources(),R.drawable.marker)))
		.draggable(true));
		
		marker_restaurant = aMap.addMarker(new MarkerOptions()
		.title("点击听解说")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.sound))
        .position(new LatLng(32.10841936,118.93355191))
        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
		.decodeResource(context.getResources(),R.drawable.marker)))
		.draggable(true));
		
		
		marker_hall = aMap.addMarker(new MarkerOptions()
		.title("点击听解说")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.sound))
        .position(new LatLng(32.11688425,118.93136322))
        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
		.decodeResource(context.getResources(),R.drawable.marker)))
		.draggable(true));
		
		marker_gym = aMap.addMarker(new MarkerOptions()
		.title("点击听解说")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.sound))
        .position(new LatLng(32.11890153,118.92919064))
        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
		.decodeResource(context.getResources(),R.drawable.marker)))
		.draggable(true));
		
		marker_apartment = aMap.addMarker(new MarkerOptions()
		.title("点击听解说")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.sound))
        .position(new LatLng(32.11907418,118.93347144))
        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
		.decodeResource(context.getResources(),R.drawable.marker)))
		.draggable(true));
		
		marker_apollo = aMap.addMarker(new MarkerOptions()
		.title("点击听解说")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.sound))
        .position(new LatLng(32.10777867,118.93012404))
        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
		.decodeResource(context.getResources(),R.drawable.marker)))
		.draggable(true));
		
		marker_library.getPosition();
		marker_restaurant.getPosition();
		marker_hall.getPosition();
		marker_gym.getPosition();
		marker_apartment.getPosition();
		marker_apollo.getPosition();
		
		apollo_id = marker_apollo.getId();
		library_id = marker_library.getId();
		restaurant_id = marker_restaurant.getId();
		hall_id = marker_hall.getId();
		gym_id = marker_gym.getId();
		apartment_id = marker_apartment.getId();
	}

	@Override
	public void onInfoWindowClick(Marker marker)
	{
		// TODO Auto-generated method stub
	//	Toast.makeText(context,marker.get, Toast.LENGTH_SHORT).show();
		//marker.getTitle();
		
		 Intent intent = new Intent(context,VideoService.class);
		 if (play_flag == false)
		{
			 intent.putExtra("playing", true);
			 play_flag = true;
			 if (marker.getId().equals(library_id))
			{
				 intent.putExtra("place", "library");
			}else if (marker.getId().equals(apollo_id))
			{
				intent.putExtra("place", "apollo");
			}else if (marker.getId().equals(restaurant_id))
			{
				intent.putExtra("place", "restaurant");
			}else if (marker.getId().equals(hall_id)) {
				intent.putExtra("place", "hall");
			}else if (marker.getId().equals(gym_id)) {
				intent.putExtra("place", "gym");
			}else if (marker.getId().equals(apartment_id))
			{
				intent.putExtra("place", "apartment");
			}
			  
			  context.startService(intent);
		}else {
			 intent.putExtra("playing", false);
			 play_flag = false;
			 // context.startService(intent);
			  context.stopService(intent);
		}
         Log.i("tag", "传出去的place = " + marker.getId());
       
      //   context.startService(intent);
	}


	@Override
	public boolean onMarkerClick(Marker marker)
	{
		// TODO Auto-generated method stub
		//Toast.makeText(context, "marker的地址:"+marker.getPosition(), Toast.LENGTH_SHORT).show();
		setMarkPosition(marker.getPosition());
		return false;
	}

	public void setMarkPosition(LatLng position)
	{
		// TODO Auto-generated method stub
		this.position = position;
	}
	
	public LatLng getMarkPosition()
	{
		return position;
	}
}
