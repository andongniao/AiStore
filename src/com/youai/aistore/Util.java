package com.youai.aistore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;
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
	/**
	 * 启动Loding...
	 * @param context
	 */
	public static void startProgressDialog(Context context){
		if (progressDialog == null){
			progressDialog = CustomProgressDialog.createDialog(context);
		}

		progressDialog.show();
	}
	/**
	 * 关闭Loding...
	 */
	public static void stopProgressDialog(){
		if (progressDialog != null && progressDialog.isShowing()){
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	


	/**
	 * 获取外置sd卡的根路径，如果沒有外置sd卡，則返回null 
	 * @return
	 */
	public String getSdPath() {
		String sdcard_path = null;
		String sd_default = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		if (sd_default.endsWith("/")) {
			sd_default = sd_default.substring(0, sd_default.length() - 1);
		}
		// 得到路径
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;
				if (line.contains("fat") && line.contains("/mnt/")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						if (sd_default.trim().equals(columns[1].trim())) {
							continue;
						}
						sdcard_path = columns[1];
					}
				} else if (line.contains("fuse") && line.contains("/mnt/")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						if (sd_default.trim().equals(columns[1].trim())) {
							continue;
						}
						sdcard_path = columns[1];
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdcard_path;
	}

	public static void ShowToast(Context context,String content){
		Toast.makeText(context, content, 1000).show();
	}


}
