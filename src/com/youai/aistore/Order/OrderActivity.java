package com.youai.aistore.Order;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.youai.aistore.BaseActivity;
import com.youai.aistore.ExampleActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.Base;
import com.youai.aistore.Bean.CommitOrderBean;
import com.youai.aistore.Bean.ListShopCartBean;
import com.youai.aistore.Bean.ShopCartBean;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.ShopCart.ConsigneeInfoActivity;
import com.youai.aistore.ShopCart.ShopCartActivity;

/**
 * 结算订单界面
 * 
 * @author Qzr
 * 
 */
public class OrderActivity extends BaseActivity implements OnClickListener {
	private OrderListview lv;
	private TextView tv_consignee, tv_address, tv_number, tv_goods_prive,
	tv_kuaidi_price, tv_final_price, tv_chose_time;
	private LinearLayout chose_time_ll;
	private Button commitbtn;
	private RadioButton zhifu_rbt, huodao_rbt;
	private Context context;
	private Dialog alertDialog;
	private OrderLvAdapter adapter;
	private ArrayList<ShopCartBean> list;
	private int type, postion, time,addtype;
	private ListShopCartBean listbean;
	private MyTask myTask;
	private CommitOrderBean bean;
	private double kuaidi, price, final_price;
	private Base base;
	private Handler mHandler;
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
		setTitleTxt(R.string.order_gopay_title);
		setContentXml(R.layout.order);
		setTopLeftBackground(R.drawable.btn_back);
		init();
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case SDK_PAY_FLAG: {
					Result resultObj = new Result((String) msg.obj);
					String resultStatus = resultObj.resultStatus;
					String memo = resultObj.memo;
					if (TextUtils.equals(resultStatus, "9000")) {
						addtype = 2;
						MyApplication.order_list.add(bean.getOrder_sn());
						if (Util.detect(context)) {
							myTask = new MyTask();
							myTask.execute("");
						} else {
							Util.ShowToast(context, R.string.net_work_is_error);
						}
					} else {
						Util.ShowToast(context, memo);
						ConsigneeInfoActivity.isfinish = true;
						ExampleActivity.setCurrentTab(2);
						finish();
					}
					break;
				}
				case SDK_CHECK_FLAG: {
					Toast.makeText(OrderActivity.this, "检查结果为：" + msg.obj,
							Toast.LENGTH_SHORT).show();
					ExampleActivity.setCurrentTab(2);
					finish();
					break;
				}
				}
			};
		};
	}

	private void init() {
		postion = 0;
		time = 1;
		type = 1;
		context = this;
		listbean = (ListShopCartBean) getIntent().getExtras().get("list");
		if(listbean!=null){
			list = listbean.getList();
		}
		adapter = new OrderLvAdapter(context, list);
		zhifu_rbt = (RadioButton) findViewById(R.id.order_radio_zhifu);
		zhifu_rbt.setOnClickListener(this);
		huodao_rbt = (RadioButton) findViewById(R.id.order_radio_huodao);
		huodao_rbt.setOnClickListener(this);
		lv = (OrderListview) findViewById(R.id.order_goods_list_lv);
		lv.setAdapter(adapter);
		tv_consignee = (TextView) findViewById(R.id.order_consignee_tv);
		tv_address = (TextView) findViewById(R.id.order_address_tv);
		tv_number = (TextView) findViewById(R.id.order_number_tv);
		tv_goods_prive = (TextView) findViewById(R.id.order_goods_price_tv);
		tv_kuaidi_price = (TextView) findViewById(R.id.order_express_cost_tv);
		tv_final_price = (TextView) findViewById(R.id.order_final_pay_tv);
		tv_chose_time = (TextView) findViewById(R.id.order_chose_time_tv);
		chose_time_ll = (LinearLayout) findViewById(R.id.order_chose_time_ll);
		chose_time_ll.setOnClickListener(this);
		commitbtn = (Button) findViewById(R.id.order_commit_btn);
		commitbtn.setOnClickListener(this);
		tv_consignee.setText(getIntent().getStringExtra("consignee"));
		tv_address.setText(getIntent().getStringExtra("address"));
		tv_number.setText(getIntent().getStringExtra("number"));
		tv_goods_prive.setText("￥" + listbean.getCount_price() + "元");
		price = Double.parseDouble(listbean.getCount_price());
		kuaidi = 0.00;
		final_price = 0.00;
		if (price > 299) {
			kuaidi = 0.00;
		} else {
			kuaidi = 12.00;
		}
		final_price = price+kuaidi;
		tv_kuaidi_price.setText("￥"+kuaidi+"元");
		tv_final_price.setText("￥"+final_price+"元");

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.order_commit_btn:
			addtype = 1;
			if (Util.detect(context)) {
				myTask = new MyTask();
				myTask.execute("");
			} else {
				Util.ShowToast(context, R.string.net_work_is_error);
			}
			break;
		case R.id.order_chose_time_ll:
			TosatDialog();
			break;
		case R.id.order_radio_zhifu:
			type = 1;
			break;
		case R.id.order_radio_huodao:
			type = 2;
			break;

		}
	}

	private void TosatDialog() {
		final String[] arrayFruit = getResources().getStringArray(
				R.array.order_chose_time);

		alertDialog = new AlertDialog.Builder(this).setSingleChoiceItems(
				arrayFruit, postion, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						postion = which;
						tv_chose_time.setText(arrayFruit[which]);
						time = which + 1;
						if (alertDialog != null && alertDialog.isShowing()) {
							alertDialog.dismiss();
							alertDialog = null;
						}
					}
				}).create();
		alertDialog.show();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (alertDialog != null && alertDialog.isShowing()) {
			alertDialog.dismiss();
			alertDialog = null;
		}
		return super.onTouchEvent(event);
	}

	private class MyTask extends AsyncTask<Object, Object, Object> {

		@Override
		protected void onPreExecute() {
			Util.startProgressDialog(context);
		}  

		@Override  
		protected Object doInBackground(Object... params) {  
			try {
				if(addtype == 1){
					Send s = new Send(context);
					String userid = MyApplication.UserId;
					bean = s.CommitOrder(userid, ""+time, ""+type, ""+kuaidi);
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
		protected void onProgressUpdate(Object... progresses) {}

		@Override
		protected void onPostExecute(Object result) {
			Util.stopProgressDialog();
			if(addtype ==1){
				bean = (CommitOrderBean) result;
				if (bean != null) {
					if (bean.getCode() == 200) {
						ShopCartActivity.shopcartchaneged = true;
						if (type == 1) {
							String price = String.valueOf(final_price);
							String goodsinfo = "";
							if(list!=null){
								if(list.size()==1){
									goodsinfo = list.get(0).getGoods_name();
								}else{
									goodsinfo = list.get(0).getGoods_name()+"等";
								}
							}
							pay("0.01",goodsinfo,"订单"+bean.getOrder_sn(),bean.getOrder_sn());
							//TODO
							ExampleActivity.setCurrentTab(2);
						} else {
							Util.ShowToast(context, R.string.commit_order_for_huodao);
							ExampleActivity.setCurrentTab(2);
							ConsigneeInfoActivity.isfinish = true;
							finish();
						}
					}else if(bean.getCode() == 500){
						Util.ShowToast(context, R.string.net_work_is_error);
					} else {
						Util.ShowToast(context, bean.getMsg());
					}
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}else if(addtype == 2){
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
				ConsigneeInfoActivity.isfinish = true;
				ExampleActivity.setCurrentTab(2);
				finish();
			}
		}

		@Override
		protected void onCancelled() {
			// Util.stopProgressDialog();
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
				PayTask alipay = new PayTask(OrderActivity.this);
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
				PayTask payTask = new PayTask(OrderActivity.this);
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
}
