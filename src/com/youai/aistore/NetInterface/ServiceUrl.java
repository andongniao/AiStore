package com.youai.aistore.NetInterface;

public class ServiceUrl {
	//����url
	private static String BaseUrl = "http://www.aiai.cn/services/api.php";
	//��ҳ
	static String HomeUrl = BaseUrl+"?module=home&";
	//��Ʒ����
	static String Product_Details_Url = BaseUrl+"?module=goods&id=";
	//��Ʒ����
	static String Product_comments_Url_head = BaseUrl+"?module=comment&goods_id=";
	static String Product_comments_Url_foot = "&action=list&page=";
	

}
