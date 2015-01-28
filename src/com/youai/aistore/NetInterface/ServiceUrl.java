package com.youai.aistore.NetInterface;

public class ServiceUrl {
	// 基本url
	public static String BaseUrl = "http://www.aiai.cn/services/api.php";
	// 首页
	public static String HomeUrl = BaseUrl + "?module=home&";
	// 产品详情
	public static String Product_Details_Url = BaseUrl + "?module=goods&id=";
	// 产品评论
	public static String Product_comments_Url_head = BaseUrl
			+ "?module=comment&goods_id=";
	public static String Product_comments_Url_foot = "&action=list&page=";
	// 一级分类
	public static String Product_fclass_Url_frist = BaseUrl
			+ "?module=category&category_id=";
	// 二级分类
	public static String Product_fclass_Url_two_head = BaseUrl
			+ "?module=sub_category&sub_cate_id=";
	public static String Product_fclass_Url_two_center = "&sort=";
	public static String Product_fclass_Url_two_foot = "&page=";
	// 加入购物车
	public static String Product_AddShopCart_Url_head = BaseUrl
			+ "?module=add_to_cart&goods_id=";
	public static String Product_AddShopCart_Url_center = "&number=";
	public static String Product_AddShopCart_Url_foot_sessid = "&session_id=";
	public static String Product_AddShopCart_Url_foot_userid = "&user_id=";
	// 获取购物车列表
	public static String GetShopCartList_Url_head = BaseUrl
			+ "?module=cart_list&session_id=";
	public static String GetShopCartList_Url_foot = "&user_id=";
	// 从购物车删除
	public static String Product_deletefromShopCart_Url_head = BaseUrl
			+ "?module=drop_cart_goods&rec_id=";
	public static String Product_deletefromShopCart_Url_foot_sessid = "&session_id=";
	public static String Product_deletefromShopCart_Url_foot_userid = "&user_id=";
	// 注册
	public static String Regist_Url_username = BaseUrl
			+ "?module=register&user_name=";
	public static String Regist_Url_password = "&password=";
	// 登录
	public static String Login_Url_username = BaseUrl
			+ "?module=login&user_name=";
	public static String Login_Url_password = "&password=";
	// 获取收货人信息
	public static String get_consignee_info = BaseUrl
			+ "?module=get_user_information&user_id=";
	// 保存收货人信息
	public static String save_consignee_info = BaseUrl
			+ "?module=save_user_information&user_id=";
	public static String save_consignee_info_consignee = "&consignee=";
	public static String save_consignee_info_tel = "&tel=";
	public static String save_consignee_info_address = "&address=";
	// 提交订单
	public static String save_order_info = BaseUrl
			+ "?module=order_lise&user_id=";
	public static String save_order_info_time = "&best_time=";
	public static String save_order_info_type = "&pay_type=";
	public static String save_order_info_time_fee = "&shipping_fee=";
	// 全部订单
	public static String get_order_list_userid = BaseUrl
			+ "?module=user&&user_id=";
	public static String get_order_list_page = "&page=";
	public static String get_order_list_action = "&action=order_list";
	// 订单详情 
	public static String get_order_details_orderid = BaseUrl
			+ "?module=user&action=order_detail&order_id=";
	public static String get_order_details_userid = "&user_id=";
	// 同步购物车信息  
	public static String updata_shopcart_session_id = BaseUrl
			+ "?module=update_cart_list&session_id=";
	public static String updata_shopcart_user_id = "&user_id=";
	// 更新订单状态  
	public static String updata_order_statu_order= BaseUrl
			+ "?module=pay&order_sn=";

}
