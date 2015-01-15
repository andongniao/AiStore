package com.youai.aistore.ShopCart;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.R;
import com.youai.aistore.Bean.ShopCartBean;
import com.youai.aistore.ShopCart.ShopCartActivity.ShopcartInterface;

@SuppressLint("InflateParams")
public class ShopCartAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private ArrayList<ShopCartBean> list;
	private LayoutInflater inflater;
	private ShopcartInterface inter;
	private MyShopItem myShopItem;

	public ShopCartAdapter(Context context, ArrayList<ShopCartBean> list,
			ShopcartInterface inter) {
		this.context = context;
		this.list = list;
		this.inter = inter;
		inflater = LayoutInflater.from(context);
	}

	public void setdata(ArrayList<ShopCartBean> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list != null ? list.size() : 0;
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
	public View getView(final int postion, View v, ViewGroup arg2) {
				if(v==null){
					myShopItem = new MyShopItem();
					v = inflater.inflate(R.layout.shopcart_lv_item, null);
					myShopItem.iv = (ImageView) v.findViewById(R.id.shopcart_item_pp_iv);
					myShopItem.tv_titlt = (TextView) v.findViewById(R.id.shopcart_item_title_tv);
					myShopItem.tv_price = (TextView) v.findViewById(R.id.shopcart_item_price_tv);
					myShopItem.tv_kucun = (TextView) v.findViewById(R.id.shopcart_lv_item_kucun_tv);
					myShopItem.et_num = (EditText) v.findViewById(R.id.shopcart_item_num_et);
					myShopItem.add_rl = (RelativeLayout) v.findViewById(R.id.shopcart_lv_item_add_rl);
					myShopItem.btn_add = (ImageButton) v.findViewById(R.id.shopcart_lv_item_add_ibt);
					myShopItem.jian_tl = (RelativeLayout) v.findViewById(R.id.shopcart_lv_item_jian_rl);
					myShopItem.btn_jian = (ImageButton) v.findViewById(R.id.shopcart_lv_item_jian_ibt);
					v.setTag(myShopItem);
				}else{
					myShopItem = (MyShopItem) v.getTag();
				}
				myShopItem.iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				ImageLoader.getInstance().displayImage(list.get(postion).getPic_url(), myShopItem.iv);
				myShopItem.tv_titlt.setText(list.get(postion).getGoods_name());
				myShopItem.tv_price.setText("ฃค"+list.get(postion).getGoods_price()+"ิช");
				myShopItem.tv_kucun.setText(list.get(postion).getGoods_count());
				myShopItem.et_num.setText(list.get(postion).getGoods_number());
				
				myShopItem.add_rl.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if(0<Integer.parseInt(list.get(postion).getGoods_number())&&
								Integer.parseInt(list.get(postion).getGoods_number())<
								Integer.parseInt(list.get(postion).getGoods_count())){
						inter.add(list, postion);
						}
					}
				});
				myShopItem.btn_add.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if(0<Integer.parseInt(list.get(postion).getGoods_number())&&
								Integer.parseInt(list.get(postion).getGoods_number())<
								Integer.parseInt(list.get(postion).getGoods_count())){
							inter.add(list, postion);
						}
					}
				});
				myShopItem.jian_tl.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if(1<Integer.parseInt(list.get(postion).getGoods_number())&&
				
								Integer.parseInt(list.get(postion).getGoods_number())<
								Integer.parseInt(list.get(postion).getGoods_count())){
						inter.jian(list, postion);
						}
					}
				});
				myShopItem.btn_jian.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if(1<Integer.parseInt(list.get(postion).getGoods_number())&&
								
								Integer.parseInt(list.get(postion).getGoods_number())<
								Integer.parseInt(list.get(postion).getGoods_count())){
							inter.jian(list, postion);
						}
					}
				});
				return v;
	}

	
		class MyShopItem{
			private ImageView iv;
			private TextView tv_titlt,tv_price,tv_kucun;
			private EditText et_num;
			private ImageButton btn_add,btn_jian;
			private RelativeLayout add_rl,jian_tl;
		}


}
