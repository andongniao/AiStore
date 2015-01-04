package com.youai.aistore.NetInterface;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.CommentsBean;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Bean.ListCommentsBean;
import com.youai.aistore.Bean.ListGoodsBean;

public class Send {
	private Context context;
	private Gson gson;

	public Send(Context context) {
		this.context = context;
		gson = new Gson();
		
	}
	
	/**
	 * ��ȡ��ҳ��Ϣ
	 */
	@SuppressWarnings("unchecked")
	public ListGoodsBean RequestHome(String time){
		ListGoodsBean List = new ListGoodsBean();
		String key = Util.hashKeyForDisk("AIAI.CN_"+time);
		String url = ServiceUrl.HomeUrl+key;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);
		
		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				if (object.get("code") != null && object.getInt("code") == 200) {
					List.setCode(200);
					List.setMsg(object.getString("message"));
					JSONObject data = object.getJSONObject("data");
					Type type = new TypeToken<ArrayList<GoodsBean>>() {}.getType();
					ArrayList<ArrayList<GoodsBean>> l = new ArrayList<ArrayList<GoodsBean>>();
					for(int i=1;i<9;i++){
					String json = data.getString("ad_"+i).toString();
					ArrayList<GoodsBean> s = gson.fromJson(json, type);
					l.add(s);
					}
					List.setList(l);
					return List;
				} else {
					List.setMsg(object.getString("message"));
					List.setCode(object.getInt("code"));
					return List;

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			List.setCode(500);
			List.setMsg(context.getResources().getString(
					R.string.http_status_code_error));
			return List;
		}
		
		return null;
		
	}
	
	
	/**
	 * ��ȡ��Ʒ��Ϣ
	 */
	@SuppressWarnings("unchecked")
	public GoodsBean GetProductDetails(int id){
		GoodsBean bean = null;
		String url = ServiceUrl.Product_Details_Url+id;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);
		
		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				if (object.get("code") != null && object.getInt("code") == 200) {
					JSONObject data = object.getJSONObject("data");
						Type type = new TypeToken<GoodsBean>() {}.getType();
						String json = data.toString();
						bean = gson.fromJson(json, type);
						JSONArray array = data.getJSONArray("picurls");
						ArrayList<String> picurls = new ArrayList<String>();
						for(int i=0;i<array.length();i++){
							picurls.add(array.getString(i));
						}
						bean.setPicurls(picurls);
						bean.setCode(200);
						bean.setMsg(object.getString("message"));
					return bean;
				} else {
					bean.setMsg(object.getString("message"));
					bean.setCode(object.getInt("code"));
					return bean;
					
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			bean.setCode(500);
			bean.setMsg(context.getResources().getString(
					R.string.http_status_code_error));
			return bean;
		}
		
		return null;
		
	}

	
	/**
	 * ��ȡ��Ʒ����
	 */
	@SuppressWarnings("unchecked")
	public ListCommentsBean GetProductComments(int id,int page){
		ListCommentsBean list = new ListCommentsBean();
		CommentsBean bean = null;
		String url = ServiceUrl.Product_comments_Url_head+id+ServiceUrl.Product_comments_Url_foot+page;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);
		
		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				if (object.get("code") != null && object.getInt("code") == 200) {
					JSONArray data = object.getJSONArray("data");
						Type type = new TypeToken<ArrayList<CommentsBean>>() {}.getType();
						String json = data.toString();
						ArrayList<CommentsBean> l = gson.fromJson(json, type);
						list.setList(l);
						list.setCode(200);
						list.setMsg(object.getString("message"));
					return list;
				} else {
					list.setMsg(object.getString("message"));
					list.setCode(object.getInt("code"));
					return list;
					
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			list.setCode(500);
			list.setMsg(context.getResources().getString(
					R.string.http_status_code_error));
			return list;
		}
		
		return null;
		
	}

	
}