package com.youai.aistore.Home;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.BaseActivity;
import com.youai.aistore.ExampleActivity;
import com.youai.aistore.ImageCycleView;
import com.youai.aistore.MyApplication;
import com.youai.aistore.WelcomeActivity;
import com.youai.aistore.ImageCycleView.ImageCycleViewListener;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Bean.ListGoodsBean;
import com.youai.aistore.Fclass.FclassFristViewActivity;
import com.youai.aistore.Fclass.FclassHomeActivity;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.Product.ProductDetailsActivity;

public class HomeActivity extends BaseActivity implements OnClickListener{
	private long exitTime = 0;
	private Context context;
	private ImageCycleView actitvit;
	private ImageView woman,man,neiyi,more,runhua,tt,tosex,home_new_l,home_new_c,home_new_r,
	home_woman_hot_iv_l,home_woman_hot_iv_r_t,home_woman_hot_iv_r_b,
	home_man_hot_iv_l,home_man_hot_iv_r_t,home_man_hot_iv_r_b,
	home_neiyi_hot_iv_l,home_neiyi_hot_iv_r_t,home_neiyi_hot_iv_r_b,
	home_tt_hot_iv_l,home_tt_hot_iv_r_t,home_tt_hot_iv_r_b,
	home_tosex_hot_iv_l,home_tosex_hot_iv_r_t,home_tosex_hot_iv_r_b
	;
	private TextView tv_newpp_l_tttle,tv_newpp_l_price,tv_newpp_c_tttle,tv_newpp_c_price,tv_newpp_r_tttle,tv_newpp_r_price;
	private LinearLayout morell;
	private boolean morellisshow;
	private ListGoodsBean homeBeanList;
	private MyTask myTask;
	private ArrayList<ImageView> womanimglist,manlist,neiyilist,ttlist,tosexlist;
	private ArrayList<GoodsBean> activitListBean,newppListBean,womanListBean,manlistBean,neiyilistBean,ttlistBean,tosexlistBean,likeListBean;
	private MyGridview gridView;
	private HomeGridviewAdapter adapter;
	private ScrollView myscrollview;
	private ArrayList<String> urllist;
	private int type,id,postion;
	private String title;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		context = HomeActivity.this;
		Util.ShowToast(context, MyApplication.SessionId);
		womanimglist = new ArrayList<ImageView>();
		manlist = new ArrayList<ImageView>();
		neiyilist = new ArrayList<ImageView>();
		ttlist = new ArrayList<ImageView>();
		tosexlist = new ArrayList<ImageView>();
		goneTopLeft();
		setTopLeftBackground(R.drawable.btn_search_navigation_back);
		setTopTitleBackground(R.drawable.logo);
		setTopRightBackground(R.drawable.search);
//		topRightVisible();
//		TextView topright = (TextView) getTopRightView();
//		topright.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				Util.ShowToast(HomeActivity.this, "search");
//				Intent intent = new Intent(HomeActivity.this,SearchActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(intent);
//				overridePendingTransition(R.anim.in_rightleft, R.anim.out_rightleft);
//			}
//		});
		setContentXml(R.layout.home);
		init();
		if(Util.detect(context)){
			myTask = new MyTask();
			myTask.execute("");  
		}else{
			Util.ShowToast(context, R.string.net_work_is_error);
		}
	}
	Runnable r = new Runnable() {

		@Override
		public void run() {

		}
	};

	private void init() {
		morellisshow = false;
		myscrollview = (ScrollView) findViewById(R.id.home_scrollview);
		myscrollview.smoothScrollTo(0,20);
		actitvit = (ImageCycleView) findViewById(R.id.home_activit);
		//		mImageUrl = new ArrayList<String>();
		//		mImageUrl.add(imageUrl1);
		//		mImageUrl.add(imageUrl2);
		//		mImageUrl.add(imageUrl3);
		//		mImageUrl.add(imageUrl4);
//				actitvit.setImageResources(mImageUrl, mAdCycleViewListener);
		morell = (LinearLayout) findViewById(R.id.home_more_ll);
		home_new_l = (ImageView) findViewById(R.id.home_new_iv_l);
		home_new_l.setOnClickListener(this);
		home_new_c = (ImageView) findViewById(R.id.home_new_iv_c);
		home_new_c.setOnClickListener(this);
		home_new_r = (ImageView) findViewById(R.id.home_new_iv_r);
		home_new_r.setOnClickListener(this);
		//女用
		home_woman_hot_iv_l = (ImageView) findViewById(R.id.home_woman_hot_iv_l);
		womanimglist.add(home_woman_hot_iv_l);
		home_woman_hot_iv_l.setOnClickListener(this);
		home_woman_hot_iv_r_t = (ImageView) findViewById(R.id.home_woman_hot_iv_r_t);
		womanimglist.add(home_woman_hot_iv_r_t);
		home_woman_hot_iv_r_t.setOnClickListener(this);
		home_woman_hot_iv_r_b = (ImageView) findViewById(R.id.home_woman_hot_iv_r_b);
		womanimglist.add(home_woman_hot_iv_r_b);
		home_woman_hot_iv_r_b.setOnClickListener(this);
		//男用
		home_man_hot_iv_l = (ImageView) findViewById(R.id.home_man_hot_iv_l);
		manlist.add(home_man_hot_iv_l);
		home_man_hot_iv_l.setOnClickListener(this);
		home_man_hot_iv_r_t = (ImageView) findViewById(R.id.home_man_hot_iv_r_t);
		manlist.add(home_man_hot_iv_r_t);
		home_man_hot_iv_r_t.setOnClickListener(this);
		home_man_hot_iv_r_b = (ImageView) findViewById(R.id.home_man_hot_iv_r_b);
		manlist.add(home_man_hot_iv_r_b);
		home_man_hot_iv_r_b.setOnClickListener(this);
		//内衣
		home_neiyi_hot_iv_l = (ImageView) findViewById(R.id.home_neiyi_hot_iv_l);
		neiyilist.add(home_neiyi_hot_iv_l);
		home_neiyi_hot_iv_l.setOnClickListener(this);
		home_neiyi_hot_iv_r_t = (ImageView) findViewById(R.id.home_neiyi_hot_iv_r_t);
		neiyilist.add(home_neiyi_hot_iv_r_t);
		home_neiyi_hot_iv_r_t.setOnClickListener(this);
		home_neiyi_hot_iv_r_b = (ImageView) findViewById(R.id.home_neiyi_hot_iv_r_b);
		neiyilist.add(home_neiyi_hot_iv_r_b);
		home_neiyi_hot_iv_r_b.setOnClickListener(this);
		//tt
		home_tt_hot_iv_l = (ImageView) findViewById(R.id.home_tt_hot_iv_l);
		ttlist.add(home_tt_hot_iv_l);
		home_tt_hot_iv_l.setOnClickListener(this);
		home_tt_hot_iv_r_t = (ImageView) findViewById(R.id.home_tt_hot_iv_r_t);
		ttlist.add(home_tt_hot_iv_r_t);
		home_tt_hot_iv_r_t.setOnClickListener(this);
		home_tt_hot_iv_r_b = (ImageView) findViewById(R.id.home_tt_hot_iv_r_b);
		ttlist.add(home_tt_hot_iv_r_b);
		home_tt_hot_iv_r_b.setOnClickListener(this);
		//tosex
		home_tosex_hot_iv_l = (ImageView) findViewById(R.id.home_tosex_hot_iv_l);
		tosexlist.add(home_tosex_hot_iv_l);
		home_tosex_hot_iv_l.setOnClickListener(this);
		home_tosex_hot_iv_r_t = (ImageView) findViewById(R.id.home_tosex_hot_iv_r_t);
		tosexlist.add(home_tosex_hot_iv_r_t);
		home_tosex_hot_iv_r_t.setOnClickListener(this);
		home_tosex_hot_iv_r_b = (ImageView) findViewById(R.id.home_tosex_hot_iv_r_b);
		tosexlist.add(home_tosex_hot_iv_r_b);
		home_tosex_hot_iv_r_b.setOnClickListener(this);
		
		
		tv_newpp_l_tttle = (TextView) findViewById(R.id.home_new_tv_l_one);
		tv_newpp_l_price = (TextView) findViewById(R.id.home_new_tv_l_two);
		tv_newpp_c_tttle = (TextView) findViewById(R.id.home_new_tv_c_one);
		tv_newpp_c_price = (TextView) findViewById(R.id.home_new_tv_c_two);
		tv_newpp_r_tttle = (TextView) findViewById(R.id.home_new_tv_r_one);
		tv_newpp_r_price = (TextView) findViewById(R.id.home_new_tv_r_two);
		
		gridView = (MyGridview) findViewById(R.id.home_gridview);
		gridView.setOnItemClickListener(new myitemlistener());
		
		more = (ImageView) findViewById(R.id.home_more);
		more.setOnClickListener(this);
		woman = (ImageView) findViewById(R.id.home_woman);
		woman.setOnClickListener(this);
		man = (ImageView) findViewById(R.id.home_mant);
		man.setOnClickListener(this);
		neiyi = (ImageView) findViewById(R.id.home_threex);
		neiyi.setOnClickListener(this);
		runhua = (ImageView) findViewById(R.id.home_lwrite);
		runhua.setOnClickListener(this);
		tt = (ImageView) findViewById(R.id.home_tt);
		tt.setOnClickListener(this);
		tosex = (ImageView) findViewById(R.id.home_tosex);
		tosex.setOnClickListener(this);


	}
	private void setimagebackground(String url,ImageView iv){
		iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		ImageLoader.getInstance().displayImage(url, iv);
	}
	private void toActivity(int type,int id){
		if(type==2){
			Intent intent = new Intent(HomeActivity.this,ProductDetailsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("id",id);
			startActivity(intent);
		}
	}
	private void toFclassFrist(String title,int postion){
		//TODO
		Intent intent = new Intent(HomeActivity.this,ProductDetailsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("listindex",postion);
		intent.putExtra("title",title);
		startActivity(intent);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_more:
			if(!morellisshow){
				morell.setVisibility(View.VISIBLE);
				morellisshow = true;
			}else{
				morell.setVisibility(View.GONE);
				morellisshow = false;
			}
			break;
		case R.id.home_woman:
			title = "女性用品";
			postion = 0;
			toFclassFrist(title, postion);
			break;
		case R.id.home_mant:
			title = "男性用品";
			postion = 1;
			toFclassFrist(title, postion);
			break;
		case R.id.home_threex:
			title = "情趣内衣";
			postion = 2;
			toFclassFrist(title, postion);
			break;
		case R.id.home_lwrite:
			//小分类
			title = "润滑消毒";
			break;
		case R.id.home_tt:
			title = "安全套";
			postion = 3;
			toFclassFrist(title, postion);
			break;
		case R.id.home_tosex:
			title = "双人情趣";
			postion = 5;
			toFclassFrist(title, postion);
			break;
		case R.id.home_new_iv_l:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(1).get(0).getType();
			id = homeBeanList.getList().get(1).get(0).getId();
			toActivity(type, id);
			}
			break;
		case R.id.home_new_iv_c:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(1).get(1).getType();
			id = homeBeanList.getList().get(1).get(1).getId();
			toActivity(type, id);
			}
			break;
		case R.id.home_new_iv_r:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(1).get(2).getType();
			id = homeBeanList.getList().get(1).get(2).getId();
			toActivity(type, id);
			}
			break;
			
		case R.id.home_woman_hot_iv_l:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(2).get(0).getType();
			id = homeBeanList.getList().get(2).get(0).getId();
			toActivity(type, id);
			}
			break;
		case R.id.home_woman_hot_iv_r_t:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(2).get(1).getType();
			id = homeBeanList.getList().get(2).get(1).getId();
			toActivity(type, id);
			}
			break;
		case R.id.home_woman_hot_iv_r_b:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(2).get(2).getType();
			id = homeBeanList.getList().get(2).get(2).getId();
			toActivity(type, id);
			}
			break;
			
		case R.id.home_man_hot_iv_l:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(3).get(0).getType();
			id = homeBeanList.getList().get(3).get(0).getId();
			toActivity(type, id);
			}
			break;
		case R.id.home_man_hot_iv_r_t:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(3).get(1).getType();
			id = homeBeanList.getList().get(3).get(1).getId();
			toActivity(type, id);
			}
			break;
		case R.id.home_man_hot_iv_r_b:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(3).get(2).getType();
			id = homeBeanList.getList().get(3).get(2).getId();
			toActivity(type, id);
			}
			break;
			
		case R.id.home_neiyi_hot_iv_l:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(4).get(0).getType();
			id = homeBeanList.getList().get(4).get(0).getId();
			toActivity(type, id);
			}
			break;
		case R.id.home_neiyi_hot_iv_r_t:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(4).get(1).getType();
			id = homeBeanList.getList().get(4).get(1).getId();
			toActivity(type, id);
			}
			break;
		case R.id.home_neiyi_hot_iv_r_b:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(4).get(2).getType();
			id = homeBeanList.getList().get(4).get(2).getId();
			toActivity(type, id);
			}
			break;
			
		case R.id.home_tt_hot_iv_l:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(5).get(0).getType();
			id = homeBeanList.getList().get(5).get(0).getId();
			toActivity(type, id);
			}
			break;
		case R.id.home_tt_hot_iv_r_t:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(5).get(1).getType();
			id = homeBeanList.getList().get(5).get(1).getId();
			toActivity(type, id);
			}
			break;
		case R.id.home_tt_hot_iv_r_b:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(5).get(2).getType();
			id = homeBeanList.getList().get(5).get(2).getId();
			toActivity(type, id);
			}
			break;
			
		case R.id.home_tosex_hot_iv_l:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(6).get(0).getType();
			id = homeBeanList.getList().get(6).get(0).getId();
			toActivity(type, id);
			}
			break;
		case R.id.home_tosex_hot_iv_r_t:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(6).get(1).getType();
			id = homeBeanList.getList().get(6).get(1).getId();
			toActivity(type, id);
			}
			break;
		case R.id.home_tosex_hot_iv_r_b:
			if(homeBeanList!=null){
			type = homeBeanList.getList().get(6).get(2).getType();
			id = homeBeanList.getList().get(6).get(2).getId();
			toActivity(type, id);
			}
			break;

		}
	}

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(int position, View imageView) {
			// TODO 单击图片处理事件
			if(urllist!=null){
				int type = homeBeanList.getList().get(0).get(position).getType();
			if(type==2){
				Intent intent = new Intent(HomeActivity.this,ProductDetailsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("id",homeBeanList.getList().get(0).get(position).getId());
				startActivity(intent);
			}
			}
		}

		@Override
		public void displayImage(String imageURL, ImageView imageView) {
			ImageLoader.getInstance().displayImage(imageURL, imageView);
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		actitvit.startImageCycle();
	};

	@Override
	protected void onPause() {
		super.onPause();
		actitvit.pushImageCycle();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		actitvit.pushImageCycle();
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
		protected ListGoodsBean doInBackground(Object... params) {  
			try {  
				Send send = new Send(context);
				String time = String.valueOf(System.currentTimeMillis());
				homeBeanList = send.RequestHome(time);
				return homeBeanList;//new String(baos.toByteArray(), "gb2312");  
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
			homeBeanList  = (ListGoodsBean) result;
			if(homeBeanList!=null && homeBeanList.getCode() == 200){
				activitListBean = homeBeanList.getList().get(0);
				urllist = new ArrayList<String>();
				for(int i=0;i<activitListBean.size();i++){
					String url1 = activitListBean.get(i).getPicurl();
					urllist.add(url1);
				}
				actitvit.setImageResources(urllist, mAdCycleViewListener);
				newppListBean = homeBeanList.getList().get(1);
				setimagebackground(newppListBean.get(0).getPicurl(), home_new_l);
				tv_newpp_l_price.setText("￥"+newppListBean.get(0).getShop_price());
				tv_newpp_l_tttle.setText(newppListBean.get(0).getTitle());
				setimagebackground(newppListBean.get(1).getPicurl(), home_new_c);
				tv_newpp_c_price.setText("￥"+newppListBean.get(1).getShop_price());
				tv_newpp_c_tttle.setText(newppListBean.get(1).getTitle());
				setimagebackground(newppListBean.get(2).getPicurl(), home_new_r);
				tv_newpp_r_price.setText("￥"+newppListBean.get(2).getShop_price());
				tv_newpp_r_tttle.setText(newppListBean.get(2).getTitle());
				//女用
				womanListBean = homeBeanList.getList().get(2);
				for(int i=0;i<womanListBean.size();i++){
					String u = womanListBean.get(i).getPicurl();
					setimagebackground(u, womanimglist.get(i));
				}
				//男用
				manlistBean = homeBeanList.getList().get(3);
				for(int i=0;i<manlistBean.size();i++){
					String u = manlistBean.get(i).getPicurl();
					setimagebackground(u, manlist.get(i));
				}
				//内衣
				neiyilistBean = homeBeanList.getList().get(4);
				for(int i=0;i<neiyilistBean.size();i++){
					String u = neiyilistBean.get(i).getPicurl();
					setimagebackground(u, neiyilist.get(i));
				}
				//tt
				ttlistBean = homeBeanList.getList().get(5);
				for(int i=0;i<ttlistBean.size();i++){
					String u = ttlistBean.get(i).getPicurl();
					setimagebackground(u, ttlist.get(i));
				}
				//tosex
				tosexlistBean = homeBeanList.getList().get(6);
				for(int i=0;i<tosexlistBean.size();i++){
					String u = tosexlistBean.get(i).getPicurl();
					setimagebackground(u, tosexlist.get(i));
				}
				
				
				likeListBean = homeBeanList.getList().get(homeBeanList.getList().size()-1);
				adapter = new HomeGridviewAdapter(context, likeListBean);
				gridView.setAdapter(adapter);
				myscrollview.smoothScrollTo(0,20);

			}else{
				if(homeBeanList!=null)Util.ShowToast(context, homeBeanList.getMsg());
			}
		}  

		//onCancelled方法用于在取消执行中的任务时更改UI  
		@Override  
		protected void onCancelled() {  
			Util.stopProgressDialog();
		}  
	} 
	
	class myitemlistener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			type = homeBeanList.getList().get(7).get(arg2).getType();
			id = homeBeanList.getList().get(7).get(arg2).getId();
			toActivity(type, id);
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
}
