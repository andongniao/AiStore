package com.youai.aistore.Home;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;

public class HomeActivity extends BaseActivity implements OnClickListener{
	private ImageView actitvit,more;
	private LinearLayout morell,likeview,addviewll;
	private boolean morellisshow;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTopLeftBackground(R.drawable.btn_search_navigation_back);
		setTopRightBackground(R.drawable.ic_launcher);
		topRightVisible();
		TextView topright = (TextView) getTopRightView();
		topright.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Util.ShowToast(HomeActivity.this, "search");
			}
		});
		setContentXml(R.layout.home);
		init();
	}


	private void init() {
		morellisshow = false;
		actitvit = (ImageView) findViewById(R.id.home_activit);
		actitvit.setBackgroundResource(R.drawable.banner);
		morell = (LinearLayout) findViewById(R.id.home_more_ll);
		likeview = (LinearLayout) findViewById(R.id.home_addview);
//		addviewll = (LinearLayout) findViewById(R.id.home_like_addview_ll);
		View v1 = getLayoutInflater().inflate(R.layout.home_like_addview, null);
		likeview.addView(v1);
		more = (ImageView) findViewById(R.id.home_more);
		more.setOnClickListener(this);
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
}
