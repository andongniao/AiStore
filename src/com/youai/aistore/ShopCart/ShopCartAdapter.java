package com.youai.aistore.ShopCart;

import java.util.ArrayList;

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
import com.youai.aistore.Util;
import com.youai.aistore.Bean.ShopCartBean;
import com.youai.aistore.ShopCart.ShopCartActivity.ShopcartInterface;
import com.youai.aistore.View.SlideView;
import com.youai.aistore.View.SlideView.OnSlideListener;

public class ShopCartAdapter extends BaseAdapter 	{
	public SlideView slideView;
	private Context context;
	private ArrayList<ShopCartBean> list;
	private LayoutInflater inflater;
	private ShopcartInterface inter;
	private MyShopItem myShopItem;
	public ArrayList<SlideView> slidelist = new ArrayList<SlideView>();

	public ShopCartAdapter(Context context,ArrayList<ShopCartBean> list,ShopcartInterface inter){
		this.context = context;
		this.list = list;
		this.inter = inter;
		inflater = LayoutInflater.from(context);
	}
	public void setdata(ArrayList<ShopCartBean> list){
		this.list = list;
	}
	public ArrayList<SlideView> getdata(){
		return slidelist;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return slidelist.get(arg0);
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
					myShopItem.btn_add = (ImageButton) v.findViewById(R.id.shopcart_lv_item_add_ibt);
					myShopItem.btn_jian = (ImageButton) v.findViewById(R.id.shopcart_lv_item_jian_ibt);
					v.setTag(myShopItem);
				}else{
					myShopItem = (MyShopItem) v.getTag();
				}
				myShopItem.tv_titlt.setText(""+postion);
				myShopItem.iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				ImageLoader.getInstance().displayImage(list.get(postion).getPic_url(), myShopItem.iv);
				myShopItem.tv_titlt.setText(list.get(postion).getGoods_name());
				myShopItem.tv_price.setText("￥"+list.get(postion).getGoods_price()+"元");
				myShopItem.tv_kucun.setText(list.get(postion).getGoods_count());
				myShopItem.et_num.setText(list.get(postion).getGoods_number());
				
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
				myShopItem.btn_jian.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if(0<Integer.parseInt(list.get(postion).getGoods_number())&&
								Integer.parseInt(list.get(postion).getGoods_number())<
								Integer.parseInt(list.get(postion).getGoods_count())){
						inter.jian(list, postion);
						}
					}
				});
				return v;



//		MyItem holder;
//		slideView = (SlideView) v;
//		if (slideView == null) {
//
//			View itemView = inflater.inflate(R.layout.shopcart_lv_item, null);
//
//			slideView = new SlideView(context);
//			slidelist.add(slideView);
//
//			slideView.setContentView(itemView);
//			holder = new MyItem(slideView);
//			slideView.setOnSlideListener(new OnSlideListener() {
//
//				@Override
//				public void onSlide(View view, int status) {
//
//					if (slideView != null && slideView != view) {
//						slideView.shrink();
//					}
//
//					if (status == SLIDE_STATUS_ON) {
//						slideView = (SlideView) view;
//					}					
//				}
//			});
//			slideView.setTag(holder);
//		} else {
//			holder = (MyItem) slideView.getTag();
//		}
//		slideView = slidelist.get(postion);
//		slideView.shrink();
//
//		holder.tv_titlt.setText(""+postion);
//		holder.iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//		ImageLoader.getInstance().displayImage(list.get(postion).getPic_url(), holder.iv);
//		holder.tv_titlt.setText(list.get(postion).getGoods_name());
//		holder.tv_price.setText("￥"+list.get(postion).getGoods_price()+"元");
//		holder.tv_kucun.setText(list.get(postion).getGoods_count());
//		holder.et_num.setText(list.get(postion).getGoods_number());
//
//		holder.delete_rl.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				Util.ShowToast(context, "点击了删除");
//				inter.delete(list, postion);
//			}
//		});
//		holder.btn_add.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				if(0<Integer.parseInt(list.get(postion).getGoods_number())&&
//						Integer.parseInt(list.get(postion).getGoods_number())<
//						Integer.parseInt(list.get(postion).getGoods_count())){
//					inter.add(list, postion);
//				}
//			}
//		});
//		holder.btn_jian.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				if(1<Integer.parseInt(list.get(postion).getGoods_number())&&
//						Integer.parseInt(list.get(postion).getGoods_number())<
//						Integer.parseInt(list.get(postion).getGoods_count())){
//					inter.jian(list, postion);
//				}
//			}
//		});
//
//		return slideView;
	}


	class MyItem{
		private ImageView iv;
		private TextView tv_titlt,tv_price,tv_kucun;
		private EditText et_num;
		private RelativeLayout delete_rl;
		private ImageButton btn_add,btn_jian;
		MyItem(View view) {
			iv = (ImageView) view.findViewById(R.id.shopcart_item_pp_iv);
			tv_titlt = (TextView) view.findViewById(R.id.shopcart_item_title_tv);
			tv_price = (TextView) view.findViewById(R.id.shopcart_item_price_tv);
			tv_kucun = (TextView) view.findViewById(R.id.shopcart_lv_item_kucun_tv);
			et_num = (EditText) view.findViewById(R.id.shopcart_item_num_et);
			delete_rl = (RelativeLayout) view.findViewById(R.id.holder);
			btn_add = (ImageButton) view.findViewById(R.id.shopcart_lv_item_add_ibt);
			btn_jian = (ImageButton) view.findViewById(R.id.shopcart_lv_item_jian_ibt);
		}
	}

		class MyShopItem{
			private ImageView iv;
			private TextView tv_titlt,tv_price,tv_kucun;
			private EditText et_num;
			private ImageButton btn_add,btn_jian;
		}


}
