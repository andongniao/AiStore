package com.youai.aistore.Order;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.OrderDetailsBean;
import com.youai.aistore.Bean.OrderDetailsBean.Goods;
import com.youai.aistore.NetInterface.Send;

/**
 * ��������
 * 
 * @author Qzr
 * 
 */
public class OrderDetailActivity extends BaseActivity {
	private MyTask myTask;
	private Context context;
	private OrderDetailsBean bean;
	private TextView number_tv, statu_tv, time_tv, pp_tv, tel_tv, money_tv,
			youfei_tv, address_tv;
	private LinearLayout gopay_ll;
	private OrderDetailsAdapter adapter;
	private ArrayList<Goods> list;
	private ListView lv;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTitleTxt(R.string.order_details_title);
		setTopLeftBackground(R.drawable.btn_back);
		setContentXml(R.layout.order_detail);
		init();
		if (Util.detect(context)) {
			myTask = new MyTask(1);
			myTask.execute("");
		} else {
			Util.ShowToast(context, R.string.net_work_is_error);
		}
	}

	private void init() {
		context = this;
		number_tv = (TextView) findViewById(R.id.order_detail_number_tv);
		statu_tv = (TextView) findViewById(R.id.order_detail_statu_tv);
		time_tv = (TextView) findViewById(R.id.order_detail_time_tv);
		pp_tv = (TextView) findViewById(R.id.order_detail_pp_tv);
		tel_tv = (TextView) findViewById(R.id.order_detail_tel_tv);
		money_tv = (TextView) findViewById(R.id.order_detail_money_tv);
		youfei_tv = (TextView) findViewById(R.id.order_detail_youfei_tv);
		address_tv = (TextView) findViewById(R.id.order_detail_address_tv);
		lv = (ListView) findViewById(R.id.order_detail_listview);
		gopay_ll = (LinearLayout) findViewById(R.id.order_detail_gopay_ll);
	}

	private class MyTask extends AsyncTask<Object, Object, Object> {
		private int type;

		public MyTask(int type) {
			this.type = type;
		}

		// onPreExecute����������ִ�к�̨����ǰ��һЩUI����
		@Override
		protected void onPreExecute() {
			// textView.setText("loading...");
			Util.startProgressDialog(context);
		}  

		//doInBackground�����ڲ�ִ�к�̨����,�����ڴ˷������޸�UI  
		@Override  
		protected Object doInBackground(Object... params) {  
			try {  
				if(type==1){
				Send s = new Send(context);
				String userid = MyApplication.UserId;
				bean = s.getOrderDetalis(getIntent().getStringExtra("orderid"), userid);
				return bean;
				}
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
			if (type == 1) {
				bean = (OrderDetailsBean) result;
				if (bean != null) {
					if (bean.getCode() == 200) {
						number_tv.setText(bean.getOrder_sn());
						statu_tv.setText(bean.getOrder_zt());
						time_tv.setText(bean.getFormated_add_time());
						pp_tv.setText(bean.getConsignee());
						tel_tv.setText(bean.getTel());
						money_tv.setText("��"+bean.getFormated_goods_amount()+"Ԫ");
						youfei_tv.setText("��"+bean.getFormated_shipping_fee()+"Ԫ");
						address_tv.setText(bean.getAddress());
						list = bean.getGoods();
						adapter = new OrderDetailsAdapter(context, list);
						lv.setAdapter(adapter);
						if (bean.getPay_status() == 2) {
							gopay_ll.setVisibility(View.VISIBLE);
						}
					} else {
						Util.ShowToast(context, bean.getMsg());
					}
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			} else {

			}

		}

		// onCancelled����������ȡ��ִ���е�����ʱ����UI
		@Override
		protected void onCancelled() {
			Util.stopProgressDialog();
		}
	}

}
