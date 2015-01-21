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
 * ��������
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
	private final String PARTNER = MyApplication.PARTNER;// ���������ID
	private final String SELLER = MyApplication.SELLER;// ����֧�����˺�
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

					// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
					if (TextUtils.equals(resultStatus, "9000")) {
						Toast.makeText(OrderDetailActivity.this, memo,// "֧���ɹ�",
								Toast.LENGTH_SHORT).show();
					} else {
						// �ж�resultStatus Ϊ�ǡ�9000����������֧��ʧ��
						// ��8000��
						// ����֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(OrderDetailActivity.this, memo,// "֧�����ȷ����",
									Toast.LENGTH_SHORT).show();

						} else if (TextUtils.equals(resultStatus, "4000")) {
							Toast.makeText(OrderDetailActivity.this, memo,// "����֧��ʧ��",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(OrderDetailActivity.this, memo,// "֧��ʧ��",
									Toast.LENGTH_SHORT).show();

						}
					}
					break;
				}
				case SDK_CHECK_FLAG: {
					Toast.makeText(OrderDetailActivity.this, "�����Ϊ��" + msg.obj,
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

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.order_detail_gopay_btn:
			if(bean!=null && list!=null){
			String price = String.valueOf(Double.parseDouble(bean.getFormated_goods_amount())+Double.parseDouble(bean.getFormated_shipping_fee()));
			String goodsinfo = list.get(0).getGoods_name()+"��";
			 pay(price,"����"+bean.getOrder_sn(),goodsinfo,bean.getOrder_sn());
			}
			break;
		case R.id.order_detail_cancel_btn:
			
			break;

		}
	}
	
	/**
	 * call alipay sdk pay. ����SDK֧��
	 * 
	 */
	public void pay(String price, String goodname, String gooddes,String orderid) {
		String orderInfo = getOrderInfo(goodname, gooddes, price,orderid);
//		String sign = "lBBK%2F0w5LOajrMrji7DUgEqNjIhQbidR13GovA5r3TgIbNqv231yC1NksLdw%2Ba3JnfHXoXuet6XNNHtn7VE%2BeCoRO1O%2BR1KugLrQEZMtG5jmJIe2pbjm%2F3kb%2FuGkpG%2BwYQYI51%2BhA3YBbvZHVQBYveBqK%2Bh8mUyb7GM1HxWs9k4%3D";
		String sign = sign(orderInfo);
		try {
			// �����sign ��URL����
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// ����PayTask ����
				PayTask alipay = new PayTask(OrderDetailActivity.this);
				// ����֧���ӿ�
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
	 * get the sign type we use. ��ȡǩ����ʽ
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * create the order info. ����������Ϣ
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price,String orderid) {
		// ���������ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";
		// ����֧�����˺�
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
		// �̻���վΨһ������
		orderInfo += "&out_trade_no=" + "\"" + orderid + "\"";
		// ��Ʒ����
		orderInfo += "&subject=" + "\"" + subject + "\"";
		// ��Ʒ����
		orderInfo += "&body=" + "\"" + body + "\"";
		// ��Ʒ���
		orderInfo += "&total_fee=" + "\"" + price + "\"";
		// �������첽֪ͨҳ��·��
		orderInfo += "&notify_url=" + "\"" + Notify_Url
				+ "\"";
		// �ӿ����ƣ� �̶�ֵ
		orderInfo += "&service=\"mobile.securitypay.pay\"";
		// ֧�����ͣ� �̶�ֵ
		orderInfo += "&payment_type=\"1\"";
		// �������룬 �̶�ֵ
		orderInfo += "&_input_charset=\"utf-8\"";
		// ����δ����׵ĳ�ʱʱ��
		// Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
		// ȡֵ��Χ��1m��15d��
		// m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
		// �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
		orderInfo += "&it_b_pay=\"30m\"";

		// ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
		orderInfo += "&return_url=\"m.alipay.com\"";

		// �������п�֧���������ô˲���������ǩ���� �̶�ֵ
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}


	/**
	 * sign the order info. �Զ�����Ϣ����ǩ��
	 * 
	 * @param content
	 *            ��ǩ��������Ϣ
	 */
	public String sign(String content) {
		return Util.sign(content, RSA_PRIVATE);
	}

	/**
	 * check whether the device has authentication alipay account.
	 * ��ѯ�ն��豸�Ƿ����֧������֤�˻�
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
