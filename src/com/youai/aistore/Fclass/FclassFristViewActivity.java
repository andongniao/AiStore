package com.youai.aistore.Fclass;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Bean.ListFclassTwo;
import com.youai.aistore.Bean.ListGoodsBean;
import com.youai.aistore.Home.HomeActivity;
import com.youai.aistore.Home.MyGridview;
import com.youai.aistore.Home.SearchResultActivity;
import com.youai.aistore.Home.SearchResultAdapter;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.Product.ProductDetailsActivity;

public class FclassFristViewActivity extends BaseActivity implements
		OnItemClickListener {
	private MyGridview toptitlegridview, g; // �������gridview
	private LinearLayout addviewll; // ��Ӷ�̬����
	private int listindex;
	private Resources rs;
	private String[] titlelist; // ��������
	private ArrayList<View> addviewlist;
	private LayoutInflater inflater; // ��Ҫʹ��inflate����̬�������
	private Context context;
	private ArrayList<MyGridview> gridviewlist;
	private MyTask myTask;
	private ListGoodsBean fclasslist;
	private ArrayList<GoodsBean> ListBean;
	private FclassFristViewAdapter fclassAdapter;
	private int type, id, postion;
	public static boolean isfinish;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTopLeftBackground(R.drawable.btn_search_navigation_back);
		setContentXml(R.layout.fclass_frist_view);
		String title = getIntent().getStringExtra("title");// ��ӱ��⣬��ȡ��������ֵ��
		listindex = getIntent().getIntExtra("listindex", 1);
		setTitleTxt(title);
		init();
		if (Util.detect(context)) {
			myTask = new MyTask();
			myTask.execute("");
		} else {
			Util.ShowToast(context, R.string.net_work_is_error);
		}

	}
	@Override
	protected void onResume() {
		super.onResume();
		if(isfinish){
			finish();
		}
	}

	private void init() {
		isfinish = false;
		context = this;
		rs = getResources();
		addviewlist = new ArrayList<View>();
		inflater = LayoutInflater.from(context);
		gridviewlist = new ArrayList<MyGridview>();
		// ��������
		toptitlegridview = (MyGridview) findViewById(R.id.fclass_frist_view_topgridview);
		// ��Ʒ����
		addviewll = (LinearLayout) findViewById(R.id.fclass_frist_view_addview_ll);

		if (listindex == 0) {
			titlelist = rs.getStringArray(R.array.fclass_frist_woman_gridview);
		} else if (listindex == 1) {
			titlelist = rs.getStringArray(R.array.fclass_frist_man_gridview);
		} else if (listindex == 2) {
			titlelist = rs.getStringArray(R.array.fclass_frist_neiyi_gridview);
		} else if (listindex == 3) {
			titlelist = rs.getStringArray(R.array.fclass_frist_tt_gridview);
		} else if (listindex == 5) {
			titlelist = rs.getStringArray(R.array.fclass_frist_tosex_gridview);
		}
		// ��������������
		toptitlegridview
				.setAdapter(new ArrayAdapter<String>(this,
						R.layout.fclass_gridview, R.id.fclass_gridview_text,
						titlelist));
		toptitlegridview.setOnItemClickListener(new Titlegridviewonclick());

		// TODO
		/**
		 * item view in adapter 2 import and setData
		 */
	}

	/*
	 * ����������ϸ���������
	 */
	class Titlegridviewonclick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			// TODO Auto-generated method stub
			/*Ů�Է���*/
			if(listindex==0){
				int[] womenlist = {MyApplication.woman_av,
						MyApplication.woman_fangzhenyangjv,
						MyApplication.woman_qingqvtiaodan,
						MyApplication.woman_shensuozhuanhzu,
						MyApplication.woman_hulibaojian,
						MyApplication.woman_otherwoman };
				Intent intent = new Intent(FclassFristViewActivity.this,
						FclassMoreActivity.class);
				// titlelist���鴫ֵ��FclassFristViewActivity�ı���
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("title", titlelist[arg2].toString());
				intent.putExtra("id", womenlist[arg2]);
				startActivity(intent);
				//Util.ShowToast(context, "�����" + titlelist[arg2]);
			
			}
			 /*���Է���*/
			else if (listindex == 1) {
				int[] menlist = { MyApplication.man_feijibei,
						MyApplication.man_daomo, MyApplication.man_fuzhu, };
				Intent intent = new Intent(FclassFristViewActivity.this,
						FclassMoreActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("title", titlelist[arg2].toString());
				intent.putExtra("id", menlist[arg2]);
				startActivity(intent);
			}
			 /*���·���*/
			else if (listindex == 2) {
				int[] neiyilist = {MyApplication.neiyi_xingganneiyi,
						MyApplication.neiyi_siwaneiku,
						MyApplication.neiyi_qingqvshuiyi,
						MyApplication.neiyi_zhifuyouhuo,
						MyApplication.neiyi_liantiwangyi,
						MyApplication.neiyi_sandiantoushi };
				Intent intent = new Intent(FclassFristViewActivity.this,
						FclassMoreActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("title", titlelist[arg2].toString());
				intent.putExtra("id", neiyilist[arg2]);
				startActivity(intent);
			}
			 /*��ȫ�׷���*/
			else if (listindex == 3) {
				int[] ttlist = {MyApplication.tt_jingdian,
						MyApplication.tt_yanshi, MyApplication.tt_nvyong,
						MyApplication.tt_daxiaohao, MyApplication.tt_huayang, };
				Intent intent = new Intent(FclassFristViewActivity.this,
						FclassMoreActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("title", titlelist[arg2].toString());
				intent.putExtra("id", ttlist[arg2]);
				startActivity(intent);
			}
			 /*˫�˷���*/
			else if (listindex == 5) {
				int[] tosexlist = {MyApplication.tosex_zhuqing,
						MyApplication.tosex_houting,
						MyApplication.tosex_huantao,
						MyApplication.tosex_runhua, MyApplication.tosex_sm,
						MyApplication.tosex_other };
				Intent intent = new Intent(FclassFristViewActivity.this,
						FclassMoreActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("title", titlelist[arg2].toString());
				intent.putExtra("id", tosexlist[arg2]);
				startActivity(intent);
			}

		}//����¼�����

	}//��������

	/*
	 * ��ϸ����gridview������
	 */
	class gridviewonclick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			// type = fclasslist.getList().get(0).get(arg2).getType();
			//id = fclasslist.getList().get(7).get(arg2).getId();
			id = fclasslist.getList().get(0).get(arg2).getId();
			// toActivity(type, id);
			Intent intent = new Intent(FclassFristViewActivity.this,
					ProductDetailsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("finishid", 1);
			intent.putExtra("id", id);
			startActivity(intent);

		}

	}

	private class MyTask extends AsyncTask<Object, Object, Object> {
		// onPreExecute����������ִ�к�̨����ǰ��һЩUI����
		@Override
		protected void onPreExecute() {
			Util.startProgressDialog(context);

		}

		// doInBackground�����ڲ�ִ�к�̨����,�����ڴ˷������޸�UI
		@Override
		protected ListGoodsBean doInBackground(Object... params) {
			try {
				Send send = new Send(context);
				//String time = String.valueOf(System.currentTimeMillis());
				//fclasslist = send.RequestHome(time);
				/*ͨ��λ���жϣ���������š���Ҫ�����ĸ����ࡣ*/
				listindex = getIntent().getIntExtra("listindex", 1);
				switch (listindex) {
				case 0:
					fclasslist = send.GetFclassFrist(MyApplication.woman);
					break;
				case 1:
					fclasslist = send.GetFclassFrist(MyApplication.woman);
					break;
				case 2:
					fclasslist = send.GetFclassFrist(MyApplication.neiyi);
					break;
				case 3:
					fclasslist = send.GetFclassFrist(MyApplication.tt);
					break;
				case 5:
					fclasslist = send.GetFclassFrist(MyApplication.tosex);
					break;
				default:
					break;
				}
				
				return fclasslist;// new String(baos.toByteArray(), "gb2312");
				// TODO getdata
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// onProgressUpdate�������ڸ��½�����Ϣ
		@Override
		protected void onProgressUpdate(Object... progresses) {
		}

		// onPostExecute����������ִ�����̨��������UI,��ʾ���
		@Override
		protected void onPostExecute(Object result) {
			Util.stopProgressDialog();
			fclasslist = (ListGoodsBean) result;
			if (fclasslist != null && fclasslist.getCode() == 200) {
				ListBean = fclasslist.getList().get(
						fclasslist.getList().size() - 1);
				
				fclassAdapter = new FclassFristViewAdapter(context,
						ListBean);
				for (int i = 0; i < titlelist.length; i++) {
					View v = inflater.inflate(
							R.layout.fclass_frist_view_added_view, null);
					TextView tv = (TextView) v
							.findViewById(R.id.fclass_frist_view_addview_title_tv);
					tv.setText(titlelist[i]);
					g = (MyGridview) v
							.findViewById(R.id.fclass_frist_view_addview_gridview);
					g.setAdapter(fclassAdapter);
					g.setOnItemClickListener(new gridviewonclick());
					gridviewlist.add(g);
					addviewlist.add(v);
					addviewll.addView(v);

				}

			} else {
				if (fclasslist != null)
					Util.ShowToast(context, fclasslist.getMsg());

			}

			// setadapter

		}

		// onCancelled����������ȡ��ִ���е�����ʱ����UI
		protected void onCancelled() {
			Util.stopProgressDialog();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
