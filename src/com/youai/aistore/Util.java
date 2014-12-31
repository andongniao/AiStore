package com.youai.aistore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
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
	/**
	 * ����Loding...
	 * @param context
	 */
	public static void startProgressDialog(Context context){
		if (progressDialog == null){
			progressDialog = CustomProgressDialog.createDialog(context);
		}

		progressDialog.show();
	}
	/**
	 * �ر�Loding...
	 */
	public static void stopProgressDialog(){
		if (progressDialog != null && progressDialog.isShowing()){
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	


	/**
	 * ��ȡ����sd���ĸ�·��������]������sd�����t����null 
	 * @return
	 */
	public String getSdPath() {
		String sdcard_path = null;
		String sd_default = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		if (sd_default.endsWith("/")) {
			sd_default = sd_default.substring(0, sd_default.length() - 1);
		}
		// �õ�·��
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
	/**
	 * ����Toast
	 * @param context
	 * @param content
	 */
	public static void ShowToast(Context context,String content){
		Toast.makeText(context, content, 1000).show();
	}
	/**
	 * ����Toast
	 * @param context
	 * @param i
	 */
	public static void ShowToast(Context context,int i){
		Toast.makeText(context, i, 1000).show();
	}
	
	/**
	 * md5����
	 * @param key
	 * @return
	 */
	public static String hashKeyForDisk(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}
	
	 public static void setListViewHeightBasedOnChildren(ListView listView) {  
	        // ��ȡListView��Ӧ��Adapter  
	        ListAdapter listAdapter = listView.getAdapter();  
	        if(listAdapter == null) {  
	            return;  
	        }  
	        int totalHeight = 0;  
	        for(int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()�������������Ŀ  
	            View listItem = listAdapter.getView(i, null, listView);  
	            listItem.measure(0, 0); // ��������View �Ŀ��  
	            totalHeight += listItem.getMeasuredHeight(); // ͳ������������ܸ߶�  
	        }  
	        ViewGroup.LayoutParams params = listView.getLayoutParams();  
	        params.height = totalHeight  
	                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
	        // listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�  
	        // params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�  
	        listView.setLayoutParams(params);  
	    }  

}
