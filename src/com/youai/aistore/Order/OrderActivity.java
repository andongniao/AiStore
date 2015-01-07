package com.youai.aistore.Order;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.ShopCartBean;
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
	private int type,postion;
	
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
		type = 1;
		context = this;
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
		adapter = new OrderLvAdapter(context, list);
		lv.setAdapter(adapter);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.order_commit_btn:
			
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
}
