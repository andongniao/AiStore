package com.youai.aistore.ShopCart;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.ExampleActivity;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.WelcomeActivity;
import com.youai.aistore.Order.OrderActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/**
 * 收货人信息界面
 * @author Qzr
 *
 */
public class ConsigneeInfoActivity extends BaseActivity implements OnClickListener{
	private EditText consigneeet,numberet,addresset;
	private Button savebtn;
	private String consignee,number,address;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitleTxt(R.string.consignee_info);
		setContentXml(R.layout.consignee_info);
		setTopLeftBackground(R.drawable.btn_back);
		init();
	}

	private void init() {
		context = this;
		consigneeet = (EditText) findViewById(R.id.consignee_consignee_et);
		consigneeet.setOnClickListener(this);
		numberet = (EditText) findViewById(R.id.consignee_number_et);
		numberet.setOnClickListener(this);
		addresset = (EditText) findViewById(R.id.consignee_address_et);
		addresset.setOnClickListener(this);
		savebtn =  (Button) findViewById(R.id.consignee_save_btn);
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
			if(Util.IsNull(consignee)){
				if(Util.IsNull(number)){
					if(Util.isMobileNO(number)){
						if(Util.IsNull(address)){
							Intent intent = new Intent(ConsigneeInfoActivity.this,OrderActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.putExtra("consignee", consignee);
							intent.putExtra("number", number);
							intent.putExtra("address", address);
							startActivity(intent);
							
							
						}else{
							Util.ShowToast(context, R.string.consignee_address_can_not_null);
						}
						
					}else{
						Util.ShowToast(context, R.string.consignee_check_number_type);
					}
				}else{
					Util.ShowToast(context, R.string.consignee_number_can_not_null);
				}
			}else{
				Util.ShowToast(context, R.string.consignee_consignee_can_not_null);
			}
			
			break;

		}
	}
}
