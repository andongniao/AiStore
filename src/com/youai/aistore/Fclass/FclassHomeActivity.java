package com.youai.aistore.Fclass;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;

/**
 * 分类首页
 * 
 * @author Qzr
 * 
 */
public class FclassHomeActivity extends BaseActivity {
	private long exitTime = 0;
	private ExpandableListView listView;
	private ArrayList<String> groupnamelist;
	private ArrayList<Integer> groupimagelist;
	private ArrayList<ArrayList<String>> childnamelist;
	private Resources rs;
	private FclassHomeAdapter adapter;
	@SuppressWarnings("unused")
	private Context context;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		setTitleTxt(R.string.fclass_home_title);
		topRightGone();
		setContentXml(R.layout.my_fclass);
		init();

	}

	private void init() {
		rs = this.getResources();
		String[] groupname = rs
				.getStringArray(R.array.fclass_home_expandablelistviewname);
		groupnamelist = new ArrayList<String>();
		groupimagelist = new ArrayList<Integer>();
		childnamelist = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < groupname.length; i++) {
			groupnamelist.add(groupname[i]);
		}
		groupimagelist.add(R.drawable.fclass_woman);
		groupimagelist.add(R.drawable.fclass_man);
		groupimagelist.add(R.drawable.fclass_neiyi);
		groupimagelist.add(R.drawable.fclass_tt);
		groupimagelist.add(R.drawable.fclass_tosex);
		// 女性用品，子名字列表
		ArrayList<String> woman = new ArrayList<String>();
		String[] womanc = rs.getStringArray(R.array.fclass_home_woman_gridview);
		for (int i = 0; i < womanc.length; i++) {
			woman.add(womanc[i]);
		}
		childnamelist.add(woman);
		// 男性用品，子名字列表
		ArrayList<String> man = new ArrayList<String>();
		String[] manc = rs.getStringArray(R.array.fclass_home_man_gridview);
		for (int i = 0; i < manc.length; i++) {
			man.add(manc[i]);
		}
		childnamelist.add(man);
		// 内衣，子名字列表
		ArrayList<String> neiyi = new ArrayList<String>();
		String[] neiyic = rs.getStringArray(R.array.fclass_home_neiyi_gridview);
		for (int i = 0; i < neiyic.length; i++) {
			neiyi.add(neiyic[i]);
		}
		childnamelist.add(neiyi);
		// 安全套，子名字列表
		ArrayList<String> tt = new ArrayList<String>();
		String[] ttc = rs.getStringArray(R.array.fclass_home_tt_gridview);
		for (int i = 0; i < ttc.length; i++) {
			tt.add(ttc[i]);
		}
		childnamelist.add(tt);

		// ArrayList<String> runhua = new ArrayList<String>();
		// String[] runhuac =
		// rs.getStringArray(R.array.fclass_home_runhua_gridview);
		// for(int i =0;i<runhuac.length;i++){
		// runhua.add(runhuac[i]);
		// }
		// childnamelist.add(runhua);
		// 双人，子名字列表
		ArrayList<String> tosex = new ArrayList<String>();
		String[] tosexc = rs.getStringArray(R.array.fclass_home_tosex_gridview);
		for (int i = 0; i < tosexc.length; i++) {
			tosex.add(tosexc[i]);
		}
		childnamelist.add(tosex);

		// 折叠式列表
		listView = (ExpandableListView) findViewById(R.id.fclass_expandableListView);
		adapter = new FclassHomeAdapter(this, groupnamelist, groupimagelist,
				childnamelist);
		listView.setAdapter(adapter);
		

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
