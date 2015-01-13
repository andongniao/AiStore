package com.youai.aistore.Bean;

/**
 * 收货人信息实体
 * 
 * @author Qzr
 * 
 */
public class ConsigneeBean extends Base {
	private String consignee;
	private String address;
	private String tel;

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

}
