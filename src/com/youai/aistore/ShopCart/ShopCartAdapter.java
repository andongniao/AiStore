package com.youai.aistore.ShopCart;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.R;
import com.youai.aistore.Bean.GoodsBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopCartAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<GoodsBean> list;
	private MyShopItem myShopItem;
	private LayoutInflater inflater;
	
	public ShopCartAdapter(Context context,ArrayList<GoodsBean> list){
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if(arg1==null){
			myShopItem = new MyShopItem();
			arg1 = inflater.inflate(R.layout.shopcart_lv_item, null);
			myShopItem.iv = (ImageView) arg1.findViewById(R.id.shopcart_item_pp_iv);
			myShopItem.tv_titlt = (TextView) arg1.findViewById(R.id.shopcart_item_title_tv);
			myShopItem.tv_price = (TextView) arg1.findViewById(R.id.shopcart_item_price_tv);
			myShopItem.tv_delete = (TextView) arg1.findViewById(R.id.shopcart_item_delete_tv);
			myShopItem.tv_kucun = (TextView) arg1.findViewById(R.id.shopcart_lv_item_kucun_tv);
			myShopItem.et_num = (EditText) arg1.findViewById(R.id.shopcart_item_num_et);
			arg1.setTag(myShopItem);
		}else{
			myShopItem = (MyShopItem) arg1.getTag();
		}
		
//		myShopItem.iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//		ImageLoader.getInstance().displayImage(list.get(arg0).getPicurl(), myShopItem.iv);
//		myShopItem.tv_delete.setText(beanlist.get(arg0).getClick());
//		myHalder.textprice.setText("гд"+beanlist.get(arg0).getShop_price());
//		myHalder.textTitle.setText(beanlist.get(arg0).getTitle());
		
		
		
		return arg1;
	}

	
	class MyShopItem{
		private ImageView iv;
		private TextView tv_titlt,tv_price,tv_delete,tv_kucun;
		private EditText et_num;
	}
}
