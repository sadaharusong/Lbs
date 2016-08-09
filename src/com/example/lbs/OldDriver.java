package com.example.lbs;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkRouteResult;

public class OldDriver implements OnRouteSearchListener
{
	List<LatLonPoint> through;
	Context context;
	AMap aMap;
	Polyline polyline;
	private RouteSearch mRouteSearch;
	private DriveRouteResult mDriveRouteResult;
	
	public void doTripRoad(Context context,AMap aMap)
	{
		this.context = context;
		this.aMap = aMap;
		mRouteSearch = new RouteSearch(context);
		mRouteSearch.setRouteSearchListener(this);
		searchRouteResult(2, RouteSearch.DrivingDefault);
	}

	
	public void searchRouteResult(int routeType, int mode)
	{	
			final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
					new LatLonPoint(32.10577023,118.93142223), new LatLonPoint(32.11926954,118.92737746));
			through = new ArrayList<>();
			through.add(new LatLonPoint(32.10777867,118.93012404));
			through.add(new LatLonPoint(32.10841936,118.93355191));
			through.add(new LatLonPoint(32.1118772,118.93179238));
			through.add(new LatLonPoint(32.11688425,118.93136322));
			through.add(new LatLonPoint(32.11907418,118.93347144));
			through.add(new LatLonPoint(32.11890153,118.92919064));
			
			DriveRouteQuery query = new DriveRouteQuery(fromAndTo , mode,through,null,"");
			mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询	
	}
	@Override
	public void onBusRouteSearched(BusRouteResult arg0, int arg1)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDriveRouteSearched(DriveRouteResult result, int errorCode)
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				//dissmissProgressDialog();
				aMap.clear();// 清理地图上的所有覆盖物
				if (errorCode == 1000) {
					if (result != null && result.getPaths() != null) {
						if (result.getPaths().size() > 0) {
							mDriveRouteResult = result;
							final DrivePath drivePath = mDriveRouteResult.getPaths()
									.get(0);
							/* DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
				                        context, aMap, drivePath, mDriveRouteResult.getStartPos(),
				                        mDriveRouteResult.getTargetPos());*/
							/* DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(context,
									 	aMap, drivePath, mDriveRouteResult.getStartPos(),  
									 	mDriveRouteResult.getTargetPos());  */
							DriveRouteColorfulOverLay drivingRouteOverlay = new DriveRouteColorfulOverLay(
									aMap, drivePath,
									mDriveRouteResult.getStartPos(),
									mDriveRouteResult.getTargetPos(), through);
							drivingRouteOverlay.setNodeIconVisibility(true);//设置节点marker是否显示
							//drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
							drivingRouteOverlay.removeFromMap();
							drivingRouteOverlay.addToMap();
							drivingRouteOverlay.zoomToSpan();
							
							polyline = aMap.addPolyline((new PolylineOptions())
									.add(new LatLng(32.10577023,118.93142223),
										 new LatLng(32.10777867,118.93012404),
										 new LatLng(32.10841936,118.93355191),
										 new LatLng(32.1118772,118.93179238),
										 new LatLng(32.11688425,118.93136322),
										 new LatLng(32.11907418,118.93347144),
										 new LatLng(32.11890153,118.92919064),
										 new LatLng(32.11926954,118.92737746)
											)
									.width(10).geodesic(true).color(Color.BLACK));
							/*mBottomLayout.setVisibility(View.VISIBLE);
							int dis = (int) drivePath.getDistance();
							int dur = (int) drivePath.getDuration();
							String des = AMapUtil.getFriendlyTime(dur)+"("+AMapUtil.getFriendlyLength(dis)+")";
							mRotueTimeDes.setText(des);
							mRouteDetailDes.setVisibility(View.VISIBLE);
							int taxiCost = (int) mDriveRouteResult.getTaxiCost();
							mRouteDetailDes.setText("打车约"+taxiCost+"元");
							mBottomLayout.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									Intent intent = new Intent(mContext,
											DriveRouteDetailActivity.class);
									intent.putExtra("drive_path", drivePath);
									intent.putExtra("drive_result",
											mDriveRouteResult);
									startActivity(intent);
								}
							});*/
						} else if (result != null && result.getPaths() == null) {
							Toast.makeText(context, "没有结果！", Toast.LENGTH_SHORT).show();
						}

					} else {
						Toast.makeText(context, "没有结果！", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(context, "错误！", Toast.LENGTH_SHORT).show();
				}
				
	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult arg0, int arg1)
	{
		// TODO Auto-generated method stub
		
	}
}
