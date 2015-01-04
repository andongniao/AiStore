package com.youai.aistore.Fclass;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Home.SearchResultAdapter;
import com.youai.aistore.xlistview.XListView;
import com.youai.aistore.xlistview.XListView.IXListViewListener;


public class FclassMoreActivity extends BaseActivity implements IXListViewListener,OnClickListener {
	private LinearLayout popll,numll,pricell;
	private XListView listView;
	private SearchResultAdapter adapter;
	private Context context;
	
	private ArrayList<GoodsBean>list;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentXml(R.layout.fclass_more);
		String title = getIntent().getStringExtra("title");
		setTitleTxt(title);
		
		init();
	}

	private void init() {
		context = FclassMoreActivity.this;
		popll = (LinearLayout) findViewById(R.id.fclass_more_popularity_ll);
		numll = (LinearLayout) findViewById(R.id.fclass_more_number_ll);
		pricell = (LinearLayout) findViewById(R.id.fclass_more_price_ll);
		popll.setOnClickListener(this);
		numll.setOnClickListener(this);
		pricell.setOnClickListener(this);
		listView = (XListView) findViewById(R.id.search_result_lv);
		/***********************Ä£ÄâÊý¾Ý***************************/
		Resources rs = getResources();
		String[] tv_shop_price = rs
				.getStringArray(R.array.fclass_gridview_avtextmoney);
		String[] tv_market_price = rs
				.getStringArray(R.array.fclass_gridview_avtextmoney);
		String[] tv_comment = rs
				.getStringArray(R.array.fclass_gridview_avtextcomment);
		String[] tv_title = rs
				.getStringArray(R.array.fclass_gridview_avtextproduct);
		TypedArray imgproduct = rs
				.obtainTypedArray(R.array.fclass_gridview_avimgproduct);
		int len = imgproduct.length();
		int[] resIds = new int[len];
		for (int i = 0; i < len; i++) {
			resIds[i] = imgproduct.getResourceId(i, 0);
		}
		imgproduct.recycle();
		//list = (ArrayList<GoodsBean>) getIntent().getExtras().get("list");
		list = new ArrayList<GoodsBean>();
		GoodsBean a=new GoodsBean();
		list.add(a);
			//list.add(tv_shop_price[i]);
	
		
		if(list!=null){
		adapter = new SearchResultAdapter(context, list);
		listView.setAdapter(adapter);
		}
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.fclass_more_popularity_ll:
			
			break;
		case R.id.fclass_more_number_ll:
			
			break;
		case R.id.fclass_more_price_ll:
			
			break;

		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		GoodsBean s = new GoodsBean();
		list.add(s);
		adapter.setdata(list);
		adapter.notifyDataSetChanged();
		onLoad();
	}
	
	private void onLoad(){
		listView.stopRefresh();
		listView.stopLoadMore();
	}

}

