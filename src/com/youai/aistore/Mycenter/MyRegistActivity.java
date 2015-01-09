package com.youai.aistore.Mycenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.Base;
import com.youai.aistore.NetInterface.Send;

/**
 * ע�����
 * 
 * @author zy
 * 
 */
public class MyRegistActivity extends BaseActivity implements OnClickListener {
	private EditText regist_ID, regist_password, regist_repassword;
	private Button regist_btn;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		// ��½������⣻��ʾ
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
			if (addUser()) {
				Util.ShowToast(MyRegistActivity.this, "ע��ɹ�");
				Intent intent = new Intent(MyRegistActivity.this,
						MyLoginActivity.class);
				startActivity(intent);
			} else {
				Util.ShowToast(MyRegistActivity.this, "ע��ʧ��");
			}

		}
	}

	/* ��֤������û���������Բ��ԡ� */
	private boolean validate() {
		String id = regist_ID.getText().toString();
		if (id.equals("")) {
			Util.ShowToast(MyRegistActivity.this, "�û�����������");
			return false;
		}
		String pwd = regist_password.getText().toString();
		if (pwd.equals("")) {
			Util.ShowToast(MyRegistActivity.this, "�����������");
			return false;
		}
		String repwd = regist_repassword.getText().toString();
		if (repwd.equals(pwd)) {

		} else {
			if (repwd.equals("")) {
				Util.ShowToast(MyRegistActivity.this, "ȷ�����벻��Ϊ��");

				return false;
			} else {
				Util.ShowToast(MyRegistActivity.this, "ȷ�����������벻һ�£�����������");

				return false;
			}

		}
		return true;
	}// validate

	/* ҵ�񷽷�����¼ҵ�� */
	private boolean addUser() {
		String id = regist_ID.getText().toString();
		String pwd = regist_password.getText().toString();
		Send send = new Send(MyRegistActivity.this);
		Base result = send.regist(id, pwd);
		if (result != null && result.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

}
