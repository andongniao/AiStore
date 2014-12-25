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

/**
 * @author zy
 * 
 */
public class FclassActivity extends BaseActivity {
	private MyGridView gridview, gridview_av, gridview_dildo,gridview_jumpegg,gridview_bead,gridview_nursing,gridview_other;
	private TextView fclass_classname_av, fclass_classname_dildo,fclass_classname_jumpegg,fclass_classname_bead,fclass_classname_nursing,fclass_classname_other;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentXml(R.layout.fclass);

		setTopLeftBackground(R.drawable.btn_search_navigation_back);
		setTopRightBackground(R.drawable.ic_launcher);
		topRightVisible();
		TextView topright = (TextView) getTopRightView();
		topright.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Util.ShowToast(FclassActivity.this, "search");
			}
		});
		// 分类界面标题；显示
		setTitleTxt(R.string.titleTV);
		visibleTitle();

		// 声明资源变量，获取资源
		Resources rs = this.getResources();
		// 创建分类数组，
		String[] gridFclass = rs.getStringArray(R.array.fclass_gridview);
		// 获取的id
		gridview = (MyGridView) findViewById(R.id.fclass_gridview);
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

		// 女性用品，av针对棒
		gridview_av = (MyGridView) findViewById(R.id.fclass_gridview_av);
		// 添加适配器
		gridview_av.setAdapter(getFclassAdapter(resIds, avtextmoney,
				avtextcomment, avtextproduct));
		// 添加分类的名字，
		fclass_classname_av = (TextView) findViewById(R.id.fclass_classname_text);
		fclass_classname_av.setText(gridFclass[0]);

		// 女性用品，仿真阳具
		gridview_dildo = (MyGridView) findViewById(R.id.fclass_gridview_dildo);
		// 添加适配器
		gridview_dildo.setAdapter(getFclassAdapter(resIds, avtextmoney,
				avtextcomment, avtextproduct));
		// 添加分类的名字，
		fclass_classname_dildo = (TextView) findViewById(R.id.fclass_classname_textdildo);
		fclass_classname_dildo.setText(gridFclass[1]);
		
		/*
		 * 女性用品，情趣跳蛋
		 */		
		gridview_jumpegg = (MyGridView) findViewById(R.id.fclass_gridview_jumpegg);
		gridview_jumpegg.setAdapter(getFclassAdapter(resIds, avtextmoney,
				avtextcomment, avtextproduct));
		fclass_classname_jumpegg = (TextView) findViewById(R.id.fclass_classname_textjumpegg);
		fclass_classname_jumpegg.setText(gridFclass[2]);
		/*
		 * 女性用品，伸缩转珠bead
		 */		
		gridview_bead = (MyGridView) findViewById(R.id.fclass_gridview_bead);
		gridview_bead.setAdapter(getFclassAdapter(resIds, avtextmoney,
				avtextcomment, avtextproduct));
		fclass_classname_bead = (TextView) findViewById(R.id.fclass_classname_textbead);
		fclass_classname_bead.setText(gridFclass[3]);
		/*
		 * 女性用品，护理保健nursing
		 */		
		gridview_nursing = (MyGridView) findViewById(R.id.fclass_gridview_nursing);
		gridview_nursing.setAdapter(getFclassAdapter(resIds, avtextmoney,
				avtextcomment, avtextproduct));
		fclass_classname_nursing = (TextView) findViewById(R.id.fclass_classname_textnursing);
		fclass_classname_nursing.setText(gridFclass[4]);
		/*
		 * 女性用品，其他other
		 */		
		gridview_other = (MyGridView) findViewById(R.id.fclass_gridview_other);
		gridview_other.setAdapter(getFclassAdapter(resIds, avtextmoney,
				avtextcomment, avtextproduct));
		fclass_classname_other = (TextView) findViewById(R.id.fclass_classname_textother);
		fclass_classname_other.setText(gridFclass[4]);

	}

	/**
	 * 自定义适配器，设置4个参数，
	 * 
	 */
	private SimpleAdapter getFclassAdapter(int[] avimgproduct,
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
