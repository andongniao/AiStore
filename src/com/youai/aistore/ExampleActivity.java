package com.youai.aistore;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TabWidget;

import com.youai.aistore.Fclass.FclassHomeActivity;
import com.youai.aistore.Home.HomeActivity;
import com.youai.aistore.Mycenter.MycenterHomeActivity;
import com.youai.aistore.ShopCart.ShopCartActivity;

public class ExampleActivity extends TabHostActivity {

	private List<TabItem> mItems;

	@Override
	protected void prepare() {
		TabItem Home = new TabItem(R.drawable.base_home, 
				new Intent(this, HomeActivity.class)); 

		TabItem Fclass = new TabItem(R.drawable.base_fclass_bg, new Intent(
				this, FclassHomeActivity.class));

		TabItem ShopCart = new TabItem(R.drawable.base_shopcart, new Intent(
				this, ShopCartActivity.class));

		TabItem MyCenter = new TabItem(R.drawable.base_mycenter, new Intent(
				this, MycenterHomeActivity.class));
		mItems = new ArrayList<TabItem>();
		mItems.add(Home);
		mItems.add(Fclass);
		mItems.add(ShopCart);
		mItems.add(MyCenter);

		@SuppressWarnings("deprecation")
		TabWidget tabWidget = getTabWidget();
		tabWidget.setDividerDrawable(R.drawable.tab_divider);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCurrentTab(0);
	}

	@Override
	protected void setTabItemTextView(ImageView textView, int position) {
		textView.setPadding(3, 3, 3, 3);
		textView.setBackgroundResource(mItems.get(position).getBg());

	}

	@Override
	protected String getTabItemId(int position) {
		return mItems.get(position).getIntent().toString(); 
	}

	@Override
	protected Intent getTabItemIntent(int position) {

		return mItems.get(position).getIntent();
	}

	@Override
	protected int getTabItemCount() {
		return mItems.size();
	}
}