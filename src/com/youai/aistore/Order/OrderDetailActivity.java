package com.youai.aistore.Order;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

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
import com.youai.aistore.ExampleActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
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
	private LinearLayout gopay_ll;
	private OrderDetailsAdapter adapter;
	private ArrayList<Goods> list;
	private OrderListview lv;
	private Handler mHandler;
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;
	private final String PARTNER = MyApplication.PARTNER;// 合作者身份ID
	private final String SELLER = MyApplication.SELLER;// 卖家支付宝账号
	private final String RSA_PRIVATE = MyApplication.RSA_PRIVATE;
	@SuppressWarnings("unused")
	private final String RSA_PUBLIC = MyApplication.RSA_PUBLIC;
	private final String Notify_Url = MyApplication.Notify_Url;// 

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
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case SDK_PAY_FLAG: {
					Result resultObj = new Result((String) msg.obj);
					String resultStatus = resultObj.resultStatus;
					String memo = resultObj.memo;

					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
					if (TextUtils.equals(resultStatus, "9000")) {
						Toast.makeText(OrderDetailActivity.this, memo,// "支付成功",
								Toast.LENGTH_SHORT).show();
					} else {
						// 判断resultStatus 为非“9000”则代表可能支付失败
						// “8000”
						// 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(OrderDetailActivity.this, memo,// "支付结果确认中",
									Toast.LENGTH_SHORT).show();

						} else if (TextUtils.equals(resultStatus, "4000")) {
							Toast.makeText(OrderDetailActivity.this, memo,// "订单支付失败",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(OrderDetailActivity.this, memo,// "支付失败",
									Toast.LENGTH_SHORT).show();

						}
					}
					break;
				}
				case SDK_CHECK_FLAG: {
					Toast.makeText(OrderDetailActivity.this, "检查结果为：" + msg.obj,
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

	private class MyTask extends AsyncTask<Object, Object, Object> {
		private int type;

		public MyTask(int type) {
			this.type = type;
		}

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			// textView.setText("loading...");
			Util.startProgressDialog(context);
		}  

		//doInBackground方法内部执行后台任务,不可在此方法内修改UI  
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

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Object... progresses) {
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
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
						money_tv.setText("￥"+bean.getFormated_goods_amount()+"元");
						youfei_tv.setText("￥"+bean.getFormated_shipping_fee()+"元");
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

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			Util.stopProgressDialog();
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.order_detail_gopay_btn:
			if(bean!=null && list!=null){
			String price = String.valueOf(Double.parseDouble(bean.getFormated_goods_amount())+Double.parseDouble(bean.getFormated_shipping_fee()));
			String goodsinfo = list.get(0).getGoods_name()+"等";
			 pay(price,"订单"+bean.getOrder_sn(),goodsinfo,bean.getOrder_sn());
			}
			break;
		case R.id.order_detail_cancel_btn:
			
			break;

		}
	}
	
	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(String price, String goodname, String gooddes,String orderid) {
		String orderInfo = getOrderInfo(goodname, gooddes, price,orderid);
//		String sign = "lBBK%2F0w5LOajrMrji7DUgEqNjIhQbidR13GovA5r3TgIbNqv231yC1NksLdw%2Ba3JnfHXoXuet6XNNHtn7VE%2BeCoRO1O%2BR1KugLrQEZMtG5jmJIe2pbjm%2F3kb%2FuGkpG%2BwYQYI51%2BhA3YBbvZHVQBYveBqK%2Bh8mUyb7GM1HxWs9k4%3D";
		String sign = sign(orderInfo);
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
				PayTask alipay = new PayTask(OrderDetailActivity.this);
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
	public String getOrderInfo(String subject, String body, String price,String orderid) {
		// 合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";
		// 卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + orderid + "\"";
		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";
		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";
		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";
		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + Notify_Url
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

}
