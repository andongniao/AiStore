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
	private MyGridview topgtitleridview; // �������gridview
	private LinearLayout addviewll; // ��Ӷ�̬����
	private int listindex;
	private Resources rs;
	private String[] titlelist; // ��������
	private ArrayList<View> addviewlist; 
	private LayoutInflater inflater; // ��Ҫʹ��inflate����̬�������
	private Context context;
	private ArrayList<MyGridview> gridviewlist;
	private MyTask myTask;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentXml(R.layout.fclass_frist_view);
		String title = getIntent().getStringExtra("title");// ��ӱ��⣬��ȡ��������ֵ��
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

		// ���ԣ�Ů����Ʒ����
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
		// onPreExecute����������ִ�к�̨����ǰ��һЩUI����
		@Override
		protected void onPreExecute() {
			Util.startProgressDialog(context);
		}

		// doInBackground�����ڲ�ִ�к�̨����,�����ڴ˷������޸�UI
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

		// onProgressUpdate�������ڸ��½�����Ϣ
		@Override
		protected void onProgressUpdate(Object... progresses) {
		}

		// onPostExecute����������ִ�����̨��������UI,��ʾ���
		@Override
		protected void onPostExecute(Object result) {
			Util.stopProgressDialog();

			// setadapter

		}

		// onCancelled����������ȡ��ִ���е�����ʱ����UI
		@Override
		protected void onCancelled() {
			Util.stopProgressDialog();
		}
	}

}
