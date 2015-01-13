package com.youai.aistore.ShopCart;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.Base;
import com.youai.aistore.Bean.ConsigneeBean;
import com.youai.aistore.Bean.ListShopCartBean;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.Order.OrderActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 收货人信息界面
 * 
 * @author Qzr
 * 
 */
public class ConsigneeInfoActivity extends BaseActivity implements
		OnClickListener {
	private EditText consigneeet, numberet, addresset;
	private Button savebtn;
	private String consignee, number, address;
	private Context context;
	private MyTask myTask;
	private ConsigneeBean bean;
	private Base b;
	private ListShopCartBean listbean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitleTxt(R.string.consignee_info);
		setContentXml(R.layout.consignee_info);
		setTopLeftBackground(R.drawable.btn_back);
		init();
		if (Util.detect(context)) {
			myTask = new MyTask(1);
			myTask.execute("");
		} else {
			Util.ShowToast(context, R.string.net_work_is_error);
		}

	}

	private void init() {
		context = this;
		listbean = (ListShopCartBean) getIntent().getExtras().get("list");
		consigneeet = (EditText) findViewById(R.id.consignee_consignee_et);
		consigneeet.setOnClickListener(this);
		numberet = (EditText) findViewById(R.id.consignee_number_et);
		numberet.setOnClickListener(this);
		addresset = (EditText) findViewById(R.id.consignee_address_et);
		addresset.setOnClickListener(this);
		savebtn = (Button) findViewById(R.id.consignee_save_btn);
		savebtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.consignee_consignee_et:

			break;
		case R.id.consignee_number_et:

			break;
		case R.id.consignee_address_et:

			break;
		case R.id.consignee_save_btn:
			consignee = consigneeet.getText().toString().trim();
			number = numberet.getText().toString().trim();
			address = addresset.getText().toString().trim();
			if (Util.IsNull(consignee)) {
				if (Util.IsNull(number)) {
					if (Util.isMobileNO(number)) {
						if (Util.IsNull(address)) {
							if (Util.detect(context)) {
								myTask = new MyTask(2);
								myTask.execute("");
							} else {
								Util.ShowToast(context,
										R.string.net_work_is_error);
							}
						} else {
							Util.ShowToast(context,
									R.string.consignee_address_can_not_null);
						}

					} else {
						Util.ShowToast(context,
								R.string.consignee_check_number_type);
					}
				} else {
					Util.ShowToast(context,
							R.string.consignee_number_can_not_null);
				}
			} else {
				Util.ShowToast(context,
						R.string.consignee_consignee_can_not_null);
			}

			break;

		}
	}

	private class MyTask extends AsyncTask<Object, Object, Object> {
		private int statu;

		public MyTask(int statu) {
			this.statu = statu;
		}

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			Util.startProgressDialog(context);
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected Object doInBackground(Object... params) {
			try {
				if (statu == 1) {
					Send s = new Send(context);
					String userid = MyApplication.UserId;
					// String userid = "188";
					bean = s.getConsigneeInfo(userid);
					return bean;
				} else {
					Send s = new Send(context);
					String userid = MyApplication.UserId;
					// String userid = "188";
					b = s.saveConsigneeInfo(userid, consignee, number, address);
					return b;
				}
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
			if (statu == 1) {
				bean = (ConsigneeBean) result;
				if (bean != null) {
					if (bean.getCode() == 200) {
						consigneeet.setText(bean.getConsignee());
						addresset.setText(bean.getAddress());
						numberet.setText(bean.getTel());
					} else {
						Util.ShowToast(context, bean.getMsg());
					}
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			} else {
				if (b != null) {
					if (b.getCode() == 200) {
						Intent intent = new Intent(ConsigneeInfoActivity.this,
								OrderActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("consignee", consignee);
						intent.putExtra("number", number);
						intent.putExtra("address", address);
						intent.putExtra("list", listbean);
						startActivity(intent);
					} else {
						Util.ShowToast(context, b.getMsg());
					}
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			// Util.stopProgressDialog();
		}

	}
}
