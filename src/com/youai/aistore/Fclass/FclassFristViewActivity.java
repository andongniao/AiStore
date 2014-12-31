package com.youai.aistore.Fclass;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Home.MyGridview;

public class FclassFristViewActivity extends BaseActivity{
	private MyGridview topgtitleridview;
	private LinearLayout addviewll;
	private int listindex;
	private Resources rs;
	private String[] titlelist;
	private ArrayList<View> addviewlist;
	private LayoutInflater inflater;
	private Context context;
	private ArrayList<MyGridview>gridviewlist;

	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentXml(R.layout.fclass_frist_view);
		String title = getIntent().getStringExtra("title");
		listindex = getIntent().getIntExtra("listindex", -1);
		setTitleTxt(title);
		init();
	}

	private void init() {
		context = this;
		rs = getResources();
		addviewlist = new ArrayList<View>();
		inflater = LayoutInflater.from(context);
		gridviewlist = new ArrayList<MyGridview>();
		topgtitleridview = (MyGridview) findViewById(R.id.fclass_frist_view_topgridview);
		addviewll = (LinearLayout) findViewById(R.id.fclass_frist_view_addview_ll);
		if(listindex == 0){
			titlelist =  rs.getStringArray(R.array.fclass_frist_woman_gridview);
		}else if(listindex == 1){
			titlelist =  rs.getStringArray(R.array.fclass_frist_man_gridview);
		}else if(listindex == 2){
			titlelist =  rs.getStringArray(R.array.fclass_frist_neiyi_gridview);
		}else if(listindex == 3){
			titlelist =  rs.getStringArray(R.array.fclass_frist_tt_gridview);
		}else if(listindex == 5){
			titlelist =  rs.getStringArray(R.array.fclass_frist_tosex_gridview);
		}
		
		topgtitleridview.setAdapter(new ArrayAdapter<String>(this,
				R.layout.fclass_gridview, R.id.fclass_gridview_text, titlelist));
		
		for(int i=0;i<titlelist.length;i++){
			View v = inflater.inflate(R.layout.fclass_frist_view_added_view, null);
			TextView tv = (TextView) v.findViewById(R.id.fclass_frist_view_addview_title_tv);
			tv.setText(titlelist[i]);
			MyGridview g = (MyGridview) v.findViewById(R.id.fclass_frist_view_addview_gridview);
			gridviewlist.add(g);
			addviewlist.add(v);
		}
		//TODO
		/**
		 * item view in adapter 2 import and setData
		 */
	}
	
}
