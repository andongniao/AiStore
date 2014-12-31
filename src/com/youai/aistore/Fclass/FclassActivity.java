package com.youai.aistore.Fclass;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Home.MyGridview;

/**
 * @author zy
 * 
 */
public class FclassActivity extends BaseActivity {
	private MyGridview gridview,gridview_av;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTopLeftBackground(R.drawable.btn_search_navigation_back);
		// 分类界面标题；显示
		setTitleTxt(R.string.titleTV);
		setContentXml(R.layout.fclass);

		// 声明资源变量，获取资源
		Resources rs = this.getResources();
		// 创建列表分类数组，
		String[] gridFclass = rs.getStringArray(R.array.fclass_gridview);
		// 获取列表的id
		gridview = (MyGridview) findViewById(R.id.fclass_gridview);
		/*
		 * 列表适配器ArrayAdapter<t>
		 * (context;子view的layout，由我们在资源中自行设定布局文件;子view中要填充数据的TextView的控件ID;数据源);
		 */
		gridview.setAdapter(new ArrayAdapter<String>(this,
				R.layout.fclass_gridview, R.id.fclass_gridview_text, gridFclass));
		// 监听器
		// gridview.setOnItemClickListener(new MiddleListclick());

		// 创建列表分类数组，
		String[] avtextmoney = rs
				.getStringArray(R.array.fclass_gridview_avtextmoney);
		String[] avtextcomment = rs
				.getStringArray(R.array.fclass_gridview_avtextcomment);
		String[] avtextproduct = rs
				.getStringArray(R.array.fclass_gridview_avtextproduct);
		TypedArray avimgproduct = rs
				.obtainTypedArray(R.array.fclass_gridview_avimgproduct);
		int len = avimgproduct.length();
		int[] resIds = new int[len];
		for (int i = 0; i < len; i++) {
			resIds[i] = avimgproduct.getResourceId(i, 0);
		}
		avimgproduct.recycle();
		// 获取列表的id
		gridview_av = (MyGridview) findViewById(R.id.fclass_gridview_av);
		gridview_av.setAdapter(getCategoryAdapter(resIds,avtextmoney,avtextcomment,avtextproduct));

	}

	private SimpleAdapter getCategoryAdapter(int[] avimgproduct,
			String[] avtextmoney, String[] avtextcomment, String[] avtextproduct) {
		ArrayList<HashMap<String, Object>> date = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < avtextmoney.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("imgproduct", avimgproduct[i]);
			map.put("textmoney", avtextmoney[i]);
			map.put("textcomment", avtextcomment[i]);
			map.put("textproduct", avtextproduct[i]);
			date.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(
				getApplicationContext(), date, R.layout.fclass_gridview_item,
				new String[] { "imgproduct", "textmoney", "textcomment",
						"textproduct" }, new int[] {
						R.id.fclass_gridview_item_imgproduct,
						R.id.fclass_gridview_item_textmoney,
						R.id.fclass_gridview_item_textcomment,
						R.id.fclass_gridview_item_textproduct });
		return simperAdapter;

		
		
	}

}// 结束
