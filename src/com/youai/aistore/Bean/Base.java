package com.youai.aistore.Bean;

/**
 * 基础类
 * 
 * @author Qzr
 * 
 */
public class Base {
	private int code;// 返回的状态码
	private String msg;// 返回的信息

	// private String counts;//记录总数
	// private String pagecount;//

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
