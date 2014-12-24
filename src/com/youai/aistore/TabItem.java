package com.youai.aistore;

import android.content.Intent;

public class TabItem {
	private String title;		// tab item title
	private int icon;			// tab item icon
	private int bg;			// tab item background
	private Intent intent;	// tab item intent
	
	public TabItem(int bg, Intent intent) {
		super();
		this.title = title;
		this.icon = icon;
		this.bg = bg;
		this.intent = intent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getBg() {
		return bg;
	}

	public void setBg(int bg) {
		this.bg = bg;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}
}
