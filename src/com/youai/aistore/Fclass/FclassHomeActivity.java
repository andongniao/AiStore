package com.youai.aistore.Fclass;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;

/**
 * ������ҳ
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
		// Ů����Ʒ���������б�
		ArrayList<String> woman = new ArrayList<String>();
		String[] womanc = rs.getStringArray(R.array.fclass_home_woman_gridview);
		for (int i = 0; i < womanc.length; i++) {
			woman.add(womanc[i]);
		}
		childnamelist.add(woman);
		// ������Ʒ���������б�
		ArrayList<String> man = new ArrayList<String>();
		String[] manc = rs.getStringArray(R.array.fclass_home_man_gridview);
		for (int i = 0; i < manc.length; i++) {
			man.add(manc[i]);
		}
		childnamelist.add(man);
		// ���£��������б�
		ArrayList<String> neiyi = new ArrayList<String>();
		String[] neiyic = rs.getStringArray(R.array.fclass_home_neiyi_gridview);
		for (int i = 0; i < neiyic.length; i++) {
			neiyi.add(neiyic[i]);
		}
		childnamelist.add(neiyi);
		// ��ȫ�ף��������б�
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
		// ˫�ˣ��������б�
		ArrayList<String> tosex = new ArrayList<String>();
		String[] tosexc = rs.getStringArray(R.array.fclass_home_tosex_gridview);
		for (int i = 0; i < tosexc.length; i++) {
			tosex.add(tosexc[i]);
		}
		childnamelist.add(tosex);

		// �۵�ʽ�б�
		listView = (ExpandableListView) findViewById(R.id.fclass_expandableListView);
		adapter = new FclassHomeAdapter(this, groupnamelist, groupimagelist,
				childnamelist);
		listView.setAdapter(adapter);
		//listView.setOnChildClickListener(new onChildClickListener());

	}
/*	class onChildClickListener implements OnChildClickListener {
		//private int index;//

	
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			String pos=groupPosition+"-"+childPosition;
			
			switch (childPosition) {
			case 0:
				
				Intent intent = new Intent(context,FclassFristViewActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//groupname���鴫ֵ��FclassFristViewActivity�ı���
				intent.putExtra("title", groupnamelist.get(0));
				context.startActivity(intent);
				//Util.ShowToast(context, "�����"+childname.get(index).get(0));

				break;
			case 1:
				Intent intent1 = new Intent(context,FclassMoreActivity.class);
				intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//groupname���鴫ֵ��FclassFristViewActivity�ı���
				intent1.putExtra("title", childnamelist.get(childPosition).toString());
				intent1.putExtra("id",childPosition-1);
				context.startActivity(intent1);
				//Util.ShowToast(context, "�����"+childname.get(index).get(childPosition));
				break;
			
			default:
				break;
			
		}
			return false;

	}
	
		
	}*/

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
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
