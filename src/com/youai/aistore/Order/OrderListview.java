package com.youai.aistore.Order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class OrderListview extends ListView {

	public OrderListview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public OrderListview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OrderListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	/**
	 * ��д�÷������ﵽʹListView��ӦScrollView��Ч��
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
