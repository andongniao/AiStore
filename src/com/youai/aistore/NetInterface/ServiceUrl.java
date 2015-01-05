package com.youai.aistore.NetInterface;

public class ServiceUrl {
	//基本url
	public static String BaseUrl = "http://www.aiai.cn/services/api.php";
	//首页
	public static String HomeUrl = BaseUrl+"?module=home&";
	//产品详情
	public static String Product_Details_Url = BaseUrl+"?module=goods&id=";
	//产品评论
	public static String Product_comments_Url_head = BaseUrl+"?module=comment&goods_id=";
	public static String Product_comments_Url_foot = "&action=list&page=";
	//一级分类
	public static String Product_fclass_Url_frist = BaseUrl+"?module=category&category_id=";
	//二级分类
	public static String Product_fclass_Url_two_head = BaseUrl+"?module=sub_category&sub_cate_id=";
	public static String Product_fclass_Url_two_center = "&sort=";
	public static String Product_fclass_Url_two_foot= "&page=";
	//加入购物车
	public static String Product_AddShopCart_Url_head = BaseUrl+"?module=add_to_cart&goods_id=";
	public static String Product_AddShopCart_Url_center = "&number=";
	public static String Product_AddShopCart_Url_foot = "&session_id=";
	//获取购物车列表 
	public static String GetShopCartList_Url_head = BaseUrl+"?module=cart_list&session_id=";
	public static String GetShopCartList_Url_foot = "&user_id=";
	

}
