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

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.UserBean;
import com.youai.aistore.NetInterface.Send;

/**
 * 注册界面
 * 
 * @author zy
 * 
 */
public class MyRegistActivity extends BaseActivity implements OnClickListener {
	private EditText regist_ID, regist_password, regist_repassword;
	private Button regist_btn;
	@SuppressWarnings("unused")
	private Context context;
	@SuppressWarnings("unused")
	private String errormsg = "";
	@SuppressWarnings("unused")
	private String code, messagetxt;
	Handler LoginMessageHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				//提示注册成功
				Util.ShowToast(MyRegistActivity.this, R.string.regist_succeed);
				Intent intent = new Intent(MyRegistActivity.this,
						MyLoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("uerID", regist_ID.getText().toString());//把注册账号，传送到登陆界面
				startActivity(intent);
				finish();
			} else if (msg.what == 2) {
				Util.ShowToast(MyRegistActivity.this, R.string.regist_error);

			}else if (msg.what == 3) {
				Util.ShowToast(MyRegistActivity.this, R.string.net_work_is_error);

			}

		}
	};

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		// 注册界面标题；显示
		setTitleTxt(R.string.my_regist_tv);
		setContentXml(R.layout.my_regist);
		// 注册账号ID和密码
		regist_ID = (EditText) findViewById(R.id.my_regist_ID_et);
		regist_password = (EditText) findViewById(R.id.my_regist_password_et);
		regist_repassword = (EditText) findViewById(R.id.my_regist_repassword_et);

		regist_btn = (Button) findViewById(R.id.my_regist_btn);
		regist_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(Util.detect(MyRegistActivity.this)){//判断是否联网
			if (validate()) {
				addUser();
			}
		}else{
			Util.ShowToast(MyRegistActivity.this, R.string.net_work_is_error);
		}

	}

	/* 验证输入的用户名和密码对不对。 */
	private boolean validate() {
		String id = regist_ID.getText().toString();
		if (id.equals("")) {
			Util.ShowToast(MyRegistActivity.this, R.string.login_id_not_null);// 账号不能空
			return false;
		} else {
			if (Util.isMobileNO(id) || Util.isEmail(id)) { // 判断账号的格式，正确可以输入密码，
				String pwd = regist_password.getText().toString();
				if (pwd.equals("")) { // 密码不能空
					Util.ShowToast(MyRegistActivity.this,
							R.string.login_password_not_null);
					return false;
				} else {
					if (Util.ispassword(pwd)) {// 判断密码格式是否正确，正确可以输入确认密码
						String repwd = regist_repassword.getText().toString();
						if (repwd.equals("")) {// 确认密码不能为空
							Util.ShowToast(MyRegistActivity.this,
									R.string.regist_repassword_not_null);

							return false;
						} else {
							if (repwd.equals(pwd)) {
								return true;
							} else {
								Util.ShowToast(MyRegistActivity.this,
										R.string.regist_repasswors_error);// 确认密码与，密码不一致

								return false;
							}
						}

					} else {
						Util.ShowToast(MyRegistActivity.this,
								R.string.my_regist_password_et);// 密码至少4位
						return false;
					}

				}

			} else {
				Util.ShowToast(MyRegistActivity.this,
						R.string.regist_format_error); // 格式错误
				return false;
			}
		}

	}// validate

	private void addUser() {
		final String id = regist_ID.getText().toString();
		final String pwd = regist_password.getText().toString();
		new Thread() {
			public void run() {

				Send send = new Send(MyRegistActivity.this);
				UserBean result = send.regist(id, pwd);
				Message msg = new Message();
				if(result!=null){
					if (result.getCode() == 200) {
						msg.what = 1;
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
