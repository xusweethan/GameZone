package com.playing.gamezone;

import java.util.ArrayList;
import java.util.List;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GuideActivity extends Activity implements OnClickListener,
		OnPageChangeListener {

	private ViewPager vp;
	private List<View> views;
	private GuideAdapter guideadapter;
	private Button bt_coming;
	// ����ͼƬ��Դ
	private static final int[] pics = { R.drawable.guide1, R.drawable.guide2 };
	// �ײ�С��
	private ImageView[] dots;
	// ��¼��ǰѡ��λ��
	private int currentIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		views = new ArrayList<View>();
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		// ��ʼ������ͼƬ
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			iv.setImageResource(pics[i]);
			views.add(iv);
		}
		guideadapter = new GuideAdapter(views);
		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(guideadapter);
		vp.setOnPageChangeListener(this);
		initDots();
		bt_coming = (Button) findViewById(R.id.bt_coming);
		bt_coming.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				GuideActivity.this.finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {// ������״̬ʱ����
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {// ����ǰ���汻����ʱ����
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {// ���µ�ҳ�汻ѡ��ʱ����
		// ���õײ�С��Ϊѡ��״̬
		setCurDot(arg0);
		if (arg0 == 1) {
			bt_coming.setVisibility(View.VISIBLE);
		} else {
			bt_coming.setVisibility(View.GONE);
		}
	}

	private void initDots() {
		LinearLayout linear_vp = (LinearLayout) findViewById(R.id.linear_viewpager);
		dots = new ImageView[pics.length];
		// ѭ��ȡ�õײ�С��
		for (int i = 0; i < pics.length; i++) {
			dots[i] = (ImageView) linear_vp.getChildAt(i);
			dots[i].setEnabled(true);// ��Ϊ��ɫ
			dots[i].setOnClickListener(this);
			dots[i].setTag(i);// ����λ��tag,�����뵱����λ��
		}
		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// ѡ��Ϊ��ɫ
	}

	// ���õ�ǰ����ҳ
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}
		vp.setCurrentItem(position);
	}

	// ���õ�ǰ����ҳС��
	private void setCurDot(int position) {
		if (position < 0 || position > pics.length - 1
				|| currentIndex == position) {
			return;
		}
		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);
		currentIndex = position;
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this); // ��Ӵ˷���
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // ��Ӵ˷���
	}
}
