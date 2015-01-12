package com.youai.aistore.Fclass;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Bean.ListFclassTwo;
import com.youai.aistore.Bean.ListGoodsBean;
import com.youai.aistore.Home.HomeActivity;
import com.youai.aistore.Home.MyGridview;
import com.youai.aistore.Home.SearchResultActivity;
import com.youai.aistore.Home.SearchResultAdapter;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.Product.ProductDetailsActivity;

public class FclassFristViewActivity extends BaseActivity implements
		OnItemClickListener {
	private MyGridview toptitlegridview, g; // 上面标题gridview
	private LinearLayout addviewll; // 添加动态布局
	private int listindex;
	private Resources rs;
	private String[] titlelist; // 标题数组
	private ArrayList<View> addviewlist;
	private LayoutInflater inflater; // 需要使用inflate来动态载入界面
	private Context context;
	private ArrayList<MyGridview> gridviewlist;
	private MyTask myTask;
	private ListGoodsBean fclasslist;
	private ArrayList<GoodsBean> womenListBean;
	private FclassFristViewAdapter fclassAdapter;
	private int type,id,postion;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentXml(R.layout.fclass_frist_view);
		String title = getIntent().getStringExtra("title");// 添加标题，获取传过来的值，
		listindex = getIntent().getIntExtra("listindex", 0);
		setTitleTxt(title);
		init();
		if (Util.detect(context)) {
			myTask = new MyTask();
			myTask.execute("");
		} else {
			Util.ShowToast(context, R.string.net_work_is_error);
		}

	}

	private void init() {
		context = this;
		rs = getResources();
		addviewlist = new ArrayList<View>();
		inflater = LayoutInflater.from(context);
		gridviewlist = new ArrayList<MyGridview>();
		//顶部网格
		toptitlegridview = (MyGridview) findViewById(R.id.fclass_frist_view_topgridview);
		//商品网格
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
		//顶部网格适配器
		toptitlegridview
				.setAdapter(new ArrayAdapter<String>(this,
						R.layout.fclass_gridview, R.id.fclass_gridview_text,
						titlelist));
		toptitlegridview.setOnItemClickListener(new Titlegridviewonclick());

		// TODO
		/**
		 * item view in adapter 2 import and setData
		 */
	}

	/*
	 * 详细分类监听器
	 */
	class Titlegridviewonclick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			switch (arg2) {
			case 0:
				Intent intent = new Intent(FclassFristViewActivity.this,
						FclassMoreActivity.class);
				// titlelist数组传值给FclassFristViewActivity的标题
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("title", titlelist[arg2].toString());
				intent.putExtra("id",arg2);
				startActivity(intent);
				Util.ShowToast(context, "点击了" + titlelist[arg2]);
				break;
			case 1:
				Intent intent1 = new Intent(FclassFristViewActivity.this,
						FclassMoreActivity.class);
				// titlelist数组传值给FclassFristViewActivity的标题
				intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent1.putExtra("title", titlelist[arg2].toString());
				
				startActivity(intent1);
				Util.ShowToast(context, "点击了" + titlelist[arg2]);
				break;
			default:
				break;
			}

		}

	}
	/*
	 * 详细分类gridview监听器
	 */
	class gridviewonclick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//type = fclasslist.getList().get(0).get(arg2).getType();
			id = fclasslist.getList().get(7).get(arg2).getId();
		//	toActivity(type, id);
			Intent intent = new Intent(FclassFristViewActivity.this,
					ProductDetailsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("id",id);
			
			startActivity(intent);
			
		}

	}
	private class MyTask extends AsyncTask<Object, Object, Object> {
		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			Util.startProgressDialog(context);

		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected ListGoodsBean doInBackground(Object... params) {
			try {
				Send send = new Send(context);
				String time = String.valueOf(System.currentTimeMillis());
				fclasslist = send.RequestHome(time);
				return fclasslist;// new String(baos.toByteArray(), "gb2312");
				// TODO getdata
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
			fclasslist = (ListGoodsBean) result;
			if (fclasslist != null && fclasslist.getCode() == 200) {
				womenListBean = fclasslist.getList().get(
						fclasslist.getList().size() - 1);
				fclassAdapter = new FclassFristViewAdapter(context,
						womenListBean);
				for (int i = 0; i < titlelist.length; i++) {
					View v = inflater.inflate(
							R.layout.fclass_frist_view_added_view, null);
					TextView tv = (TextView) v
							.findViewById(R.id.fclass_frist_view_addview_title_tv);
					tv.setText(titlelist[i]);
					g = (MyGridview) v
							.findViewById(R.id.fclass_frist_view_addview_gridview);
					g.setAdapter(fclassAdapter);
					g.setOnItemClickListener(new gridviewonclick());
					gridviewlist.add(g);
					addviewlist.add(v);
					addviewll.addView(v);

				}

			} else {
				if (fclasslist != null)
					Util.ShowToast(context, fclasslist.getMsg());

			}

			// setadapter

		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		protected void onCancelled() {
			Util.stopProgressDialog();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub


	}

}
