package com.youai.aistore.NetInterface;

public class ServiceUrl {
	//����url
	public static String BaseUrl = "http://www.aiai.cn/services/api.php";
	//��ҳ
	public static String HomeUrl = BaseUrl+"?module=home&";
	//��Ʒ����
	public static String Product_Details_Url = BaseUrl+"?module=goods&id=";
	//��Ʒ����
	public static String Product_comments_Url_head = BaseUrl+"?module=comment&goods_id=";
	public static String Product_comments_Url_foot = "&action=list&page=";
	//һ������
	public static String Product_fclass_Url_frist = BaseUrl+"?module=category&category_id=";
	//��������
	public static String Product_fclass_Url_two_head = BaseUrl+"?module=sub_category&sub_cate_id=";
	public static String Product_fclass_Url_two_center = "&sort=";
	public static String Product_fclass_Url_two_foot= "&page=";
	//���빺�ﳵ
	public static String Product_AddShopCart_Url_head = BaseUrl+"?module=add_to_cart&goods_id=";
	public static String Product_AddShopCart_Url_center = "&number=";
	public static String Product_AddShopCart_Url_foot_sessid = "&session_id=";
	public static String Product_AddShopCart_Url_foot_userid = "&user_id=";
	//��ȡ���ﳵ�б� 
	public static String GetShopCartList_Url_head = BaseUrl+"?module=cart_list&session_id=";
	public static String GetShopCartList_Url_foot = "&user_id=";
	//�ӹ��ﳵɾ��
	public static String Product_deletefromShopCart_Url_head = BaseUrl+"?module=drop_cart_goods&rec_id=";
	public static String Product_deletefromShopCart_Url_foot_sessid = "&session_id=";
	public static String Product_deletefromShopCart_Url_foot_userid = "&user_id=";
	//ע��   
	public static String Regist_Url_username = BaseUrl+"?module=register&user_name=";
	public static String Regist_Url_password = "&password=";
	//��¼   
	public static String Login_Url_username = BaseUrl+"?module=login&user_name=";
	public static String Login_Url_password = "&password=";

}
