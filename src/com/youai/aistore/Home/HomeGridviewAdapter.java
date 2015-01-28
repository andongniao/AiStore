package com.youai.aistore.Home;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.R;
import com.youai.aistore.Bean.GoodsBean;

public class HomeGridviewAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private ArrayList<GoodsBean> beanlist;
	private LayoutInflater inflater;
	private MyHalder myHalder;

	public HomeGridviewAdapter(Context context, ArrayList<GoodsBean> beanlist) {
		this.beanlist = beanlist;
		this.context = context;
		inflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return beanlist != null ? beanlist.size() : 0;
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			myHalder = new MyHalder();
			convertView = inflater.inflate(R.layout.home_like_addview, null);
			myHalder.imageView = (ImageView) convertView
					.findViewById(R.id.home_like_iv_l);
			myHalder.textTitle = (TextView) convertView
					.findViewById(R.id.home_like_tv_l_title);
			myHalder.textprice = (TextView) convertView

			.findViewById(R.id.home_like_l_tv_price);
			myHalder.textclick = (TextView) convertView
					.findViewById(R.id.home_like_l_tv_num);
			convertView.setTag(myHalder);
		} else {
			myHalder = (MyHalder) convertView.getTag();
		}
		myHalder.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		ImageLoader.getInstance().displayImage(beanlist.get(arg0).getPicurl(),
				myHalder.imageView);
		myHalder.textclick.setText(beanlist.get(arg0).getClick());
		myHalder.textprice.setText("￥" + beanlist.get(arg0).getShop_price());
		myHalder.textTitle.setText(beanlist.get(arg0).getTitle());

		return convertView;
	}

	private class MyHalder {
		public ImageView imageView;
		public TextView textTitle, textprice, textclick;
	}

}
