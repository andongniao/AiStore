package com.youai.aistore.Mycenter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.BaseActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.Base;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.Order.AllOrderActivity;
import com.youai.aistore.View.CircleImageView;

/**
 * 个人中心首页
 * 
 * @author Qzr
 * 
 */
public class MycenterHomeActivity extends BaseActivity implements
		OnClickListener {
	private CircleImageView headeriv;
	private LinearLayout dingdan_ll, youhui_ll, kefu_ll, set_ll, call_ll,
			sms_ll, show_ll, login_ll;
	private Button login_btn, regist_btn;
	private TextView uernametv;
	private boolean isshowing,log;
	private Dialog alertDialog;
	private Context context;
	private Base bean;
	private MyTask myTask;
	private Bundle ble;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		setTopLeftBackground(R.drawable.btn_search_navigation_back);
		setTopTitleBackground(R.drawable.logo);
		setContentXml(R.layout.mycenter_home_view);
		init();
		this.ble = arg0;
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(ble);
	}

	private void init() {
		context = this;
		isshowing = false;
		log = true;
		headeriv = (CircleImageView) findViewById(R.id.mycenter_home_header_iv);
		headeriv.setOnClickListener(this);
		dingdan_ll = (LinearLayout) findViewById(R.id.mycenter_home_dingdan_ll);
		dingdan_ll.setOnClickListener(this);
		youhui_ll = (LinearLayout) findViewById(R.id.mycenter_home_youhui_ll);
		youhui_ll.setOnClickListener(this);
		kefu_ll = (LinearLayout) findViewById(R.id.mycenter_home_kefu_ll);
		kefu_ll.setOnClickListener(this);
		set_ll = (LinearLayout) findViewById(R.id.mycenter_home_set_ll);
		set_ll.setOnClickListener(this);
		call_ll = (LinearLayout) findViewById(R.id.mycenter_home_call_ll);
		call_ll.setOnClickListener(this);
		sms_ll = (LinearLayout) findViewById(R.id.mycenter_home_sms_ll);
		sms_ll.setOnClickListener(this);
		show_ll = (LinearLayout) findViewById(R.id.mycenter_home_show_ll);
		uernametv = (TextView) findViewById(R.id.mycenter_home_username_tv);
		login_ll = (LinearLayout) findViewById(R.id.mycenter_home_login_ll);
		// 登陆和注册
		login_btn = (Button) findViewById(R.id.mycenter_home_login_btn);
		login_btn.setOnClickListener(this);
		regist_btn = (Button) findViewById(R.id.mycenter_home_regist_btn);
		regist_btn.setOnClickListener(this);
		if (MyApplication.userBean != null) {
			uernametv.setText(MyApplication.userBean.getUser_name());
		}
		String url = "http://img5.imgtn.bdimg.com/it/u=3292851460,915918973&fm=116&gp=0.jpg";
		ImageLoader.getInstance().displayImage(url, headeriv);
		if (MyApplication.logined) {
			login_ll.setVisibility(View.GONE);
			uernametv.setText(MyApplication.UserName);
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.mycenter_home_header_iv:
			break;

		case R.id.mycenter_home_login_btn:
			Intent intent = new Intent(MycenterHomeActivity.this,
					MyLoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_regist_btn:
			Intent intent1 = new Intent(MycenterHomeActivity.this,
					MyRegistActivity.class);
			intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent1);
			break;
		case R.id.mycenter_home_dingdan_ll:
			Intent dd = null;
			if(MyApplication.logined){
				dd = new Intent(MycenterHomeActivity.this,AllOrderActivity.class);
			}else{
				dd = new Intent(MycenterHomeActivity.this,MyLoginActivity.class);
			}
			dd.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(dd);
			break;
		case R.id.mycenter_home_youhui_ll:

			break;
		case R.id.mycenter_home_kefu_ll:
			if (isshowing) {
				show_ll.setVisibility(View.GONE);
				isshowing = false;
			} else {
				show_ll.setVisibility(View.VISIBLE);
				isshowing = true;
			}

			break;
		case R.id.mycenter_home_set_ll:

			break;
		case R.id.mycenter_home_call_ll:
			ShowDialog(1);
			break;
		case R.id.mycenter_home_sms_ll:
			ShowDialog(2);
			break;
		}
	}

	private void ShowDialog(final int statu) {
		int index, title;
		if (statu == 1) {
			title = R.string.product_iscall;
			index = R.string.product_cll_tell;
		} else {
			title = R.string.product_issms;
			index = R.string.product_sms_send;

		}
		alertDialog = new AlertDialog.Builder(this)
				.setTitle(title)
				.setIcon(null)
				.setPositiveButton(R.string.product_cancle,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						})
				.setNegativeButton(index,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (statu == 1) {
									Intent phoneIntent = new Intent(
											"android.intent.action.CALL",
											Uri.parse("tel:"
													+ MyApplication.callnumber));
									startActivity(phoneIntent);
								} else {
									Uri uri = Uri.parse("smsto:"
											+ MyApplication.smsnumber);
									Intent ii = new Intent(
											Intent.ACTION_SENDTO, uri);
									ii.putExtra("sms_body", "");
									startActivity(ii);
								}
							}
						}).create();
		alertDialog.show();
	}
	@Override
	protected void onResume() {
		super.onResume();
		if(MyApplication.logined){
			login_ll.setVisibility(View.GONE);
			uernametv.setText(MyApplication.userBean.getUser_name());
			if(log){
			if (Util.detect(context)) {
				myTask = new MyTask();
				myTask.execute("");
			} else {
				Util.ShowToast(context, R.string.net_work_is_error);
			}
			}
		}
	}
	
	
	private class MyTask extends AsyncTask<Object, Object, Object> {
		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			Util.startProgressDialog(context);
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected Object doInBackground(Object... params) {
			try {
					Send s = new Send(context);
					String sessionid = MyApplication.SessionId;
					String userid = MyApplication.UserId;
					bean = s.updataShopcartInfo(sessionid, userid);
					return bean;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Object... progresses) {

		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(Object result) {
			Util.stopProgressDialog();
			bean = (Base) result;
			if(bean!=null){
				if(bean.getCode()==200){
					log = false;
					Util.ShowToast(context,R.string.updata_shopcart_info_success);
				}else{
					Util.ShowToast(context,bean.getMsg());
				}
			}else{
				Util.ShowToast(context, R.string.net_work_is_error);
			}

		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			// Util.stopProgressDialog();
		}

	}
	
}
