package com.youai.aistore.Order;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.Base;
import com.youai.aistore.Bean.ListShopCartBean;
import com.youai.aistore.Bean.ShopCartBean;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.xlistview.XListView;
import com.youai.aistore.xlistview.XListView.IXListViewListener;

/**
 * ȫ������
 * @author Qzr
 *
 */
public class AllOrderActivity extends BaseActivity implements IXListViewListener{
	private Context context;
	private XListView lv;
	private MyTask myTask;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTitleTxt(R.string.all_order_title);
		setContentXml(R.layout.shopcart);
		init();
		if(Util.detect(context)){
			myTask = new MyTask();
			myTask.execute("");  
		}else{
			Util.ShowToast(context, R.string.net_work_is_error);
		}
	}

	private void init() {
		context = this;
		lv = (XListView) findViewById(R.id.order_all_listview);
		lv.GoneFooterView();
		lv.setFocusable(false);
		lv.setPullLoadEnable(true);
		lv.setXListViewListener(this);
		
	}

	@Override
	public void onRefresh() {
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
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
		} 

		//onCancelled����������ȡ��ִ���е�����ʱ����UI  
		@Override  
		protected void onCancelled() {  
			Util.stopProgressDialog();
		}  
	}
}
