package com.example.lbs;

import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.Query;

public class Search implements  OnPoiSearchListener, TextWatcher
{
	String keyWord ;
	private PoiResult poiResult;
	Query query;
	AMap aMap;
	Context context;

	protected void doSearchQuery(EditText editText,Context context ,AMap aMap) {
		this.aMap = aMap;
		this.context = context;
		Log.i("tag", "执行搜索方法、editText ==" + editText.getText().toString());
		keyWord = editText.getText().toString().trim();
		int currentPage = 0;
		query = new PoiSearch.Query(keyWord, "", "南京市");
		query.setPageSize(20);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页
		PoiSearch poiSearch = new PoiSearch(context, query);
		poiSearch.setOnPoiSearchListener(this);
		poiSearch.searchPOIAsyn();// 异步搜索
		
	}



	@Override
	public void onPoiItemSearched(PoiItem arg0, int arg1)
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onPoiSearched(PoiResult result, int rCode)
	{
		// TODO Auto-generated method stub
		if (rCode == 1000)
		{
			if (result != null && result.getPois() != null)
			{
				if (result.getQuery().equals(query))
			{
				poiResult = result;
				List<PoiItem> poiItems = poiResult.getPois();
				List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();
				
				if (poiItems != null && poiItems.size() > 0) {
					aMap.clear();// 清理之前的图标
					PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
					poiOverlay.removeFromMap();
					poiOverlay.addToMap();
					poiOverlay.zoomToSpan();
				} else if (suggestionCities != null
						&& suggestionCities.size() > 0) {
					showSuggestCity(suggestionCities);
				} else {
					Toast.makeText(context, "没有搜索到相关地点！", Toast.LENGTH_SHORT).show();
				}
			}
		} else {
			Toast.makeText(context, "没有搜索到相关地点！", Toast.LENGTH_SHORT).show();
		}
	} else {
		Toast.makeText(context, rCode, Toast.LENGTH_SHORT).show();
	}
			
}
	
	
	private void showSuggestCity(List<SuggestionCity> cities) {
		String infomation = "推荐城市\n";
		for (int i = 0; i < cities.size(); i++) {
			infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
					+ cities.get(i).getCityCode() + "城市编码:"
					+ cities.get(i).getAdCode() + "\n";
		}
		Toast.makeText(context, infomation, Toast.LENGTH_SHORT).show();

	}



	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after)
	{
	
		
	}



	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{		
		String newText = s.toString().trim();	
	}



	@Override
	public void afterTextChanged(Editable s)
	{
		
	}
}
