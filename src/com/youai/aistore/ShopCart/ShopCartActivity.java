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
		topRightGone();
		setTitleTxt(R.string.shopcart_title);
//		setContentXml(R.layout.shopcart);
		setContentXml(R.layout.my_fclass);
//		isnull = findViewById(R.id.shopcart_isnull_ll);//getLayoutInflater().inflate(R.layout.shorcart_isnull_show, null);
		init();
		
		
		
	}

	private void init() {
//		lv = (ListView) findViewById(R.id.shopcart_listview);
//		isnull_iv = (ImageView)findViewById(R.id.shopcart_isnull_iv);
//		isnull_iv.setOnClickListener(this);
//		lv.setEmptyView(isnull);
		listView = (ExpandableListView) findViewById(R.id.fclass_expandableListView);

		listView.setGroupIndicator(this.getResources().getDrawable(R.drawable.ic_launcher));
		MyexpandableListAdapter adapter = new MyexpandableListAdapter(this);
		listView.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.shopcart_isnull_iv:
//			Util.ShowToast(context, "没有数据");
//			break;
//
//		}
	}
	
	class MyexpandableListAdapter extends BaseExpandableListAdapter {
		private Context context;  
        private LayoutInflater inflater;  
  
        public MyexpandableListAdapter(Context context) {  
            this.context = context;  
            inflater = LayoutInflater.from(context);  
        }  

		@Override
		public Object getChild(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {
			// TODO Auto-generated method stub
			View v = inflater.inflate(R.layout.shorcart_isnull_show, null);
			ImageView iv = (ImageView) v.findViewById(R.id.shopcart_isnull_iv);
			iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Util.ShowToast(context, "测试");
				}
			});
			return v;
		}

		@Override
		public int getChildrenCount(int arg0) {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public Object getGroup(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public long getGroupId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getGroupView(int arg0, boolean arg1, View arg2,
				ViewGroup arg3) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}

}
