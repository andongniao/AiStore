package com.youai.aistore.Fclass;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Home.MyGridview;
import com.youai.aistore.NetInterface.Send;

public class FclassFristViewActivity extends BaseActivity {
	private MyGridview topgtitleridview; // 上面标题gridview
	private LinearLayout addviewll; // 添加动态布局
	private int listindex;
	private Resources rs;
	private String[] titlelist; // 标题数组
	private ArrayList<View> addviewlist; 
	private LayoutInflater inflater; // 需要使用inflate来动态载入界面
	private Context context;
	private ArrayList<MyGridview> gridviewlist;
	private MyTask myTask;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentXml(R.layout.fclass_frist_view);
		String title = getIntent().getStringExtra("title");// 添加标题，获取传过来的值，
		listindex = getIntent().getIntExtra("listindex",0);
		setTitleTxt(title);
		init();

	}

	private void init() {
		context = this;
		rs = getResources();
		addviewlist = new ArrayList<View>();
		inflater = LayoutInflater.from(context);
		gridviewlist = new ArrayList<MyGridview>();

		topgtitleridview = (MyGridview) findViewById(R.id.fclass_frist_view_topgridview);

		addviewll = (LinearLayout) findViewById(R.id.fclass_frist_view_addview_ll);

		if (listindex == 0) {
			titlelist = rs.getStringArray(R.array.fclass_frist_woman_gridview);
		} else if (listindex == 1) {
			titlelist = rs.getStringArray(R.array.fclass_frist_man_gridview);
		} else if (listindex == 2) {
			titlelist = rs.getStringArray(R.array.fclass_frist_neiyi_gridview);
		} else if (listindex == 3) {
			titlelist = rs.getStringArray(R.array.fclass_frist_tt_gridview);
		} else if (listindex == 5) {
			titlelist = rs.getStringArray(R.array.fclass_frist_tosex_gridview);
		}

		topgtitleridview
				.setAdapter(new ArrayAdapter<String>(this,
						R.layout.fclass_gridview, R.id.fclass_gridview_text,
						titlelist));

		// 测试，女性用品数据
		String[] avtextmoney = rs
				.getStringArray(R.array.fclass_gridview_avtextmoney);
		String[] avtextcomment = rs
				.getStringArray(R.array.fclass_gridview_avtextcomment);
		String[] avtextproduct = rs
				.getStringArray(R.array.fclass_gridview_avtextproduct);
		TypedArray avimgproduct = rs
				.obtainTypedArray(R.array.fclass_gridview_avimgproduct);
		int len = avimgproduct.length();
		int[] resIds = new int[len];
		for (int i = 0; i < len; i++) {
			resIds[i] = avimgproduct.getResourceId(i, 0);
		}
		avimgproduct.recycle();
		//

		for (int i = 0; i < titlelist.length; i++) {
			View v = inflater.inflate(R.layout.fclass_frist_view_added_view,
					null);
			TextView tv = (TextView) v
					.findViewById(R.id.fclass_frist_view_addview_title_tv);
			tv.setText(titlelist[i]);
			MyGridview g = (MyGridview) v
					.findViewById(R.id.fclass_frist_view_addview_gridview);
			g.setAdapter(getCategoryAdapter(resIds, avtextmoney, avtextcomment,
					avtextproduct));
			gridviewlist.add(g);
			addviewlist.add(v);
			addviewll.addView(v);

		}
		
		// TODO
		/**
		 * item view in adapter 2 import and setData
		 */
	}

	private SimpleAdapter getCategoryAdapter(int[] avimgproduct,
			String[] avtextmoney, String[] avtextcomment, String[] avtextproduct) {
		ArrayList<HashMap<String, Object>> date = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < avtextmoney.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("imgproduct", avimgproduct[i]);
			map.put("textmoney", avtextmoney[i]);
			map.put("textcomment", avtextcomment[i]);
			map.put("textproduct", avtextproduct[i]);
			date.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(
				getApplicationContext(), date,
				R.layout.fclass_frist_view_addview_item,
				new String[] { "imgproduct", "textmoney", "textcomment",
						"textproduct" }, new int[] {
						R.id.fclass_frist_view_addview_item_iv,
						R.id.fclass_frist_view_addview_item_price_tv,
						R.id.fclass_frist_view_addview_item_likes_tv,
						R.id.fclass_frist_view_addview_item_title_tv });
		return simperAdapter;

	}

	private class MyTask extends AsyncTask<Object, Object, Object> {
		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			Util.startProgressDialog(context);
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected GoodsBean doInBackground(Object... params) {
			try {

				// TODO getdata
				return null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Object... progresses) {
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(Object result) {
			Util.stopProgressDialog();

			// setadapter

		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			Util.stopProgressDialog();
		}
	}

}
