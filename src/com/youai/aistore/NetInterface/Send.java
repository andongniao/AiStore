package com.youai.aistore.NetInterface;

import android.content.Context;

import com.google.gson.Gson;
import com.youai.aistore.Util;

public class Send {
	private Context context;
	private Gson gson;

	public Send(Context context) {
		this.context = context;
		gson = new Gson();
		
	}
	
	/**
	 * 获取首页信息
	 */
	public void RequestHome(String time){
		String key = Util.hashKeyForDisk("AIAI.CN_"+time);
		String url = ServiceUrl.HomeUrl+key;
		
	}

}
