package com.example.lbs;

import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

public class Line
{
	Polyline polyline;
	public void showline(AMap aMap)
	{
		polyline = aMap.addPolyline((new PolylineOptions())
				.add(new LatLng(32.1118772,118.93179238),
					 new LatLng(32.10841936,118.93355191),
					 new LatLng(32.11688425,118.93136322),
					 new LatLng(32.11890153,118.92919064),
					 new LatLng(32.11907418,118.93347144),
					 new LatLng(32.10777867,118.93012404))
				.width(10).geodesic(true).color(Color.BLACK));
	}
	
	
}
