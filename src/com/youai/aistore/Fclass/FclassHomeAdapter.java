package com.youai.aistore.Fclass;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.youai.aistore.MainActivity;
import com.youai.aistore.MyApplication;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Home.MyGridview;

@SuppressLint("InflateParams")
public class FclassHomeAdapter extends BaseExpandableListAdapter {
	@SuppressWarnings("unused")
	private MyGridview myGridview;
	private LayoutInflater inflater;
	private Context context;
	private ArrayList<String> groupname;
	private ArrayList<Integer> groumimg;
	private ArrayList<ArrayList<String>> childname;
	private MyHalderLabel halderLabel;
	private MyHalderItem halderItem;
	private GridviewItem gridviewItem;

	public FclassHomeAdapter(Context context, ArrayList<String> groupname,
			ArrayList<Integer> groumimg, ArrayList<ArrayList<String>> childname) {
		this.context = context;
		this.groupname = groupname;
		this.groumimg = groumimg;
		this.childname = childname;
		inflater = LayoutInflater.from(context);

	}

	@Override
	public  Object getChild(int arg0, int arg1) {
		return childname.get(arg0).get(arg1);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2,
			View convertView, ViewGroup arg4) {
		if (convertView == null) {
			halderItem = new MyHalderItem();
			// inflate载入界面，
			convertView = inflater.inflate(R.layout.fclass_home_gridview, null);
			halderItem.myGridview = (MyGridview) convertView
					.findViewById(R.id.fclass_home_gridview);
			convertView.setTag(halderItem);
		} else {
			halderItem = (MyHalderItem) convertView.getTag();
		}
		halderItem.myGridview.setAdapter(new MyGridviewAdapter(arg0));// 子类添加适配器
		halderItem.myGridview.setOnItemClickListener(new MygridviewItenlistener(arg0));// 子类点击事件
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int arg0) {
		return 1;
	}

	@Override
	public Object getGroup(int arg0) {
		return groupname.get(arg0);
	}

	@Override
	public int getGroupCount() {
		return groupname.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View convertView,
			ViewGroup arg3) {
		if (convertView == null) {
			halderLabel = new MyHalderLabel();
			convertView = inflater
					.inflate(R.layout.fclass_home_groupitem, null);
			halderLabel.tv = (TextView) convertView.findViewById(R.id.group_tv);
			halderLabel.iv = (ImageView) convertView
					.findViewById(R.id.group_img);
			convertView.setTag(halderLabel);
		} else {
			halderLabel = (MyHalderLabel) convertView.getTag();
		}
		// ImageView groupImg = (ImageView) convertView
		// .findViewById(R.id.group_img);
		// TextView groupTv = (TextView)
		// convertView.findViewById(R.id.group_tv);
		halderLabel.iv.setImageResource(groumimg.get(arg0));// 父类添加图片
		halderLabel.tv.setText(groupname.get(arg0).toString());// 父类添加文字
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}

	class MyGridviewAdapter extends BaseAdapter {
		private int postion;

		public MyGridviewAdapter(int postion) {
			this.postion = postion;
		}

		@Override
		public int getCount() {
			return childname.get(postion).size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			if (arg1 == null) {
				gridviewItem = new GridviewItem();
				arg1 = inflater.inflate(R.layout.fclass_home_childitem, null);
				gridviewItem.gridviewtv = (TextView) arg1
						.findViewById(R.id.child_tv);
				arg1.setTag(gridviewItem);
			} else {
				gridviewItem = (GridviewItem) arg1.getTag();
			}
			if (arg0 < childname.get(postion).size()) {
				gridviewItem.gridviewtv.setText(childname.get(postion)
						.get(arg0));
			}
			return arg1;
		}

	}
	/*
	 * 适配器，子类点击事件
	 */
	class MygridviewItenlistener implements OnItemClickListener {
		private int index;//

		public MygridviewItenlistener(int index) {
			this.index = index;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			/*
			 * 女性分类下点击事件
			 */
			if(groupname.get(index).equals("女性")){
/*				for(int i =1;i<=childname[){
					
				}*/
				switch (arg2) {
				case 0 :				
					Intent intent = new Intent(context,
							FclassFristViewActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					// groupname数组传值给FclassFristViewActivity的标题
					intent.putExtra("title", groupname.get(index).toString());
					intent.putExtra("listindex", 0);
					System.out.println(arg2);
					context.startActivity(intent);
					Util.ShowToast(context, "点击了" + childname.get(index).get(0));
					break;
				case 1:
					Intent intent1 = new Intent(context, FclassMoreActivity.class);
					intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent1.putExtra("title", childname.get(index).get(arg2).toString());
					intent1.putExtra("id", MyApplication.woman_av);
					System.out.println(arg2);
					context.startActivity(intent1);
					Util.ShowToast(context, "点击了" + childname.get(index).get(arg2));
					break;
				case 2:
					Intent intent2 = new Intent(context, FclassMoreActivity.class);
					intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent2.putExtra("title", childname.get(index).get(arg2)
							.toString());
					intent2.putExtra("id", MyApplication.woman_fangzhenyangjv);
					context.startActivity(intent2);
					Util.ShowToast(context, "点击了" + childname.get(index).get(arg2));
					break;
				case 3:
					Intent intent3 = new Intent(context, FclassMoreActivity.class);
					intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent3.putExtra("title", childname.get(index).get(arg2)
							.toString());
					intent3.putExtra("id", MyApplication.woman_qingqvtiaodan);
					context.startActivity(intent3);
					break;
				case 4:
					Intent intent4 = new Intent(context, FclassMoreActivity.class);
					intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent4.putExtra("title", childname.get(index).get(arg2)
							.toString());
					intent4.putExtra("id", MyApplication.woman_shensuozhuanhzu);
					context.startActivity(intent4);
					break;
				case 5:
					Intent intent5 = new Intent(context, FclassMoreActivity.class);
					intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent5.putExtra("title", childname.get(index).get(arg2)
							.toString());
					intent5.putExtra("id", MyApplication.woman_hulibaojian);
					context.startActivity(intent5);
					break;
				case 6:
					Intent intent6 = new Intent(context, FclassMoreActivity.class);
					intent6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent6.putExtra("title", childname.get(index).get(arg2)
							.toString());
					intent6.putExtra("id", MyApplication.woman_otherwoman);
					context.startActivity(intent6);
					break;

				default:
					break;
				}
			}
			/*
			 * 男性分类下点击事件
			 */
			else if(groupname.get(index).equals("男性")){
					switch (arg2) {
					case 0 :				
						Intent intent = new Intent(context,
								FclassFristViewActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						// groupname数组传值给FclassFristViewActivity的标题
						intent.putExtra("title", groupname.get(index).toString());
						intent.putExtra("listindex", 1);
						System.out.println(arg2);
						context.startActivity(intent);
						Util.ShowToast(context, "点击了" + childname.get(index).get(0));
						break;
					case 1:
						Intent intent1 = new Intent(context, FclassMoreActivity.class);
						intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent1.putExtra("title", childname.get(index).get(arg2).toString());
						intent1.putExtra("id", MyApplication.man_feijibei);
						System.out.println(arg2);
						context.startActivity(intent1);
						Util.ShowToast(context, "点击了" + childname.get(index).get(arg2));
						break;
					case 2:
						Intent intent2 = new Intent(context, FclassMoreActivity.class);
						intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent2.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent2.putExtra("id", MyApplication.man_daomo);
						context.startActivity(intent2);
						Util.ShowToast(context, "点击了" + childname.get(index).get(arg2));
						break;
					case 3:
						Intent intent3 = new Intent(context, FclassMoreActivity.class);
						intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent3.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent3.putExtra("id", MyApplication.man_fuzhu);
						context.startActivity(intent3);
						break;

					default:
						break;
					}
				}

			/*
			 * 内衣分类下点击事件
			 */
			else if(groupname.get(index).equals("内衣")){
					switch (arg2) {
					case 0 :				
						Intent intent = new Intent(context,
								FclassFristViewActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						// groupname数组传值给FclassFristViewActivity的标题
						intent.putExtra("title", groupname.get(index).toString());
						intent.putExtra("listindex", 2);
						System.out.println(arg2);
						context.startActivity(intent);
						Util.ShowToast(context, "点击了" + childname.get(index).get(0));
						break;
					case 1:
						Intent intent1 = new Intent(context, FclassMoreActivity.class);
						intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent1.putExtra("title", childname.get(index).get(arg2).toString());
						intent1.putExtra("id", MyApplication.neiyi_xingganneiyi);
						System.out.println(arg2);
						context.startActivity(intent1);
						Util.ShowToast(context, "点击了" + childname.get(index).get(arg2));
						break;
					case 2:
						Intent intent2 = new Intent(context, FclassMoreActivity.class);
						intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent2.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent2.putExtra("id", MyApplication.neiyi_siwaneiku);
						context.startActivity(intent2);
						Util.ShowToast(context, "点击了" + childname.get(index).get(arg2));
						break;
					case 3:
						Intent intent3 = new Intent(context, FclassMoreActivity.class);
						intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent3.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent3.putExtra("id", MyApplication.neiyi_qingqvshuiyi);
						context.startActivity(intent3);
						break;
					case 4:
						Intent intent4 = new Intent(context, FclassMoreActivity.class);
						intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent4.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent4.putExtra("id", MyApplication.neiyi_zhifuyouhuo);
						context.startActivity(intent4);
						break;
					case 5:
						Intent intent5 = new Intent(context, FclassMoreActivity.class);
						intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent5.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent5.putExtra("id", MyApplication.neiyi_liantiwangyi);
						context.startActivity(intent5);
						break;
					case 6:
						Intent intent6 = new Intent(context, FclassMoreActivity.class);
						intent6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent6.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent6.putExtra("id", MyApplication.neiyi_sandiantoushi);
						context.startActivity(intent6);
						break;	
						
					default:
						break;
					}
				}

			/*
			 * 安全套分类下点击事件
			 */
			else if(groupname.get(index).equals("安全套")){
					switch (arg2) {
					case 0 :				
						Intent intent = new Intent(context,
								FclassFristViewActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						// groupname数组传值给FclassFristViewActivity的标题
						intent.putExtra("title", groupname.get(index).toString());
						intent.putExtra("listindex", 3);
						System.out.println(arg2);
						context.startActivity(intent);
						Util.ShowToast(context, "点击了" + childname.get(index).get(0));
						break;
					case 1:
						Intent intent1 = new Intent(context, FclassMoreActivity.class);
						intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent1.putExtra("title", childname.get(index).get(arg2).toString());
						intent1.putExtra("id", MyApplication.tt_jingdian);
						System.out.println(arg2);
						context.startActivity(intent1);
						Util.ShowToast(context, "点击了" + childname.get(index).get(arg2));
						break;
					case 2:
						Intent intent2 = new Intent(context, FclassMoreActivity.class);
						intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent2.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent2.putExtra("id", MyApplication.tt_yanshi);
						context.startActivity(intent2);
						Util.ShowToast(context, "点击了" + childname.get(index).get(arg2));
						break;
					case 3:
						Intent intent3 = new Intent(context, FclassMoreActivity.class);
						intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent3.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent3.putExtra("id", MyApplication.tt_nvyong);
						context.startActivity(intent3);
						break;
					case 4:
						Intent intent4 = new Intent(context, FclassMoreActivity.class);
						intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent4.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent4.putExtra("id", MyApplication.tt_daxiaohao);
						context.startActivity(intent4);
						break;
					case 5:
						Intent intent5 = new Intent(context, FclassMoreActivity.class);
						intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent5.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent5.putExtra("id", MyApplication.tt_huayang);
						context.startActivity(intent5);
						break;
						
					default:
						break;
					}
				}

			/*
			 * 双人分类下点击事件
			 */
			else if(groupname.get(index).equals("双人")){
					switch (arg2) {
					case 0 :				
						Intent intent = new Intent(context,
								FclassFristViewActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						// groupname数组传值给FclassFristViewActivity的标题
						intent.putExtra("title", groupname.get(index).toString());
						intent.putExtra("listindex", 2);
						System.out.println(arg2);
						context.startActivity(intent);
						Util.ShowToast(context, "点击了" + childname.get(index).get(0));
						break;
					case 1:
						Intent intent1 = new Intent(context, FclassMoreActivity.class);
						intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent1.putExtra("title", childname.get(index).get(arg2).toString());
						intent1.putExtra("id", MyApplication.tosex_zhuqing);
						System.out.println(arg2);
						context.startActivity(intent1);
						Util.ShowToast(context, "点击了" + childname.get(index).get(arg2));
						break;
					case 2:
						Intent intent2 = new Intent(context, FclassMoreActivity.class);
						intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent2.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent2.putExtra("id", MyApplication.tosex_houting);
						context.startActivity(intent2);
						Util.ShowToast(context, "点击了" + childname.get(index).get(arg2));
						break;
					case 3:
						Intent intent3 = new Intent(context, FclassMoreActivity.class);
						intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent3.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent3.putExtra("id", MyApplication.tosex_huantao);
						context.startActivity(intent3);
						break;
					case 4:
						Intent intent4 = new Intent(context, FclassMoreActivity.class);
						intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent4.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent4.putExtra("id", MyApplication.tosex_runhua);
						context.startActivity(intent4);
						break;
					case 5:
						Intent intent5 = new Intent(context, FclassMoreActivity.class);
						intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent5.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent5.putExtra("id", MyApplication.tosex_runhua);
						context.startActivity(intent5);
						break;
					case 6:
						Intent intent6 = new Intent(context, FclassMoreActivity.class);
						intent6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent6.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent6.putExtra("id", MyApplication.tosex_sm);
						context.startActivity(intent6);
						break;	
					case 7:
						Intent intent7 = new Intent(context, FclassMoreActivity.class);
						intent7.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent7.putExtra("title", childname.get(index).get(arg2)
								.toString());
						intent7.putExtra("id", MyApplication.tosex_other);
						context.startActivity(intent7);
						break;	
					default:
						break;
					}
				}



		}//点击事件结束

	}//类结束

	public class MyHalderLabel {
		public TextView tv;
		public ImageView iv;
	}

	public class MyHalderItem {
		public MyGridview myGridview;
	}

	public class GridviewItem {
		public TextView gridviewtv;
	}

}
