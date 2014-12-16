package com.youai.aistore;

import java.sql.Date;
import java.text.SimpleDateFormat;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
/**
 * 公用工具类
 * 
 * @author Qzr
 * 
 */
public class Util {
	private static CustomProgressDialog progressDialog = null;

	/**
	 * 判断是否是电话号码
	 * 
	 * @param mobiles
	 * @return true是电话号码
	 */
	public static boolean isMobileNO(String mobiles) {
		String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param text
	 * @return true 不为空
	 */
	public static boolean IsNull(String text) {
		if (text != null && !text.equals("")) {
			return true;
		}
		return false;
	}
	/**
	 * 获取图片宽度和高度
	 * 
	 * @param id
	 * @param resources
	 * @return
	 */
	public static int[] getBitmapWidth(int id, Resources resources) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		BitmapFactory.decodeResource(resources, id, opts);
		return new int[] { opts.outWidth, opts.outHeight };
	}

	/**
	 * 判断网络是否可用
	 * 
	 * @param act
	 * @return ture可用
	 */
	public static boolean detect(Context act) {
		ConnectivityManager manager = (ConnectivityManager) act
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}
	/**
	 * 把时间戳转化成时间类型
	 * 
	 * @param str
	 *            时间类型格式
	 * @param time
	 *            时间戳 单位毫秒
	 * @return
	 */
	public static String getData(String str, long time) {
		// yyyy-MM-dd hh:mm:ss
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		String date = sdf.format(new Date(time));
		return date;
	}

	/**
	 * 获取当前时间
	 * 
	 * @param str
	 *            时间类型格式
	 * @return
	 */
	public static String getdata(String str) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(date);
	}
	
	public static void startProgressDialog(Context context){
		if (progressDialog == null){
			progressDialog = CustomProgressDialog.createDialog(context);
		}

		progressDialog.show();
	}

	public static void stopProgressDialog(){
		if (progressDialog != null){
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

}
