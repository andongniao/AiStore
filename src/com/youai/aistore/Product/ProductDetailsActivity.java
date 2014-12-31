package com.youai.aistore.Product;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.BaseActivity;
import com.youai.aistore.ImageCycleView;
import com.youai.aistore.ImageCycleView.ImageCycleViewListener;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.GoodsBean;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.xlistview.XListView;
import com.youai.aistore.xlistview.XListView.IXListViewListener;
/**
 * ��Ʒ�������
 * @author 
 *
 */
public class ProductDetailsActivity extends BaseActivity implements IXListViewListener,OnClickListener{
	private ImageCycleView topshowic;
	private WebView webView;
	private XListView xListView;
	private View view_webview,view_listview;
	private TextView tv_shop_price,tv_market_price,tv_title,tv_click_num,tv_image_text_tv,tv_user_comment;
	private LinearLayout addviewll;
	private LayoutInflater inflater;
	private Context context;
	private String URL;
	private MyTask myTask;
	private int id,showsatau;
	private GoodsBean bean;
	private UserCommentAdapter adapter;
	private ArrayList<String> list;
	private Handler handler;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTitleTxt(R.string.product_title);
		setContentXml(R.layout.product_details);
		handler = new Handler();
		init();
		if(Util.detect(context)){
			myTask = new MyTask();
			myTask.execute("");  
		}else{
			Util.ShowToast(context, R.string.net_work_is_error);
		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	private void init() {
		showsatau = 0;
		id = getIntent().getIntExtra("id", -1);
		context = ProductDetailsActivity.this;
		inflater = LayoutInflater.from(context);
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
		tv_shop_price.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //�»���
		tv_shop_price.getPaint().setAntiAlias(true);//�����
		tv_market_price = (TextView) findViewById(R.id.product_market_price_tv);
		tv_market_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //�л���
		tv_market_price.getPaint().setAntiAlias(true);//�����
		tv_click_num = (TextView) findViewById(R.id.product_click_num_tv);
		tv_title = (TextView) findViewById(R.id.product_title_tv);
		tv_image_text_tv = (TextView) findViewById(R.id.product_image_text_tv);
		tv_image_text_tv.setOnClickListener(this);
		tv_user_comment = (TextView) findViewById(R.id.product_user_comment_tv);
		tv_user_comment.setOnClickListener(this);
		
		
		tv_image_text_tv.setTextColor(getResources().getColor(R.color.home_text_red));
		tv_image_text_tv.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //�»���
		tv_image_text_tv.getPaint().setAntiAlias(true);//�����
		tv_user_comment.getPaint().setFlags(0); // ȡ�����õĵĻ���
		tv_user_comment.setTextColor(getResources().getColor(R.color.black));
		
		
		/******************ģ������******************************/
		String imageUrl1 = "http://www.ppt123.net/beijing/UploadFiles_8374/201202/2012022812392852.jpg";
		String imageUrl2 = "http://img3.imgtn.bdimg.com/it/u=3371032114,892333757&fm=21&gp=0.jpg";
		String imageUrl3 = "http://img1.imgtn.bdimg.com/it/u=115106945,3813595292&fm=21&gp=0.jpg";
		String imageUrl4 = "http://img5.imgtn.bdimg.com/it/u=2065705989,2820968328&fm=21&gp=0.jpg";
		ArrayList<String>mImageUrl = new ArrayList<String>();
		mImageUrl.add(imageUrl1);
		mImageUrl.add(imageUrl2);
		mImageUrl.add(imageUrl3);
		mImageUrl.add(imageUrl4);
		topshowic.setImageResources(mImageUrl, mAdCycleViewListener);
		/***************webview*********************/
//		webView.getSettings().setJavaScriptEnabled(true);  
//		webView.getSettings().setSupportZoom(true);   
		//���ó������Ź���   
//		webView.getSettings().setBuiltInZoomControls(true);
//		webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);  		
		//����Ӧ��Ļ
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.setWebViewClient(new MyWebViewClient());
		/******************ģ������******************************/
		URL = "http://www.android100.org/html/201306/25/3281.html";//"http://www.baidu.com";//bean.getGood_desc();
		webView.loadUrl(URL);
//		webView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		DisplayMetrics dm = new DisplayMetrics();//��ȡ��ǰ��ʾ�Ľ����С
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;//��ȡ��ǰ����ĸ߶�
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) webView.getLayoutParams(); 
        linearParams.height = height;//linearParams.WRAP_CONTENT;
        webView.setLayoutParams(linearParams);
        handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				DisplayMetrics dm = new DisplayMetrics();//��ȡ��ǰ��ʾ�Ľ����С
		        getWindowManager().getDefaultDisplay().getMetrics(dm);
				 int width=dm.widthPixels;
			        int height=dm.heightPixels;//��ȡ��ǰ����ĸ߶�
			        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) webView.getLayoutParams(); 
			        linearParams.height = linearParams.WRAP_CONTENT;
			        webView.setLayoutParams(linearParams);
			}
		}, 1000);
        
        
        
		ArrayList<String> list = new ArrayList<String>();
		list.add("1");
		list.add("w");
		list.add("f");
		adapter = new UserCommentAdapter(context, list);
		xListView.setAdapter(adapter);
		Util.setListViewHeightBasedOnChildren(xListView);
	}
	
	class MyWebViewClient extends WebViewClient {
		//��д���෽�������´򿪵���ҳ�ڵ�ǰ��WebView����ʾ
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		//��ҳ��ʼ����
		@Override
		public void onPageStarted(WebView 
				view, String url, Bitmap favicon) {
			
			super.onPageStarted(view, url, favicon);
		}
		 //��ҳ�������

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}


	}

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(int position, View imageView) {
			// TODO ����ͼƬ�����¼�
		}

		@Override
		public void displayImage(String imageURL, ImageView imageView) {
			ImageLoader.getInstance().displayImage(imageURL, imageView);
		}
	};

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		onLoad();
		String s = String.valueOf(System.currentTimeMillis());
		list.add(s);
		adapter.setdata(list);
		Util.setListViewHeightBasedOnChildren(xListView);
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
		protected GoodsBean doInBackground(Object... params) {  
			try {  
				Send send = new Send(context);
				bean = send.GetProductDetails(2056);
				return bean;//new String(baos.toByteArray(), "gb2312");  
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
			bean = (GoodsBean) result;
			if(bean!=null && bean.getCode()==200){
			//ͼƬ
			topshowic.setImageResources(bean.getPicurls(), mAdCycleViewListener);

				
			//webview
//			URL = bean.getGood_desc();
//			webView.loadUrl(URL);
			}else{
				if(bean!=null)
				Util.ShowToast(context, bean.getMsg());
			}
			
			
		
		}  

		//onCancelled����������ȡ��ִ���е�����ʱ����UI  
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
				tv_image_text_tv.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //�»���
				tv_image_text_tv.getPaint().setAntiAlias(true);//�����
				tv_user_comment.getPaint().setFlags(0); // ȡ�����õĵĻ���
				tv_user_comment.setTextColor(getResources().getColor(R.color.black));

			}
			break;
		case R.id.product_user_comment_tv:
			if(showsatau == 0){
				view_webview.setVisibility(View.GONE);
				view_listview.setVisibility(View.VISIBLE);
				showsatau = 1;
				tv_user_comment.setTextColor(getResources().getColor(R.color.home_text_red));
				tv_user_comment.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //�»���
				tv_user_comment.getPaint().setAntiAlias(true);//�����
				tv_image_text_tv.getPaint().setFlags(0); // ȡ�����õĵĻ���
				tv_image_text_tv.setTextColor(getResources().getColor(R.color.black));
				list = new ArrayList<String>();
				list.add("1");
				list.add("w");
				list.add("f");
				if(adapter==null){
					adapter = new UserCommentAdapter(context, list);
					xListView.setAdapter(adapter);
				}else{
					adapter.setdata(list);
					adapter.notifyDataSetInvalidated();
				}
			}
			break;

		}
		
	} 
	
	private void onLoad() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
//		xListView.setRefreshTime(changetime);
	}
}
