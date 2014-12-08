package com.hx.intropage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.hx.intropage.IntroPageAdapter;

public class MainActivity extends Activity implements OnPageChangeListener,
		OnClickListener {

	private List<View> mViews = new ArrayList<View>();
	private static int[] mIndexDrawable = { R.drawable.guide1,
			R.drawable.guide2, R.drawable.guide3, R.drawable.guide4,
			R.drawable.guide5 };

	private ImageView[] mIndexImages = null;
	private int mCurrentIndex = 0;
	private ViewPager mViewPager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		initIndexImages();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		setIndexImage(arg0);
	}

	@Override
	public void onClick(View v) {
		ImageView iv = (ImageView) v;
		int index = (Integer) iv.getTag();
		if (mCurrentIndex != index) {
			setIndexImage(index);
			mViewPager.setCurrentItem(mCurrentIndex);
		}
	}

	private void initViews() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < mIndexDrawable.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(params);
			imageView.setImageResource(mIndexDrawable[i]);
			mViews.add(imageView);
		}

		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mViewPager.setAdapter(new IntroPageAdapter(mViews));
		mViewPager.setOnPageChangeListener(this);
	}

	private void initIndexImages() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.index_layout);
		mIndexImages = new ImageView[mIndexDrawable.length];
		for (int i = 0; i < mIndexDrawable.length; i++) {
			mIndexImages[i] = (ImageView) ll.getChildAt(i);
			mIndexImages[i].setEnabled(true);
			mIndexImages[i].setOnClickListener(this);
			mIndexImages[i].setTag(i);
		}
		mIndexImages[mCurrentIndex].setEnabled(false);
	}

	private void setIndexImage(int index) {
		if (mCurrentIndex != index) {
			mIndexImages[index].setEnabled(false);
			mIndexImages[mCurrentIndex].setEnabled(true);
			mCurrentIndex = index;
		}
	}
}
