package com.youai.aistore.ShopCart;

import java.text.SimpleDateFormat;
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
import android.widget.TextView;
import android.widget.Toast;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.ExampleActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.Base;
import com.youai.aistore.Bean.ListShopCartBean;
import com.youai.aistore.Bean.ShopCartBean;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.NetInterface.ServiceUrl;
import com.youai.aistore.View.SlideView;
import com.youai.aistore.xlistview.XListView;
import com.youai.aistore.xlistview.XListView.IXListViewListener;
/**
 * 购物车首页
 * @author Qzr
 *
 */
public class ShopCartActivity extends BaseActivity implements IXListViewListener,OnClickListener{
	//,IXListViewListener{
	private long exitTime = 0;
	private Context context;
	private XListView lv;
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
	private Base beanresult;
	private double price;
	private int statu;

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
						statu = 2;
						adapter.setdata(listbean.getList(), statu);
						adapter.notifyDataSetChanged();
						tv_topright.setText("完成");
						isshowing = true;
					}
				}else{
					if(adapter!=null){
						statu = 1;
						adapter.setdata(listbean.getList(), statu);
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
			myTask = new MyTask(1,0);
			myTask.execute("");  
		}else{
			Util.ShowToast(context, R.string.net_work_is_error);
		}

	}

	private void init() {
		inter = new ShopcartInterface() {

			@Override
			public void delete(ArrayList<ShopCartBean> list, int index) {
//				list.remove(index);
//				ListShopCartBean l = new ListShopCartBean();
//				price = 0.00;
//				for(int i=0;i<list.size();i++){
//					price +=(Double.parseDouble(list.get(i).getGoods_price())*Integer.parseInt(list.get(i).getGoods_number()));
//				}
//				l.setList(list);
//				l.setCount_price("￥"+String.valueOf(price)+"元");
//				tv_gongji.setText(String.valueOf(price));
//				listbean = l;
//				statu = 2;
//				adapter.setdata(list, statu);
//				adapter.notifyDataSetChanged();
//				if(listbean!=null && listbean.getList().size()>0){
//					showviewll.setVisibility(View.VISIBLE);
//				}else{
//					showviewll.setVisibility(View.GONE);
//				}
				if(Util.detect(context)){
					myTask = new MyTask(2,0);
					myTask.setdata(list, index);
					myTask.execute("");  
				}else{
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}

			@Override
			public void add(ArrayList<ShopCartBean> list, int index) {
				if(Util.detect(context)){
					myTask = new MyTask(0,1);
					myTask.setdata(list, index);
					myTask.execute("");  
				}else{
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}

			@Override
			public void jian(ArrayList<ShopCartBean> list, int index) {
				// TODO Auto-generated method stub
				if(Util.detect(context)){
					myTask = new MyTask(0,0);
					myTask.setdata(list, index);
					myTask.execute("");  
				}else{
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}

			@Override
			public void setlvdata(SlideView data) {
				lv.setdata(data);
			}


		};
		isnull = findViewById(R.id.shopcart_isnull_ll);
		showviewll = (LinearLayout) findViewById(R.id.shopcart_showview_ll);
		showviewll.setVisibility(View.GONE);
		seeagainbt = (Button) findViewById(R.id.shopcart_see_again_bt);
		seeagainbt.setOnClickListener(this);
		goypaybt = (Button) findViewById(R.id.shopcart_gopay_bt);
		goypaybt.setOnClickListener(this);
		tv_gongji = (TextView) findViewById(R.id.shopcart_gongji_tv);
		lv = (XListView) findViewById(R.id.shopcart_listview);
		lv.isSliding = true;
		lv.GoneFooterView();
		lv.setFocusable(false);
		lv.setPullLoadEnable(true);
		lv.setXListViewListener(this);
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
		private int index,type,number;
		private ArrayList<ShopCartBean> list;
		private int postion;
		public MyTask(int index,int type){
			this.index = index;
			this.type = type;
		}
		public void setdata(ArrayList<ShopCartBean> list, int index){
			this.list = list;
			this.postion = index;
		}
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
				if(index==1){
					Send send = new Send(context);
					listbean  = send.getShopCartlist(MyApplication.SessionId, MyApplication.UserId);
					return listbean;//new String(baos.toByteArray(), "gb2312");  
				}else if(index == 2){
					Send send = new Send(context);
					String rec_id =list.get(postion).getRec_id();
					String session_id = MyApplication.SessionId;
					String user_id = MyApplication.UserId;
					beanresult = send.DeletefromShopCart(rec_id, session_id,user_id);
					return beanresult;
				}else{
					Send send = new Send(context);
					int good_id =Integer.parseInt(list.get(postion).getGoods_id());
					if(type==1){
						number = 1;
					}else{
						number = -1;
					}
					String session_id = MyApplication.SessionId;
					String user_id = MyApplication.UserId;
					beanresult = send.AddShopCart(good_id, number, session_id,user_id);
					return beanresult;
				}
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
			if(index == 1){
				onLoad();
				listbean = (ListShopCartBean) result;
				if(listbean!=null){
					if(listbean.getCode()==200){
						UpUI();
						price = Double.parseDouble(listbean.getCount_price());
						tv_gongji.setText("￥"+listbean.getCount_price()+"元");
					}else{
						showviewll.setVisibility(View.GONE);
						Util.ShowToast(context, listbean.getMsg());
					}
				}else{
					showviewll.setVisibility(View.GONE);
					Util.ShowToast(context, R.string.net_work_is_error);
				}

			}else if(index == 2){
				beanresult = (Base) result;
				if(beanresult!=null){
					if(beanresult.getCode()==200){
						list.get(postion).setGoods_number(String.valueOf(Integer.parseInt(list.get(postion).getGoods_number())+number));
						price -=Double.parseDouble(list.get(postion).getGoods_price())*
								Integer.parseInt(list.get(postion).getGoods_number());
						tv_gongji.setText(String.valueOf(price));
						list.remove(list.get(postion));
						adapter.setdata(list, 2);
						adapter.notifyDataSetChanged();
					}else{
						Util.ShowToast(context,beanresult.getMsg());
					}
				}else{
					Util.ShowToast(context,R.string.net_work_is_error);
				}
			}else{
				beanresult = (Base) result;
				if(beanresult!=null){
					if(beanresult.getCode()==200){
						//						if(Util.detect(context)){
						//							myTask = new MyTask(1);
						//							myTask.execute("");  
						//						}else{
						//							Util.ShowToast(context, R.string.net_work_is_error);
						//						}
						list.get(postion).setGoods_number(String.valueOf(Integer.parseInt(list.get(postion).getGoods_number())+number));
						adapter.setdata(list, 1);
						adapter.notifyDataSetChanged();
						price +=Double.parseDouble(list.get(postion).getGoods_price());
						tv_gongji.setText(String.valueOf(price));
					}else{
						Util.ShowToast(context,beanresult.getMsg());
					}
				}else{
					Util.ShowToast(context,R.string.net_work_is_error);
				}
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
		void add(ArrayList<ShopCartBean> list,int index);
		void jian(ArrayList<ShopCartBean> list,int index);
		void setlvdata(SlideView data);
	}


	private void UpUI(){
		if(adapter == null){
			adapter = new ShopCartAdapter(context, listbean.getList(),1,inter);
			lv.setAdapter(adapter);
		}else{
			statu = 1;
			adapter.setdata(listbean.getList(), statu);
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
		if(listbean==null){
			//if(event.getAction()==MotionEvent.ACTION_DOWN && event.getAction()==MotionEvent.ACTION_UP){
			if(Util.detect(context)){
				myTask = new MyTask(1,0);
				myTask.execute("");  
			}else{
				Util.ShowToast(context, R.string.net_work_is_error);
			}
			//}
		}else if(listbean!=null){
			if(listbean.getList()==null || (listbean.getList()!=null && listbean.getList().size()==0)){
				//if(event.getAction()==MotionEvent.ACTION_DOWN && event.getAction()==MotionEvent.ACTION_UP){
				if(Util.detect(context)){
					myTask = new MyTask(1,0);
					myTask.execute("");  
				}else{
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}
		}
		//}
		return super.onTouchEvent(event);
	}

	@Override
	public void onRefresh() {
		if(Util.detect(context)){
			myTask = new MyTask(1,0);
			myTask.execute("");  
		}else{
			Util.ShowToast(context, R.string.net_work_is_error);
		}
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}
	private void onLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");       
		String    date    =    sDateFormat.format(new    java.util.Date());    
		lv.setRefreshTime(date);
	}
	
}
