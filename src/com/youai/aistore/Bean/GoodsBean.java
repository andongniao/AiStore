package com.youai.aistore.Bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 单品 实体
 * 
 * @author Qzr
 * 
 */
@SuppressWarnings("serial")
public class GoodsBean extends Base implements Serializable {
	private int id;
	private int type;
	private String title;
	private String picurl;
	private String market_price;
	private String shop_price;
	private String click;
	private String good_desc;
	private String comments_num;
	private ArrayList<String> picurls;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getMarket_price() {
		return market_price;
	}

	public void setMarket_price(String market_price) {
		this.market_price = market_price;
	}

	public String getShop_price() {
		return shop_price;
	}

	public void setShop_price(String shop_price) {
		this.shop_price = shop_price;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public String getGood_desc() {
		return good_desc;
	}

	public void setGood_desc(String good_desc) {
		this.good_desc = good_desc;
	}

	public String getComments_num() {
		return comments_num;
	}

	public void setComments_num(String comments_num) {
		this.comments_num = comments_num;
	}

	public ArrayList<String> getPicurls() {
		return picurls;
	}

	public void setPicurls(ArrayList<String> picurls) {
		this.picurls = picurls;
	}

}
