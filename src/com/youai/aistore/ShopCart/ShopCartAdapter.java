package com.youai.aistore.ShopCart;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.R;
import com.youai.aistore.Bean.ShopCartBean;
import com.youai.aistore.ShopCart.ShopCartActivity.ShopcartInterface;
import com.youai.aistore.View.SlideView;

public class ShopCartAdapter extends BaseAdapter 	{
	public SlideView slideView;
	private Context context;
	private ArrayList<ShopCartBean> list;
	private MyShopItem myShopItem;
	private LayoutInflater inflater;
	private int statu;
	private ShopcartInterface inter;
	public List<SlideView> slidelist = new ArrayList<SlideView>();
	
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
	public View getView(final int postion, View v, ViewGroup arg2) {
		if(v==null){
			myShopItem = new MyShopItem();
			v = inflater.inflate(R.layout.shopcart_lv_item, null);
			myShopItem.iv = (ImageView) v.findViewById(R.id.shopcart_item_pp_iv);
			myShopItem.tv_titlt = (TextView) v.findViewById(R.id.shopcart_item_title_tv);
			myShopItem.tv_price = (TextView) v.findViewById(R.id.shopcart_item_price_tv);
			myShopItem.tv_delete = (TextView) v.findViewById(R.id.shopcart_item_delete_tv);
			myShopItem.tv_kucun = (TextView) v.findViewById(R.id.shopcart_lv_item_kucun_tv);
			myShopItem.et_num = (EditText) v.findViewById(R.id.shopcart_item_num_et);
			myShopItem.delete_ll = (LinearLayout) v.findViewById(R.id.shopcart_item_delete_ll);
			myShopItem.btn_add = (ImageButton) v.findViewById(R.id.shopcart_lv_item_add_ibt);
			myShopItem.btn_jian = (ImageButton) v.findViewById(R.id.shopcart_lv_item_jian_ibt);
			v.setTag(myShopItem);
		}else{
			myShopItem = (MyShopItem) v.getTag();
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
		
		
		
//		 MyItem holder;
//         slideView = (SlideView) v;
//         if (slideView == null) {
//        	 
//             View itemView = inflater.inflate(R.layout.shopcart_lv_item, null);
//
//             slideView = new SlideView(context);
//             slidelist.add(slideView);
//             
//             slideView.setContentView(itemView);
//             holder = new MyItem(slideView);
//             slideView.setOnSlideListener(new OnSlideListener() {
//				
//				@Override
//				public void onSlide(View view, int status) {
//					inter.setlvdata(slidelist.get(postion));
//					
//					 if (slideView != null && slideView != view) {
//						 slideView.shrink();
//				        }
//
//				        if (status == SLIDE_STATUS_ON) {
//				        	slideView = (SlideView) view;
//				        }					
//				}
//			});
//             slideView.setTag(holder);
//         } else {
//             holder = (MyItem) slideView.getTag();
//         }
////         MessageItem item = mMessageItems.get(postion);
//         slideView = slidelist.get(postion);
//         slideView.shrink();
//
//        holder.tv_titlt.setText(""+postion);
// 		holder.iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
// 		ImageLoader.getInstance().displayImage(list.get(postion).getPic_url(), holder.iv);
// 		holder.tv_titlt.setText(list.get(postion).getGoods_name());
// 		holder.tv_price.setText("ฃค"+list.get(postion).getGoods_price()+"ิช");
// 		holder.tv_kucun.setText(list.get(postion).getGoods_count());
// 		holder.et_num.setText(list.get(postion).getGoods_number());
//		
//		
//		return slideView;
	}

	
	class MyItem{
		private ImageView iv;
		private TextView tv_titlt,tv_price,tv_delete,tv_kucun;
		private EditText et_num;
		private LinearLayout delete_ll;
		private ImageButton btn_add,btn_jian;
		MyItem(View view) {
			iv = (ImageView) view.findViewById(R.id.shopcart_item_pp_iv);
			tv_titlt = (TextView) view.findViewById(R.id.shopcart_item_title_tv);
			tv_price = (TextView) view.findViewById(R.id.shopcart_item_price_tv);
			tv_delete = (TextView) view.findViewById(R.id.shopcart_item_delete_tv);
			tv_kucun = (TextView) view.findViewById(R.id.shopcart_lv_item_kucun_tv);
			et_num = (EditText) view.findViewById(R.id.shopcart_item_num_et);
			delete_ll = (LinearLayout) view.findViewById(R.id.shopcart_item_delete_ll);
			btn_add = (ImageButton) view.findViewById(R.id.shopcart_lv_item_add_ibt);
			btn_jian = (ImageButton) view.findViewById(R.id.shopcart_lv_item_jian_ibt);
		 }
	}
	
	class MyShopItem{
		private ImageView iv;
		private TextView tv_titlt,tv_price,tv_delete,tv_kucun;
		private EditText et_num;
		private LinearLayout delete_ll;
		private ImageButton btn_add,btn_jian;
	}
	
	
}
