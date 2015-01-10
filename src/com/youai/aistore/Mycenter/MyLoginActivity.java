package com.youai.aistore.Mycenter;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.Base;
import com.youai.aistore.NetInterface.GetHttp;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.NetInterface.ServiceUrl;

/**
 * 登陆界面
 * 
 * @author zy
 * 
 */

public class MyLoginActivity extends BaseActivity implements OnClickListener {
	private EditText login_ID, login_password;
	private Button login_btn;
	Context context;
	String errormsg = "";
	String code, messagetxt;
	Handler LoginMessageHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				Util.ShowToast(MyLoginActivity.this, "服务器连接异常，请重试");
				
			} else if (msg.what == 2) {
				Util.ShowToast(MyLoginActivity.this, "输入的用户名或密码有问题，请重来");
				System.out.println("密码错误");
			} else if (msg.what == 3) {
				Util.ShowToast(context, "抛异常");
			} else if (msg.what == 4) {
				Intent intent = new Intent(MyLoginActivity.this,
						MycenterHomeActivity.class);
				intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
				System.out.println(code+messagetxt);
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

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (validate()) {// 判断验证是不是成功了
			 login();

		}
	}

	/* 验证输入的用户名和密码对不对。 */
	private boolean validate() {
		String id = login_ID.getText().toString();
		if (id.equals("")) {
			Util.ShowToast(MyLoginActivity.this, R.string.login_id_not_null);//账号不能空
			return false;
		}else{
			if(Util.isMobileNO(id) || Util.isEmail(id)){		
				String pwd = login_password.getText().toString();
				if (pwd.equals("")) {
					Util.ShowToast(MyLoginActivity.this, R.string.login_password_not_null);//密码不能空
					return false;
				}else{
					return true;
				}
			}else {
				Util.ShowToast(MyLoginActivity.this, R.string.login_format_error);	//格式错误
				return false;
			}
		}
		
		
		
	}// validate

	/*
	 * 业务方法，登录业务
	 */

/*	private boolean login() { // 取得用户输入的账号和密码

		String id = login_ID.getText().toString();
		String pwd = login_password.getText().toString();
		Send send = new Send(MyLoginActivity.this);
		Base result = send.getLogin(id, pwd);
		if (result != null && result.equals("1")) {
			return true;
		} else {
			return false;
		}
	}// login
*/	 

	/*private void login() {
		final String id = login_ID.getText().toString();
		final String pwd = login_password.getText().toString();
		new Thread(){
			public void run(){
				try{
					Send send = new Send(MyLoginActivity.this);
					Base result = send.getLogin(id, pwd);
					Log.i("账号", id);
					if (result != null && result.equals("1")) {
						System.out.println("正确");  
						//Util.ShowToast(MyLoginActivity.this, "登陆成功");
						Intent intent = new Intent(MyLoginActivity.this,
								MycenterHomeActivity.class);
						startActivity(intent);
						
					} else {
						System.out.println("错误");  
						//Util.ShowToast(MyLoginActivity.this, "输入的用户名或密码有问题，请重来");
					}
				}catch(Exception e){
					System.out.println("异常" + e);  
					e.printStackTrace();  
				}
			}
			}.start();
		

	}*/
	
	
	private void login() {
		final String id = login_ID.getText().toString();
		final String pwd = login_password.getText().toString();
		new Thread() {
			public void run() {
				try {
					/* 登录 */
					String url = ServiceUrl.Login_Url_username + id
							+ ServiceUrl.Login_Url_password + pwd;
					Log.i("地址", url);
					String jsonStr = GetHttp.sendGet(url);
					JSONObject loginjson =new JSONObject(jsonStr);
					if (loginjson.has("result")) {
						String result = loginjson.getString("result");
						if (!result.equals("true")) {
							errormsg = "请求出错";
							Message message = new Message();
							message.what = 2;
							LoginMessageHandler.sendMessage(message);

						} else {
							code = loginjson.getString("code");
							messagetxt = loginjson.getString("message");
							JSONObject data = loginjson.getJSONObject("data");
							Message msg = new Message();
							msg.what = 4;
							LoginMessageHandler.sendMessage(msg);

						}

					} else {
						Message message = new Message();
						message.what = 1;
						LoginMessageHandler.sendMessage(message);
					}

				} catch (Exception e) {
					Message message = new Message();
					message.what = 3;
					LoginMessageHandler.sendMessage(message);
				}

			}
		}.start();

	}
}
