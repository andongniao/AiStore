package com.youai.aistore;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TabWidget;

import com.youai.aistore.Fclass.FclassHomeActivity;
import com.youai.aistore.Home.HomeActivity;
import com.youai.aistore.Product.ProductDetailsActivity;
import com.youai.aistore.ShopCart.ShopCartActivity;

/**
 * ����
 * @author Qzr
 *
 */
public class ExampleActivity extends TabHostActivity {

	private List<TabItem> mItems;
	/**�ڳ�ʼ��TabWidgetǰ����
	 * ��TabWidget�йصı����������ʼ��*/
	@Override
	protected void prepare() {
		TabItem Home = new TabItem(
				R.drawable.base_home,					// background
				new Intent(this, HomeActivity.class));	// intent
		
		TabItem Fclass = new TabItem(
				R.drawable.base_fclass_bg,
				new Intent(this, FclassHomeActivity.class));
		
		TabItem ShopCart = new TabItem(
				R.drawable.base_shopcart,
				new Intent(this, ShopCartActivity.class));
		
		TabItem MyCenter = new TabItem(
				R.drawable.base_mycenter,
				new Intent(this, ProductDetailsActivity.class));
		
//		TabItem more = new TabItem(
//				"����",
//				R.drawable.icon_more,
//				R.drawable.example_tab_item_bg,
//				new Intent(this, HomeActivity.class));
		
		mItems = new ArrayList<TabItem>();
		mItems.add(Home);
		mItems.add(Fclass);
		mItems.add(ShopCart);
		mItems.add(MyCenter);
//		mItems.add(more);

		// ���÷ָ���
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
	
	/**tab��title��icon���߾��趨�ȵ�*/
	@Override
	protected void setTabItemTextView(ImageView textView, int position) {
		textView.setPadding(3, 3, 3, 3);
//		textView.setText(mItems.get(position).getTitle());
		textView.setBackgroundResource(mItems.get(position).getBg());
//		textView.setCompoundDrawablesWithIntrinsicBounds(0, mItems.get(position).getIcon(), 0, 0);
		
	}
	
	/**tabΨһ��id*/
	@Override
	protected String getTabItemId(int position) {
		return mItems.get(position).getIntent().toString();	// ����ʹ��title����Ϊid����Ҳ�����Զ�
	}

	/**���tabʱ�������¼�*/
	@Override
	protected Intent getTabItemIntent(int position) {
		
		return mItems.get(position).getIntent();
	}

	@Override
	protected int getTabItemCount() {
		return mItems.size();
	}
	
//	/**�Զ���ͷ���ļ�*/
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