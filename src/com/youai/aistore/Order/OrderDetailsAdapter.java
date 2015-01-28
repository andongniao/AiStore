package com.youai.aistore.Order;

import java.util.ArrayList;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.R;
import com.youai.aistore.Bean.OrderDetailsBean.Goods;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 订单详情适配器
 * 
 * @author Qzr
 * 
 */
@SuppressLint("InflateParams")
public class OrderDetailsAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private ArrayList<Goods> list;
	private LayoutInflater inflater;
	private Item item;

	public OrderDetailsAdapter(Context context, ArrayList<Goods> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
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
	public View getView(int postion, View v, ViewGroup arg2) {
		if (v == null) {
			item = new Item();
			v = inflater.inflate(R.layout.order_detail_item, null);
			item.view(v);
			v.setTag(item);
		} else {
			item = (Item) v.getTag();
		}
		item.tv_title.setText(list.get(postion).getGoods_name());
		item.tv_price.setText("￥" + list.get(postion).getGoods_price() + "元");
		item.tv_number.setText(list.get(postion).getGoods_number() + "件");
		item.iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		ImageLoader.getInstance().displayImage(
				list.get(postion).getGoods_thumb(), item.iv);
		return v;
	}

	class Item {
		public TextView tv_title, tv_price, tv_number;
		public ImageView iv;

		void view(View v) {
			iv = (ImageView) v.findViewById(R.id.order_detail_item_pp_iv);
			tv_title = (TextView) v
					.findViewById(R.id.order_detail_item_title_tv);
			tv_price = (TextView) v
					.findViewById(R.id.order_detail_item_price_tv);
			tv_number = (TextView) v
					.findViewById(R.id.order_detail_item_number_tv);
		}
	}
}
