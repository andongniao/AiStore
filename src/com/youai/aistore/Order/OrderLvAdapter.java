package com.youai.aistore.Order;

import java.util.ArrayList;

import com.youai.aistore.R;
import com.youai.aistore.Bean.ShopCartBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Ω·À„∂©µ•  ≈‰∆˜
 * @author Qzr
 *
 */
public class OrderLvAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ShopCartBean> list;
	private LayoutInflater inflater;
	private MyItem myItem;
	
	public OrderLvAdapter(Context context,ArrayList<ShopCartBean> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int postion, View v, ViewGroup arg2) {
		if(v==null){
			myItem = new MyItem();
			v = inflater.inflate(R.layout.shopcart_lv_item, null);
			myItem.iv = (ImageView) v.findViewById(R.id.shopcart_item_pp_iv);
			myItem.tv_titlt = (TextView) v.findViewById(R.id.shopcart_item_title_tv);
			myItem.tv_price = (TextView) v.findViewById(R.id.shopcart_item_price_tv);
			myItem.tv_delete = (TextView) v.findViewById(R.id.shopcart_item_delete_tv);
			myItem.tv_kucun = (TextView) v.findViewById(R.id.shopcart_lv_item_kucun_tv);
			myItem.et_num = (EditText) v.findViewById(R.id.shopcart_item_num_et);
			myItem.delete_ll = (LinearLayout) v.findViewById(R.id.shopcart_item_delete_ll);
			myItem.btn_add = (ImageButton) v.findViewById(R.id.shopcart_lv_item_add_ibt);
			myItem.btn_jian = (ImageButton) v.findViewById(R.id.shopcart_lv_item_jian_ibt);
			v.setTag(myItem);
		}else{
			myItem = (MyItem) v.getTag();
		}
		myItem.btn_add.setVisibility(View.GONE);
		myItem.btn_jian.setVisibility(View.GONE);
		myItem.delete_ll.setVisibility(View.GONE);
		
		
		
		
		
		return v;
	}
	class MyItem{
		private ImageView iv;
		private TextView tv_titlt,tv_price,tv_delete,tv_kucun;
		private EditText et_num;
		private LinearLayout delete_ll;
		private ImageButton btn_add,btn_jian;
	}

}
