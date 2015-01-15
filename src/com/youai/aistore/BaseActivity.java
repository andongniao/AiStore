package com.youai.aistore;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/**
 * ������
 * 
 * @author Qzr
 * 
 */
public abstract class BaseActivity extends Activity {
	
	protected int activityCloseEnterAnimation;

	protected int activityCloseExitAnimation;
	public TextView titleTv;// ����ؼ�
	public TextView topLeftTv;// ���� �ؼ������أ�
	public TextView topRightTv;// ���� �ؼ������أ�
	private LinearLayout baseContentLayout;// ������ݿؼ�
	public RelativeLayout baseTopLayout, toplefttl;// ����layout

	private BaseLeftClickListener leftClickListener;// ���� �������

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);      
		activityStyle.recycle();
		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);

		activityStyle.recycle();
		setContentView(R.layout.base_layout);
		baseTopLayout = (RelativeLayout) findViewById(R.id.Base_Layout);
		titleTv = (TextView) findViewById(R.id.base_title_tv);
		topLeftTv = (TextView) findViewById(R.id.base_top_left_tv);
		toplefttl = (RelativeLayout) findViewById(R.id.base_top_left_tv_rl);
		toplefttl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (leftClickListener != null) {
					leftClickListener.onTopLeftClickListener();
				} else {
					finish();
				}
			}
		});
		topRightTv = (TextView) findViewById(R.id.base_top_right_tv);
		topRightTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		baseContentLayout = (LinearLayout) findViewById(R.id.base_content_layout);

	}

	/**
	 * baseҳ�� ���Ͻǵ��Ч������
	 * 
	 * @param clickListener
	 *            ����
	 */
	public void setTopLeftClickListener(BaseLeftClickListener clickListener) {
		this.leftClickListener = clickListener;
	}

	/**
	 * �������ϽǱ���
	 * 
	 * @param drawableid
	 *            ��Դid
	 */
	public void setTopLeftBackground(int drawableId) {
		topLeftTv.setBackgroundResource(drawableId);
	}

	/**
	 * �������ϽǱ���
	 * 
	 * @param drawable
	 *            ͼƬ
	 */
	@SuppressWarnings("deprecation")
	public void setTopLeftBackground(Drawable drawable) {
		topLeftTv.setBackgroundDrawable(drawable);
	}

	/**
	 * �������ϲ���
	 */
	public void goneTopLeft() {
		topLeftTv.setVisibility(View.GONE);
	}

	/**
	 * �������ϲ���
	 */
	public void visibleTopLeft() {
		topLeftTv.setVisibility(View.VISIBLE);
	}

	/**
	 * �����ҳ�沼��
	 * 
	 * @param contentViewId
	 *            �Ӳ���
	 */
	public void setContentXml(int contentViewId) {
		addViewXML(baseContentLayout, contentViewId, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	/**
	 * �����ҳ��view
	 * 
	 * @param childView
	 *            �� View
	 */
	public void setContentChildView(View childView) {
		baseContentLayout.addView(childView, new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/**
	 * ���ñ���
	 * 
	 * @param textId
	 *            ������Դid
	 */
	public void setTitleTxt(int textId) {
		titleTv.setText(textId);
	}

	/**
	 * ���ñ���
	 * 
	 * @param text
	 *            ������Դ
	 */
	public void setTitleTxt(String text) {
		titleTv.setText(text);
	}

	/**
	 * ����title
	 */
	public void goneTitle() {
		titleTv.setVisibility(View.GONE);
	}

	/**
	 * ��ʾtitle
	 */
	public void visibleTitle() {
		titleTv.setVisibility(View.VISIBLE);
	}

	/**
	 * ���ö�������
	 * 
	 * @param drawableid
	 *            ��Դid
	 */
	public void setTopTitleBackground(int drawableId) {
		titleTv.setBackgroundResource(drawableId);
	}

	/**
	 * ���ö�������
	 * 
	 * @param drawable
	 *            ͼƬ
	 */
	@SuppressWarnings("deprecation")
	public void setTopTitleBackground(Drawable drawable) {
		titleTv.setBackgroundDrawable(drawable);
	}

	/**
	 * ���view
	 * 
	 * @param group
	 *            ������
	 * @param id
	 *            ��view id
	 * @param width
	 *            ��
	 * @param height
	 *            ��
	 */
	public void addViewXML(ViewGroup group, int id, int width, int height) {
		View contentView = View.inflate(this, id, null);
		group.addView(contentView, width, height);
	}

	/**
	 * ���ϰ�ť����
	 */
	public void topRightGone() {
		topRightTv.setVisibility(View.GONE);
	}

	/**
	 * ���ϰ�ť��ʾ
	 */
	public void topRightVisible() {
		topRightTv.setVisibility(View.VISIBLE);
	}

	/**
	 * �������ϽǱ���
	 * 
	 * @param drawable
	 *            ͼƬ
	 */
	@SuppressWarnings("deprecation")
	public void setTopRightBackground(Drawable drawable) {
		topRightTv.setBackgroundDrawable(drawable);
	}

	/**
	 * �������ϽǱ���
	 * 
	 * @param drawableid
	 *            ��Դid
	 */
	public void setTopRightBackground(int drawableId) {
		topRightTv.setBackgroundResource(drawableId);
	}

	/**
	 * ��ȡ���Ͻ�view
	 * 
	 * @return
	 */
	public View getTopRightView() {
		return topRightTv;
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}

}
