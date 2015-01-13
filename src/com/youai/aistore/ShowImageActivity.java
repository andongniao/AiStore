package com.youai.aistore;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * µã»÷ÏÔÊ¾´óÍ¼
 * 
 * @author Qzr
 * 
 */

public class ShowImageActivity extends Activity {

	private Bitmap bp = null;
	private ImageView imageview;
	private RelativeLayout layout;
	private float scaleWidth;
	@SuppressWarnings("unused")
	private float scaleHeight;
	@SuppressWarnings("unused")
	private int h;
	@SuppressWarnings("unused")
	private boolean num = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showimage);

		Display display = getWindowManager().getDefaultDisplay();
		layout = (RelativeLayout) findViewById(R.id.show_rl);
		imageview = (ImageView) findViewById(R.id.show_imageview);
		String path = getIntent().getStringExtra("path");
		bp = BitmapFactory.decodeFile(path);// Resource(getResources(),R.drawable.aixioahua);
		if (bp != null) {
			int width = bp.getWidth();
			int height = bp.getHeight();
			@SuppressWarnings("deprecation")
			int w = display.getWidth();
			@SuppressWarnings("deprecation")
			int h = display.getHeight();
			scaleWidth = ((float) w) / width;
			scaleHeight = ((float) h) / height;
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleWidth);
			Bitmap newBitmap = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(),
					bp.getHeight(), matrix, true);
			imageview.setImageBitmap(newBitmap);
		}
		// imageview.setImageBitmap(bp);
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}
