package com.youai.aistore.Order;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.youai.aistore.R;
import com.youai.aistore.Bean.ListOrderBean.OrderBean;

/**
 * »´≤ø∂©µ•  ≈‰
 * 
 * @author Qzr
 * 
 */
@SuppressLint("InflateParams")
public class AllOrderAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<OrderBean> list;
	private LayoutInflater inflater;
	private MyItem myitem;

	public AllOrderAdapter(Context context, ArrayList<OrderBean> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);

	}

	public void setdata(ArrayList<OrderBean> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list != null ? list.size() : 0;
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
	public View getView(final int postion, View v, ViewGroup arg2) {
		if (v == null) {
			myitem = new MyItem();
			v = inflater.inflate(R.layout.order_all_item, null);
			myitem.btn_detail = (Button) v
					.findViewById(R.id.all_order_item_detail_btn);
			myitem.tv_number = (TextView) v
					.findViewById(R.id.all_order_item_number_tv);
			myitem.tv_time = (TextView) v
					.findViewById(R.id.all_order_item_time_tv);
			myitem.tv_type = (TextView) v
					.findViewById(R.id.all_order_item_gopay_type);
			myitem.tv_statu = (TextView) v
					.findViewById(R.id.all_order_item_statu);
			// TODO

			v.setTag(myitem);
		} else {
			myitem = (MyItem) v.getTag();
		}
		myitem.tv_number.setText(list.get(postion).getOrder_sn());
		myitem.tv_time.setText(list.get(postion).getOrder_time());
		myitem.tv_type.setText(list.get(postion).getPay_name());
		myitem.tv_statu.setText(list.get(postion).getOrder_status());
		// TODO
		myitem.btn_detail.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unused")
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, OrderDetailActivity.class);
				String oer = list.get(postion).getOrder_id();
				intent.putExtra("orderid", list.get(postion).getOrder_id());
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);

			}
		});
		return v;
	}

	class MyItem {
		TextView tv_number, tv_time, tv_type, tv_statu;
		Button btn_detail;

	}
}
