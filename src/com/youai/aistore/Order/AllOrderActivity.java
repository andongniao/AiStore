package com.youai.aistore.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.ListOrderBean;
import com.youai.aistore.Bean.ListOrderBean.OrderBean;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.xlistview.XListView;
import com.youai.aistore.xlistview.XListView.IXListViewListener;

/**
 * 全部订单
 * 
 * @author Qzr
 * 
 */
public class AllOrderActivity extends BaseActivity implements
		IXListViewListener {
	private Context context;
	private XListView lv;
	private MyTask myTask;
	private ListOrderBean listbean;
	private ArrayList<OrderBean> list;
	private AllOrderAdapter adapter;
	private int page, addtype;
	private View isnull;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTitleTxt(R.string.all_order_title);
		setTopLeftBackground(R.drawable.btn_back);
		setContentXml(R.layout.order_all);
		init();
		if (Util.detect(context)) {
			myTask = new MyTask();
			myTask.execute("");
		} else {
			Util.ShowToast(context, R.string.net_work_is_error);
		}
	}

	private void init() {
		page = 1;
		addtype = 1;
		context = this;
		lv = (XListView) findViewById(R.id.order_all_listview);
		isnull = findViewById(R.id.order_isnull_ll);
		// lv.GoneFooterView();
		lv.setFocusable(false);
		lv.setPullLoadEnable(true);
		lv.setXListViewListener(this);
		lv.setEmptyView(isnull);

	}

	@Override
	public void onRefresh() {
		page = 1;
		addtype = 1;
		if (Util.detect(context)) {
			myTask = new MyTask();
			myTask.execute("");
		} else {
			Util.ShowToast(context, R.string.net_work_is_error);
		}
	}

	@Override
	public void onLoadMore() {
		page += 1;
		addtype = 2;
		if (Util.detect(context)) {
			myTask = new MyTask();
			myTask.execute("");
		} else {
			Util.ShowToast(context, R.string.net_work_is_error);
		}
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
				Send s = new Send(context);
				String userid = MyApplication.UserId;
				listbean = s.getAllOrderlist(userid, page);
				return listbean;
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
			onLoad();
			Util.stopProgressDialog();
			listbean = (ListOrderBean) result;
			if (listbean != null) {
				if (listbean.getCode() == 200) {
					if (addtype == 1) {
						list = listbean.getList();
						adapter = new AllOrderAdapter(context, list);
						lv.setAdapter(adapter);
					} else {
						ArrayList<OrderBean> l = listbean.getList();
						if (l != null && l.size() > 0) {
							list.addAll(l);
							if (adapter == null) {
								adapter = new AllOrderAdapter(context, list);
								lv.setAdapter(adapter);
							} else {
								adapter.setdata(list);
								adapter.notifyDataSetChanged();
							}
						}else{
							page-=1;
							Util.ShowToast(context,R.string.page_is_final);
						}
					}
				} else {
					Util.ShowToast(context, listbean.getMsg());
				}
			} else {
				if(addtype == 2){
					page-=1;
				}
				Util.ShowToast(context, R.string.net_work_is_error);
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			Util.stopProgressDialog();
		}
	}

	private void onLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd   hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		lv.setRefreshTime(date);
	}
}
