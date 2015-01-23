package com.youai.aistore.Mycenter;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.ExampleActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.WelcomeActivity;
import com.youai.aistore.R.string;
/**
 * 个人设置
 * 
 * @author zy
 * 
 */

public class MySettingActivity extends BaseActivity implements OnClickListener {
	private Button exit_btn;
	private LinearLayout changepwdll;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		// 设置界面标题；显示
		setTitleTxt(R.string.mycenter_home_geren_set);
		// 布局文件
		setContentXml(R.layout.my_setting);
		exit_btn = (Button) findViewById(R.id.my_setting_exitid);
		exit_btn.setOnClickListener(this);
		changepwdll = (LinearLayout) findViewById(R.id.my_setting_changepassword);
		changepwdll.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.my_setting_exitid:
			
			String username = getIntent().getStringExtra("username");
			MyApplication.logined = false;
			MyApplication.RemvoeUser(username);
			Intent intent = new Intent(MySettingActivity.this,ExampleActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		case R.id.my_setting_changepassword:

			
			break;

		default:
			break;
		}

	}

}
