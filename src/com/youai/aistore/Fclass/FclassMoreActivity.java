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
		// ����������,�۸�Ĳ���
		popll = (LinearLayout) findViewById(R.id.fclass_more_popularity_ll);
		numll = (LinearLayout) findViewById(R.id.fclass_more_number_ll);
		pricell = (LinearLayout) findViewById(R.id.fclass_more_price_ll);
		// ��ͷͼƬ
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
	 * �б����ݵĵ���¼�
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

		// onPreExecute����������ִ�к�̨����ǰ��һЩUI����
		@Override
		protected void onPreExecute() {
			// textView.setText("loading...");
			Util.startProgressDialog(context);
		}

		// doInBackground�����ڲ�ִ�к�̨����,�����ڴ˷������޸�UI
		@Override
		protected Object doInBackground(Object... params) {
			/*
			 * ����fclassHome,��������ID���ݡ�
			 * ͨ��ID����ȡС������Ϣ��
			 */
			int getid = getIntent().getIntExtra("id", 1);
			// Send send = new Send(context);	
			switch (x) {
			case 1:
				if (p % 2 != 0) { //��ͷ���ϣ���������
					listf = send.GetFclassTwo(getid,
							MyApplication.clickdasc, 1);

				} else {		//��ͷ���ϣ���������
					listf = send.GetFclassTwo(getid,
							MyApplication.clickdesc, 1);
				}
				break;
			case 2:
				if (n % 2 != 0) {//��ͷ���ϣ���������
					listf = send.GetFclassTwo(getid,
							MyApplication.salesdasc, 1);

				} else {		//��ͷ���£���������
					listf = send.GetFclassTwo(getid,
							MyApplication.salesdesc, 1);
				}
			break;
			case 3:
				if (j % 2 != 0) {//��ͷ���ϣ��۸�����
					listf = send.GetFclassTwo(getid,
							MyApplication.priceasc, 1);

				} else {		//��ͷ���£��۸���
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

		// onProgressUpdate�������ڸ��½�����Ϣ
		@Override
		protected void onProgressUpdate(Object... progresses) {
		}

		// onPostExecute����������ִ�����̨��������UI,��ʾ���
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

		// onCancelled����������ȡ��ִ���е�����ʱ����UI
		@Override
		protected void onCancelled() {
			Util.stopProgressDialog();
		}

	}
}
