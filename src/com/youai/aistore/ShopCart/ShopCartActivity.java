package com.youai.aistore.ShopCart;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.ExampleActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.WelcomeActivity;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Bean.ListFclassTwo;
import com.youai.aistore.Bean.ListShopCartBean;
import com.youai.aistore.Bean.ShopCartBean;
import com.youai.aistore.Home.SearchResultAdapter;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.NetInterface.ServiceUrl;
import com.youai.aistore.Order.OrderActivity;
import com.youai.aistore.xlistview.XListView;
import com.youai.aistore.xlistview.XListView.IXListViewListener;
/**
 * 购物车首页
 * @author Qzr
 *
 */
public class ShopCartActivity extends BaseActivity implements OnClickListener{
	//,IXListViewListener{
	private long exitTime = 0;
	private Context context;
	private ListView lv;
	private ImageView isnull_iv;
	private View isnull;
	private ShopCartAdapter adapter;
	private LinearLayout showviewll;
	private Button seeagainbt,goypaybt;
	private TextView tv_topright,tv_gongji;
	String s;
	private ShopcartInterface inter;
	private boolean isshowing;
	private MyTask myTask;
	private ListShopCartBean listbean;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		context = this;
		goneTopLeft();
		topRightGone();
		setTitleTxt(R.string.shopcart_title);
		setContentXml(R.layout.shopcart);
		topRightVisible();
		tv_topright = (TextView) getTopRightView();
		tv_topright.setText("编辑");
		isshowing = false;
		tv_topright.setTextColor(getResources().getColor(R.color.white));
		tv_topright.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!isshowing){
					if(adapter!=null){
						adapter.setdata(listbean.getList(), 2);
						adapter.notifyDataSetChanged();
						tv_topright.setText("完成");
						isshowing = true;
					}
				}else{
					if(adapter!=null){
						adapter.setdata(listbean.getList(), 1);
						adapter.notifyDataSetChanged();
						tv_topright.setText("编辑");
						isshowing = false;
					}
				}
			}
		});
		//getLayoutInflater().inflate(R.layout.shorcart_isnull_show, null);
		init();

		s = ServiceUrl.GetShopCartList_Url_head+MyApplication.SessionId+
				ServiceUrl.GetShopCartList_Url_foot+MyApplication.UserId;
		//

		if(Util.detect(context)){
			myTask = new MyTask();
			myTask.execute("");  
		}else{
			Util.ShowToast(context, R.string.net_work_is_error);
		}

	}

	private void init() {
		inter = new ShopcartInterface() {

			@Override
			public void delete(ArrayList<ShopCartBean> list, int index) {
				list.remove(index);
				ListShopCartBean l = new ListShopCartBean();
				double price = 0.00;
				for(int i=0;i<list.size();i++){
					price +=(Double.parseDouble(list.get(i).getGoods_price())*Integer.parseInt(list.get(i).getGoods_number()));
				}
				l.setList(list);
				l.setCount_price("￥"+String.valueOf(price)+"元");
				tv_gongji.setText(String.valueOf(price));
				listbean = l;
				adapter.setdata(list, 2);
				adapter.notifyDataSetChanged();
				if(listbean!=null && listbean.getList().size()>0){
					showviewll.setVisibility(View.VISIBLE);
				}else{
					showviewll.setVisibility(View.GONE);
				}
			}


		};
		isnull = findViewById(R.id.shopcart_isnull_ll);
		showviewll = (LinearLayout) findViewById(R.id.shopcart_showview_ll);
		seeagainbt = (Button) findViewById(R.id.shopcart_see_again_bt);
		seeagainbt.setOnClickListener(this);
		goypaybt = (Button) findViewById(R.id.shopcart_gopay_bt);
		goypaybt.setOnClickListener(this);
		tv_gongji = (TextView) findViewById(R.id.shopcart_gongji_tv);
		lv = (ListView) findViewById(R.id.shopcart_listview);
		isnull_iv = (ImageView)findViewById(R.id.shopcart_isnull_iv);
		isnull_iv.setOnClickListener(this);
		lv.setEmptyView(isnull);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shopcart_isnull_iv:
			Util.ShowToast(context, "没有数据");
			break;
		case R.id.shopcart_see_again_bt:
			Util.ShowToast(context, "再看看");
			ExampleActivity.setCurrentTab(0);
			break;
		case R.id.shopcart_gopay_bt:
			Util.ShowToast(context, "去支付");
			System.out.println("购物车列表所用sessionid======"+s);
			Intent intent = new Intent(ShopCartActivity.this,ConsigneeInfoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;

		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
			if((System.currentTimeMillis()-exitTime) > 2000){  
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
				exitTime = System.currentTimeMillis();   
			} else {
				finish();
				System.exit(0);
			}
			return true;   
		}
		return super.onKeyDown(keyCode, event);
	}

	private class MyTask extends AsyncTask<Object, Object, Object> {  
		//onPreExecute方法用于在执行后台任务前做一些UI操作  
		@Override  
		protected void onPreExecute() {  
			//	            textView.setText("loading...");  
			Util.startProgressDialog(context);
		}  

		//doInBackground方法内部执行后台任务,不可在此方法内修改UI  
		@Override  
		protected Object doInBackground(Object... params) {  
			try {  
				Send send = new Send(context);
				listbean  = send.getShopCartlist(MyApplication.SessionId, MyApplication.UserId);
				return listbean;//new String(baos.toByteArray(), "gb2312");  
			} catch (Exception e) {  
				e.printStackTrace();
			}  
			return null;  
		}  

		//onProgressUpdate方法用于更新进度信息  
		@Override  
		protected void onProgressUpdate(Object... progresses) {  
		}  

		//onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
		@Override  
		protected void onPostExecute(Object result) {  
			Util.stopProgressDialog();
			listbean = (ListShopCartBean) result;
			if(listbean!=null){
				if(listbean.getCode()==200){
					UpUI();
					tv_gongji.setText("￥"+listbean.getCount_price()+"元");
				}else{
					showviewll.setVisibility(View.GONE);
					Util.ShowToast(context, listbean.getMsg());
				}
			}else{
				showviewll.setVisibility(View.GONE);
				Util.ShowToast(context, R.string.net_work_is_error);
			}

		}  

		//onCancelled方法用于在取消执行中的任务时更改UI  
		@Override  
		protected void onCancelled() {  
			Util.stopProgressDialog();
		}  
	}

	public interface ShopcartInterface{
		void delete(ArrayList<ShopCartBean> list,int index);
	}


	private void UpUI(){
		if(adapter == null){
			adapter = new ShopCartAdapter(context, listbean.getList(),1,inter);
			lv.setAdapter(adapter);
		}else{
			adapter.setdata(listbean.getList(), 1);
			adapter.notifyDataSetChanged();
		}
		if(listbean!=null && listbean.getList().size()>0){
			showviewll.setVisibility(View.VISIBLE);
		}else{
			showviewll.setVisibility(View.GONE);
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(listbean==null || listbean.getList().size()==0){
			if(event.getAction()==MotionEvent.ACTION_DOWN && event.getAction()==MotionEvent.ACTION_UP){
				if(Util.detect(context)){
					myTask = new MyTask();
					myTask.execute("");  
				}else{
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}
		}
		return super.onTouchEvent(event);
	}
}
