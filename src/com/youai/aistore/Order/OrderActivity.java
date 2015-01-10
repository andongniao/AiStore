package com.youai.aistore.Order;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.ExampleActivity;
import com.youai.aistore.Fiap;
import com.youai.aistore.MainActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.Base;
import com.youai.aistore.Bean.ListShopCartBean;
import com.youai.aistore.Bean.ShopCartBean;
import com.youai.aistore.NetInterface.Send;
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

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTitleTxt(R.string.consignee_info);
		setContentXml(R.layout.order);
		setTopLeftBackground(R.drawable.btn_back);
		init();
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
		
		/************************模拟数据**********************************/
		list = new ArrayList<ShopCartBean>();
		ShopCartBean a = new ShopCartBean();
		ShopCartBean s = new ShopCartBean();
		ShopCartBean d = new ShopCartBean();
		ShopCartBean f = new ShopCartBean();
		list.add(a);
		list.add(s);
		list.add(d);
		list.add(f);
		adapter = new OrderLvAdapter(context, listbean.getList());
		lv.setAdapter(adapter);
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
//					String userid = MyApplication.UserId;
					String userid = "188";
					bean = s.CommitOrder(userid, ""+time, ""+type, ""+final_price);
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
					if(type ==1){
						Util.ShowToast(context,"调用支付宝");	
						 Fiap fiap = new Fiap(OrderActivity.this);  
					      // 调用支付方法，并传入支付金额  
					      fiap.pay(0.01,"测试商品","测试商品信息","测试订单号");  
					}else{
						Util.ShowToast(context,"已提交订单，请等待发货");	
						ExampleActivity.setCurrentTab(2);
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
	
}
