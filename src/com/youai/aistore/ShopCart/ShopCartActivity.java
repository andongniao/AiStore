package com.youai.aistore.ShopCart;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.xlistview.XListView;
import com.youai.aistore.xlistview.XListView.IXListViewListener;
/**
 * 购物车首页
 * @author Qzr
 *
 */
public class ShopCartActivity extends BaseActivity implements OnClickListener{
//,IXListViewListener{
	private long exitTime = 0;
	private Context context;
	private ListView lv;
	private ImageView isnull_iv;
	private View isnull;
	private ShopCartAdapter adapter;
	private LinearLayout showviewll;
	private Button seeagainbt,goypaybt;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		context = ShopCartActivity.this;
		goneTopLeft();
		topRightGone();
		setTitleTxt(R.string.shopcart_title);
		setContentXml(R.layout.shopcart);
		//getLayoutInflater().inflate(R.layout.shorcart_isnull_show, null);
		init();
		
		
		
	}

	private void init() {
		isnull = findViewById(R.id.shopcart_isnull_ll);
		showviewll = (LinearLayout) findViewById(R.id.shopcart_showview_ll);
		seeagainbt = (Button) findViewById(R.id.shopcart_see_again_bt);
		goypaybt = (Button) findViewById(R.id.shopcart_gopay_bt);
		lv = (ListView) findViewById(R.id.shopcart_listview);
//		lv.setPullLoadEnable(true);
//		lv.setXListViewListener(this);
		isnull_iv = (ImageView)findViewById(R.id.shopcart_isnull_iv);
		isnull_iv.setOnClickListener(this);
		lv.setEmptyView(isnull);
		/*********模拟数据*********/
		ArrayList<GoodsBean> list = new ArrayList<GoodsBean>();
		GoodsBean a = new GoodsBean();
		a.setId(12);
		a.setPicurl("xxx");
		a.setTitle("test");
		a.setShop_price("12.00");
		a.setType(2);
		GoodsBean s = new GoodsBean();
		GoodsBean d = new GoodsBean();
		GoodsBean f = new GoodsBean();
		GoodsBean g = new GoodsBean();
		list.add(a);
		list.add(s);
		list.add(d);
		list.add(f);
		list.add(g);
		
		adapter = new ShopCartAdapter(context, list);
		lv.setAdapter(adapter);
		if(list!=null && list.size()>0){
			showviewll.setVisibility(View.VISIBLE);
		}else{
			showviewll.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shopcart_isnull_iv:
			Util.ShowToast(context, "没有数据");
			break;
		case R.id.shopcart_see_again_bt:
			Util.ShowToast(context, "再看看");
			break;
		case R.id.shopcart_gopay_bt:
			Util.ShowToast(context, "去支付");
			break;

		}
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
				if((System.currentTimeMillis()-exitTime) > 2000){  
					Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
					exitTime = System.currentTimeMillis();   
				} else {
					finish();
					System.exit(0);
				}
				return true;   
			}
		return super.onKeyDown(keyCode, event);
	}

//	@Override
//	public void onRefresh() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onLoadMore() {
//		// TODO Auto-generated method stub
//		
//	}
}
