package com.youai.aistore.Bean;

import java.util.ArrayList;

/**
 * 全部订单实体
 * 
 * @author Qzr
 * 
 */
public class ListOrderBean extends Base {
	private ArrayList<OrderBean> list;

	public ArrayList<OrderBean> getList() {
		return list;
	}

	public void setList(ArrayList<OrderBean> list) {
		this.list = list;
	}

	public class OrderBean {
		private String order_id;
		private String order_sn;
		private String order_time;
		private String order_status;
		private String pay_name;

		public String getOrder_id() {
			return order_id;
		}

		public void setOrder_id(String order_id) {
			this.order_id = order_id;
		}

		public String getOrder_sn() {
			return order_sn;
		}

		public void setOrder_sn(String order_sn) {
			this.order_sn = order_sn;
		}

		public String getOrder_time() {
			return order_time;
		}

		public void setOrder_time(String order_time) {
			this.order_time = order_time;
		}

		public String getOrder_status() {
			return order_status;
		}

		public void setOrder_status(String order_status) {
			this.order_status = order_status;
		}

		public String getPay_name() {
			return pay_name;
		}

		public void setPay_name(String pay_name) {
			this.pay_name = pay_name;
		}

	}
}
