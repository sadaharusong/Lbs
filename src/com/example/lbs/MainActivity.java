package com.example.lbs;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.mapcore.util.f;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener,
		LocationSource, AMapLocationListener
{

	private MapView mapView;
	private AMap aMap;

	private Button normal_but;
	private Button sate_but;
	private Button night_but;
	private Button traffic_but;
	private Button location_but;
	private Button place_but;
	private Button circle_but;
	private Button line_but;

	boolean traffic_flag = false;
	boolean location_flag = false;

	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		aMap = mapView.getMap();
		aMap.setLocationSource(this);
		init();
		
	}



	private void init()
	{
		// TODO Auto-generated method stub
		normal_but = (Button) findViewById(R.id.normal);
		sate_but = (Button) findViewById(R.id.sate);
		night_but = (Button) findViewById(R.id.night);
		traffic_but = (Button) findViewById(R.id.traffic);
		location_but = (Button) findViewById(R.id.location);
		place_but = (Button) findViewById(R.id.place);
		circle_but = (Button) findViewById(R.id.circle);
		line_but = (Button) findViewById(R.id.line);

		normal_but.setOnClickListener(this);
		sate_but.setOnClickListener(this);
		night_but.setOnClickListener(this);
		traffic_but.setOnClickListener(this);
		location_but.setOnClickListener(this);
		place_but.setOnClickListener(this);
		circle_but.setOnClickListener(this);
		line_but.setOnClickListener(this);
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.normal:
			aMap.setMapType(AMap.MAP_TYPE_NORMAL);
			break;
		case R.id.sate:
			aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.night:
			aMap.setMapType(AMap.MAP_TYPE_NIGHT);
			break;
		case R.id.traffic:
			if (traffic_flag == false)
			{
				aMap.setTrafficEnabled(false);
				traffic_flag = true;
			} else
			{
				aMap.setTrafficEnabled(true);
				traffic_flag = false;
			}
			break;
		case R.id.location:
			if (location_flag == false)
			{
				aMap.setMyLocationEnabled(false);
				location_flag = true;
			} else
			{
				aMap.setMyLocationEnabled(true);
				location_flag = false;
			}
			break;

		case R.id.place:
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.position(new LatLng( 32.05000,118.78333));
			markerOptions.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.ic_launcher));

			aMap.addMarker(markerOptions);
			break;

		case R.id.circle:

			CircleOptions circleOptions = new CircleOptions();
			circleOptions.center(new LatLng(32.05000,118.78333));
			circleOptions.radius(10000);
			circleOptions.fillColor(Color.GREEN);

			aMap.addCircle(circleOptions);
			break;

		case R.id.line:

			PolylineOptions polylineOptions = new PolylineOptions();
			polylineOptions.add(new LatLng(32.05000,118.78333), new LatLng(
					 23.16667,113.23333));
			polylineOptions.color(Color.BLUE);
			polylineOptions.setDottedLine(true);
			polylineOptions.width(10);

			aMap.addPolyline(polylineOptions);

			break;

		}
	}

	@Override
	public void activate(OnLocationChangedListener arg0)
	{
		// TODO Auto-generated method stub
		mListener = arg0;
		if (mlocationClient == null)
		{
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			// 设置定位监听
			mlocationClient.setLocationListener(this);
			// 设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			// 设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();
		}
	}

	@Override
	public void deactivate()
	{
		// TODO Auto-generated method stub
		mListener = null;
		if (mlocationClient != null)
		{
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}

	@Override
	public void onLocationChanged(AMapLocation arg0)
	{
		// TODO Auto-generated method stub
		if (mListener != null && arg0 != null)
		{
			if (arg0 != null && arg0.getErrorCode() == 0)
			{
				mListener.onLocationChanged(arg0);// 显示系统小蓝点
			} else
			{
				String errText = "定位失败," + arg0.getErrorCode() + ": "
						+ arg0.getErrorInfo();
				Log.e("AmapErr", errText);
			}
		}
	}
}
