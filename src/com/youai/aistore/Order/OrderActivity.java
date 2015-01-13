package com.youai.aistore.Order;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

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
import com.youai.aistore.MainActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.Base;
import com.youai.aistore.Bean.ListShopCartBean;
import com.youai.aistore.Bean.ShopCartBean;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.ShopCart.ConsigneeInfoActivity;
import com.youai.aistore.ShopCart.ShopCartActivity;
/**
 * 结算订单界面
 * @author Qzr
 *
 */
public class OrderActivity extends BaseActivity implements OnClickListener{
	private OrderListview lv;
	private TextView tv_consignee,tv_address,tv_number,tv_goods_prive,
	tv_kuaidi_price,tv_final_price,tv_chose_time;
	private LinearLayout chose_time_ll;
	private Button commitbtn;
	private RadioButton zhifu_rbt,huodao_rbt;
	private Context context;
	private Dialog alertDialog;
	private OrderLvAdapter adapter;
	private ArrayList<ShopCartBean>list;
	private int type,postion,time;
	private ListShopCartBean listbean;
	private MyTask myTask;
	private Base bean;
	private double kuaidi,price,final_price;
	private Handler mHandler;
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;
	public static final String PARTNER = "";// 合作者身份ID
	public static final String SELLER = "";// 卖家支付宝账号
	public static final String RSA_PRIVATE = "151321";
	public static final String RSA_PUBLIC = "";

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

						// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
						if (TextUtils.equals(resultStatus, "9000")) {
							Toast.makeText(OrderActivity.this, memo,//"支付成功",
									Toast.LENGTH_SHORT).show();
						} else {
							// 判断resultStatus 为非“9000”则代表可能支付失败
							// “8000” 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
							if (TextUtils.equals(resultStatus, "8000")) {
								Toast.makeText(OrderActivity.this, memo,//"支付结果确认中",
										Toast.LENGTH_SHORT).show();

							} else if(TextUtils.equals(resultStatus, "4000")){
								Toast.makeText(OrderActivity.this, memo,//"订单支付失败",
										Toast.LENGTH_SHORT).show();
							}else {
								Toast.makeText(OrderActivity.this, memo,//"支付失败",
										Toast.LENGTH_SHORT).show();

							}
						}
						break;
					}
					case SDK_CHECK_FLAG: {
						Toast.makeText(OrderActivity.this, "检查结果为：" + msg.obj,
								Toast.LENGTH_SHORT).show();
						break;
					}
					default:
						ExampleActivity.setCurrentTab(2);
						finish();
						break;
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
		zhifu_rbt = (RadioButton) findViewById(R.id.order_radio_zhifu);
		zhifu_rbt.setOnClickListener(this);
		huodao_rbt = (RadioButton) findViewById(R.id.order_radio_huodao);
		huodao_rbt.setOnClickListener(this);
		lv = (OrderListview) findViewById(R.id.order_goods_list_lv);
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
		tv_goods_prive.setText("￥"+listbean.getCount_price()+"元");
		price = Double.parseDouble(listbean.getCount_price());
		kuaidi = 0.00;
		final_price = 0.00;
		if(price>299){
			kuaidi = 0.00;
		}else{
			kuaidi = 12.00;
		}
		final_price = price+kuaidi;
		tv_kuaidi_price.setText("￥"+kuaidi+"元");
		tv_final_price.setText("￥"+final_price+"元");
		
//		/************************模拟数据**********************************/
//		list = new ArrayList<ShopCartBean>();
//		ShopCartBean a = new ShopCartBean();
//		ShopCartBean s = new ShopCartBean();
//		ShopCartBean d = new ShopCartBean();
//		ShopCartBean f = new ShopCartBean();
//		list.add(a);
//		list.add(s);
//		list.add(d);
//		list.add(f);
//		adapter = new OrderLvAdapter(context, listbean.getList());
//		lv.setAdapter(adapter);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.order_commit_btn:
			if(Util.detect(context)){
				myTask = new MyTask();
				myTask.execute("");  
			}else{
				Util.ShowToast(context, R.string.net_work_is_error);
			}
			break;
		case R.id.order_chose_time_ll:
			TosatDialog();
			break;
		case R.id.order_radio_zhifu:
			type = 1;
			Util.ShowToast(context, ""+type);
			break;
		case R.id.order_radio_huodao:
			type = 2;
			Util.ShowToast(context, ""+type);
			break;

		}
	}

	private void TosatDialog(){
		final String[] arrayFruit = getResources().getStringArray(R.array.order_chose_time);

		alertDialog = new AlertDialog.Builder(this).
				setSingleChoiceItems(arrayFruit, postion, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						postion = which;
						tv_chose_time.setText(arrayFruit[which]);
						time = which+1;
						if(alertDialog!=null && alertDialog.isShowing()){
							alertDialog.dismiss();
							alertDialog = null;
						}
					}
				}).
				create();
		alertDialog.show();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(alertDialog!=null && alertDialog.isShowing()){
			alertDialog.dismiss();
			alertDialog = null;
		}
		return super.onTouchEvent(event);
	}
	
	private class MyTask extends AsyncTask<Object, Object, Object> {  
		
		//onPreExecute方法用于在执行后台任务前做一些UI操作  
		@Override  
		protected void onPreExecute() {  
			Util.startProgressDialog(context);
		}  

		//doInBackground方法内部执行后台任务,不可在此方法内修改UI  
		@Override  
		protected Object doInBackground(Object... params) {  
			try {  
					Send s = new Send(context);
					String userid = MyApplication.UserId;
					bean = s.CommitOrder(userid, ""+time, ""+type, ""+kuaidi);
					return bean;
			} catch (Exception e) {  
				e.printStackTrace();
			}  
			return null;  
		}  

		//onProgressUpdate方法用于更新进度信息  
		@Override  
		protected void onProgressUpdate(Object... progresses) {  
			
		}  

		//onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
		@Override  
		protected void onPostExecute(Object result) {  
			Util.stopProgressDialog();
			bean = (Base) result;
			if(bean!=null){
				if(bean.getCode() == 200){
					ShopCartActivity.shopcartchaneged=true;
					if(type ==1){
						Util.ShowToast(context,"调用支付宝");	
//						 Fiap fiap = new Fiap(OrderActivity.this);  
//					      // 调用支付方法，并传入支付金额  
//					      fiap.pay(0.01,"测试商品","测试商品信息","测试订单号");  
//						pay("0.01","测试商品","测试商品信息");
						ExampleActivity.setCurrentTab(2);
						ConsigneeInfoActivity.isfinish = true;
						finish();
					}else{
						Util.ShowToast(context,"已提交订单，请等待发货");	
						ExampleActivity.setCurrentTab(2);
						ConsigneeInfoActivity.isfinish = true;
						finish();
					}
				}else{
					Util.ShowToast(context,bean.getMsg());		
				}
			}else{
				Util.ShowToast(context,R.string.net_work_is_error);
			}
		}  

		//onCancelled方法用于在取消执行中的任务时更改UI  
		@Override  
		protected void onCancelled() {  
//			Util.stopProgressDialog();
		}  
	
	}
	
	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(String price,String goodname,String gooddes) {
		String orderInfo = getOrderInfo(goodname, gooddes, price);
		String sign ="lBBK%2F0w5LOajrMrji7DUgEqNjIhQbidR13GovA5r3TgIbNqv231yC1NksLdw%2Ba3JnfHXoXuet6XNNHtn7VE%2BeCoRO1O%2BR1KugLrQEZMtG5jmJIe2pbjm%2F3kb%2FuGkpG%2BwYQYI51%2BhA3YBbvZHVQBYveBqK%2Bh8mUyb7GM1HxWs9k4%3D";
		//= sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(OrderActivity.this);
				// 调用支付接口
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

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
	
	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {
		// 合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// 接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}
	
	/**
	 * get the out_trade_no for an order. 获取外部订单号
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
	
	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return Util.sign(content, RSA_PRIVATE);
	}
	

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
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
