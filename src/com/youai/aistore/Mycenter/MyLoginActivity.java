package com.youai.aistore.Mycenter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.UserBean;
import com.youai.aistore.NetInterface.Send;

/**
 * 登陆界面
 * 
 * @author zy
 * 
 */

public class MyLoginActivity extends BaseActivity implements OnClickListener {
	private EditText login_ID, login_password;
	private TextView regist_link;
	private Button login_btn;
	@SuppressWarnings("unused")
	private Context context;
	private UserBean result;
	Handler LoginMessageHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				//提示登录成功
				Util.ShowToast(MyLoginActivity.this, R.string.login_succeed);
				// 保存登陆状态
				result = (UserBean) msg.obj;// result,赋值
				MyApplication.logined = true;
				MyApplication.SaveUserBean(result);
				finish();
			} else if (msg.what == 2) {
				Util.ShowToast(MyLoginActivity.this, R.string.login_error);
			}else if (msg.what == 3) {
				Util.ShowToast(MyLoginActivity.this, R.string.net_work_is_error);
			}

		}
	};

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		// 登陆界面标题；显示
		setTitleTxt(R.string.my_login_tv);
		// 布局文件
		setContentXml(R.layout.my_login);
		// 登陆账号ID和密码
		login_ID = (EditText) findViewById(R.id.my_login_ID_et);
		login_password = (EditText) findViewById(R.id.my_login_password_et);

		login_btn = (Button) findViewById(R.id.my_login_btn);
		login_btn.setOnClickListener(this);
		/*接收注册界面发过来的账号，*/
		String uerid = getIntent().getExtras().getString("uerID", "");
		login_ID.setText(uerid);
		//注册链接
		regist_link =(TextView) findViewById(R.id.regist_link);
		regist_link.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.my_login_btn:
			if(Util.detect(MyLoginActivity.this)){//判断是否联网
				if (validate()) {// 判断验证是不是成功了
					login();

				}
			}else{
				Util.ShowToast(MyLoginActivity.this, R.string.net_work_is_error);
			}
			break;
		case R.id.regist_link:
			Intent intent = new Intent(MyLoginActivity.this,
					MyRegistActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

		default:
			break;
		}
		
		
		
	}

	/* 验证输入的用户名和密码对不对。 */
	private boolean validate() {
		String id = login_ID.getText().toString();
		if (id.equals("")) {
			Util.ShowToast(MyLoginActivity.this, R.string.login_id_not_null);// 账号不能空
			return false;
		} else {
			if (Util.isMobileNO(id) || Util.isEmail(id)) {
				String pwd = login_password.getText().toString();
				if (pwd.equals("")) {
					Util.ShowToast(MyLoginActivity.this,
							R.string.login_password_not_null);// 密码不能空
					return false;
				} else {
					return true;
				}
			} else {
				Util.ShowToast(MyLoginActivity.this,
						R.string.login_format_error); // 格式错误
				return false;
			}
		}

	}// validate

	/*
	 * 登录(子线程，异步加载的方式加载数据)
	 */

	private void login() {
		final String id = login_ID.getText().toString();
		final String pwd = login_password.getText().toString();
		new Thread() {
			public void run() {

				Send send = new Send(MyLoginActivity.this);
				result = send.getLogin(id, pwd);
				Message msg = new Message();
				if(result!=null){
					if (result.getCode() == 200 ) {
						msg.what = 1;
						msg.obj = result;
					}else if(result.getCode() == 500){
						msg.what = 3;
					} else {
						msg.what = 2;
					}
				}else{
					msg.what = 3;
				}
				LoginMessageHandler.sendMessage(msg);
			}
		}.start();

	}

}
