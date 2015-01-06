package com.youai.aistore.ShopCart;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.R;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Bean.ShopCartBean;
import com.youai.aistore.ShopCart.ShopCartActivity.ShopcartInterface;

public class ShopCartAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ShopCartBean> list;
	private MyShopItem myShopItem;
	private LayoutInflater inflater;
	private int statu;
	private ShopcartInterface inter;
	
	public ShopCartAdapter(Context context,ArrayList<ShopCartBean> list,int statu,ShopcartInterface inter){
		this.context = context;
		this.list = list;
		this.statu = statu;
		this.inter = inter;
		inflater = LayoutInflater.from(context);
	}
	public void setdata(ArrayList<ShopCartBean> list,int statu){
		this.list = list;
		this.statu = statu;
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
	public View getView(final int postion, View arg1, ViewGroup arg2) {
		if(arg1==null){
			myShopItem = new MyShopItem();
			arg1 = inflater.inflate(R.layout.shopcart_lv_item, null);
			myShopItem.iv = (ImageView) arg1.findViewById(R.id.shopcart_item_pp_iv);
			myShopItem.tv_titlt = (TextView) arg1.findViewById(R.id.shopcart_item_title_tv);
			myShopItem.tv_price = (TextView) arg1.findViewById(R.id.shopcart_item_price_tv);
			myShopItem.tv_delete = (TextView) arg1.findViewById(R.id.shopcart_item_delete_tv);
			myShopItem.tv_kucun = (TextView) arg1.findViewById(R.id.shopcart_lv_item_kucun_tv);
			myShopItem.et_num = (EditText) arg1.findViewById(R.id.shopcart_item_num_et);
			myShopItem.delete_ll = (LinearLayout) arg1.findViewById(R.id.shopcart_item_delete_ll);
			arg1.setTag(myShopItem);
		}else{
			myShopItem = (MyShopItem) arg1.getTag();
		}
		if(statu == 1){
			myShopItem.delete_ll.setVisibility(View.GONE);
		}else{
			myShopItem.delete_ll.setVisibility(View.VISIBLE);
		}
		myShopItem.tv_titlt.setText(""+postion);
		myShopItem.iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		ImageLoader.getInstance().displayImage(list.get(postion).getPic_url(), myShopItem.iv);
		myShopItem.tv_titlt.setText(list.get(postion).getGoods_name());
		myShopItem.tv_price.setText("ฃค"+list.get(postion).getGoods_price()+"ิช");
		myShopItem.tv_kucun.setText(list.get(postion).getGoods_count());
		myShopItem.et_num.setText(list.get(postion).getGoods_number());
		
		myShopItem.tv_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				inter.delete(list, postion);
			}
		});
		
		
		
		return arg1;
	}

	
	class MyShopItem{
		private ImageView iv;
		private TextView tv_titlt,tv_price,tv_delete,tv_kucun;
		private EditText et_num;
		private LinearLayout delete_ll;
	}
}
