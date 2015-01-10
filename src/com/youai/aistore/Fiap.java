package com.youai.aistore;

//支付宝应用支付
//2012-09-20 14:41:47
//(c) 2012 Catcap


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.alipay.android.app.IAlixPay;
import com.alipay.android.app.IRemoteServiceCallback;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;




@SuppressLint ("HandlerLeak")
public class Fiap{
	Activity mActivity = null;

	// ===================================
	// JAVA 的接口
	// ===================================


	public Fiap(Activity activity){

		mActivity = activity;

	}

	//这里传过来的是想支付多少钱(最好定义成double的，方便调试，毕竟每次测试都支付几元大洋不是每个人都负担的起的)
	public void pay (double coin,String subject,String body,String order_id){

		//支付宝支付必须依赖网络，所以在这里必须加网络判定
		if (!is_can_internet (mActivity)){

			fiapHandler.sendEmptyMessage(1);
			return;
		}

		Message msg = new Message ();
		Bundle bundle = new Bundle();
		bundle.putDouble("coin", coin);
		bundle.putString("subject", subject);
		bundle.putString("body", body);
		bundle.putString("order_id", order_id);
		msg.setData(bundle);
		msg.what = 1;
		fss.sendMessage (msg);
	}

	private Handler fiapHandler = new Handler(){

		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				new AlertDialog.Builder (mActivity).setTitle ("提示").setMessage ("连接不到网络。").setPositiveButton ("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//TODO Auto-generated method stub
						Intent intent = new Intent(
								"android.settings.WIFI_SETTINGS");
						mActivity.startActivity(intent);
					}
				}).create ().show ();
			}
		};
	};

	// ===================================
	// 支付宝
	// ===================================
	public class PartnerConfig {


		//以下配置涉及到公司内容，所以略去，需自己配置
		// 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
		public static final String PARTNER = ""; 
		// 商户收款的支付宝账号
		public static final String SELLER = "";
		// 商户（RSA）私钥(注意一定要转PKCS8格式，否则在Android4.0及以上系统会支付失败)
		public static final String RSA_PRIVATE = "";
		// 支付宝（RSA）公钥用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
		public static final String RSA_ALIPAY_PUBLIC = "";
	}


	private ProgressDialog mProgress = null;


	public static class AlixOnCancelListener implements DialogInterface.OnCancelListener {
		Activity mcontext;


		AlixOnCancelListener (Activity context){
			mcontext = context;
		}


		public void onCancel (DialogInterface dialog){
			mcontext.onKeyDown (KeyEvent.KEYCODE_BACK, null);
		}
	}


	private Handler fss = new Handler (){
		@SuppressWarnings ("deprecation")
		public void handleMessage (Message msg){
			MobileSecurePayHelper mspHelper = new MobileSecurePayHelper (mActivity);
			boolean isMobile_spExist = mspHelper.detectMobile_sp ();
			if (!isMobile_spExist)
				return;
			// 根据订单信息开始进行支付
			try{
				// 准备订单信息
				Bundle bundle = msg.getData();
				double _coin = bundle.getDouble("coin");
				String _subject = bundle.getString("subject");
				String _body = bundle.getString("body");
				String _order_id = bundle.getString("order_id");
				String orderInfo = getOrderInfo(_coin,_subject,_body,_order_id);
				// 这里根据签名方式对订单信息进行签名
				String signType = getSignType ();
				String strsign = sign (signType, orderInfo);
				// 对签名进行编码
				strsign = URLEncoder.encode (strsign);
				// 组装好参数
				String info = orderInfo + "&sign=" + "" + strsign + "" + "&" + getSignType ();
				// 调用pay方法进行支付
				MobileSecurePayer msp = new MobileSecurePayer ();
				boolean bRet = msp.pay (info, mHandler, AlixId.RQF_PAY, mActivity);
				if (bRet){
					// 显示“正在支付”进度条
					closeProgress ();
					mProgress = BaseHelper.showProgress (mActivity, null, "正在支付", false, true);
				}
			} catch (Exception ex){
				ex.printStackTrace ();
			}
		}
	};

	private Handler mHandler = new Handler (){
		public void handleMessage (Message msg){
			try{
				String strRet = (String) msg.obj;
				switch (msg.what){
				case AlixId.RQF_PAY:{
					//
					closeProgress ();
					// 处理交易结果
					try{
						// 获取交易状态码，具体状态代码请参看文档
						String tradeStatus = "resultStatus={";
						int imemoStart = strRet.indexOf ("resultStatus=");
						imemoStart += tradeStatus.length ();
						int imemoEnd = strRet.indexOf ("};memo=");
						tradeStatus = strRet.substring (imemoStart, imemoEnd);
						//先验签通知
						ResultChecker resultChecker = new ResultChecker (strRet);
						int retVal = resultChecker.checkSign ();
						if (retVal == ResultChecker.RESULT_CHECK_SIGN_FAILED){
							BaseHelper.showDialog (mActivity, "提示", "您的订单信息已被非法篡改。", android.R.drawable.ic_dialog_alert);
						} else{
							if (tradeStatus.equals ("9000")){

								//程序到这里表示支付已经成功了，想干什么就在这里干吧 -v-
								Toast.makeText(mActivity, "支付成功",Toast.LENGTH_LONG).show();
								Log.i("result of this pay:", "successful");

							} else if (!tradeStatus.equals ("4000")){

								//程序到这里表示此次支付失败，查看具体原因可以从这里打印个log
								Toast.makeText(mActivity, "支付失败,交易状态码为:" + tradeStatus, Toast.LENGTH_LONG).show();
								Log.e("result of this pay", "falied");
							}
						}
					} catch (Exception e){
						e.printStackTrace ();
					}
				}
				break;
				}
				super.handleMessage (msg);
			} catch (Exception e){
				e.printStackTrace ();
			}
		}
	};


	String getSignType (){
		String getSignType = "sign_type=" + "" + "RSA" + "";
		return getSignType;
	}


	void closeProgress (){
		try{
			if (mProgress != null){
				mProgress.dismiss ();
				mProgress = null;
			}
		} catch (Exception e){
			e.printStackTrace ();
		}
	}


	String getOrderInfo (double position,String subject,String body,String order_id){

		String strOrderInfo = "partner=" + "" + PartnerConfig.PARTNER + "";
		strOrderInfo += "&";
		strOrderInfo += "seller=" + "" + PartnerConfig.SELLER + "";
		strOrderInfo += "&";
		strOrderInfo += "out_trade_no=" + "" + order_id + "";
		strOrderInfo += "&";

		//这里是显示到支付宝支付界面上的付费信息提示（这里一定要严格按照此格式填写）
		strOrderInfo += "subject=" + subject;
		strOrderInfo += "&";
		strOrderInfo += "body=" + body;
		strOrderInfo += "&";
		strOrderInfo += "total_fee=" + "" + position + "";
		strOrderInfo += "&";
		strOrderInfo += "notify_url=" + "" + "http://notify.java.jpxx.org/index.jsp" + "";
		return strOrderInfo;
	}


	String sign (String signType, String content){
		return Rsa.sign (content, PartnerConfig.RSA_PRIVATE);
	}


	public boolean is_can_internet (final Context context){
		try{
			ConnectivityManager manger = (ConnectivityManager) context.getSystemService (Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manger.getActiveNetworkInfo ();

			return (info != null && info.isConnected ());
		} catch (Exception e){

			return false;
		}
	}

	/**
	 * 订单号
	 * @return
	 */
	public String get_order_id (){
		long ran1 = get_round (1111, 9999);
		long ran2 = get_round (1111, 9999);

		//注掉的这里是返回的渠道号（我们用的友盟）+随机数和当前系统时间组合
		//return android_get_umeng_channel () + "_" + ran1 + System.currentTimeMillis () + ran2;
		return "_"+ran1 + System.currentTimeMillis () + ran2;
	}


	public long get_round (int min, int max){
		return Math.round (Math.random () * (max - min) + min);
	}


	//=================================================================================================
	// 
	// 支付宝不用动的
	// ==================================================================================================
	public final class AlixId {
		public static final int BASE_ID = 0;
		public static final int RQF_PAY = BASE_ID + 1;
		public static final int RQF_INSTALL_CHECK = RQF_PAY + 1;
	}

	final class AlixDefine {
		public static final String IMEI = "imei";
		public static final String IMSI = "imsi";
		public static final String KEY = "key";
		public static final String USER_AGENT = "user_agent";
		public static final String VERSION = "version";
		public static final String DEVICE = "device";
		public static final String SID = "sid";
		public static final String partner = "partner";
		public static final String charset = "charset";
		public static final String sign_type = "sign_type";
		public static final String sign = "sign";
		public static final String URL = "URL";
		public static final String split = "&";
		public static final String AlixPay = "AlixPay";
		public static final String action = "action";
		public static final String actionUpdate = "update";
		public static final String data = "data";
		public static final String platform = "platform";
	}

	public static final class Base64 {
		static private final int BASELENGTH = 128;
		static private final int LOOKUPLENGTH = 64;
		static private final int TWENTYFOURBITGROUP = 24;
		static private final int EIGHTBIT = 8;
		static private final int SIXTEENBIT = 16;
		static private final int FOURBYTE = 4;
		static private final int SIGN = -128;
		static private final char PAD = '=';
		static private final boolean fDebug = false;
		static final private byte[] base64Alphabet = new byte[BASELENGTH];
		static final private char[] lookUpBase64Alphabet = new char[LOOKUPLENGTH];
		static{
			for (int i = 0; i < BASELENGTH; ++i){
				base64Alphabet[i] = -1;
			}
			for (int i = 'Z'; i >= 'A'; i--){
				base64Alphabet[i] = (byte) (i - 'A');
			}
			for (int i = 'z'; i >= 'a'; i--){
				base64Alphabet[i] = (byte) (i - 'a' + 26);
			}
			for (int i = '9'; i >= '0'; i--){
				base64Alphabet[i] = (byte) (i - '0' + 52);
			}
			base64Alphabet['+'] = 62;
			base64Alphabet['/'] = 63;
			for (int i = 0; i <= 25; i++){
				lookUpBase64Alphabet[i] = (char) ('A' + i);
			}
			for (int i = 26, j = 0; i <= 51; i++, j++){
				lookUpBase64Alphabet[i] = (char) ('a' + j);
			}
			for (int i = 52, j = 0; i <= 61; i++, j++){
				lookUpBase64Alphabet[i] = (char) ('0' + j);
			}
			lookUpBase64Alphabet[62] = (char) '+';
			lookUpBase64Alphabet[63] = (char) '/';
		}


		private static boolean isWhiteSpace (char octect){
			return (octect == 0x20 || octect == 0xd || octect == 0xa || octect == 0x9);
		}


		private static boolean isPad (char octect){
			return (octect == PAD);
		}


		private static boolean isData (char octect){
			return (octect < BASELENGTH && base64Alphabet[octect] != -1);
		}



		public static String encode (byte[] binaryData){
			if (binaryData == null){
				return null;
			}
			int lengthDataBits = binaryData.length * EIGHTBIT;
			if (lengthDataBits == 0){
				return "";
			}
			int fewerThan24bits = lengthDataBits % TWENTYFOURBITGROUP;
			int numberTriplets = lengthDataBits / TWENTYFOURBITGROUP;
			int numberQuartet = fewerThan24bits != 0? numberTriplets + 1 : numberTriplets;
			char encodedData[] = null;
			encodedData = new char[numberQuartet * 4];
			byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;
			int encodedIndex = 0;
			int dataIndex = 0;
			if (fDebug){
				System.out.println ("number of triplets = " + numberTriplets);
			}
			for (int i = 0; i < numberTriplets; i++){
				b1 = binaryData[dataIndex++];
				b2 = binaryData[dataIndex++];
				b3 = binaryData[dataIndex++];
				if (fDebug){
					System.out.println ("b1= " + b1 + ", b2= " + b2 + ", b3= " + b3);
				}
				l = (byte) (b2 & 0x0f);
				k = (byte) (b1 & 0x03);
				byte val1 = ((b1 & SIGN) == 0)? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
			byte val2 = ((b2 & SIGN) == 0)? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);
			byte val3 = ((b3 & SIGN) == 0)? (byte) (b3 >> 6) : (byte) ((b3) >> 6 ^ 0xfc);
			if (fDebug){
				System.out.println ("val2 = " + val2);
				System.out.println ("k4   = " + (k << 4));
				System.out.println ("vak  = " + (val2 | (k << 4)));
			}
			encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | (k << 4)];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[(l << 2) | val3];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3f];
			}
			// form integral number of 6-bit groups
			if (fewerThan24bits == EIGHTBIT){
				b1 = binaryData[dataIndex];
				k = (byte) (b1 & 0x03);
				if (fDebug){
					System.out.println ("b1=" + b1);
					System.out.println ("b1<<2 = " + (b1 >> 2));
				}
				byte val1 = ((b1 & SIGN) == 0)? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
			encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[k << 4];
			encodedData[encodedIndex++] = PAD;
			encodedData[encodedIndex++] = PAD;
			} else if (fewerThan24bits == SIXTEENBIT){
				b1 = binaryData[dataIndex];
				b2 = binaryData[dataIndex + 1];
				l = (byte) (b2 & 0x0f);
				k = (byte) (b1 & 0x03);
				byte val1 = ((b1 & SIGN) == 0)? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
			byte val2 = ((b2 & SIGN) == 0)? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);
			encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | (k << 4)];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2];
			encodedData[encodedIndex++] = PAD;
			}
			return new String (encodedData);
		}



		public static byte[] decode (String encoded){
			if (encoded == null){
				return null;
			}
			char[] base64Data = encoded.toCharArray ();
			// remove white spaces
			int len = removeWhiteSpace (base64Data);
			if (len % FOURBYTE != 0){
				return null;// should be divisible by four
			}
			int numberQuadruple = (len / FOURBYTE);
			if (numberQuadruple == 0){
				return new byte[0];
			}
			byte decodedData[] = null;
			byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
			char d1 = 0, d2 = 0, d3 = 0, d4 = 0;
			int i = 0;
			int encodedIndex = 0;
			int dataIndex = 0;
			decodedData = new byte[(numberQuadruple) * 3];
			for (; i < numberQuadruple - 1; i++){
				if (!isData ((d1 = base64Data[dataIndex++])) || !isData ((d2 = base64Data[dataIndex++])) || !isData ((d3 = base64Data[dataIndex++])) || !isData ((d4 = base64Data[dataIndex++]))){
					return null;
				}// if found "no data" just return null
						b1 = base64Alphabet[d1];
				b2 = base64Alphabet[d2];
				b3 = base64Alphabet[d3];
				b4 = base64Alphabet[d4];
				decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
				decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
				decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);
			}
			if (!isData ((d1 = base64Data[dataIndex++])) || !isData ((d2 = base64Data[dataIndex++]))){
				return null;// if found "no data" just return null
			}
			b1 = base64Alphabet[d1];
			b2 = base64Alphabet[d2];
			d3 = base64Data[dataIndex++];
			d4 = base64Data[dataIndex++];
			if (!isData ((d3)) || !isData ((d4))){// Check if they are PAD characters
				if (isPad (d3) && isPad (d4)){
					if ((b2 & 0xf) != 0)// last 4 bits should be zero
					{
						return null;
					}
					byte[] tmp = new byte[i * 3 + 1];
					System.arraycopy (decodedData, 0, tmp, 0, i * 3);
					tmp[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
					return tmp;
				} else if (!isPad (d3) && isPad (d4)){
					b3 = base64Alphabet[d3];
					if ((b3 & 0x3) != 0)// last 2 bits should be zero
					{
						return null;
					}
					byte[] tmp = new byte[i * 3 + 2];
					System.arraycopy (decodedData, 0, tmp, 0, i * 3);
					tmp[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
					tmp[encodedIndex] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
					return tmp;
				} else{
					return null;
				}
			} else{ // No PAD e.g 3cQl
				b3 = base64Alphabet[d3];
				b4 = base64Alphabet[d4];
				decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
				decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
				decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);
			}
			return decodedData;
		}



		private static int removeWhiteSpace (char[] data){
			if (data == null){
				return 0;
			}
			// count characters that's not whitespace
			int newSize = 0;
			int len = data.length;
			for (int i = 0; i < len; i++){
				if (!isWhiteSpace (data[i])){
					data[newSize++] = data[i];
				}
			}
			return newSize;
		}
	}

	public static class BaseHelper {

		public static String convertStreamToString (InputStream is){
			BufferedReader reader = new BufferedReader (new InputStreamReader (is));
			StringBuilder sb = new StringBuilder ();
			String line = null;
			try{
				while ((line = reader.readLine ()) != null){
					sb.append (line);
				}
			} catch (IOException e){
				e.printStackTrace ();
			} finally{
				try{
					is.close ();
				} catch (IOException e){
					e.printStackTrace ();
				}
			}
			return sb.toString ();
		}



		public static void showDialog (Activity context, String strTitle, String strText, int icon){
			AlertDialog.Builder tDialog = new AlertDialog.Builder (context);
			tDialog.setIcon (icon);
			tDialog.setTitle (strTitle);
			tDialog.setMessage (strText);
			tDialog.setPositiveButton ("确定", null);
			tDialog.show ();
		}



		public static void log (String tag, String info){
			// Log.d(tag, info);
		}



		public static void chmod (String permission, String path){
			try{
				String command = "chmod " + permission + " " + path;
				Runtime runtime = Runtime.getRuntime ();
				runtime.exec (command);
			} catch (IOException e){
				e.printStackTrace ();
			}
		}


		//
		// show the progress bar.

		public static ProgressDialog showProgress (Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable){
			ProgressDialog dialog = new ProgressDialog (context);
			dialog.setTitle (title);
			dialog.setMessage (message);
			dialog.setIndeterminate (indeterminate);
			dialog.setCancelable (false);
			// dialog.setDefaultButton(false);
			dialog.setOnCancelListener (new Fiap.AlixOnCancelListener ((Activity) context));
			dialog.show ();
			return dialog;
		}



		public static JSONObject string2JSON (String str, String split){
			JSONObject json = new JSONObject ();
			try{
				String[] arrStr = str.split (split);
				for (int i = 0; i < arrStr.length; i++){
					String[] arrKeyValue = arrStr[i].split ("=");
					json.put (arrKeyValue[0], arrStr[i].substring (arrKeyValue[0].length () + 1));
				}
			} catch (Exception e){
				e.printStackTrace ();
			}
			return json;
		}
	}

	public class Constant {
		public final static String server_url = "https://msp.alipay.com/x.htm";
	}

	public class MobileSecurePayer {
		Integer lock = 0;
		IAlixPay mAlixPay = null;
		boolean mbPaying = false;
		Activity mActivity = null;
		// 和安全支付服务建立连接
		private ServiceConnection mAlixPayConnection = new ServiceConnection (){
			public void onServiceConnected (ComponentName className, IBinder service){
				//
				// wake up the binder to continue.
				// 获得通信通道
				synchronized (lock){
					mAlixPay = IAlixPay.Stub.asInterface (service);
					lock.notify ();
				}
			}


			public void onServiceDisconnected (ComponentName className){
				mAlixPay = null;
			}
		};



		public boolean pay (final String strOrderInfo, final Handler callback, final int myWhat, final Activity activity){
			if (mbPaying)
				return false;
			mbPaying = true;
			//
			mActivity = activity;
			// bind the service.
			// 绑定服务
			if (mAlixPay == null){
				// 绑定安全支付服务需要获取上下文环境，
				// 如果绑定不成功使用mActivity.getApplicationContext().bindService
				// 解绑时同理
				mActivity.getApplicationContext ().bindService (new Intent (IAlixPay.class.getName ()), mAlixPayConnection, Context.BIND_AUTO_CREATE);
			}
			// else ok.
			// 实例一个线程来进行支付
			new Thread (new Runnable (){
				public void run (){
					try{
						// wait for the service bind operation to completely
						// finished.
						// Note: this is important,otherwise the next mAlixPay.Pay()
						// will fail.
						// 等待安全支付服务绑定操作结束
						// 注意：这里很重要，否则mAlixPay.Pay()方法会失败
						synchronized (lock){
							if (mAlixPay == null)
								lock.wait ();
						}
						// register a Callback for the service.
						// 为安全支付服务注册一个回调
						mAlixPay.registerCallback (mCallback);
						// call the MobileSecurePay service.
						// 调用安全支付服务的pay方法
						String strRet = mAlixPay.Pay (strOrderInfo);
						// set the flag to indicate that we have finished.
						// unregister the Callback, and unbind the service.
						// 将mbPaying置为false，表示支付结束
						// 移除回调的注册，解绑安全支付服务
						mbPaying = false;
						mAlixPay.unregisterCallback (mCallback);
						mActivity.getApplicationContext ().unbindService (mAlixPayConnection);
						// send the result back to caller.
						// 发送交易结果
						Message msg = new Message ();
						msg.what = myWhat;
						msg.obj = strRet;
						callback.sendMessage (msg);
					} catch (Exception e){
						e.printStackTrace ();
						// send the result back to caller.
						// 发送交易结果
						Message msg = new Message ();
						msg.what = myWhat;
						msg.obj = e.toString ();
						callback.sendMessage (msg);
					}
				}
			}).start ();
			return true;
		}



		private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub (){

			public void startActivity (String packageName, String className, int iCallingPid, Bundle bundle) throws RemoteException{
				Intent intent = new Intent (Intent.ACTION_MAIN, null);
				if (bundle == null)
					bundle = new Bundle ();
				// else ok.
				try{
					bundle.putInt ("CallingPid", iCallingPid);
					intent.putExtras (bundle);
				} catch (Exception e){
					e.printStackTrace ();
				}
				intent.setClassName (packageName, className);
				mActivity.startActivity (intent);
			}


			@Override
			public boolean isHideLoadingScreen () throws RemoteException{
				return false;
			}


			@Override
			public void payEnd (boolean arg0, String arg1) throws RemoteException{

			}
		};
	}

	public class MobileSecurePayHelper {
		static final String TAG = "MobileSecurePayHelper";
		private ProgressDialog mProgress = null;
		Context mContext = null;


		public MobileSecurePayHelper (Context context){
			this.mContext = context;
		}



		public boolean detectMobile_sp (){
			boolean isMobile_spExist = isMobile_spExist ();
			if (!isMobile_spExist){
				// 获取系统缓冲绝对路径获取/data/data//cache目录
				File cacheDir = mContext.getCacheDir ();
				final String cachePath = cacheDir.getAbsolutePath () + "/temp.apk";
				mProgress = BaseHelper.showProgress (mContext, null, "正在检测安全支付服务版本", false, true);
				// 实例新线程检测是否有新版本进行下载
				new Thread (new Runnable (){
					public void run (){
						// 检测是否有新的版本。
						String newApkdlUrl = checkNewUpdate ();
						closeProgress ();
						// 动态下载
						if (newApkdlUrl != null)
							retrieveApkFromNet (mContext, newApkdlUrl, cachePath);
						showInstallConfirmDialog (mContext, cachePath);
					}
				}).start ();
			}
			return isMobile_spExist;
		}



		public void showInstallConfirmDialog (final Context context, final String cachePath){
			Looper.prepare ();
			AlertDialog.Builder tDialog = new AlertDialog.Builder (context);
			tDialog.setTitle ("安装提示");
			tDialog.setMessage ("为保证您的交易安全，需要您安装支付宝安全支付服务，才能进行付款。\n\n点击确定，立即安装。");
			tDialog.setPositiveButton ("确定", new DialogInterface.OnClickListener (){
				public void onClick (DialogInterface dialog, int which){
					//
					// 修改apk权限
					BaseHelper.chmod ("777", cachePath);
					//
					// install the apk.
					// 安装安全支付服务APK
					Intent intent = new Intent (Intent.ACTION_VIEW);
					intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setDataAndType (Uri.parse ("file://" + cachePath), "application/vnd.android.package-archive");
					context.startActivity (intent);
				}
			});
			tDialog.setNegativeButton ("取消", new DialogInterface.OnClickListener (){
				public void onClick (DialogInterface dialog, int which){}
			});
			tDialog.show ();
			Looper.loop ();
		}



		public boolean isMobile_spExist (){
			PackageManager manager = mContext.getPackageManager ();
			List<PackageInfo> pkgList = manager.getInstalledPackages (0);
			for (int i = 0; i < pkgList.size (); i++){
				PackageInfo pI = pkgList.get (i);
				if (pI.packageName.equalsIgnoreCase ("com.alipay.android.app"))
					return true;
			}
			return false;
		}



		public boolean retrieveApkFromAssets (Context context, String fileName, String path){
			boolean bRet = false;
			try{
				InputStream is = context.getAssets ().open (fileName);
				File file = new File (path);
				file.createNewFile ();
				FileOutputStream fos = new FileOutputStream (file);
				byte[] temp = new byte[1024];
				int i = 0;
				while ((i = is.read (temp)) > 0){
					fos.write (temp, 0, i);
				}
				fos.close ();
				is.close ();
				bRet = true;
			} catch (IOException e){
				e.printStackTrace ();
			}
			return bRet;
		}



		public PackageInfo getApkInfo (Context context, String archiveFilePath){
			PackageManager pm = context.getPackageManager ();
			PackageInfo apkInfo = pm.getPackageArchiveInfo (archiveFilePath, PackageManager.GET_META_DATA);
			return apkInfo;
		}



		public String checkNewUpdate (){
			String url = null;
			try{
				//             JSONObject resp = sendCheckNewUpdate (packageInfo.versionName);
				JSONObject resp = sendCheckNewUpdate("1.0.0");
				if (resp.getString ("needUpdate").equalsIgnoreCase ("true")){
					url = resp.getString ("updateUrl");
				}
				// else ok.
			} catch (Exception e){
				e.printStackTrace ();
			}
			return url;
		}



		public JSONObject sendCheckNewUpdate (String versionName){
			JSONObject objResp = null;
			try{
				JSONObject req = new JSONObject ();
				req.put (AlixDefine.action, AlixDefine.actionUpdate);
				JSONObject data = new JSONObject ();
				data.put (AlixDefine.platform, "android");
				data.put (AlixDefine.VERSION, versionName);
				data.put (AlixDefine.partner, "");
				req.put (AlixDefine.data, data);
				objResp = sendRequest (req.toString ());
			} catch (JSONException e){
				e.printStackTrace ();
			}
			return objResp;
		}



		public JSONObject sendRequest (final String content){
			NetworkManager nM = new NetworkManager (this.mContext);
			//
			JSONObject jsonResponse = null;
			try{
				String response = null;
				synchronized (nM){
					//
					response = nM.SendAndWaitResponse (content, Constant.server_url);
				}
				jsonResponse = new JSONObject (response);
			} catch (Exception e){
				e.printStackTrace ();
			}
			//
			if (jsonResponse != null)
				BaseHelper.log (TAG, jsonResponse.toString ());
			return jsonResponse;
		}



		public boolean retrieveApkFromNet (Context context, String strurl, String filename){
			boolean bRet = false;
			try{
				NetworkManager nM = new NetworkManager (this.mContext);
				bRet = nM.urlDownloadToFile (context, strurl, filename);
			} catch (Exception e){
				e.printStackTrace ();
			}
			return bRet;
		}


		//
		// close the progress bar
		void closeProgress (){
			try{
				if (mProgress != null){
					mProgress.dismiss ();
					mProgress = null;
				}
			} catch (Exception e){
				e.printStackTrace ();
			}
		}
	}

	public class NetworkManager {
		static final String TAG = "NetworkManager";
		private int connectTimeout = 30 * 1000;
		private int readTimeout = 30 * 1000;
		Proxy mProxy = null;
		Context mContext;


		public NetworkManager (Context context){
			this.mContext = context;
			setDefaultHostnameVerifier ();
		}



		@SuppressWarnings ("deprecation")
		private void detectProxy (){
			ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService (Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo ();
			if (ni != null && ni.isAvailable () && ni.getType () == ConnectivityManager.TYPE_MOBILE){
				String proxyHost = android.net.Proxy.getDefaultHost ();
				int port = android.net.Proxy.getDefaultPort ();
				if (proxyHost != null){
					final InetSocketAddress sa = new InetSocketAddress (proxyHost, port);
					mProxy = new Proxy (Proxy.Type.HTTP, sa);
				}
			}
		}


		private void setDefaultHostnameVerifier (){
			//
			HostnameVerifier hv = new HostnameVerifier (){
				public boolean verify (String hostname, SSLSession session){
					return true;
				}
			};
			HttpsURLConnection.setDefaultHostnameVerifier (hv);
		}



		public String SendAndWaitResponse (String strReqData, String strUrl){
			//
			detectProxy ();
			String strResponse = null;
			ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair> ();
			pairs.add (new BasicNameValuePair ("requestData", strReqData));
			HttpURLConnection httpConnect = null;
			UrlEncodedFormEntity p_entity;
			try{
				p_entity = new UrlEncodedFormEntity (pairs, "utf-8");
				URL url = new URL (strUrl);
				if (mProxy != null){
					httpConnect = (HttpURLConnection) url.openConnection (mProxy);
				} else{
					httpConnect = (HttpURLConnection) url.openConnection ();
				}
				httpConnect.setConnectTimeout (connectTimeout);
				httpConnect.setReadTimeout (readTimeout);
				httpConnect.setDoOutput (true);
				httpConnect.addRequestProperty ("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				httpConnect.connect ();
				OutputStream os = httpConnect.getOutputStream ();
				p_entity.writeTo (os);
				os.flush ();
				InputStream content = httpConnect.getInputStream ();
				strResponse = BaseHelper.convertStreamToString (content);
				BaseHelper.log (TAG, "response " + strResponse);
			} catch (IOException e){
				e.printStackTrace ();
			} finally{
				httpConnect.disconnect ();
			}
			return strResponse;
		}



		public boolean urlDownloadToFile (Context context, String strurl, String path){
			boolean bRet = false;
			//
			detectProxy ();
			try{
				URL url = new URL (strurl);
				HttpURLConnection conn = null;
				if (mProxy != null){
					conn = (HttpURLConnection) url.openConnection (mProxy);
				} else{
					conn = (HttpURLConnection) url.openConnection ();
				}
				conn.setConnectTimeout (connectTimeout);
				conn.setReadTimeout (readTimeout);
				conn.setDoInput (true);
				conn.connect ();
				InputStream is = conn.getInputStream ();
				File file = new File (path);
				file.createNewFile ();
				FileOutputStream fos = new FileOutputStream (file);
				byte[] temp = new byte[1024];
				int i = 0;
				while ((i = is.read (temp)) > 0){
					fos.write (temp, 0, i);
				}
				fos.close ();
				is.close ();
				bRet = true;
			} catch (IOException e){
				e.printStackTrace ();
			}
			return bRet;
		}
	}

	public class ResultChecker {
		public static final int RESULT_INVALID_PARAM = 0;
		public static final int RESULT_CHECK_SIGN_FAILED = 1;
		public static final int RESULT_CHECK_SIGN_SUCCEED = 2;
		String mContent;


		public ResultChecker (String content){
			this.mContent = content;
		}



		int checkSign (){
			int retVal = RESULT_CHECK_SIGN_SUCCEED;
			try{
				JSONObject objContent = BaseHelper.string2JSON (this.mContent, ";");
				String result = objContent.getString ("result");
				result = result.substring (1, result.length () - 1);
				// 获取待签名数据
				int iSignContentEnd = result.indexOf ("&sign_type=");
				String signContent = result.substring (0, iSignContentEnd);
				// 获取签名
				JSONObject objResult = BaseHelper.string2JSON (result, "&");
				String signType = objResult.getString ("sign_type");
				signType = signType.replace ("", "");
				String sign = objResult.getString ("sign");
				sign = sign.replace ("", "");
				// 进行验签 返回验签结果
				if (signType.equalsIgnoreCase ("RSA")){
					if (!Rsa.doCheck (signContent, sign, PartnerConfig.RSA_ALIPAY_PUBLIC))
						retVal = RESULT_CHECK_SIGN_FAILED;
				}
			} catch (Exception e){
				retVal = RESULT_INVALID_PARAM;
				e.printStackTrace ();
			}
			return retVal;
		}
	}

	public static class Rsa {
		public static final String SIGN_ALGORITHMS = "SHA1WithRSA";


		public static String sign (String content, String privateKey){
			String charset = "utf-8";
			try{
				PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec (Base64.decode (privateKey));
				KeyFactory keyf = KeyFactory.getInstance ("RSA");
				PrivateKey priKey = keyf.generatePrivate (priPKCS8);
				java.security.Signature signature = java.security.Signature.getInstance (SIGN_ALGORITHMS);
				signature.initSign (priKey);
				signature.update (content.getBytes (charset));
				byte[] signed = signature.sign ();
				return Base64.encode (signed);
			} catch (Exception e){
				e.printStackTrace ();
			}
			return null;
		}


		public static boolean doCheck (String content, String sign, String publicKey){
			try{
				KeyFactory keyFactory = KeyFactory.getInstance ("RSA");
				byte[] encodedKey = Base64.decode (publicKey);
				PublicKey pubKey = keyFactory.generatePublic (new X509EncodedKeySpec (encodedKey));
				java.security.Signature signature = java.security.Signature.getInstance (SIGN_ALGORITHMS);
				signature.initVerify (pubKey);
				signature.update (content.getBytes ("utf-8"));
				boolean bverify = signature.verify (Base64.decode (sign));
				return bverify;
			} catch (Exception e){
				e.printStackTrace ();
			}
			return false;
		}
	}
}
