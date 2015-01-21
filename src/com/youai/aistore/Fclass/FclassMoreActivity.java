package com.youai.aistore.Fclass;

import java.util.ArrayList;

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
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Bean.ListFclassTwo;
import com.youai.aistore.Home.SearchResultAdapter;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.Product.ProductDetailsActivity;
import com.youai.aistore.xlistview.XListView;
import com.youai.aistore.xlistview.XListView.IXListViewListener;

public class FclassMoreActivity extends BaseActivity implements IXListViewListener, OnClickListener {
	private LinearLayout popll, numll, pricell;
	private XListView listView;
	private FclassMoreAdapter adapter;
	private Context context;
	private MyTask myTask;
	private ArrayList<GoodsBean> list;
	private ListFclassTwo listf, listf1;
	private ImageView popll_iv, numll_iv, pricell_iv;
	private int p = 1, n = 1, j = 1,x;
	private Send send;
	public static boolean isfinish;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentXml(R.layout.fclass_more);
		String title = getIntent().getStringExtra("title");
		send = new Send(context);
		setTitleTxt(title);
		init();
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
		isfinish = false;
		context = FclassMoreActivity.this;
		// 人气，销量,价格的布局
		popll = (LinearLayout) findViewById(R.id.fclass_more_popularity_ll);
		numll = (LinearLayout) findViewById(R.id.fclass_more_number_ll);
		pricell = (LinearLayout) findViewById(R.id.fclass_more_price_ll);
		// 箭头图片
		popll_iv = (ImageView) findViewById(R.id.fclass_more_popularity_img);
		numll_iv = (ImageView) findViewById(R.id.fclass_more_number_img);
		pricell_iv = (ImageView) findViewById(R.id.fclass_more_price_img);

		popll.setOnClickListener(this);
		numll.setOnClickListener(this);
		pricell.setOnClickListener(this);

		listView = (XListView) findViewById(R.id.fclass_more_lv);
		listView.setOnItemClickListener(new mylistener());

	}
	/*
	 * 列表数据的点击事件
	 */
	class mylistener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(FclassMoreActivity.this,
					ProductDetailsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("finishid", 2);
			intent.putExtra("id", listf.getList().get(arg2 - 1).getId());
			startActivity(intent);
		}

	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.fclass_more_popularity_ll:
			x = 1 ;
			
			if (p % 2 != 0) {
				popll_iv.setImageResource(R.drawable.order_top);
				
				if (Util.detect(context)) {
					myTask = new MyTask();
					myTask.execute("");
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
				p++;
			} else {
				popll_iv.setImageResource(R.drawable.order_bottom);
				if (Util.detect(context)) {
					myTask = new MyTask();
					myTask.execute("");
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
				p++;
			}
			
			// order();
			
			break;

		case R.id.fclass_more_number_ll:
			x = 2 ;
			if (n % 2 != 0) {
				numll_iv.setImageResource(R.drawable.order_top);
				
				if (Util.detect(context)) {
					myTask = new MyTask();
					myTask.execute("");
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
				n++;
			} else {
				numll_iv.setImageResource(R.drawable.order_bottom);
				
				if (Util.detect(context)) {
					myTask = new MyTask();
					myTask.execute("");
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
				n++;
			}
			break;
		case R.id.fclass_more_price_ll:
			x = 3 ;
			if (j % 2 != 0) {
				pricell_iv.setImageResource(R.drawable.order_top);
				
				if (Util.detect(context)) {
					myTask = new MyTask();
					myTask.execute("");
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
				j++;
			} else {
				pricell_iv.setImageResource(R.drawable.order_bottom);
				
				if (Util.detect(context)) {
					myTask = new MyTask();
					myTask.execute("");
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
				j++;
			}
			break;

		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoadMore() {

	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
	}

	private class MyTask extends AsyncTask<Object, Object, Object> {

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			// textView.setText("loading...");
			Util.startProgressDialog(context);
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected Object doInBackground(Object... params) {
			/*
			 * 接收fclassHome,发过来的ID数据。
			 * 通过ID，读取小分类信息。
			 */
			int getid = getIntent().getIntExtra("id", 1);
			// Send send = new Send(context);	
			switch (x) {
			case 1:
				if (p % 2 != 0) { //箭头向上，人气正序
					listf = send.GetFclassTwo(getid,
							MyApplication.clickdasc, 1);

				} else {		//箭头向上，人气倒序
					listf = send.GetFclassTwo(getid,
							MyApplication.clickdesc, 1);
				}
				break;
			case 2:
				if (n % 2 != 0) {//箭头向上，销量正序
					listf = send.GetFclassTwo(getid,
							MyApplication.salesdasc, 1);

				} else {		//箭头向下，销量倒序
					listf = send.GetFclassTwo(getid,
							MyApplication.salesdesc, 1);
				}
			break;
			case 3:
				if (j % 2 != 0) {//箭头向上，价格正序
					listf = send.GetFclassTwo(getid,
							MyApplication.priceasc, 1);

				} else {		//箭头向下，价格倒序
					listf = send.GetFclassTwo(getid,
							MyApplication.pricedesc, 1);
				}
			break;
			default:
				listf = send.GetFclassTwo(getid,
						MyApplication.clickdesc, 1);
				break;
			}
			
			//listf = send.GetFclassTwo(getid,MyApplication.salesdesc, 1);
			return listf;

		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Object... progresses) {
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(Object result) {
			Util.stopProgressDialog();
			listf = (ListFclassTwo) result;
			if (listf != null) {
				if (listf.getCode() == 200) {
					Util.ShowToast(context, listf.getMsg());

					adapter = new FclassMoreAdapter(context, listf.getList());
					listView.setAdapter(adapter);

				} else {
					Util.ShowToast(context, listf.getMsg());
				}
			}

		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			Util.stopProgressDialog();
		}

	}
}
