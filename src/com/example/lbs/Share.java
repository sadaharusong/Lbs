package com.example.lbs;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.share.ShareSearch;
import com.amap.api.services.share.ShareSearch.OnShareSearchListener;
import com.amap.api.services.share.ShareSearch.ShareFromAndTo;
import com.amap.api.services.share.ShareSearch.ShareNaviQuery;

public class Share implements OnShareSearchListener
{ 
	private ShareSearch mShareSearch;
	private LatLonPoint START ;
	private LatLonPoint END ;
	private Context context;
	private AMap mAMap;
	private String url;
	
	public void setPosition(Context context,AMap aMap ,LatLng endlatLng , double location_s ,double location_e)
	{
		START = new LatLonPoint(location_s, location_e);
		END = new LatLonPoint(endlatLng.latitude, endlatLng.longitude);
		this.context = context;
		this.mAMap = aMap;
	}

	@Override
	public void onBusRouteShareUrlSearched(String arg0, int arg1)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDrivingRouteShareUrlSearched(String arg0, int arg1)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationShareUrlSearched(String arg0, int arg1)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNaviShareUrlSearched(String url, int errorCode)
	{
		// TODO Auto-generated method stub
		if (errorCode == 1000) {
			shareUrl(url, "导航分享");
		} else {
			Toast.makeText(context, "分享错误！", Toast.LENGTH_SHORT).show();

		//	ToastUtil.showerror(this.getApplicationContext(), errorCode);
		}
	}

	@Override
	public void onPoiShareUrlSearched(String arg0, int arg1)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWalkRouteShareUrlSearched(String url, int errorCode)
	{
		// TODO Auto-generated method stub
		if (errorCode == 1000) {
			shareUrl(url, "步行路径规划分享");
		} else {
			Toast.makeText(context, "分享错误！", Toast.LENGTH_SHORT).show();
			//ToastUtil.showerror(this.getApplicationContext(), errorCode);
		}
	}
	
	/**
	 * 导航短串分享
	 */
	public void shareNavi() {

		mShareSearch = new ShareSearch(context);
		mShareSearch.setOnShareSearchListener(this);
		addRouteMarker();
		ShareFromAndTo fromAndTo = new ShareFromAndTo(START, END);
		ShareNaviQuery query = new ShareNaviQuery(fromAndTo,
				ShareSearch.NaviDefault);
	//	showProgressDialog();
		mShareSearch.searchNaviShareUrlAsyn(query);
	}
	
	/**
	 * 添加线路marker
	 */
	private void addRouteMarker() {
		mAMap.clear();
		addMarker(START.getLatitude(), START.getLongitude(), "", "",
				BitmapDescriptorFactory.fromResource(R.drawable.amap_start));
		addMarker(END.getLatitude(), END.getLongitude(), "", "",
				BitmapDescriptorFactory.fromResource(R.drawable.amap_end));
		LatLngBounds.Builder builder = LatLngBounds.builder();
		builder.include(new LatLng(START.getLatitude(), START.getLongitude()));
		builder.include(new LatLng(END.getLatitude(), END.getLongitude()));
		mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),
				50));
	}
	
	/**
	 * 添加marker方法
	 * 
	 * @param lat
	 * @param lon
	 * @param title
	 * @param snippet
	 * @param icon
	 */
	private void addMarker(double lat, double lon, String title,
			String snippet, BitmapDescriptor icon) {
		MarkerOptions markerOption = new MarkerOptions();
		LatLng markerPoint = new LatLng(lat, lon);
		markerOption.position(markerPoint);
		markerOption.title(title).snippet(snippet);
		markerOption.icon(icon);
		mAMap.addMarker(markerOption);
	}

	/**
	 * 调用系统分享短串
	 * 
	 * @param url
	 * @param title
	 */
	private void shareUrl(String url, String title) {
		
		
		Log.d("tag", url);
		this.url = url;
		/*Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, url);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, title));*/
	}
	
	public void showShare() {
		
		 ShareSDK.initSDK(context);
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 

		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher, "邮一游");
		 
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle("分享旅游线路");
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl(url);
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("O(∩_∩)O哈哈~，我在这里旅游，给你分享了路线和语音哟！");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 oks.setImagePath("/phone/icon666.png");//确保SDcard下面存在此张图片
		 
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl(url);
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("O(∩_∩)O哈哈~，我在这里旅游，给你们分享了路线和语音哟！");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite("邮一游");
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl(url);

		// 启动分享GUI
		 oks.show(context);
		 }
}
