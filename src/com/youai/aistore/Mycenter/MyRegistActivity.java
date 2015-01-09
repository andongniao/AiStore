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
 * 注册界面
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
		// 登陆界面标题；显示
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
		if (validate()) {
			if (addUser()) {
				Util.ShowToast(MyRegistActivity.this, "注册成功");
				Intent intent = new Intent(MyRegistActivity.this,
						MyLoginActivity.class);
				startActivity(intent);
			} else {
				Util.ShowToast(MyRegistActivity.this, "注册失败");
			}

		}
	}

	/* 验证输入的用户名和密码对不对。 */
	private boolean validate() {
		String id = regist_ID.getText().toString();
		if (id.equals("")) {
			Util.ShowToast(MyRegistActivity.this, "用户名必须输入");
			return false;
		}
		String pwd = regist_password.getText().toString();
		if (pwd.equals("")) {
			Util.ShowToast(MyRegistActivity.this, "密码必须输入");
			return false;
		}
		String repwd = regist_repassword.getText().toString();
		if (repwd.equals(pwd)) {

		} else {
			if (repwd.equals("")) {
				Util.ShowToast(MyRegistActivity.this, "确认密码不能为空");

				return false;
			} else {
				Util.ShowToast(MyRegistActivity.this, "确认密码与密码不一致，请重新输入");

				return false;
			}

		}
		return true;
	}// validate

	/* 业务方法，登录业务 */
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
