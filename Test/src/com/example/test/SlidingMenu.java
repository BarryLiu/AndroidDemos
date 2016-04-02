package com.example.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * �໬�ο�
 * http://blog.csdn.net/lmj623565791/article/details/39185641
 * @author Barry
 *
 */
public class SlidingMenu extends HorizontalScrollView {
	/**
	 * ��Ļ���
	 */
	private int mScreenWidth;
	/**
	 * dp
	 */
	private int mMenuRightPadding = 50;
	/**
	 * �˵��Ŀ��
	 */
	private int mMenuWidth;
	private int mHalfMenuWidth;

	private boolean once;

	public int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getWidth();
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScreenWidth = getScreenWidth(context);// ScreenUtils.getScreenWidth(context);
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScreenWidth = getScreenWidth(context);// ScreenUtils.getScreenWidth(context);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.SlidingMenu_rightPadding:
				// Ĭ��50
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50f,
								getResources().getDisplayMetrics()));// Ĭ��Ϊ10DP
				break;
			}
		}
		a.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/**
		 * ��ʾ������һ�����
		 */
		if (!once) {
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);
			ViewGroup content = (ViewGroup) wrapper.getChildAt(1);
			// dp to px
			mMenuRightPadding = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding, content
							.getResources().getDisplayMetrics());

			mMenuWidth = mScreenWidth - mMenuRightPadding;
			mHalfMenuWidth = mMenuWidth / 2;
			menu.getLayoutParams().width = mMenuWidth;
			content.getLayoutParams().width = mScreenWidth;

		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			// ���˵�����
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		// Upʱ�������жϣ������ʾ������ڲ˵����һ������ȫ��ʾ����������
		/*
		 * case MotionEvent.ACTION_UP: int scrollX = getScrollX(); if (scrollX >
		 * mHalfMenuWidth) this.smoothScrollTo(mMenuWidth, 0); else
		 * this.smoothScrollTo(0, 0); return true;
		 */
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if (scrollX > mHalfMenuWidth) {
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
			} else {
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}
	boolean isOpen = false;
	/**
	 * �򿪲˵�
	 */
	public void openMenu() {
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}
	/**
	 * �رղ˵�
	 */
	public void closeMenu() {
		if (isOpen) {
			this.smoothScrollTo(mMenuWidth, 0);
			isOpen = false;
		}
	}

	/**
	 * �л��˵�״̬
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}

}
