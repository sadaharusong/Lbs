package com.example.lbs;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.autonavi.aps.amapapi.model.AmapLoc;

public class FindRoad implements OnRouteSearchListener
{
	private LatLonPoint mStartPoint ;//起点，
	private LatLonPoint mEndPoint;//终点，
	private RouteSearch mRouteSearch;
	private WalkRouteResult mWalkRouteResult;
	
	Context context;
	AMap aMap;
	
	public void setPosition(LatLng endlatLng , double location_s ,double location_e)
	{
		mStartPoint = new LatLonPoint(location_s, location_e);
		mEndPoint = new LatLonPoint(endlatLng.latitude, endlatLng.longitude);
	}
	
	public void doFindRoad(Context context ,AMap aMap)
	{
		this.context = context;
		this.aMap = aMap;
		mRouteSearch = new RouteSearch(context);
		mRouteSearch.setRouteSearchListener(this);
		searchRouteResult(3, RouteSearch.WalkDefault);
	}
	

	
	public void searchRouteResult(int routeType, int mode)
	{	
		
	
			if (mEndPoint == null) {
				Toast.makeText(context, "请选择你要去的景点", Toast.LENGTH_SHORT).show();
			}
			final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
					mStartPoint, mEndPoint);
			WalkRouteQuery query = new WalkRouteQuery(fromAndTo, mode);
			mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
	
	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult result, int errorCode)
	{
		// TODO Auto-generated method stub
	//	dissmissProgressDialog();
		aMap.clear();// 清理地图上的所有覆盖物
		if (errorCode == 1000) {
			if (result != null && result.getPaths() != null) {
				if (result.getPaths().size() > 0) {
					mWalkRouteResult = result;
					final WalkPath walkPath = mWalkRouteResult.getPaths()
							.get(0);
					
					WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
							context, aMap, walkPath,
							mWalkRouteResult.getStartPos(),
							mWalkRouteResult.getTargetPos());
					walkRouteOverlay.removeFromMap();
					walkRouteOverlay.addToMap();
					walkRouteOverlay.zoomToSpan();
					/*mBottomLayout.setVisibility(View.VISIBLE);
					int dis = (int) walkPath.getDistance();
					int dur = (int) walkPath.getDuration();
					String des = AMapUtil.getFriendlyTime(dur)+"("+AMapUtil.getFriendlyLength(dis)+")";
					mRotueTimeDes.setText(des);
					mRouteDetailDes.setVisibility(View.GONE);
					mBottomLayout.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(mContext,
									WalkRouteDetailActivity.class);
							intent.putExtra("walk_path", walkPath);
							intent.putExtra("walk_result",
									mWalkRouteResult);
							startActivity(intent);
						}
					});*/
				} else if (result != null && result.getPaths() == null) {
					Toast.makeText(context, "没有查询到结果", Toast.LENGTH_SHORT).show();
				}

			} else {
				Toast.makeText(context, "没有查询到结果", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(context, "查询错误", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	

	@Override
	public void onBusRouteSearched(BusRouteResult arg0, int arg1)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDriveRouteSearched(DriveRouteResult result, int errorCode)
	{
		
	}
}
