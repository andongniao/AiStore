package com.youai.aistore;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * ���ͼƬ�Զ��ֲ��ؼ�</br>
 * 
 * <pre>
 *   ����ViewPager��ָʾ����һ���ֲ��ؼ�����Ҫ����һ�㳣���Ĺ��ͼƬ�ֲ��������Զ��ֲ����ֶ��ֲ����� 
 *   �����ṩ{@link #startImageCycle() } \ {@link #pushImageCycle() }���ַ�����������Activity���ɼ�֮ʱ��ʡ��Դ��
 *   ��Ϊ�Զ��ֲ���Ҫ���п��ƣ��������ڴ����
 * </pre>
 * 
 * @author minking
 */
public class ImageCycleView extends LinearLayout {
	private long time = 3000;

	/**
	 * ������
	 */
	private Context mContext;

	/**
	 * ͼƬ�ֲ���ͼ
	 */
	private ViewPager mAdvPager = null;

	/**
	 * ����ͼƬ��ͼ������
	 */
	private ImageCycleAdapter mAdvAdapter;

	/**
	 * ͼƬ�ֲ�ָʾ���ؼ�
	 */
	private ViewGroup mGroup;

	/**
	 * ͼƬ�ֲ�ָʾ��-��ͼ
	 */
	private ImageView mImageView = null;

	/**
	 * ����ͼƬָʾ��-��ͼ�б�
	 */
	private ImageView[] mImageViews = null;

	/**
	 * ͼƬ������ǰͼƬ�±�
	 */
	private int mImageIndex = 0;

	/**
	 * �ֻ��ܶ�
	 */
	private float mScale;

	/**
	 * @param context
	 */
	public ImageCycleView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ImageCycleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mScale = context.getResources().getDisplayMetrics().density;
		LayoutInflater.from(context).inflate(R.layout.ad_cycle_view, this);
		mAdvPager = (ViewPager) findViewById(R.id.adv_pager);
		mAdvPager.setOnPageChangeListener(new GuidePageChangeListener());
		mAdvPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_UP:
						// ��ʼͼƬ����
						startImageTimerTask();
						break;
					default:
						// ֹͣͼƬ����
						stopImageTimerTask();
						break;
				}
				return false;
			}
		});
		// ����ͼƬ����ָʾ����ͼ
		mGroup = (ViewGroup) findViewById(R.id.viewGroup);
	}

	/**
	 * װ��ͼƬ����
	 * 
	 * @param imageUrlList
	 * @param imageCycleViewListener
	 */
	public void setImageResources(ArrayList<String> imageUrlList, ImageCycleViewListener imageCycleViewListener) {
		// �����������ͼ
		mGroup.removeAllViews();
		// ͼƬ�������
		final int imageCount = imageUrlList.size();
		mImageViews = new ImageView[imageCount];
		for (int i = 0; i < imageCount; i++) {
			mImageView = new ImageView(mContext);
			int imageParams = (int) (mScale * 10 + 0.5f);// XP��DPת������Ӧ��ͬ�ֱ���
			int imagePadding = (int) (mScale * 15 + 0.5f);
			mImageView.setLayoutParams(new LayoutParams(imageParams, imageParams));
			mImageView.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
			mImageViews[i] = mImageView;
			if (i == 0) {
				mImageViews[i].setBackgroundResource(R.drawable.cor_red);
			} else {
				mImageViews[i].setBackgroundResource(R.drawable.cor_gray);
			}
			mGroup.addView(mImageViews[i]);
		}
		mAdvAdapter = new ImageCycleAdapter(mContext, imageUrlList, imageCycleViewListener);
		mAdvPager.setAdapter(mAdvAdapter);
		startImageTimerTask();
	}

	/**
	 * ��ʼ�ֲ�(�ֶ������Զ��ֲ���񣬱�����Դ����)
	 */
	public void startImageCycle() {
		startImageTimerTask();
	}

	/**
	 * ��ͣ�ֲ��������ڽ�ʡ��Դ
	 */
	public void pushImageCycle() {
		stopImageTimerTask();
	}

	/**
	 * ��ʼͼƬ��������
	 */
	private void startImageTimerTask() {
		stopImageTimerTask();
		// ͼƬÿ3�����һ��
		mHandler.postDelayed(mImageTimerTask, time);
	}

	/**
	 * ֹͣͼƬ��������
	 */
	public void stopImageTimerTask() {
		mHandler.removeCallbacks(mImageTimerTask);
	}
	public void settime(long time){
		this.time = time;
	}

	private Handler mHandler = new Handler();

	/**
	 * ͼƬ�Զ��ֲ�Task
	 */
	private Runnable mImageTimerTask = new Runnable() {

		@Override
		public void run() {
			if (mImageViews != null) {
				// �±����ͼƬ�б���˵���ѹ��������һ��ͼƬ,�����±�
				if ((++mImageIndex) == mImageViews.length) {
					mImageIndex = 0;
				}
				mAdvPager.setCurrentItem(mImageIndex);
			}
		}
	};

	/**
	 * �ֲ�ͼƬ״̬������
	 * 
	 * @author minking
	 */
	private final class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE)
				startImageTimerTask(); // ��ʼ�´μ�ʱ
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int index) {
			// ���õ�ǰ��ʾ��ͼƬ�±�
			mImageIndex = index;
			// ����ͼƬ����ָʾ������
			mImageViews[index].setBackgroundResource(R.drawable.cor_red);
			for (int i = 0; i < mImageViews.length; i++) {
				if (index != i) {
					mImageViews[i].setBackgroundResource(R.drawable.cor_gray);
				}
			}

		}

	}

	private class ImageCycleAdapter extends PagerAdapter {

		/**
		 * ͼƬ��ͼ�����б�
		 */
		private ArrayList<ImageView> mImageViewCacheList;

		/**
		 * ͼƬ��Դ�б�
		 */
		private ArrayList<String> mAdList = new ArrayList<String>();

		/**
		 * ���ͼƬ���������
		 */
		private ImageCycleViewListener mImageCycleViewListener;

		private Context mContext;

		public ImageCycleAdapter(Context context, ArrayList<String> adList, ImageCycleViewListener imageCycleViewListener) {
			mContext = context;
			mAdList = adList;
			mImageCycleViewListener = imageCycleViewListener;
			mImageViewCacheList = new ArrayList<ImageView>();
		}

		@Override
		public int getCount() {
			return mAdList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			String imageUrl = mAdList.get(position);
			ImageView imageView = null;
			if (mImageViewCacheList.isEmpty()) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				
			} else {
				imageView = mImageViewCacheList.remove(0);
			}
			// ����ͼƬ�������
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mImageCycleViewListener.onImageClick(position, v);
				}
			});
			imageView.setTag(imageUrl);
			container.addView(imageView);
			mImageCycleViewListener.displayImage(imageUrl, imageView);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView view = (ImageView) object;
			container.removeView(view);
			mImageViewCacheList.add(view);
		}

	}

	/**
	 * �ֲ��ؼ��ļ����¼�
	 * 
	 * @author minking
	 */
	public static interface ImageCycleViewListener {

		/**
		 * ����ͼƬ��Դ
		 * 
		 * @param imageURL
		 * @param imageView
		 */
		public void displayImage(String imageURL, ImageView imageView);

		/**
		 * ����ͼƬ�¼�
		 * 
		 * @param position
		 * @param imageView
		 */
		public void onImageClick(int position, View imageView);
	}

}