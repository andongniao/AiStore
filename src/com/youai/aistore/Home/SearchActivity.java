package com.youai.aistore.Home;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.youai.aistore.R;

/**
 * 
 * @author Qzr
 *
 */
public class SearchActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.in_rightleft, R.anim.out_rightleft);
		setContentView(R.layout.search_view);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

			finish();

			// ¸²¸ÇÄ¬ÈÏactivityÌøÇ¨ÒÆ×ª±ä»­

			overridePendingTransition(0, 0);
		}

		return true;
	}
}
