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
 * ���㶩������
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
	public static final String PARTNER = "";// ���������ID
	public static final String SELLER = "";// ����֧�����˺�
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

						// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
						if (TextUtils.equals(resultStatus, "9000")) {
							Toast.makeText(OrderActivity.this, memo,//"֧���ɹ�",
									Toast.LENGTH_SHORT).show();
						} else {
							// �ж�resultStatus Ϊ�ǡ�9000����������֧��ʧ��
							// ��8000�� ����֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
							if (TextUtils.equals(resultStatus, "8000")) {
								Toast.makeText(OrderActivity.this, memo,//"֧�����ȷ����",
										Toast.LENGTH_SHORT).show();

							} else if(TextUtils.equals(resultStatus, "4000")){
								Toast.makeText(OrderActivity.this, memo,//"����֧��ʧ��",
										Toast.LENGTH_SHORT).show();
							}else {
								Toast.makeText(OrderActivity.this, memo,//"֧��ʧ��",
										Toast.LENGTH_SHORT).show();

							}
						}
						break;
					}
					case SDK_CHECK_FLAG: {
						Toast.makeText(OrderActivity.this, "�����Ϊ��" + msg.obj,
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
		tv_goods_prive.setText("��"+listbean.getCount_price()+"Ԫ");
		price = Double.parseDouble(listbean.getCount_price());
		kuaidi = 0.00;
		final_price = 0.00;
		if(price>299){
			kuaidi = 0.00;
		}else{
			kuaidi = 12.00;
		}
		final_price = price+kuaidi;
		tv_kuaidi_price.setText("��"+kuaidi+"Ԫ");
		tv_final_price.setText("��"+final_price+"Ԫ");
		
//		/************************ģ������**********************************/
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
		
		//onPreExecute����������ִ�к�̨����ǰ��һЩUI����  
		@Override  
		protected void onPreExecute() {  
			Util.startProgressDialog(context);
		}  

		//doInBackground�����ڲ�ִ�к�̨����,�����ڴ˷������޸�UI  
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

		//onProgressUpdate�������ڸ��½�����Ϣ  
		@Override  
		protected void onProgressUpdate(Object... progresses) {  
			
		}  

		//onPostExecute����������ִ�����̨��������UI,��ʾ���  
		@Override  
		protected void onPostExecute(Object result) {  
			Util.stopProgressDialog();
			bean = (Base) result;
			if(bean!=null){
				if(bean.getCode() == 200){
					ShopCartActivity.shopcartchaneged=true;
					if(type ==1){
						Util.ShowToast(context,"����֧����");	
//						 Fiap fiap = new Fiap(OrderActivity.this);  
//					      // ����֧��������������֧�����  
//					      fiap.pay(0.01,"������Ʒ","������Ʒ��Ϣ","���Զ�����");  
//						pay("0.01","������Ʒ","������Ʒ��Ϣ");
						ExampleActivity.setCurrentTab(2);
						ConsigneeInfoActivity.isfinish = true;
						finish();
					}else{
						Util.ShowToast(context,"���ύ��������ȴ�����");	
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

		//onCancelled����������ȡ��ִ���е�����ʱ����UI  
		@Override  
		protected void onCancelled() {  
//			Util.stopProgressDialog();
		}  
	
	}
	
	/**
	 * call alipay sdk pay. ����SDK֧��
	 * 
	 */
	public void pay(String price,String goodname,String gooddes) {
		String orderInfo = getOrderInfo(goodname, gooddes, price);
		String sign ="lBBK%2F0w5LOajrMrji7DUgEqNjIhQbidR13GovA5r3TgIbNqv231yC1NksLdw%2Ba3JnfHXoXuet6XNNHtn7VE%2BeCoRO1O%2BR1KugLrQEZMtG5jmJIe2pbjm%2F3kb%2FuGkpG%2BwYQYI51%2BhA3YBbvZHVQBYveBqK%2Bh8mUyb7GM1HxWs9k4%3D";
		//= sign(orderInfo);
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
				PayTask alipay = new PayTask(OrderActivity.this);
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
	public String getOrderInfo(String subject, String body, String price) {
		// ���������ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// ����֧�����˺�
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// �̻���վΨһ������
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// ��Ʒ����
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// ��Ʒ����
		orderInfo += "&body=" + "\"" + body + "\"";

		// ��Ʒ���
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// �������첽֪ͨҳ��·��
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
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
	 * get the out_trade_no for an order. ��ȡ�ⲿ������
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
