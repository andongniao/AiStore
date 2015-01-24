package com.youai.aistore.ShopCart;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youai.aistore.BaseActivity;
import com.youai.aistore.ExampleActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.Base;
import com.youai.aistore.Bean.ListShopCartBean;
import com.youai.aistore.Bean.ShopCartBean;
import com.youai.aistore.Mycenter.MyLoginActivity;
import com.youai.aistore.NetInterface.Send;
import com.youai.aistore.View.SwipeMenuListView.SwipeMenu;
import com.youai.aistore.View.SwipeMenuListView.SwipeMenuCreator;
import com.youai.aistore.View.SwipeMenuListView.SwipeMenuItem;
import com.youai.aistore.View.SwipeMenuListView.SwipeMenuListView;
import com.youai.aistore.View.SwipeMenuListView.SwipeMenuListView.OnMenuItemClickListener;
import com.youai.aistore.View.SwipeMenuListView.SwipeMenuListView.OnSwipeListener;

/**
 * 购物车首页
 * 
 * @author Qzr
 * 
 */
public class ShopCartActivity extends BaseActivity implements OnClickListener {
	// ,IXListViewListener{
	private long exitTime = 0;
	private Context context;
	private SwipeMenuListView lv;
	@SuppressWarnings("unused")
	private ImageView isnull_iv;
	private View isnull;
	private ShopCartAdapter adapter;
	private LinearLayout showviewll;
	private Button seeagainbt, goypaybt;
	@SuppressWarnings("unused")
	private TextView tv_topright, tv_gongji;
	private ShopcartInterface inter;
	public static boolean shopcartchaneged;
	private MyTask myTask;
	private ListShopCartBean listbean;
	private Base beanresult;
	private double price;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		context = this;
		goneTopLeft();
		topRightGone();
		setTitleTxt(R.string.shopcart_title);
		setContentXml(R.layout.shopcart);
		init();
		if (Util.detect(context)) {
			myTask = new MyTask(1, 0);
			myTask.execute("");
		} else {
			Util.ShowToast(context, R.string.net_work_is_error);
		}

	}

	private void init() {
		inter = new ShopcartInterface() {

			@Override
			public void delete(ArrayList<ShopCartBean> list, int index) {
				if (Util.detect(context)) {
					myTask = new MyTask(2, 0);
					myTask.setdata(list, index);
					myTask.execute("");
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}

			@Override
			public void add(ArrayList<ShopCartBean> list, int index) {
				if (Util.detect(context)) {
					myTask = new MyTask(0, 1);
					myTask.setdata(list, index);
					myTask.execute("");
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}

			@Override
			public void jian(ArrayList<ShopCartBean> list, int index) {
				// TODO Auto-generated method stub
				if (Util.detect(context)) {
					myTask = new MyTask(0, 0);
					myTask.setdata(list, index);
					myTask.execute("");
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}

		};
		isnull = findViewById(R.id.shopcart_isnull_ll);
		showviewll = (LinearLayout) findViewById(R.id.shopcart_showview_ll);
		showviewll.setVisibility(View.GONE);
		seeagainbt = (Button) findViewById(R.id.shopcart_see_again_bt);
		seeagainbt.setOnClickListener(this);
		goypaybt = (Button) findViewById(R.id.shopcart_gopay_bt);
		goypaybt.setOnClickListener(this);
		tv_gongji = (TextView) findViewById(R.id.shopcart_gongji_tv);
		lv = (SwipeMenuListView) findViewById(R.id.shopcart_listview);
		lv.setFocusable(false);
		lv.setEmptyView(isnull);
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getApplicationContext());
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				deleteItem.setWidth(dp2px(90));
				deleteItem.setIcon(R.drawable.del_icon_normal);
				menu.addMenuItem(deleteItem);
			}
		};
		lv.setMenuCreator(creator);
		lv.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					if (Util.detect(context)) {
						myTask = new MyTask(2, 0);
						myTask.setdata(listbean.getList(), position);
						myTask.execute("");
					} else {
						Util.ShowToast(context, R.string.net_work_is_error);
					}
					break;
				}
			}
		});

		// set SwipeListener
		lv.setOnSwipeListener(new OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}

			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.shopcart_isnull_iv:
		// Util.ShowToast(context, "没有数据");
		// break;
		case R.id.shopcart_see_again_bt:
			ExampleActivity.setCurrentTab(0);
			break;
		case R.id.shopcart_gopay_bt:
			Intent intent = null;
			if (MyApplication.logined) {
				intent = new Intent(ShopCartActivity.this,
						ConsigneeInfoActivity.class);
				intent.putExtra("list", listbean);
			} else {
				intent = new Intent(ShopCartActivity.this,
						MyLoginActivity.class);
			}
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private class MyTask extends AsyncTask<Object, Object, Object> {
		private int index, type, number;
		private ArrayList<ShopCartBean> list;
		private int postion;

		public MyTask(int index, int type) {
			this.index = index;
			this.type = type;
		}

		public void setdata(ArrayList<ShopCartBean> list, int index) {
			this.list = list;
			this.postion = index;
		}

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			// textView.setText("loading...");
			Util.startProgressDialog(context);
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected Object doInBackground(Object... params) {
			try {
				if (index == 1) {
					Send send = new Send(context);
					listbean = send.getShopCartlist(MyApplication.SessionId,
							MyApplication.UserId);
					return listbean;// new String(baos.toByteArray(), "gb2312");
				} else if (index == 2) {
					Send send = new Send(context);
					String rec_id = list.get(postion).getRec_id();
					String session_id = MyApplication.SessionId;
					String user_id = MyApplication.UserId;
					beanresult = send.DeletefromShopCart(rec_id, session_id,
							user_id);
					return beanresult;
				} else {
					Send send = new Send(context);
					int good_id = Integer.parseInt(list.get(postion)
							.getGoods_id());
					if (type == 1) {
						number = 1;
					} else {
						number = -1;
					}
					String session_id = MyApplication.SessionId;
					String user_id = MyApplication.UserId;
					beanresult = send.AddShopCart(good_id, number, session_id,
							user_id);
					return beanresult;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Object... progresses) {
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(Object result) {
			Util.stopProgressDialog();
			if (index == 1) {
				listbean = (ListShopCartBean) result;
				if (listbean != null) {
					if (listbean.getCode() == 200) {
						UpUI();
						price = Double.parseDouble(listbean.getCount_price());
						tv_gongji
								.setText("￥" + listbean.getCount_price() + "元");
					}else if(listbean.getCode() == 500){
						Util.ShowToast(context, R.string.net_work_is_error);
					} else {
						list = null;
						list = new ArrayList<ShopCartBean>();
						if (adapter != null) {
							adapter.setdata(list);
							adapter.notifyDataSetChanged();
						} else {
							adapter = new ShopCartAdapter(context, list, inter);
							lv.setAdapter(adapter);
						}
						showviewll.setVisibility(View.GONE);
						Util.ShowToast(context, listbean.getMsg());
					}
				} else {
					list = null;
					list = new ArrayList<ShopCartBean>();
					if (adapter != null) {
						adapter.setdata(list);
						adapter.notifyDataSetChanged();
					} else {
						adapter = new ShopCartAdapter(context, list, inter);
						lv.setAdapter(adapter);
					}
					showviewll.setVisibility(View.GONE);
					Util.ShowToast(context, R.string.net_work_is_error);
				}

			} else if (index == 2) {
				beanresult = (Base) result;
				if (beanresult != null) {
					if (beanresult.getCode() == 200) {
						list.get(postion).setGoods_number(
								String.valueOf(Integer.parseInt(list.get(
										postion).getGoods_number())
										+ number));
						price -= Double.parseDouble(list.get(postion)
								.getGoods_price())
								* Integer.parseInt(list.get(postion)
										.getGoods_number());
						tv_gongji.setText(String.valueOf(price));
						list.remove(list.get(postion));
						adapter.setdata(list);
						adapter.notifyDataSetChanged();
						if(list.size()==0){
							showviewll.setVisibility(View.GONE);
						}
					}else if(beanresult.getCode() == 500){
						Util.ShowToast(context, R.string.net_work_is_error);
					}else{
						Util.ShowToast(context,beanresult.getMsg());
					}
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			} else {
				beanresult = (Base) result;
				if (beanresult != null) {
					if (beanresult.getCode() == 200) {
						list.get(postion).setGoods_number(
								String.valueOf(Integer.parseInt(list.get(
										postion).getGoods_number())
										+ number));
						adapter.setdata(list);
						adapter.notifyDataSetChanged();
						if (type == 1) {
							price += Double.parseDouble(list.get(postion)
									.getGoods_price());
						} else {
							price -= Double.parseDouble(list.get(postion)
									.getGoods_price());
						}
						tv_gongji.setText(String.valueOf(price));
					}else if(beanresult.getCode() == 500){
						Util.ShowToast(context, R.string.net_work_is_error);
					} else {
						Util.ShowToast(context, beanresult.getMsg());
					}
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			Util.stopProgressDialog();
		}
	}

	public interface ShopcartInterface {
		void delete(ArrayList<ShopCartBean> list, int index);

		void add(ArrayList<ShopCartBean> list, int index);

		void jian(ArrayList<ShopCartBean> list, int index);
	}

	private void UpUI() {
		if (adapter == null) {
			adapter = new ShopCartAdapter(context, listbean.getList(), inter);
			lv.setAdapter(adapter);
		} else {
			adapter.setdata(listbean.getList());
			adapter.notifyDataSetChanged();
		}
		if (listbean != null && listbean.getList().size() > 0) {
			showviewll.setVisibility(View.VISIBLE);
		} else {
			showviewll.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (listbean == null) {
			// if(event.getAction()==MotionEvent.ACTION_DOWN &&
			// event.getAction()==MotionEvent.ACTION_UP){
			if (Util.detect(context)) {
				myTask = new MyTask(1, 0);
				myTask.execute("");
			} else {
				Util.ShowToast(context, R.string.net_work_is_error);
			}
			// }
		} else if (listbean != null) {
			if (listbean.getList() == null
					|| (listbean.getList() != null && listbean.getList().size() == 0)) {
				// if(event.getAction()==MotionEvent.ACTION_DOWN &&
				// event.getAction()==MotionEvent.ACTION_UP){
				if (Util.detect(context)) {
					myTask = new MyTask(1, 0);
					myTask.execute("");
				} else {
					Util.ShowToast(context, R.string.net_work_is_error);
				}
			}
		}

		// }
		return super.onTouchEvent(event);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (shopcartchaneged) {
			if (Util.detect(context)) {
				myTask = new MyTask(1, 0);
				myTask.execute("");
			} else {
				Util.ShowToast(context, R.string.net_work_is_error);
			}
			shopcartchaneged = false;
		}
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

}
