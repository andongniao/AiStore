package com.youai.aistore.Product;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.BaseActivity;
import com.youai.aistore.ImageCycleView;
import com.youai.aistore.ImageCycleView.ImageCycleViewListener;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.CommentsBean;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.Bean.ListCommentsBean;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.xlistview.XListView;
import com.youai.aistore.xlistview.XListView.IXListViewListener;
/**
 * 产品详情界面
 * @author 
 *
 */
public class ProductDetailsActivity extends BaseActivity implements IXListViewListener,OnClickListener{
	private ImageCycleView topshowic;
	private WebView webView;
	private XListView xListView;
	private View view_webview,view_listview;
	private TextView tv_shop_price,tv_market_price,tv_title,tv_click_num,tv_image_text_tv,tv_user_comment;
	private LinearLayout addviewll,call_ll,sms_ll;
	private LayoutInflater inflater;
	private Context context;
	private String URL;
	private MyTask myTask;
	private int id,showsatau,page;
	private GoodsBean bean;
	private UserCommentAdapter adapter;
	private ArrayList<CommentsBean> list;
	private Handler handler,myHandler;
	private ListCommentsBean listcombean,nextpagelist;
	private  Dialog alertDialog;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTitleTxt(R.string.product_title);
		setContentXml(R.layout.product_details);
		setTopLeftBackground(R.drawable.btn_back);
		handler = new Handler();
		init();
		if(Util.detect(context)){
			myTask = new MyTask(1);
			myTask.execute("");  
			myTask = new MyTask(2);
			myTask.execute("");  
		}else{
			Util.ShowToast(context, R.string.net_work_is_error);
		}
		//		myHandler = new Handler(){
		//			@Override
		//			public void handleMessage(Message msg) {
		//				super.handleMessage(msg);
		//				//TODO
		//			}
		//		};
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	private void init() {
		page = 1;
		showsatau = 0;
		id = getIntent().getIntExtra("id", -1);
		context = ProductDetailsActivity.this;
		inflater = LayoutInflater.from(context);
		call_ll = (LinearLayout) findViewById(R.id.product_details_call_ll);
		call_ll.setOnClickListener(this);
		sms_ll = (LinearLayout) findViewById(R.id.product_details_sms_ll);
		sms_ll.setOnClickListener(this);
		view_webview =  inflater.inflate(R.layout.product_addview_webview, null);
		view_listview =  inflater.inflate(R.layout.product_addview_listview, null);
		webView = (WebView) view_webview.findViewById(R.id.product_webview);
		webView.setFocusable(false);
		xListView = (XListView) view_listview.findViewById(R.id.product_xListView);
		xListView.setFocusable(false);
		xListView.setPullLoadEnable(true);
		xListView.setXListViewListener(this);
		addviewll = (LinearLayout) findViewById(R.id.product_addview_ll);
		addviewll.addView(view_webview);
		addviewll.addView(view_listview);
		view_listview.setVisibility(View.GONE);
		topshowic = (ImageCycleView) findViewById(R.id.product_details_ic);
		topshowic.stopImageTimerTask();
		topshowic.settime(999999999);

		tv_shop_price = (TextView) findViewById(R.id.product_shop_price_tv);
		tv_shop_price.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
		tv_shop_price.getPaint().setAntiAlias(true);//抗锯齿
		tv_market_price = (TextView) findViewById(R.id.product_market_price_tv);
		tv_market_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
		tv_market_price.getPaint().setAntiAlias(true);//抗锯齿
		tv_click_num = (TextView) findViewById(R.id.product_click_num_tv);
		tv_title = (TextView) findViewById(R.id.product_title_tv);
		tv_image_text_tv = (TextView) findViewById(R.id.product_image_text_tv);
		tv_image_text_tv.setOnClickListener(this);
		tv_user_comment = (TextView) findViewById(R.id.product_user_comment_tv);
		tv_user_comment.setOnClickListener(this);


		tv_image_text_tv.setTextColor(getResources().getColor(R.color.home_text_red));
		tv_image_text_tv.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
		tv_image_text_tv.getPaint().setAntiAlias(true);//抗锯齿
		tv_user_comment.getPaint().setFlags(0); // 取消设置的的划线
		tv_user_comment.setTextColor(getResources().getColor(R.color.black));


		//自适应屏幕
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.setWebViewClient(new MyWebViewClient());



	}

	class MyWebViewClient extends WebViewClient {
		//重写父类方法，让新打开的网页在当前的WebView中显示
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		//网页开始加载
		@Override
		public void onPageStarted(WebView 
				view, String url, Bitmap favicon) {

			super.onPageStarted(view, url, favicon);
		}
		//网页加载完毕

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}


	}

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(int position, View imageView) {
			// TODO 单击图片处理事件
		}

		@Override
		public void displayImage(String imageURL, ImageView imageView) {
			ImageLoader.getInstance().displayImage(imageURL, imageView);
		}
	};

	@Override
	public void onRefresh() {
		page = 1;
		myTask = new MyTask(3);
		myTask.execute("");  

	}

	@Override
	public void onLoadMore() {
		page += 1;
		myTask = new MyTask(3);
		myTask.execute("");  


	}



	private class MyTask extends AsyncTask<Object, Object, Object> {  
		private int getstatu = 1;
		public MyTask(int getstatu){
			this.getstatu = getstatu;
		}
		//onPreExecute方法用于在执行后台任务前做一些UI操作  
		@Override  
		protected void onPreExecute() {  
			//	            textView.setText("loading...");  
			if(getstatu == 1 ||getstatu == 2){
				Util.startProgressDialog(context);
			}
		}  

		//doInBackground方法内部执行后台任务,不可在此方法内修改UI  
		@Override  
		protected Object doInBackground(Object... params) {  
			try {  
				if(getstatu == 1){
					Send send = new Send(context);
					bean = send.GetProductDetails(id);
					return bean;//new String(baos.toByteArray(), "gb2312");  
				}else if(getstatu == 2){
					Send send = new Send(context);
					listcombean = send.GetProductComments(id, page);
					return listcombean;//new String(baos.toByteArr
				}else if(getstatu == 3){
					Send send = new Send(context);
					nextpagelist = send.GetProductComments(id, page);
					return nextpagelist;//new String(baos.toByteArr
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
			if(getstatu==1){
				bean = (GoodsBean) result;
				if(bean!=null && bean.getCode()==200){
					//图片
					topshowic.setImageResources(bean.getPicurls(), mAdCycleViewListener);
					tv_shop_price.setText("￥"+bean.getShop_price()+"元");
					tv_market_price.setText("￥"+bean.getMarket_price()+"元");
					tv_click_num.setText(bean.getClick());
					tv_title.setText(bean.getTitle());
					//webview
					URL = bean.getGood_desc();
					webView.loadUrl(URL);
					DisplayMetrics dm = new DisplayMetrics();//获取当前显示的界面大小
					getWindowManager().getDefaultDisplay().getMetrics(dm);
					int width=dm.widthPixels;
					int height=dm.heightPixels;//获取当前界面的高度
					LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) webView.getLayoutParams(); 
					linearParams.height = height;//linearParams.WRAP_CONTENT;
					webView.setLayoutParams(linearParams);
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							DisplayMetrics dm = new DisplayMetrics();//获取当前显示的界面大小
							getWindowManager().getDefaultDisplay().getMetrics(dm);
							int width=dm.widthPixels;
							int height=dm.heightPixels;//获取当前界面的高度
							LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) webView.getLayoutParams(); 
							linearParams.height = linearParams.WRAP_CONTENT;
							webView.setLayoutParams(linearParams);
						}
					}, 2000);
				}else{
					if(bean!=null)
						Util.ShowToast(context, bean.getMsg());
				}
			}else if(getstatu == 2){
				listcombean = (ListCommentsBean) result;
				if(listcombean!=null && listcombean.getCode()==200){
					list = listcombean.getList();
					adapter = new UserCommentAdapter(context,list);
					xListView.setAdapter(adapter);
					Util.setListViewHeightBasedOnChildren(xListView);
				}else{
					if(listcombean!=null)
						Util.ShowToast(context, listcombean.getMsg());
				}
			}else if(getstatu == 3){
				onLoad();
				nextpagelist = (ListCommentsBean) result;
				if(nextpagelist!=null && nextpagelist.getCode()==200){
					if(nextpagelist.getList().size()>0){
						if(page==1){
							list = nextpagelist.getList();
							if(adapter==null){
								adapter = new UserCommentAdapter(context, list);
								xListView.setAdapter(adapter);
							}else{
								adapter.setdata(list);
								adapter.notifyDataSetInvalidated();
							}
						}else{
							list.addAll(nextpagelist.getList());
							if(adapter==null){
								adapter = new UserCommentAdapter(context, list);
								xListView.setAdapter(adapter);
							}else{
								adapter.setdata(list);
								adapter.notifyDataSetInvalidated();
							}
						}
						Util.setListViewHeightBasedOnChildren(xListView);
					}else{
						Util.ShowToast(context, "最后一页了，亲");
					}
				}else{
					if(listcombean!=null)
						Util.ShowToast(context, listcombean.getMsg());
				}
			}



		}  

		//onCancelled方法用于在取消执行中的任务时更改UI  
		@Override  
		protected void onCancelled() {  
			Util.stopProgressDialog();
		}  
	}



	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.product_image_text_tv:
			if(showsatau == 1){
				view_listview.setVisibility(View.GONE);
				view_webview.setVisibility(View.VISIBLE);
				showsatau = 0;
				tv_image_text_tv.setTextColor(getResources().getColor(R.color.home_text_red));
				tv_image_text_tv.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
				tv_image_text_tv.getPaint().setAntiAlias(true);//抗锯齿
				tv_user_comment.getPaint().setFlags(0); // 取消设置的的划线
				tv_user_comment.setTextColor(getResources().getColor(R.color.black));

			}
			break;
		case R.id.product_user_comment_tv:
			if(showsatau == 0){
				view_webview.setVisibility(View.GONE);
				view_listview.setVisibility(View.VISIBLE);
				showsatau = 1;
				tv_user_comment.setTextColor(getResources().getColor(R.color.home_text_red));
				tv_user_comment.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
				tv_user_comment.getPaint().setAntiAlias(true);//抗锯齿
				tv_image_text_tv.getPaint().setFlags(0); // 取消设置的的划线
				tv_image_text_tv.setTextColor(getResources().getColor(R.color.black));
				//								if(adapter==null){
				//									adapter = new UserCommentAdapter(context, list);
				//									xListView.setAdapter(adapter);
				//								}else{
				//									adapter.setdata(list);
				//									adapter.notifyDataSetInvalidated();
				//								}
			}
			break;
		case R.id.product_details_call_ll:
			ShowDialog(1);
			break;
		case R.id.product_details_sms_ll:
			ShowDialog(2);
			break;

		}

	} 

	private void onLoad() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		//		xListView.setRefreshTime(changetime);
	}

	private void ShowDialog(final int statu){
		int index,title;
		if(statu == 1){
			title = R.string.product_iscall;
			index = R.string.product_cll_tell;
		}else{
			title = R.string.product_issms;
			index = R.string.product_sms_send;

		}
		alertDialog = new AlertDialog.Builder(this).
				setTitle(title).
				setIcon(null).
				setPositiveButton(R.string.product_cancle, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).
				setNegativeButton(index, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(statu==1){
							Intent phoneIntent = new Intent("android.intent.action.CALL",
									Uri.parse("tel:" + MyApplication.callnumber));
							startActivity(phoneIntent); 
						}else{
							Uri uri=Uri.parse("smsto:"+MyApplication.smsnumber);
							Intent ii=new Intent(Intent.ACTION_SENDTO,uri);
							ii.putExtra("sms_body", "");
							startActivity(ii);
						}
					}
				}).
				create();
		alertDialog.show();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(alertDialog!=null && alertDialog.isShowing()){
			alertDialog.dismiss();
			alertDialog = null;
		}else
			if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
				finish();
				return true;   
			}
		return super.onKeyDown(keyCode, event);
	}

}
