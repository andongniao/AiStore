package com.youai.aistore;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.youai.aistore.Home.HomeActivity;

/**
 * 导航
 * @author Qzr
 *
 */
public class ExampleActivity extends TabHostActivity {

	List<TabItem> mItems;
	
	/**在初始化TabWidget前调用
	 * 和TabWidget有关的必须在这里初始化*/
	@Override
	protected void prepare() {
		TabItem Home = new TabItem(
				R.drawable.base_home,					// background
				new Intent(this, HomeActivity.class));	// intent
		
		TabItem Fclass = new TabItem(
				R.drawable.base_fclass_bg,
				new Intent(this, HomeActivity.class));
		
		TabItem ShopCart = new TabItem(
				R.drawable.base_shopcart,
				new Intent(this, HomeActivity.class));
		
		TabItem MyCenter = new TabItem(
				R.drawable.base_mycenter,
				new Intent(this, HomeActivity.class));
		
//		TabItem more = new TabItem(
//				"更多",
//				R.drawable.icon_more,
//				R.drawable.example_tab_item_bg,
//				new Intent(this, HomeActivity.class));
		
		mItems = new ArrayList<TabItem>();
		mItems.add(Home);
		mItems.add(Fclass);
		mItems.add(ShopCart);
		mItems.add(MyCenter);
//		mItems.add(more);

		// 设置分割线
		@SuppressWarnings("deprecation")
		TabWidget tabWidget = getTabWidget();
		tabWidget.setDividerDrawable(R.drawable.tab_divider);
		
//		mLayoutInflater = getLayoutInflater();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCurrentTab(0);
	}
	
	/**tab的title，icon，边距设定等等*/
	@Override
	protected void setTabItemTextView(TextView textView, int position) {
		textView.setPadding(3, 3, 3, 3);
		textView.setText(mItems.get(position).getTitle());
		textView.setBackgroundResource(mItems.get(position).getBg());
		textView.setCompoundDrawablesWithIntrinsicBounds(0, mItems.get(position).getIcon(), 0, 0);
		
	}
	
	/**tab唯一的id*/
	@Override
	protected String getTabItemId(int position) {
		return mItems.get(position).getTitle();	// 我们使用title来作为id，你也可以自定
	}

	/**点击tab时触发的事件*/
	@Override
	protected Intent getTabItemIntent(int position) {
		
		return mItems.get(position).getIntent();
	}

	@Override
	protected int getTabItemCount() {
		return mItems.size();
	}
	
//	/**自定义头部文件*/
//	@Override
//	protected View getTop() {
//		View view = mLayoutInflater.inflate(R.layout.example_top, null);
////		ImageView lf = (ImageView) view.findViewById(R.id.example_left);
////		lf.setOnClickListener(new OnClickListener() {
////			@Override
////			public void onClick(View arg0) {
////				
////			}
////		});
//		return null;//mLayoutInflater.inflate(R.layout.example_top, null);
//	}

}