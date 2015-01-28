package com.youai.aistore.Order;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.youai.aistore.BaseActivity;
import com.youai.aistore.CustomProgressDialog;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.Base;
import com.youai.aistore.Bean.OrderDetailsBean;
import com.youai.aistore.Bean.OrderDetailsBean.Goods;
import com.youai.aistore.NetInterface.Send;

/**
 * 订单详情
 * 
 * @author Qzr
 * 
 */
public class OrderDetailActivity extends BaseActivity implements OnClickListener{
	private MyTask myTask;
	private Context context;
	private OrderDetailsBean bean;
	private TextView number_tv, statu_tv, time_tv, pp_tv, tel_tv, money_tv,
	youfei_tv, address_tv;
	private Button cancel_btn,gopay_btn;
	@SuppressWarnings("unused")
	private LinearLayout gopay_ll;
	private OrderDetailsAdapter adapter;
	private ArrayList<Goods> list;
	private OrderListview lv;
	private Handler mHandler;
	private Dialog progressDialog;
	private Base base;
	private boolean ishave;
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;
	private final String PARTNER = MyApplication.PARTNER;
	private final String SELLER = MyApplication.SELLER;
	private final String RSA_PRIVATE = MyApplication.RSA_PRIVATE;
	@SuppressWarnings("unused")
	private final String RSA_PUBLIC = MyApplication.RSA_PUBLIC;
	private final String Notify_Url = MyApplication.Notify_Url; 

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
		lv = (OrderListview) findViewById(R.id.order_detail_listview);
		gopay_ll = (LinearLayout) findViewById(R.id.order_detail_gopay_ll);
		cancel_btn = (Button) findViewById(R.id.order_detail_cancel_btn);
		cancel_btn.setOnClickListener(this);
		gopay_btn = (Button) findViewById(R.id.order_detail_gopay_btn);
		gopay_btn.setOnClickListener(this);
		cancel_btn.setVisibility(View.GONE);
		gopay_btn.setVisibility(View.GONE);
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case SDK_PAY_FLAG: {
					Result resultObj = new Result((String) msg.obj);
					String resultStatus = resultObj.resultStatus;
					String memo = resultObj.memo;
					if (TextUtils.equals(resultStatus, "9000")) {
						ishave = false;
						for(int i=0;i<MyApplication.order_list.size();i++){
							if(MyApplication.order_list.get(i)==bean.getOrder_sn()){
								ishave = true;
								break;
							}
						}
						if(!ishave){
							MyApplication.order_list.add(bean.getOrder_sn());
						}
						if (Util.detect(context)) {
							myTask = new MyTask(2);
							myTask.execute("");
						} else {
							Util.ShowToast(context, R.string.net_work_is_error);
						}
						statu_tv.setText(R.string.pay_paying_ok);
						gopay_btn.setVisibility(View.GONE);
						AllOrderActivity.ispaied = true;
					} else {
						Util.ShowToast(context, memo);
					}
					break;
				}
				case SDK_CHECK_FLAG: {
					Toast.makeText(OrderDetailActivity.this, "检查结果为：" + msg.obj,
							Toast.LENGTH_SHORT).show();
					break;
				}
				}
			};
		};
	}

	private class MyTask extends AsyncTask<Object, Object, Object> {
		private int type;

		public MyTask(int type) {
			this.type = type;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context);
		}  

		@Override  
		protected Object doInBackground(Object... params) {  
			try {  
				if(type==1){
					Send s = new Send(context);
					String userid = MyApplication.UserId;
					bean = s.getOrderDetalis(getIntent().getStringExtra("orderid"), userid);
					return bean;
				}else{
					Send s = new Send(context);
					base = s.UpdataOrderStatu(bean.getOrder_sn());
					return base;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Object... progresses) {
		}

		@Override
		protected void onPostExecute(Object result) {
			stopProgressDialog();
			if (type == 1) {
				bean = (OrderDetailsBean) result;
				if (bean != null) {
					if (bean.getCode() == 200) {
						number_tv.setText(bean.getOrder_sn());
						statu_tv.setText(bean.getOrder_zt());
						time_tv.setText(bean.getFormated_add_time());
						pp_tv.setText(bean.getConsignee());
						tel_tv.setText(bean.getTel());
						money_tv.setText("￥"+bean.getFormated_goods_amount()+"元");
						youfei_tv.setText("￥"+bean.getFormated_shipping_fee()+"元");
						address_tv.setText(bean.getAddress());
						list = bean.getGoods();
						adapter = new OrderDetailsAdapter(context, list);
						lv.setAdapter(adapter);
						if("1".endsWith(bean.getPay_id())){
							if (bean.getPay_status() == 2) {
								gopay_btn.setVisibility(View.VISIBLE);
							}else{
								gopay_btn.setVisibility(View.GONE);
							}
						}else{
							gopay_btn.setVisibility(View.GONE);
						}
					}else if(bean.getCode() == 500){
						Util.ShowToast(context, R.string.net_work_is_error);
					} else {
						Util.ShowToast(context, bean.getMsg());
					}
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			} else {
				base = (Base) result;
				if(base!=null){
					if(base.getCode()==200){
						MyApplication.order_list.remove(bean.getOrder_sn());
					}else if(base.getCode() == 500){
						Util.ShowToast(context, R.string.net_work_is_error);
					}else{
						Util.ShowToast(context, base.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_work_is_error);
				}

			}

		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.order_detail_gopay_btn:
			if(bean!=null && list!=null){
				String price = String.valueOf(Double.parseDouble(bean.getFormated_goods_amount())+Double.parseDouble(bean.getFormated_shipping_fee()));
				String goodsinfo = "";
				if(list!=null){
					if(list.size()==1){
						goodsinfo = list.get(0).getGoods_name();
					}else{
						goodsinfo = list.get(0).getGoods_name()+"等";
					}
				}
				pay(price,goodsinfo,"订单"+bean.getOrder_sn(),bean.getOrder_sn());
			}
			break;
		case R.id.order_detail_cancel_btn:

			break;

		}
	}

	public void pay(String price, String goodname, String gooddes,String orderid) {
		String orderInfo = getOrderInfo(goodname, gooddes, price,orderid);
		String sign = sign(orderInfo);
		try {
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();
		Runnable payRunnable = new Runnable() {
			@Override
			public void run() {
				PayTask alipay = new PayTask(OrderDetailActivity.this);
				String result = alipay.pay(payInfo);
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	public String getOrderInfo(String subject, String body, String price,String orderid) {
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
		orderInfo += "&out_trade_no=" + "\"" + orderid + "\"";
		orderInfo += "&subject=" + "\"" + subject + "\"";
		orderInfo += "&body=" + "\"" + body + "\"";
		orderInfo += "&total_fee=" + "\"" + price + "\"";
		orderInfo += "&notify_url=" + "\"" + Notify_Url
				+ "\"";
		orderInfo += "&service=\"mobile.securitypay.pay\"";
		orderInfo += "&payment_type=\"1\"";
		orderInfo += "&_input_charset=\"utf-8\"";
		orderInfo += "&it_b_pay=\"30m\"";
		orderInfo += "&return_url=\"m.alipay.com\"";
		// orderInfo += "&paymethod=\"expressGateway\"";
		return orderInfo;
	}


	public String sign(String content) {
		return Util.sign(content, RSA_PRIVATE);
	}

	public void check() {
		Runnable checkRunnable = new Runnable() {
			@Override
			public void run() {
				PayTask payTask = new PayTask(OrderDetailActivity.this);
				boolean isExist = payTask.checkAccountIfExist();
				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	public class Result {
		String resultStatus;
		String result;
		String memo;

		public Result(String rawResult) {
			try {
				String[] resultParams = rawResult.split(";");
				for (String resultParam : resultParams) {
					if (resultParam.startsWith("resultStatus")) {
						resultStatus = gatValue(resultParam, "resultStatus");
					}
					if (resultParam.startsWith("result")) {
						result = gatValue(resultParam, "result");
					}
					if (resultParam.startsWith("memo")) {
						memo = gatValue(resultParam, "memo");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public String toString() {
			return "resultStatus={" + resultStatus + "};memo={" + memo
					+ "};result={" + result + "}";
		}
		private String gatValue(String content, String key) {
			String prefix = key + "={";
			return content.substring(content.indexOf(prefix) + prefix.length(),
					content.lastIndexOf("}"));
		}
	}
	public void startProgressDialog(Context context) {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(context);
		}

		progressDialog.show();
	}

	public void stopProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

}
