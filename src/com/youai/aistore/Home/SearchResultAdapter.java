package com.youai.aistore.Home;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.R;
import com.youai.aistore.Bean.GoodsBean;

@SuppressLint("InflateParams")
public class SearchResultAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private ArrayList<GoodsBean> list;
	private LayoutInflater inflater;
	private MyItem myItem;

	public SearchResultAdapter(Context context, ArrayList<GoodsBean> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	public void setdata(ArrayList<GoodsBean> list) {
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if (arg1 == null) {
			myItem = new MyItem();
			arg1 = inflater.inflate(R.layout.search_result_lv_item, null);
			myItem.iv = (ImageView) arg1
					.findViewById(R.id.search_result_item_iv);
			myItem.tv_title = (TextView) arg1
					.findViewById(R.id.search_result_item_title_tv);
			myItem.tv_shop_price = (TextView) arg1
					.findViewById(R.id.search_result_item_shop_price_tv);
			myItem.tv_market_price = (TextView) arg1
					.findViewById(R.id.search_result_item_market_price_tv);
			myItem.tv_comments = (TextView) arg1
					.findViewById(R.id.search_result_item_comments_tv);
			myItem.tv_click = (TextView) arg1
					.findViewById(R.id.search_result_item_click_tv);
			arg1.setTag(myItem);
		} else {
			myItem = (MyItem) arg1.getTag();
		}

		// TODO s
		myItem.iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		ImageLoader.getInstance().displayImage(list.get(arg0).getPicurl(),
				myItem.iv);
		myItem.tv_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中划线
		myItem.tv_market_price.getPaint().setAntiAlias(true);// 抗锯齿
		myItem.tv_title.setText(list.get(arg0).getTitle());
		myItem.tv_market_price.setText("￥" + list.get(arg0).getMarket_price()
				+ "元");
		myItem.tv_shop_price
				.setText("￥" + list.get(arg0).getShop_price() + "元");
		myItem.tv_comments.setText(list.get(arg0).getComments_num());
		myItem.tv_click.setText(list.get(arg0).getClick());
		return arg1;
	}

	class MyItem {
		public ImageView iv;
		public TextView tv_title, tv_shop_price, tv_market_price, tv_comments,
				tv_click;
	}
}
