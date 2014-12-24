package com.youai.aistore;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**主程序入口*/
public class MainActivity extends ListActivity {
    private ListView mListView;
	private ListAdapter mAdapter;
	private ArrayList<Item> mItems;
	private LayoutInflater mLayoutInflater;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mLayoutInflater = getLayoutInflater();
        
        mItems = new ArrayList<Item>();
    	Item item = new Item("Tab Host Example", ExampleActivity.class);
    	mItems.add(item);
    	
        mAdapter = new MyAdapter();
        mListView = getListView();
        mListView.setAdapter(mAdapter);
        
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Item item = mItems.get(position);
    	startActivity(new Intent(this, item.cls));
    }
    
    class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mItems.size();
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
		public View getView(int position, View arg1, ViewGroup arg2) {
			Item item = mItems.get(position);
			
			View view = mLayoutInflater.inflate(R.layout.listview_item, null);
			TextView tv = (TextView) view.findViewById(R.id.list_item_tv);
			tv.setText(item.text);
			
			return view;
		}
    	
    }
    
    class Item { 
    	Class<? extends Activity> cls;
    	CharSequence text;
    	
		public Item(CharSequence text, Class<? extends Activity> cls) {
			super();
			this.cls = cls;
			this.text = text;
		}
    	
    	
    }

}