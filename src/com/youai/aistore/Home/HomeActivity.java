package com.youai.aistore.Home;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.BaseActivity;
import com.youai.aistore.ImageCycleView;
import com.youai.aistore.ImageCycleView.ImageCycleViewListener;
import com.youai.aistore.R;
import com.youai.aistore.Util;

public class HomeActivity extends BaseActivity implements OnClickListener{
	private ImageCycleView actitvit;
	private ArrayList<String> mImageUrl = null;
	private ImageView more,home_new_l,home_new_c,home_new_r,home_woman_hot_iv_l,home_woman_hot_iv_r_t
	,home_woman_hot_iv_r_b,home_like_iv_l,home_like_iv_r;
	private LinearLayout morell,likeview,addviewll;
	private boolean morellisshow;
	
	private String imageUrl1 = "http://imgs.xiuna.com/xiezhen/2014-9-25/2/5562900520140919100645087.jpg";

	private String imageUrl2 = "http://imgs.xiuna.com/xiezhen/2013-3-20/1/12.jpg";

	private String imageUrl3 = "http://srimg1.meimei22.com/pic/suren/2014-9-24/1/8740_11329820378.jpg";
	
	private String imageUrl4 = "http://imgs.xiuna.com/xiezhen/2013-3-20/1/12.jpg";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		setTopLeftBackground(R.drawable.btn_search_navigation_back);
		setTopTitleBackground(R.drawable.logo);
		setTopRightBackground(R.drawable.search);
		topRightVisible();
		TextView topright = (TextView) getTopRightView();
		topright.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Util.ShowToast(HomeActivity.this, "search");
				Intent intent = new Intent(HomeActivity.this,SearchActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				overridePendingTransition(R.anim.in_rightleft, R.anim.out_rightleft);
			}
		});
		setContentXml(R.layout.home);
		init();
	}


	private void init() {
		morellisshow = false;
		View v1 = getLayoutInflater().inflate(R.layout.home_like_addview, null);
		actitvit = (ImageCycleView) findViewById(R.id.home_activit);
		mImageUrl = new ArrayList<String>();
		mImageUrl.add(imageUrl1);
		mImageUrl.add(imageUrl2);
		mImageUrl.add(imageUrl3);
//		mImageUrl.add(imageUrl4);
		actitvit.setImageResources(mImageUrl, mAdCycleViewListener);
//		actitvit.setBackgroundResource(R.drawable.banner);
		morell = (LinearLayout) findViewById(R.id.home_more_ll);
		likeview = (LinearLayout) findViewById(R.id.home_addview);
		home_new_l = (ImageView) findViewById(R.id.home_new_iv_l);
		home_new_c = (ImageView) findViewById(R.id.home_new_iv_c);
		home_new_r = (ImageView) findViewById(R.id.home_new_iv_r);
		home_woman_hot_iv_l = (ImageView) findViewById(R.id.home_woman_hot_iv_l);
		home_woman_hot_iv_r_t = (ImageView) findViewById(R.id.home_woman_hot_iv_r_t);
		home_woman_hot_iv_r_b = (ImageView) findViewById(R.id.home_woman_hot_iv_r_b);
		home_like_iv_l = (ImageView) v1.findViewById(R.id.home_like_iv_l);
		home_like_iv_r = (ImageView) v1.findViewById(R.id.home_like_iv_r);
//		addviewll = (LinearLayout) findViewById(R.id.home_like_addview_ll);
		likeview.addView(v1);
		more = (ImageView) findViewById(R.id.home_more);
		more.setOnClickListener(this);
		/************************测试**********************/
		setimagebackground(imageUrl1, home_new_l);
		setimagebackground(imageUrl1, home_new_c);
		setimagebackground(imageUrl1, home_new_r);
		setimagebackground(imageUrl1, home_woman_hot_iv_l);
		setimagebackground(imageUrl1, home_woman_hot_iv_r_t);
		setimagebackground(imageUrl1, home_woman_hot_iv_r_b);
		setimagebackground(imageUrl1, home_like_iv_l);
		setimagebackground(imageUrl1, home_like_iv_r);
		
		
	}
	private void setimagebackground(String url,ImageView iv){
		iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		ImageLoader.getInstance().displayImage(url, iv);
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
	
}
