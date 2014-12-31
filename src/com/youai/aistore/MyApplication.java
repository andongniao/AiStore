/**
 * 
 */
package com.youai.aistore;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * MyApplication
 * 
 * @author Qzr
 */
public class MyApplication extends Application {
	public static String SessionId = ""; 
	public static SharedPreferences mSharedPreferences;
	public static String callnumber = "4000965585";
	public static String smsnumber = "13331054789";

	@Override
	public void onCreate() {
		super.onCreate();
		// ��ʼ��ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_stub) // ����ͼƬ�����ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.ic_empty) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				.showImageOnFail(R.drawable.ic_error) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisc(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
				// .displayer(new RoundedBitmapDisplayer(20)) // ���ó�Բ��ͼƬ
				.build(); // �������ù���DisplayImageOption����

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
		TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
		String DEVICE_ID = tm.getDeviceId();
		String time = String.valueOf(System.currentTimeMillis());
		mSharedPreferences = getSharedPreferences("aistoresp", 0);
		String sessionid = mSharedPreferences.getString("sessionid", "nulla");
		if(sessionid!=null && !sessionid.equals("nulla")){
			SessionId = sessionid;
		}else{
			SessionId = Util.hashKeyForDisk(DEVICE_ID+time);
		SharedPreferences.Editor mEditor = mSharedPreferences.edit();
		mEditor.putString("ukuuid", SessionId);
		mEditor.commit();  
		}
	}

}

