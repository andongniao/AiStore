package com.youai.aistore.Product;

import java.util.ArrayList;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youai.aistore.R;
import com.youai.aistore.Util;
import com.youai.aistore.Bean.CommentsBean;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ”√ªß∆¿¬€Adapter
 * 
 * @author Qzr
 * 
 */
@SuppressLint("InflateParams")
public class UserCommentAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private ArrayList<CommentsBean> list;
	private LayoutInflater inflater;
	private UserCommentItem commentItem;

	public UserCommentAdapter(Context context, ArrayList<CommentsBean> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);

	}

	public void setdata(ArrayList<CommentsBean> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list != null ? list.size() : 0;
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
			commentItem = new UserCommentItem();
			arg1 = inflater.inflate(R.layout.product_comment_item, null);
			commentItem.iv = (ImageView) arg1
					.findViewById(R.id.product_comment_item_iv);
			commentItem.tv_username = (TextView) arg1
					.findViewById(R.id.product_comment_item_name_tv);
			commentItem.tv_fans = (TextView) arg1
					.findViewById(R.id.product_comment_item_fans_tv);
			commentItem.tv_time = (TextView) arg1
					.findViewById(R.id.product_comment_item_time_tv);
			commentItem.tv_comment_content = (TextView) arg1
					.findViewById(R.id.product_comment_item_content_tv);

			arg1.setTag(commentItem);
		} else {
			commentItem = (UserCommentItem) arg1.getTag();
		}

		// TODO
		commentItem.iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		ImageLoader.getInstance().displayImage(list.get(arg0).getTouxiang(),
				commentItem.iv);
		commentItem.tv_username.setText(list.get(arg0).getYonghuming());
		commentItem.tv_fans.setText(list.get(arg0).getFensi());
		commentItem.tv_comment_content.setText(list.get(arg0).getContent());
		long t = Long.parseLong(list.get(arg0).getAdd_time());
		String time = Util.getTimeForData("yyyy-MM-dd", t);
		commentItem.tv_time.setText(time);

		return arg1;
	}

	class UserCommentItem {
		public ImageView iv;
		public TextView tv_username, tv_fans, tv_comment_content, tv_time;
	}

}
