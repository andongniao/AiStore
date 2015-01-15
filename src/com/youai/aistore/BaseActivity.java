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
 * 基本类
 * 
 * @author Qzr
 * 
 */
public abstract class BaseActivity extends Activity {
	
	protected int activityCloseEnterAnimation;

	protected int activityCloseExitAnimation;
	public TextView titleTv;// 标题控件
	public TextView topLeftTv;// 左上 控件（返回）
	public TextView topRightTv;// 右上 控件（返回）
	private LinearLayout baseContentLayout;// 添加内容控件
	public RelativeLayout baseTopLayout, toplefttl;// 顶部layout

	private BaseLeftClickListener leftClickListener;// 左上 点击监听

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
	 * base页面 左上角点击效果监听
	 * 
	 * @param clickListener
	 *            监听
	 */
	public void setTopLeftClickListener(BaseLeftClickListener clickListener) {
		this.leftClickListener = clickListener;
	}

	/**
	 * 设置左上角背景
	 * 
	 * @param drawableid
	 *            资源id
	 */
	public void setTopLeftBackground(int drawableId) {
		topLeftTv.setBackgroundResource(drawableId);
	}

	/**
	 * 设置左上角背景
	 * 
	 * @param drawable
	 *            图片
	 */
	@SuppressWarnings("deprecation")
	public void setTopLeftBackground(Drawable drawable) {
		topLeftTv.setBackgroundDrawable(drawable);
	}

	/**
	 * 隐藏左上布局
	 */
	public void goneTopLeft() {
		topLeftTv.setVisibility(View.GONE);
	}

	/**
	 * 隐藏左上布局
	 */
	public void visibleTopLeft() {
		topLeftTv.setVisibility(View.VISIBLE);
	}

	/**
	 * 添加子页面布局
	 * 
	 * @param contentViewId
	 *            子布局
	 */
	public void setContentXml(int contentViewId) {
		addViewXML(baseContentLayout, contentViewId, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	/**
	 * 添加子页面view
	 * 
	 * @param childView
	 *            子 View
	 */
	public void setContentChildView(View childView) {
		baseContentLayout.addView(childView, new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/**
	 * 设置标题
	 * 
	 * @param textId
	 *            标题资源id
	 */
	public void setTitleTxt(int textId) {
		titleTv.setText(textId);
	}

	/**
	 * 设置标题
	 * 
	 * @param text
	 *            标题资源
	 */
	public void setTitleTxt(String text) {
		titleTv.setText(text);
	}

	/**
	 * 隐藏title
	 */
	public void goneTitle() {
		titleTv.setVisibility(View.GONE);
	}

	/**
	 * 显示title
	 */
	public void visibleTitle() {
		titleTv.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置顶部背景
	 * 
	 * @param drawableid
	 *            资源id
	 */
	public void setTopTitleBackground(int drawableId) {
		titleTv.setBackgroundResource(drawableId);
	}

	/**
	 * 设置顶部背景
	 * 
	 * @param drawable
	 *            图片
	 */
	@SuppressWarnings("deprecation")
	public void setTopTitleBackground(Drawable drawable) {
		titleTv.setBackgroundDrawable(drawable);
	}

	/**
	 * 添加view
	 * 
	 * @param group
	 *            父容器
	 * @param id
	 *            子view id
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 */
	public void addViewXML(ViewGroup group, int id, int width, int height) {
		View contentView = View.inflate(this, id, null);
		group.addView(contentView, width, height);
	}

	/**
	 * 右上按钮隐藏
	 */
	public void topRightGone() {
		topRightTv.setVisibility(View.GONE);
	}

	/**
	 * 右上按钮显示
	 */
	public void topRightVisible() {
		topRightTv.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置右上角背景
	 * 
	 * @param drawable
	 *            图片
	 */
	@SuppressWarnings("deprecation")
	public void setTopRightBackground(Drawable drawable) {
		topRightTv.setBackgroundDrawable(drawable);
	}

	/**
	 * 设置右上角背景
	 * 
	 * @param drawableid
	 *            资源id
	 */
	public void setTopRightBackground(int drawableId) {
		topRightTv.setBackgroundResource(drawableId);
	}

	/**
	 * 获取右上角view
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
