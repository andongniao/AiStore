package com.youai.aistore.Bean;

import java.util.ArrayList;

public class OrderDetailsBean extends Base {
	private int pay_status;
	private String order_sn;
	private String order_zt;
	private String consignee;
	private String address;
	private String tel;
	private String formated_add_time;
	private String formated_goods_amount;
	private String formated_shipping_fee;
	private ArrayList<Goods> goods;

	public int getPay_status() {
		return pay_status;
	}

	public void setPay_status(int pay_status) {
		this.pay_status = pay_status;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public String getOrder_zt() {
		return order_zt;
	}

	public void setOrder_zt(String order_zt) {
		this.order_zt = order_zt;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFormated_add_time() {
		return formated_add_time;
	}

	public void setFormated_add_time(String formated_add_time) {
		this.formated_add_time = formated_add_time;
	}

	public String getFormated_goods_amount() {
		return formated_goods_amount;
	}

	public void setFormated_goods_amount(String formated_goods_amount) {
		this.formated_goods_amount = formated_goods_amount;
	}

	public String getFormated_shipping_fee() {
		return formated_shipping_fee;
	}

	public void setFormated_shipping_fee(String formated_shipping_fee) {
		this.formated_shipping_fee = formated_shipping_fee;
	}

	public ArrayList<Goods> getGoods() {
		return goods;
	}

	public void setGoods(ArrayList<Goods> goods) {
		this.goods = goods;
	}

	public class Goods {
		private String goods_name;
		private String goods_thumb;
		private String goods_price;
		private String goods_number;

		public String getGoods_name() {
			return goods_name;
		}

		public void setGoods_name(String goods_name) {
			this.goods_name = goods_name;
		}

		public String getGoods_thumb() {
			return goods_thumb;
		}

		public void setGoods_thumb(String goods_thumb) {
			this.goods_thumb = goods_thumb;
		}

		public String getGoods_price() {
			return goods_price;
		}

		public void setGoods_price(String goods_price) {
			this.goods_price = goods_price;
		}

		public String getGoods_number() {
			return goods_number;
		}

		public void setGoods_number(String goods_number) {
			this.goods_number = goods_number;
		}

	}

}
