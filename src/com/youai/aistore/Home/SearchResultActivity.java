package com.youai.aistore.Home;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Bean.ListFclassTwo;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.Product.ProductDetailsActivity;
import com.youai.aistore.xlistview.XListView;
import com.youai.aistore.xlistview.XListView.IXListViewListener;

/**
 * 搜索结果
 * 
 * @author Qzr
 * 
 */
public class SearchResultActivity extends BaseActivity implements
		IXListViewListener, OnClickListener {
	private LinearLayout popll, numll, pricell;
	private XListView listView;
	private SearchResultAdapter adapter;
	private Context context;
	private MyTask myTask;
	private ArrayList<GoodsBean> list;
	private ListFclassTwo listf;
	private float pby, pay;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTitleTxt(R.string.search_bt_text);
		setContentXml(R.layout.search_result);
		init();
		if (Util.detect(context)) {
			myTask = new MyTask();
			myTask.execute("");
		} else {
			Util.ShowToast(context, R.string.net_work_is_error);
		}
	}

	private void init() {
		context = SearchResultActivity.this;
		popll = (LinearLayout) findViewById(R.id.search_result_popularity_ll);
		numll = (LinearLayout) findViewById(R.id.search_result_number_ll);
		pricell = (LinearLayout) findViewById(R.id.search_result_price_ll);

		popll.setOnClickListener(this);
		numll.setOnClickListener(this);
		pricell.setOnClickListener(this);
		listView = (XListView) findViewById(R.id.search_result_lv);
		listView.setOnItemClickListener(new mylistener());
		/*********************** 模拟数据 ***************************/
		// list = (ArrayList<GoodsBean>) getIntent().getExtras().get("list");
		// if(list!=null){
		// adapter = new SearchResultAdapter(context, list);
		// listView.setAdapter(adapter);
		// }
	}

	class mylistener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(SearchResultActivity.this,
					ProductDetailsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("id", listf.getList().get(arg2 - 1).getId());
			startActivity(intent);
		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.search_result_popularity_ll:

			break;
		case R.id.search_result_number_ll:

			break;
		case R.id.search_result_price_ll:

			break;

		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoadMore() {
		// GoodsBean s = new GoodsBean();
		// list.add(s);
		// adapter.setdata(list);
		// adapter.notifyDataSetChanged();
		// onLoad();
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
			try {
				Send send = new Send(context);
				listf = send.GetFclassTwo(MyApplication.woman_av,
						MyApplication.clickdesc, 1);
				return listf;// new String(baos.toByteArray(), "gb2312");
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
			listf = (ListFclassTwo) result;
			if (listf != null) {
				if (listf.getCode() == 200) {
					Util.ShowToast(context, listf.getMsg());

					adapter = new SearchResultAdapter(context, listf.getList());
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
