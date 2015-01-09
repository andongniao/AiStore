package com.youai.aistore.NetInterface;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;


public class GetHttp {
	//	String uriAPI = "http://192.168.1.100:8080/test/test.jsp?u=wangyi&p=456";  
	static	String Jx = "http://xh.ai.cn/service/list.php?type=jing&";  
	static String Cw = "http://xh.ai.cn/service/list.php?type=character&";  
	static String Pic = "http://xh.ai.cn/service/list.php?type=pic&";  
	static String Zan = "http://xh.ai.cn/service/update.php?id=";
	static String CancelZan = "http://xh.ai.cn/service/update.php?id=";

	/** 
	 * 向指定URL发送GET方法的请求 
	 *  
	 * @param url 
	 *            发送请求的URL 
	 * @param params 
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。 
	 * @return URL所代表远程资源的响应 
	 */  
	public static String sendGet(String urlName)  
	{  
		String result = "";  
		BufferedReader in = null;  
		try  
		{  
//			String urlName = "";
//			if(type ==1){
//				urlName = Jx + "page=" + page+"&per_page="+perpage;  
//			}else if(type ==2){
//				urlName = Cw + "page=" + page+"&per_page="+perpage;  
//			}else if(type ==3){
//				urlName = Pic + "page=" + page+"&per_page="+perpage;  
//			}else if(type ==4){
//				//3&type=zan&key=91a0b7a6574c164f7e874796c06353f3
//				urlName = Zan + page+"&type=zan&key="+k;  
//			}else if(type ==5){
//				//3&type=cancel&key=056ea842413a1bff309a7926e3e6d409
//				urlName = CancelZan + page+"&type=cancel&key="+k;  
//			}
			URL realUrl = new URL(urlName);  
			// 打开和URL之间的连接  
			URLConnection conn = realUrl.openConnection();  
			// 设置通用的请求属性  
			conn.setRequestProperty("accept", "*/*");  
			conn.setRequestProperty("connection", "Keep-Alive");  
			conn.setRequestProperty("user-agent",  
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
			// 建立实际的连接  
			conn.connect();  
			// 获取所有响应头字段  
			Map<String, List<String>> map = conn.getHeaderFields();  
			// 遍历所有的响应头字段  
			for (String key : map.keySet())  
			{  
				System.out.println(key + "--->" + map.get(key));  
			}  
			// 定义BufferedReader输入流来读取URL的响应  
			in = new BufferedReader(  
					new InputStreamReader(conn.getInputStream()));  
			String line;  
			while ((line = in.readLine()) != null)  
			{  
				result += "\n" + line;  
			}  
		}  
		catch (Exception e)  
		{  
			System.out.println("发送GET请求出现异常！" + e);  
			e.printStackTrace();  
		}  
		// 使用finally块来关闭输入流  
		finally  
		{  
			try  
			{  
				if (in != null)  
				{  
					in.close();  
				}  
			}  
			catch (IOException ex)  
			{  
				ex.printStackTrace();  
			}  
		}  
		return result;  
	} 
	
	
}
