package com.youai.aistore.Mycenter;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
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
			/*if (login()) {// �жϵ�½�ǲ��ǳɹ���
				Util.ShowToast(MyLoginActivity.this, "��½�ɹ�");
				Intent intent = new Intent(MyLoginActivity.this,
						MycenterHomeActivity.class);
				startActivity(intent);
			} else {
				Util.ShowToast(MyLoginActivity.this, "������û��������������⣬������");
			}
*/
		}
	}

	/* ��֤������û���������Բ��ԡ� */
	private boolean validate() {
		String id = login_ID.getText().toString();
		if (id.equals("")) {
			Util.ShowToast(MyLoginActivity.this, "�û�����������");
			return false;
		}
		String pwd = login_password.getText().toString();
		if (pwd.equals("")) {
			Util.ShowToast(MyLoginActivity.this, "�����������");
			return false;
		}
		return true;
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

	private void login() {
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
		

	}
}
