package com.youai.aistore.Order;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.youai.aistore.ExampleActivity;
import com.youai.aistore.R;
import com.youai.aistore.WelcomeActivity;

/**
 * »´≤ø∂©µ•  ≈‰
 * @author Qzr
 *
 */
public class AllOrderAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<String> list;
	private LayoutInflater inflater;
	private MyItem myitem;
	
	public AllOrderAdapter(Context context,ArrayList<String> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		if(v == null){
			myitem = new MyItem();
			v = inflater.inflate(R.layout.all_order_item, null);
			myitem.btn_detail = (Button) v.findViewById(R.id.all_order_item_detail_btn);
			myitem.tv_number = (TextView) v.findViewById(R.id.all_order_item_number_tv);
			myitem.tv_time = (TextView) v.findViewById(R.id.all_order_item_time_tv);
			myitem.tv_type = (TextView) v.findViewById(R.id.all_order_item_gopay_type);
			myitem.tv_statu = (TextView) v.findViewById(R.id.all_order_item_statu);
			//TODO
			
			v.setTag(myitem);
		}else{
			myitem = (MyItem) v.getTag();
		}
		
		//TODO
		myitem.btn_detail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent(context,.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				context.startActivity(intent);
			}
		});
		return v;
	}

	class MyItem{
		TextView tv_number,tv_time,tv_type,tv_statu;
		Button btn_detail;
		
	}
}
