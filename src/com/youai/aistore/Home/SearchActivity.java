package com.youai.aistore.Home;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.youai.aistore.R;
import com.youai.aistore.Bean.GoodsBean;

/**
 * ËÑË÷½çÃæ
 * 
 * @author Qzr
 * 
 */
public class SearchActivity extends Activity implements OnClickListener {
	private Button searchbtn;
	private EditText searchet;
	private ArrayList<GoodsBean> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_view);
		init();
	}

	private void init() {
		searchbtn = (Button) findViewById(R.id.search_view_bt);
		searchet = (EditText) findViewById(R.id.search_view_et);
		searchbtn.setOnClickListener(this);
		searchet.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			finish();

			// ¸²¸ÇÄ¬ÈÏactivityÌøÇ¨ÒÆ×ª±ä»­

			overridePendingTransition(0, 0);
		}

		return true;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.search_view_bt:
			Intent intent = new Intent(SearchActivity.this,
					SearchResultActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			/*************************************/
			list = new ArrayList<GoodsBean>();
			GoodsBean a = new GoodsBean();
			GoodsBean b = new GoodsBean();
			GoodsBean c = new GoodsBean();
			GoodsBean d = new GoodsBean();
			list.add(a);
			list.add(b);
			list.add(c);
			list.add(d);
			intent.putExtra("list", list);
			startActivity(intent);
			break;

		}
	}
}
