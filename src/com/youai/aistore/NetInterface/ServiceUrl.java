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
	//һ������
	static String Product_fclass_Url_frist = BaseUrl+"?module=category&category_id=";
	//��������
	static String Product_fclass_Url_two_head = BaseUrl+"?module=sub_category&sub_cate_id=";
	static String Product_fclass_Url_two_center = "&sort=";
	static String Product_fclass_Url_two_foot= "&page=";
	//���빺�ﳵ
	static String Product_AddShopCart_Url_head = BaseUrl+"?module=add_to_cart&goods_id=";
	static String Product_AddShopCart_Url_center = "&number=";
	static String Product_AddShopCart_Url_foot = "&session_id=";
	//��ȡ���ﳵ�б� 
	static String Product_GetShopCartList_Url_head = BaseUrl+"?module=cart_list&session_id=";
	static String Product_GetShopCartList_Url_foot = "&user_id=";
	

}
