package com.youai.aistore.Bean;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ListShopCartBean extends Base implements Serializable {
	private ArrayList<ShopCartBean> list;
	private String count_price;

	public ArrayList<ShopCartBean> getList() {
		return list;
	}

	public void setList(ArrayList<ShopCartBean> list) {
		this.list = list;
	}

	public String getCount_price() {
		return count_price;
	}

	public void setCount_price(String count_price) {
		this.count_price = count_price;
	}

}
