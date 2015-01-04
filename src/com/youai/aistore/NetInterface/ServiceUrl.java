package com.youai.aistore.NetInterface;

public class ServiceUrl {
	//基本url
	private static String BaseUrl = "http://www.aiai.cn/services/api.php";
	//首页
	static String HomeUrl = BaseUrl+"?module=home&";
	//产品详情
	static String Product_Details_Url = BaseUrl+"?module=goods&id=";
	//产品评论
	static String Product_comments_Url_head = BaseUrl+"?module=comment&goods_id=";
	static String Product_comments_Url_foot = "&action=list&page=";
	

}
