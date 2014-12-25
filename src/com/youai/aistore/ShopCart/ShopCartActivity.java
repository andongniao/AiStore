package com.youai.aistore.ShopCart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;

public class ShopCartActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private ListView lv;
	private ImageView isnull_iv;
	private View isnull;
	ExpandableListView listView;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		context = ShopCartActivity.this;
		goneTopLeft();
		topRightGone();
		setTitleTxt(R.string.shopcart_title);
		setContentXml(R.layout.shopcart);
		isnull = findViewById(R.id.shopcart_isnull_ll);//getLayoutInflater().inflate(R.layout.shorcart_isnull_show, null);
		init();
		
		
		
	}

	private void init() {
		lv = (ListView) findViewById(R.id.shopcart_listview);
		isnull_iv = (ImageView)findViewById(R.id.shopcart_isnull_iv);
		isnull_iv.setOnClickListener(this);
		lv.setEmptyView(isnull);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shopcart_isnull_iv:
			Util.ShowToast(context, "没有数据");
			break;

		}
	}
	

}
