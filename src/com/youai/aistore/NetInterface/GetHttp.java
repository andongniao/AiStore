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
	 * ��ָ��URL����GET���������� 
	 *  
	 * @param url 
	 *            ���������URL 
	 * @param params 
	 *            ����������������Ӧ����name1=value1&name2=value2����ʽ�� 
	 * @return URL������Զ����Դ����Ӧ 
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
			// �򿪺�URL֮�������  
			URLConnection conn = realUrl.openConnection();  
			// ����ͨ�õ���������  
			conn.setRequestProperty("accept", "*/*");  
			conn.setRequestProperty("connection", "Keep-Alive");  
			conn.setRequestProperty("user-agent",  
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
			// ����ʵ�ʵ�����  
			conn.connect();  
			// ��ȡ������Ӧͷ�ֶ�  
			Map<String, List<String>> map = conn.getHeaderFields();  
			// �������е���Ӧͷ�ֶ�  
			for (String key : map.keySet())  
			{  
				System.out.println(key + "--->" + map.get(key));  
			}  
			// ����BufferedReader����������ȡURL����Ӧ  
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
			System.out.println("����GET��������쳣��" + e);  
			e.printStackTrace();  
		}  
		// ʹ��finally�����ر�������  
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
