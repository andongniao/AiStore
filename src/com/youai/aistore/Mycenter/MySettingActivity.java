package com.youai.aistore.Mycenter;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
/**
 * 个人设置
 * 
 * @author zy
 * 
 */

public class MySettingActivity extends BaseActivity implements OnClickListener {
	private Button exit_btn;
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

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
