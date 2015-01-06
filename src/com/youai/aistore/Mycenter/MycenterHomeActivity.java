package com.youai.aistore.Mycenter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.BaseActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.View.CircleImageView;

public class MycenterHomeActivity extends BaseActivity implements OnClickListener{
	private CircleImageView headeriv;
	private LinearLayout dingdan_ll,youhui_ll,kefu_ll,set_ll,call_ll,sms_ll,show_ll;
	private boolean isshowing;
	private Dialog alertDialog;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		setTopLeftBackground(R.drawable.btn_search_navigation_back);
		setTopTitleBackground(R.drawable.logo);
		setContentXml(R.layout.mycenter_home_view);
		init();
	}
	private void init() {
		isshowing = false;
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
		
		
		String url = "http://img5.imgtn.bdimg.com/it/u=3292851460,915918973&fm=116&gp=0.jpg";
		ImageLoader.getInstance().displayImage(url, headeriv);
		
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.mycenter_home_header_iv:
			
			break;
		case R.id.mycenter_home_dingdan_ll:
			
			break;
		case R.id.mycenter_home_youhui_ll:
			
			break;
		case R.id.mycenter_home_kefu_ll:
			if(isshowing){
				show_ll.setVisibility(View.GONE);
				isshowing = false;
			}else{
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

	private void ShowDialog(final int statu){
		int index,title;
		if(statu == 1){
			title = R.string.product_iscall;
			index = R.string.product_cll_tell;
		}else{
			title = R.string.product_issms;
			index = R.string.product_sms_send;

		}
		alertDialog = new AlertDialog.Builder(this).
				setTitle(title).
				setIcon(null).
				setPositiveButton(R.string.product_cancle, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).
				setNegativeButton(index, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(statu==1){
							Intent phoneIntent = new Intent("android.intent.action.CALL",
									Uri.parse("tel:" + MyApplication.callnumber));
							startActivity(phoneIntent); 
						}else{
							Uri uri=Uri.parse("smsto:"+MyApplication.smsnumber);
							Intent ii=new Intent(Intent.ACTION_SENDTO,uri);
							ii.putExtra("sms_body", "");
							startActivity(ii);
						}
					}
				}).
				create();
		alertDialog.show();
	}
}
