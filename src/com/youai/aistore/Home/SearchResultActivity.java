package com.youai.aistore.Home;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Bean.ListGoodsBean;
import com.youai.aistore.xlistview.XListView;
import com.youai.aistore.xlistview.XListView.IXListViewListener;
/**
 * 搜索结果
 * @author Qzr
 *
 */
public class SearchResultActivity extends BaseActivity implements IXListViewListener,OnClickListener{
	private LinearLayout popll,numll,pricell;
	private XListView listView;
	private SearchResultAdapter adapter;
	private Context context;
	
	private ArrayList<GoodsBean>list;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTitleTxt(R.string.search_bt_text);
		setContentXml(R.layout.search_result);
		init();
	}

	private void init() {
		context = SearchResultActivity.this;
		popll = (LinearLayout) findViewById(R.id.search_result_popularity_ll);
		numll = (LinearLayout) findViewById(R.id.search_result_number_ll);
		pricell = (LinearLayout) findViewById(R.id.search_result_price_ll);
		popll.setOnClickListener(this);
		numll.setOnClickListener(this);
		pricell.setOnClickListener(this);
		listView = (XListView) findViewById(R.id.search_result_lv);
		/***********************模拟数据***************************/
		list = (ArrayList<GoodsBean>) getIntent().getExtras().get("list");
		if(list!=null){
		adapter = new SearchResultAdapter(context, list);
		listView.setAdapter(adapter);
		}
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.search_result_popularity_ll:
			
			break;
		case R.id.search_result_number_ll:
			
			break;
		case R.id.search_result_price_ll:
			
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
