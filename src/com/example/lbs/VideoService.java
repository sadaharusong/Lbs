package com.example.lbs;

import java.io.IOException;

import com.amap.api.maps.model.LatLng;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class VideoService extends Service 
{
	private MediaPlayer library_mp3,restaurant_map3,apratment_mp3,apollo_mp3,gym_mp3,hall_map3;
	Context context;
	MarkerDo markerDo = new MarkerDo();
	/*public void play(Context context)
	{
		choiceMP3();
	}
	
	private void choiceMP3()
	{
		// TODO Auto-generated method stub
		
		try
		{
			Log.i("tag", "正在播放MP3");
			library_mp3.prepare();
			library_mp3.start(); 
			release(library_mp3);
		} catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		 
	}
	
	private void release(MediaPlayer mp3)
	{
		mp3.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				// TODO Auto-generated method stub
				mp.release();
			}
		});
	}*/
	
	

	@Override
	public void onCreate()
	{
		
		//library_mp3.start();
		// TODO Auto-generated method stub
		
		super.onCreate();
		library_mp3 = MediaPlayer.create(this, R.raw.library);
		apollo_mp3 = MediaPlayer.create(this, R.raw.apollo);
		apratment_mp3 = MediaPlayer.create(this, R.raw.apartment);
		restaurant_map3 = MediaPlayer.create(this, R.raw.restaurant);
		hall_map3 = MediaPlayer.create(this, R.raw.hall);
		gym_mp3 = MediaPlayer.create(this, R.raw.gym);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO Auto-generated method stub
		 boolean playing = intent.getBooleanExtra("playing", false);
		 String place = intent.getStringExtra("place");
		/* Log.i("tag", "收到的place = " + place + "markerDo.getApollo_id() = " + markerDo.getApollo_id() 
				 + "????????? = " + (place.equals("Marker6") + ""));*/
	        if (playing && place != null) {
	        	//apollo_mp3.start();
	        	if (place.equals("apollo"))
				{   
	        		apollo_mp3.start();
				}else if (place.equals("apartment")) {
					
					apratment_mp3.start();
				}else if (place.equals("gym")) {
					
					gym_mp3.start();
				}else if (place.equals("hall")) {
					
					hall_map3.start();
				}else if (place.equals("library")) {
					
					library_mp3.start();
				}else if (place.equals("restaurant")) {
					
					restaurant_map3.start();
				}
	        	
	        
	        }
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy()
	{
		
		// TODO Auto-generated method stub
		super.onDestroy();
		//library_mp3.release();
		 	
        	
        		apollo_mp3.stop();
        		apollo_mp3.release();
			
				apratment_mp3.stop();
				apratment_mp3.release();
				
				gym_mp3.stop();
				gym_mp3.release();
			
				hall_map3.stop();
				hall_map3.release();
			
				library_mp3.stop();
				library_mp3.release();
				
				restaurant_map3.stop();
				restaurant_map3.release();
			
		stopSelf();
	}
}
