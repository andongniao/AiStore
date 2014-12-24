package com.youai.aistore.Home;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;

public class HomeActivity extends BaseActivity{

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
//		setContentXml(R.layout.);
	}
}
