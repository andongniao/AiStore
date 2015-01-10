package com.youai.aistore.Mycenter;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.Base;
import com.youai.aistore.Bean.UserBean;
import com.youai.aistore.NetInterface.GetHttp;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.NetInterface.ServiceUrl;

/**
 * ע�����
 * 
 * @author zy
 * 
 */
public class MyRegistActivity extends BaseActivity implements OnClickListener {
	private EditText regist_ID, regist_password, regist_repassword;
	private Button regist_btn;
	Context context;
	String errormsg = "";
	String code, messagetxt;
	Handler LoginMessageHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				Intent intent = new Intent(MyRegistActivity.this,
						MyLoginActivity.class);
				intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			} else if (msg.what == 2) {
				Util.ShowToast(MyRegistActivity.this, "ע��ʧ��");

			}

		}
	};

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		// ע�������⣻��ʾ
		setTitleTxt(R.string.my_regist_tv);
		setContentXml(R.layout.my_regist);
		// ע���˺�ID������
		regist_ID = (EditText) findViewById(R.id.my_regist_ID_et);
		regist_password = (EditText) findViewById(R.id.my_regist_password_et);
		regist_repassword = (EditText) findViewById(R.id.my_regist_repassword_et);

		regist_btn = (Button) findViewById(R.id.my_regist_btn);
		regist_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (validate()) {
			addUser();
			/*
			 * if (addUser()) { Util.ShowToast(MyRegistActivity.this, "ע��ɹ�");
			 * Intent intent = new Intent(MyRegistActivity.this,
			 * MyLoginActivity.class); startActivity(intent); } else {
			 * Util.ShowToast(MyRegistActivity.this, "ע��ʧ��"); }
			 */
		}
	}

	/* ��֤������û���������Բ��ԡ� */
	private boolean validate() {
		String id = regist_ID.getText().toString();
		if (id.equals("")) {
			Util.ShowToast(MyRegistActivity.this, R.string.login_id_not_null);// �˺Ų��ܿ�
			return false;
		} else {
			if (Util.isMobileNO(id) || Util.isEmail(id)) {
				String pwd = regist_password.getText().toString();
				if (pwd.equals("")) {
					Util.ShowToast(MyRegistActivity.this,
							R.string.login_password_not_null);// ���벻�ܿ�
					return false;
				}
				String repwd = regist_repassword.getText().toString();
				if (repwd.equals(pwd)) {
					if (Util.ispassword(pwd)) {
						return true;
					} else {
						Util.ShowToast(MyRegistActivity.this,
								R.string.my_regist_password_et);// ��������4λ
						return false;
					}
				} else {
					if (repwd.equals("")) {
						Util.ShowToast(MyRegistActivity.this,
								R.string.regist_repassword_not_null);// ȷ�����벻��Ϊ��

						return false;
					} else {
						Util.ShowToast(MyRegistActivity.this,
								R.string.regist_repasswors_error);// ȷ�������룬���벻һ��

						return false;
					}

				}
			} else {
				Util.ShowToast(MyRegistActivity.this,
						R.string.regist_format_error); // ��ʽ����
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
				if (result.getUser_id() != null
						&& !result.getUser_id().equals("200")) {

					Message msg = new Message();
					msg.what = 1;
					LoginMessageHandler.sendMessage(msg);

				} else {
					Message message = new Message();
					message.what = 2;
					LoginMessageHandler.sendMessage(message);

				}

			}
		}.start();

	}
}
