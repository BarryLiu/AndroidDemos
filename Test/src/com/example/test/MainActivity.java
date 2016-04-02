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
	// ����İ���
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

		// 1��ȡ����
		vp = (ViewPager) findViewById(R.id.vp);
		// 2.����������
		MyAdapter adapter = new MyAdapter();

		// 3.������
		vp.setAdapter(adapter);

		setLisenter();

	}

	private void setLisenter() {
		/** ����������Ŀ */
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

		// ʹ���һ�����������������ĸ���Ŀ�µ�Ч��
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			/**
			 * �������Ѿ���ѡ��֮��
			 * 
			 * @param position
			 *            ��ѡ�е�λ��
			 */
			@Override
			public void onPageSelected(int position) {
				// ����λ�û�ȡ��ǰӦ�ñ�ѡ�е�radioButton
				RadioButton rb = (RadioButton) rg.getChildAt(position);
				// ���õ�ǰ��button��ѡ��
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

		// �õ�����ĵ������ʾ�İ��� (TextView)
		listLines = new ArrayList<TextView>();
		for (int i = 0; i < listLineIds.length; i++) {
			TextView tv = (TextView) findViewById(listLineIds[i]);
			listLines.add(tv);
		}
	}

	private void initMenu() {
		ListView lv = (ListView) findViewById(R.id.lv_menu);

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		String items[] = { "��ҳ", "������Ϣ", "��Ŀ", "�ҵĶ���", "����" };
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
		Toast.makeText(this, "��ʾ������Ϣ", Toast.LENGTH_SHORT).show();
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
	 * ViewPager ��������,�ڽ����е����һ����ı�
	 */
	class MyAdapter extends PagerAdapter {
		// ��ȡview������
		@Override
		public int getCount() {
			return list.size();
		}

		// �ж�view�Ƿ���һ������
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		// ����ÿһ��View
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// ����ӦΪ�õ�View�ŵ�Vp��
			container.addView(list.get(position));
			// ������ӵ�View
			return list.get(position);
		}

		// ɾ��ViewPager��һ��view
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// super.destroyItem(container, position, object);
			container.removeView(list.get(position));
		}
	}

}
