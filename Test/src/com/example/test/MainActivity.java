package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private SlidingMenu mMenu;
	// 下面的白线
	private List<TextView> listLines;
	private int[] listLineIds = { R.id.line_group, R.id.line_contact,
			R.id.line_love };
	ViewPager vp;
	RadioGroup rg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mMenu = (SlidingMenu) findViewById(R.id.sm_menu);
		rg = (RadioGroup) findViewById(R.id.rg);
		initMenu();

		initMain();

		// 1获取数据
		vp = (ViewPager) findViewById(R.id.vp);
		// 2.创建适配器
		MyAdapter adapter = new MyAdapter();

		// 3.绑定数据
		vp.setAdapter(adapter);

		setLisenter();

	}

	private void setLisenter() {
		/** 点击上面的栏目 */
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int position = 0;
				switch (checkedId) {
				case R.id.rb_group:
					vp.setCurrentItem(0);
					position = 0;
					break;
				case R.id.rb_contact:
					vp.setCurrentItem(1);
					position = 1;
					break;
				case R.id.rb_love:
					vp.setCurrentItem(2);
					position = 2;
					break;
				}

				for (int i = 0; i < listLines.size(); i++) {
					if (position == i)
						listLines.get(i).setVisibility(View.VISIBLE);
					else
						listLines.get(i).setVisibility(View.INVISIBLE);
				}
			}
		});

		// 使左右滑动做到点击滑动到哪个栏目下的效果
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			/**
			 * 当界面已经被选中之后
			 * 
			 * @param position
			 *            被选中的位置
			 */
			@Override
			public void onPageSelected(int position) {
				// 根据位置获取当前应该被选中的radioButton
				RadioButton rb = (RadioButton) rg.getChildAt(position);
				// 设置当前的button被选中
				rb.setChecked(true);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	private void initMain() {
		View v1 = getLayoutInflater().inflate(R.layout.layout_first, null);
		View v2 = getLayoutInflater().inflate(R.layout.layout_second, null);
		View v3 = getLayoutInflater().inflate(R.layout.layout_third, null);
		list.add(v1);
		list.add(v2);
		list.add(v3);

		// 得到上面的点击后显示的白线 (TextView)
		listLines = new ArrayList<TextView>();
		for (int i = 0; i < listLineIds.length; i++) {
			TextView tv = (TextView) findViewById(listLineIds[i]);
			listLines.add(tv);
		}
	}

	private void initMenu() {
		ListView lv = (ListView) findViewById(R.id.lv_menu);

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		String items[] = { "首页", "个人信息", "栏目", "我的订阅", "设置" };
		String from[] = { "icon", "title" };
		int to[] = { R.id.iv_menu, R.id.tv_menu };

		for (int i = 0; i < items.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(from[0], R.drawable.ic_launcher);
			map.put(from[1], items[i]);
			data.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.lv_menu,
				from, to);
		lv.setAdapter(adapter);

	}

	public void toggleMenu(View view) {
		mMenu.toggle();
	}

	public void showDetail(View view) {
		Toast.makeText(this, "显示个人信息", Toast.LENGTH_SHORT).show();
	}
	public void click1(View view) {
		Toast.makeText(this, "click1...", Toast.LENGTH_SHORT).show();
	}
	public void click2(View view) {
		Toast.makeText(this, "click2...", Toast.LENGTH_SHORT).show();
	}
	public void click3(View view) {
		Toast.makeText(this, "click3...", Toast.LENGTH_SHORT).show();
	}
	

	List<View> list = new ArrayList<View>();

	/*
	 * ViewPager 的适配器,在界面中的左右滑动改变
	 */
	class MyAdapter extends PagerAdapter {
		// 获取view的数量
		@Override
		public int getCount() {
			return list.size();
		}

		// 判断view是否是一个对象
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		// 创建每一个View
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 讲对应为置的View放到Vp中
			container.addView(list.get(position));
			// 返回添加的View
			return list.get(position);
		}

		// 删除ViewPager的一个view
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// super.destroyItem(container, position, object);
			container.removeView(list.get(position));
		}
	}

}
