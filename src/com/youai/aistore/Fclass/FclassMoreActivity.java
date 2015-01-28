package com.youai.aistore.Fclass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.CustomProgressDialog;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Bean.ListFclassTwo;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.Product.ProductDetailsActivity;
import com.youai.aistore.xlistview.XListView;
import com.youai.aistore.xlistview.XListView.IXListViewListener;

public class FclassMoreActivity extends BaseActivity implements
IXListViewListener, OnClickListener {
	private LinearLayout popll, numll, pricell;
	private XListView listView;
	private FclassMoreAdapter adapter;
	private Context context;
	private MyTask myTask;
	private ArrayList<GoodsBean> list;
	private ListFclassTwo listf;
	private ImageView pricell_iv;
	private Send send;
	public static boolean isfinish;
	private int addtype,page;
	private String desc;
	private boolean pp,xl,price;
	private Dialog progressDialog;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setTopLeftBackground(R.drawable.btn_search_navigation_back);
		setContentXml(R.layout.fclass_more);
		String title = getIntent().getStringExtra("title");
		send = new Send(context);
		setTitleTxt(title);
		init();
		addtype = 1;
		page = 1;
		if (Util.detect(context)) {
			myTask = new MyTask();
			myTask.execute("");
		} else {
			Util.ShowToast(context, R.string.net_work_is_error);
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		if(isfinish){
			finish();
		}
	}

	private void init() {
		pp = true;
		xl = false;
		price = false;
		desc = MyApplication.clickdasc;
		isfinish = false;
		context = FclassMoreActivity.this;
		// 人气，销量,价格的布局
		popll = (LinearLayout) findViewById(R.id.fclass_more_popularity_ll);
		numll = (LinearLayout) findViewById(R.id.fclass_more_number_ll);
		pricell = (LinearLayout) findViewById(R.id.fclass_more_price_ll);
		// 箭头图片
		pricell_iv = (ImageView) findViewById(R.id.fclass_more_price_img);

		popll.setOnClickListener(this);
		numll.setOnClickListener(this);
		pricell.setOnClickListener(this);

		listView = (XListView) findViewById(R.id.fclass_more_lv);
		listView.setOnItemClickListener(new mylistener());
		listView.setFocusable(false);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);

	}
	/*
	 * 列表数据的点击事件
	 */
	class mylistener implements OnItemClickListener {

		@SuppressWarnings("static-access")
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(0<arg2 && arg2<list.size()+1){
				Intent intent = new Intent(FclassMoreActivity.this,
						ProductDetailsActivity.class);
				intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("finishid", 2);
				intent.putExtra("id", list.get(arg2 - 1).getId());
				startActivity(intent);
			}
		}

	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.fclass_more_popularity_ll:
			if(!pp){

				price = !price;
				desc = MyApplication.clickdasc;
				pp = true;

				if (Util.detect(context)) {
					myTask = new MyTask();
					myTask.execute("");
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}
			xl = false;
			break;

		case R.id.fclass_more_number_ll:
			if(!xl){
				price = !price;
				desc = MyApplication.salesdasc;
				xl = true;
				if (Util.detect(context)) {
					myTask = new MyTask();
					myTask.execute("");
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}
			pp = false;

			break;
		case R.id.fclass_more_price_ll:
			if(!price){
				desc = MyApplication.priceasc;
				pricell_iv.setImageResource(R.drawable.order_top);
				price = true;
			}else{
				desc = MyApplication.pricedesc;
				pricell_iv.setImageResource(R.drawable.order_bottom);
				price = false;
			}
			if (Util.detect(context)) {
				myTask = new MyTask();
				myTask.execute("");
			} else {
				Util.ShowToast(context, R.string.net_work_is_error);
			}
			pp = false;
			xl = false;
			break;

		}
	}

	@Override
	public void onRefresh() {
		addtype = 1;
		page = 1;
		if (Util.detect(context)) {
			myTask = new MyTask();
			myTask.execute("");
		} else {
			Util.ShowToast(context, R.string.net_work_is_error);
		}
	}

	@Override
	public void onLoadMore() {
		addtype = 2;
		page += 1;
		if (Util.detect(context)) {
			myTask = new MyTask();
			myTask.execute("");
		} else {
			Util.ShowToast(context, R.string.net_work_is_error);
		}
	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		if(addtype==1){
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd   hh:mm:ss");
			String date = sDateFormat.format(new java.util.Date());
			listView.setRefreshTime(date);
		}
	}

	private class MyTask extends AsyncTask<Object, Object, Object> {

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			// textView.setText("loading...");
			startProgressDialog(context);
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected Object doInBackground(Object... params) {
			/*接收fclasshome,发过来的数据。*/
			int getid = getIntent().getIntExtra("id", -1);
			// Send send = new Send(context);
			listf = send.GetFclassTwo(getid,desc, page);
			return listf;

		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Object... progresses) {
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(Object result) {
			onLoad();
			stopProgressDialog();
			listf = (ListFclassTwo) result;
			if (listf != null) {
				if (listf.getCode() == 200) {
					if(addtype == 1){
						list =listf.getList();
						if(list.size()>0){
							if(adapter!=null){
								adapter.setdata(list);
								adapter.notifyDataSetChanged();
							}else{
								adapter = new FclassMoreAdapter(context,list);
								listView.setAdapter(adapter);
							}
						}else{
							Util.ShowToast(context, R.string.page_is_final);
						}
					}else if(addtype == 2){
						ArrayList<GoodsBean> l = listf.getList();
						if(l.size()>0){
							list.addAll(l);
							if(adapter!=null){
								adapter.setdata(list);
								adapter.notifyDataSetChanged();
							}else{
								adapter = new FclassMoreAdapter(context,list);
								listView.setAdapter(adapter);
							}
						}else{
							page-=1;
							Util.ShowToast(context, R.string.page_is_final);
						}
					}

				} else if(listf != null && listf.getCode() == 500){
					Util.ShowToast(context, R.string.net_work_is_error);
				}else{
					if (listf != null)
						Util.ShowToast(context, listf.getMsg());

				}
			}else{
				if(addtype == 2){
					page-=1;
				}
				Util.ShowToast(context, R.string.net_work_is_error);
			}

		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			stopProgressDialog();
		}

	}

	/**
	 * 启动Loding...
	 * 
	 * @param context
	 */
	public void startProgressDialog(Context context) {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(context);
		}

		progressDialog.show();
	}

	/**
	 * 关闭Loding...
	 */
	public void stopProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
}
