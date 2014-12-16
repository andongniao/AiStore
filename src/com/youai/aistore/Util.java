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
 * ���ù�����
 * 
 * @author Qzr
 * 
 */
public class Util {
	private static CustomProgressDialog progressDialog = null;

	/**
	 * �ж��Ƿ��ǵ绰����
	 * 
	 * @param mobiles
	 * @return true�ǵ绰����
	 */
	public static boolean isMobileNO(String mobiles) {
		String telRegex = "[1][358]\\d{9}";// "[1]"�����1λΪ����1��"[358]"����ڶ�λ����Ϊ3��5��8�е�һ����"\\d{9}"��������ǿ�����0��9�����֣���9λ��
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * 
	 * @param text
	 * @return true ��Ϊ��
	 */
	public static boolean IsNull(String text) {
		if (text != null && !text.equals("")) {
			return true;
		}
		return false;
	}
	/**
	 * ��ȡͼƬ��Ⱥ͸߶�
	 * 
	 * @param id
	 * @param resources
	 * @return
	 */
	public static int[] getBitmapWidth(int id, Resources resources) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// ����Ϊtureֻ��ȡͼƬ��С
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		BitmapFactory.decodeResource(resources, id, opts);
		return new int[] { opts.outWidth, opts.outHeight };
	}

	/**
	 * �ж������Ƿ����
	 * 
	 * @param act
	 * @return ture����
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
	 * ��ʱ���ת����ʱ������
	 * 
	 * @param str
	 *            ʱ�����͸�ʽ
	 * @param time
	 *            ʱ��� ��λ����
	 * @return
	 */
	public static String getData(String str, long time) {
		// yyyy-MM-dd hh:mm:ss
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		String date = sdf.format(new Date(time));
		return date;
	}

	/**
	 * ��ȡ��ǰʱ��
	 * 
	 * @param str
	 *            ʱ�����͸�ʽ
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
