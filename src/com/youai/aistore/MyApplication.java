/**
 * 
 */
package com.youai.aistore;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.youai.aistore.Bean.UserBean;

/**
 * MyApplication
 * 
 * @author Qzr
 */
public class MyApplication extends Application {
	public static boolean logined;
	public static UserBean userBean;
	public static String SessionId = "";
	public static String UserId = "0";
	public static String UserName = "";
	public static SharedPreferences mSharedPreferences;
	public static String callnumber = "4000965585";
	public static String smsnumber = "13331054189";
	public static int neiyi = 4;// ������
	public static int neiyi_qingqvshuiyi = 40;
	public static int neiyi_zhifuyouhuo = 37;
	public static int neiyi_xingganneiyi = 86;
	public static int neiyi_siwaneiku = 85;
	public static int neiyi_liantiwangyi = 83;
	public static int neiyi_sandiantoushi = 84;
	public static int woman = 74;// Ů����
	public static int woman_av = 75;
	public static int woman_fangzhenyangjv = 76;
	public static int woman_qingqvtiaodan = 91;
	public static int woman_shensuozhuanhzu = 19;
	public static int woman_hulibaojian = 77;
	public static int woman_otherwoman = 78;
	public static int man = 69;// ������
	public static int man_feijibei = 70;
	public static int man_daomo = 73;
	public static int man_fuzhu = 98;
	public static int tt = 63;// tt
	public static int tt_jingdian = 67;
	public static int tt_yanshi = 64;
	public static int tt_nvyong = 68;
	public static int tt_daxiaohao = 65;
	public static int tt_huayang = 66;
	public static int tosex = 87;// ˫��
	public static int tosex_zhuqing = 93;
	public static int tosex_houting = 71;
	public static int tosex_huantao = 89;
	public static int tosex_runhua = 92;
	public static int tosex_sm = 88;
	public static int tosex_other = 90;
	public static String clickdesc = "clickdesc";// ��������
	public static String clickdasc = "clickdasc";// ��������
	public static String salesdesc = "salesdesc";// ��������
	public static String salesdasc = "salesdasc";// ��������
	public static String pricedesc = "pricedesc";// �۸���
	public static String priceasc = "priceasc";// �۸�����
	public static final String PARTNER = "2088612509277434";// ���������ID
	public static final String SELLER = "youaishidai@qq.com";// ����֧�����˺�
	public static final String RSA_PRIVATE ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALvCKNMyj8bcM6jshdjzZBKbK/Jcb+gyFPz4WUdMKdtOEZWlsBVgAifvwN/w8rSVHSnIEU+4UOdgNwJSNDadU+gj0LtoPEPGiU3kZme0TcqsjgIt56XhSfZ7lS999WNd3BA7O9ctx9qyDNQ5X1XWeDDpgs3eW/eaSiR2mMSD/SaLAgMBAAECgYEAoQLy0ZJ1e5pQbtdOPOsT8WTCPheJG/z+xKUQorSs3FVJfSRWKS5pCuhuZVySNcv981uPhObR8mkvIz1tazxlu57ctnucLFaVatq3OZpplNQ9luZiU9wVcENQE0fQCd/alW04GvpijIctyxDPWPFTbzWXOJjWa60fcBu7BXeX32kCQQDt4SdA6VYXNFnp4RKPxGMmCA5bGDvnLOERBNNMgu6OH6FgUDg+ZyByJQixbtVUayJrPOOVF93Juf8xyX487ShdAkEAyg+amiU37eIgaaxrE7tyBW0EbUbGLwBTa896JwTheARObIQ6j8l/0qesDXu/UJ9DWtZRxv7HgH936AD/MoR8BwJBAOwJHk8NeOw7eQBV4XogLxV8puoC6OWhJY4ikJt5y8XgYttTngtlfs9kKfUNecZzK8rmtxeof9z7ntnDCmzgEc0CQDehIiRMGODSP7A4Ouac1aBuyig3svpfsZfd8Dhr3JNJRDoWEXViuWFKrlCsABj3L/kDRlJt9IkJDv79v0SuOuMCQDAiq+EBz7mBfToiOnB1MikO3L3WsBeKmldL/2U8cnY88nBWzSHF1tICy5W8CbuYS4zLXFTXnCEZWle/yvq8WIk=";
	public static final String RSA_PUBLIC ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	public static final String Notify_Url = "http://www.aiai.cn/services/api.php?module=notify_url";
	public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
	public static ArrayList<String> order_list;

	@Override
	public void onCreate() {
		super.onCreate();
		// ��ʼ��ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub) // ����ͼƬ�����ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.ic_empty) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				.showImageOnFail(R.drawable.ic_error) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisc(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
				// .displayer(new RoundedBitmapDisplayer(20)) // ���ó�Բ��ͼƬ
				.build(); // �������ù���DisplayImageOption����
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
		order_list = new ArrayList<String>();
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String DEVICE_ID = tm.getDeviceId();
		String time = String.valueOf(System.currentTimeMillis());
		mSharedPreferences = getSharedPreferences("aistoresp", 0);
		String sessionid = mSharedPreferences.getString("sessionid", "nulla");
		SharedPreferences.Editor mEditor = mSharedPreferences.edit();
		if (sessionid != null && !sessionid.equals("nulla")) {
			SessionId = sessionid;
		} else {
			SessionId = Util.hashKeyForDisk(DEVICE_ID + time);
		}
		mEditor.putString("sessionid", SessionId);
		mEditor.commit();
		UserName = mSharedPreferences.getString("username", "");
		UserId = mSharedPreferences.getString("userid", "0");
		if (Util.IsNull(UserName)) {
			logined = true;
			userBean = new UserBean();
			userBean.setUser_id(UserId);
			userBean.setUser_name(UserName);
		} else {
			logined = false;
		}
	}

	public static void setUserId(String userid) {
		SharedPreferences.Editor mEditor = mSharedPreferences.edit();
		mEditor.putString("userid", userid);
		mEditor.commit();
	}

	public static void SaveUserBean(UserBean bean) {
		userBean = bean;
		UserId = userBean.getUser_id();
		UserName = userBean.getUser_name();
		SharedPreferences.Editor mEditor = mSharedPreferences.edit();
		mEditor.putString("userid", bean.getUser_id());
		mEditor.putString("username", bean.getUser_name());
		mEditor.commit();
	}

}
