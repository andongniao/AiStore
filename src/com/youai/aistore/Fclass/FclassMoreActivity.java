package com.youai.aistore.Fclass;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Bean.ListFclassTwo;
import com.youai.aistore.Home.SearchResultAdapter;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.Product.ProductDetailsActivity;
import com.youai.aistore.xlistview.XListView;
import com.youai.aistore.xlistview.XListView.IXListViewListener;


public class FclassMoreActivity extends BaseActivity implements IXListViewListener,OnClickListener {
	private LinearLayout popll,numll,pricell;
	private XListView listView;
	private FclassMoreAdapter adapter;
	private Context context;
	private MyTask myTask;
	private ArrayList<GoodsBean>list;
	private ListFclassTwo listf;
	private ImageView popll_iv;
	private int cou=1;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentXml(R.layout.fclass_more);
		String title = getIntent().getStringExtra("title");
		setTitleTxt(title);		
		init();
		if(Util.detect(context)){
			myTask = new MyTask();
			myTask.execute("");
		}else{
			Util.ShowToast(context, R.string.net_work_is_error);
		}
	}

	private void init() {
		context = FclassMoreActivity.this;
		popll = (LinearLayout) findViewById(R.id.fclass_more_popularity_ll);
		numll = (LinearLayout) findViewById(R.id.fclass_more_number_ll);
		pricell = (LinearLayout) findViewById(R.id.fclass_more_price_ll);

		popll_iv = (ImageView) findViewById(R.id.fclass_more_popularity_img);
		popll_iv.setOnClickListener(this);
		//popll.setClickable(true);
		popll.setOnClickListener(this);
		numll.setOnClickListener(this);
		pricell.setOnClickListener(this);
		listView = (XListView) findViewById(R.id.fclass_more_lv);
		listView.setOnItemClickListener(new mylistener());
		
	}
	
	class mylistener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(FclassMoreActivity.this,ProductDetailsActivity.class);
			intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("id",listf.getList().get(arg2-1).getId() );
			startActivity(intent);
		}
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {		
		case R.id.fclass_more_popularity_ll:
			if(cou%2!=0)
			{
				popll_iv.setImageResource(R.drawable.order_bottom);
				
			cou++;
			}
			else
			{
				popll_iv.setImageResource(R.drawable.order_top);
			cou++;
			}
			
			break;
		case R.id.fclass_more_popularity_img:
			
			//Util.ShowToast(context, "����");
			break;
		case R.id.fclass_more_number_ll:
			
			break;
		case R.id.fclass_more_price_ll:
			
			break;

		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {

	}
	
	private void onLoad(){
		listView.stopRefresh();
		listView.stopLoadMore();
	}

	private class MyTask extends AsyncTask<Object, Object, Object> {

		//onPreExecute����������ִ�к�̨����ǰ��һЩUI����  
		@Override  
		protected void onPreExecute() {  
			//	            textView.setText("loading...");  
			Util.startProgressDialog(context);
		}  

		//doInBackground�����ڲ�ִ�к�̨����,�����ڴ˷������޸�UI  
		@Override  
		protected Object doInBackground(Object... params) {  
			try {  
				
				/*����жϾ�*/
				Send send = new Send(context);
				listf  = send.GetFclassTwo(MyApplication.woman_av, MyApplication.clickdesc, 1);
				return listf;//new String(baos.toByteArray(), "gb2312");  
			} catch (Exception e) {  
				e.printStackTrace();
			}  
			return null;  
		}  

		//onProgressUpdate�������ڸ��½�����Ϣ  
		@Override  
		protected void onProgressUpdate(Object... progresses) {  
		}  

		//onPostExecute����������ִ�����̨��������UI,��ʾ���  
		@Override  
		protected void onPostExecute(Object result) {  
			Util.stopProgressDialog();
			listf = (ListFclassTwo) result;
			if(listf!=null){
				if(listf.getCode()==200){
					Util.ShowToast(context, listf.getMsg());
					
					adapter = new FclassMoreAdapter(context, listf.getList());
					listView.setAdapter(adapter);
					
					
				}else{
					Util.ShowToast(context, listf.getMsg());
				}
			}
		
		}  

		//onCancelled����������ȡ��ִ���е�����ʱ����UI  
		@Override  
		protected void onCancelled() {  
			Util.stopProgressDialog();
		}  
		
	}
}

