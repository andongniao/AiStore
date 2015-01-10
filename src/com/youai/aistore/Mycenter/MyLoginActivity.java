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
 * ��½����
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
				Util.ShowToast(MyLoginActivity.this, "�����������쳣��������");
				
			} else if (msg.what == 2) {
				Util.ShowToast(MyLoginActivity.this, "������û��������������⣬������");
				System.out.println("�������");
			} else if (msg.what == 3) {
				Util.ShowToast(context, "���쳣");
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
		// ��½������⣻��ʾ
		setTitleTxt(R.string.my_login_tv);
		// �����ļ�
		setContentXml(R.layout.my_login);
		// ��½�˺�ID������
		login_ID = (EditText) findViewById(R.id.my_login_ID_et);
		login_password = (EditText) findViewById(R.id.my_login_password_et);

		login_btn = (Button) findViewById(R.id.my_login_btn);
		login_btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (validate()) {// �ж���֤�ǲ��ǳɹ���
			 login();

		}
	}

	/* ��֤������û���������Բ��ԡ� */
	private boolean validate() {
		String id = login_ID.getText().toString();
		if (id.equals("")) {
			Util.ShowToast(MyLoginActivity.this, R.string.login_id_not_null);//�˺Ų��ܿ�
			return false;
		}else{
			if(Util.isMobileNO(id) || Util.isEmail(id)){		
				String pwd = login_password.getText().toString();
				if (pwd.equals("")) {
					Util.ShowToast(MyLoginActivity.this, R.string.login_password_not_null);//���벻�ܿ�
					return false;
				}else{
					return true;
				}
			}else {
				Util.ShowToast(MyLoginActivity.this, R.string.login_format_error);	//��ʽ����
				return false;
			}
		}
		
		
		
	}// validate

	/*
	 * ҵ�񷽷�����¼ҵ��
	 */

/*	private boolean login() { // ȡ���û�������˺ź�����

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
					Log.i("�˺�", id);
					if (result != null && result.equals("1")) {
						System.out.println("��ȷ");  
						//Util.ShowToast(MyLoginActivity.this, "��½�ɹ�");
						Intent intent = new Intent(MyLoginActivity.this,
								MycenterHomeActivity.class);
						startActivity(intent);
						
					} else {
						System.out.println("����");  
						//Util.ShowToast(MyLoginActivity.this, "������û��������������⣬������");
					}
				}catch(Exception e){
					System.out.println("�쳣" + e);  
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
					/* ��¼ */
					String url = ServiceUrl.Login_Url_username + id
							+ ServiceUrl.Login_Url_password + pwd;
					Log.i("��ַ", url);
					String jsonStr = GetHttp.sendGet(url);
					JSONObject loginjson =new JSONObject(jsonStr);
					if (loginjson.has("result")) {
						String result = loginjson.getString("result");
						if (!result.equals("true")) {
							errormsg = "�������";
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
