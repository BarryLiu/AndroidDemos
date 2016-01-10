package io.barryliu.fileediter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 文件管理器<br>
 * 对手机目录的查看复制粘贴删除查看等基本的操作
 * 
 * @author Barry
 */
public class MainActivity extends Activity {
	// 需要操作的数据
	private List<FileBean> datas = new ArrayList<FileBean>();
	// 页面显示的路径
	private TextView tv_path;
	// 显示文件夹或文件
	private ListView lv_listView;
	// Menu的操作
	private GridView gv_menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		initMenu();
		initData("/");
		initEvent();
	}

	/** 初始化GridView 为其添加Menu的操作 */
	private void initMenu() {
		// 1准备数据
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < ResUtils.menuIds.length; i++) {
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("name", ResUtils.menuNames[i]);
			maps.put("icon", ResUtils.menuIds[i]);
			datas.add(maps);
		}

		// 2.定义适配器
		SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, datas,
				R.layout.gv_layout, new String[] { "name", "icon" }, new int[] {
						R.id.tv_name, R.id.iv_resId });

		// 设置适配器
		gv_menu.setAdapter(adapter);

	}
	
	/** 设置ListView 和 GridView的点击事件 */
	private void initEvent() {
		// 点击listview的item
		setListViewItemClickLisenter();
		// listView 的长按删除或复制
		setListViewLongClickLisenter();

		// 点击GridView的item
		setGridViewClickLisenter();
	}

	/**点击GridView的item*/
	private void setGridViewClickLisenter() {
		gv_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:// 点击手机
					String phoneCard = FileManager.getPhoneCard();
					if (phoneCard == null)
						showTips("sd卡不存在");
					else
						initData(phoneCard);
					break;
				case 1:// 点击sd卡
					String sdCardPath = FileManager.getSdCard();
					if (sdCardPath == null)
						showTips("sd卡不存在");
					else
						initData(sdCardPath);
					break;
				case 2:// 新建
					Builder builder = new Builder(MainActivity.this);
					builder.setTitle("操作");
					final View v = LayoutInflater.from(MainActivity.this)
							.inflate(R.layout.dailog_layout, null);
					builder.setView(v);

					builder.setNegativeButton("取消", null);
					builder.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							TextView et_name = (TextView) v
									.findViewById(R.id.et_name);
							RadioGroup rg_new = (RadioGroup) v
									.findViewById(R.id.rg_new);
							boolean flag = true;
							switch (rg_new.getCheckedRadioButtonId()) {
							case R.id.rb_file:
								flag = FileManager.createFile(et_name.getText()
										.toString());
								break;
							case R.id.rb_folder:
								flag = FileManager.createFolder(et_name
										.getText().toString());
								break;
							}
							if (flag) {
								showTips("创建成功");
								initData(FileManager.CurrPath);
							} else
								showTips("创建失败，可能是权限不够");
						}
					});
					builder.show();
					break;
				case 3:// 粘贴
					if (FileManager.CopyPath == null)
						showTips("没有要复制的内容");
					else if (FileManager.getInstance().copyFile()) {
						showTips("粘贴成功");
						initData(FileManager.CurrPath);
					} else
						showTips("粘贴失败");
					break;
				case 4:// 退出
					Builder builder2 = new Builder(MainActivity.this);
					builder2.setTitle("退出吗？");
					builder2.setPositiveButton("取消", null)
							.setNegativeButton("确定", new OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									finish();
								}
							}).show();
					break;

				}
			}
		});
	}
	/**listView 的长按删除或复制*/
	private void setListViewLongClickLisenter() {
		lv_listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				FileAdapter adapter = (FileAdapter) parent.getAdapter();
				final FileBean fileBean = (FileBean) adapter.getItem(position);
				final File file = new File(fileBean.getFilePath());
				Builder builder = new Builder(MainActivity.this);
				builder.setTitle("操作");
				builder.setItems(new String[] { "复制", "删除", "重命名" },
						
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int witch) {
								
								switch (witch) {
								case 0:// 复制
									FileManager.CopyPath = fileBean
											.getFilePath();
									showTips("复制");
									break;
								case 1:// 删除
									if (FileManager.getInstance().deleteFile(
											file)) {
										showTips("删除成功");
										initData(FileManager.CurrPath);
									} else
										showTips("删除失败");
									break;
								case 2:// 重命名
									if(!file.canWrite()){
										showTips("权限不足");
										return;
									}
									
									//inputNewName(file);
									final EditText et_nFileName = new EditText(
											MainActivity.this);
									et_nFileName.setFocusable(true);
									    newFile =new File(file.getPath(),file.getName());
									AlertDialog.Builder builder = new AlertDialog.Builder(
											MainActivity.this);
									builder.setTitle("新文件名");
									builder.setView(et_nFileName);
									builder.setPositiveButton("确定",
											new DialogInterface.OnClickListener() {

												public void onClick(DialogInterface dialog,int which) {
													String newName = et_nFileName.getText().toString();
													newFile =new File(file.getParentFile().getPath(),newName);
													if (FileManager.getInstance().reNameFile(file,newFile)) {
														initData(FileManager.CurrPath);
													} else
														showTips("操作失败");
												}
											});
									builder.show();
									
									break;
								}
							}

							
						}).show();
				return true;
			}
		});
	}
	//文件需要重命名暂时存放的文件
	File newFile ;
	 
	/**点击listview的item*/
	private void setListViewItemClickLisenter() {
		lv_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FileAdapter adapter = (FileAdapter) parent.getAdapter();
				FileBean fb = (FileBean) adapter.getItem(position);

				File file = new File(fb.getFilePath());
				if (file.canRead()) {
					if (file.isDirectory())
						initData(file.getPath());
					else {
						Intent intent = ResUtils.getByMIME_Map(file);
						if (intent != null)
							try {
								startActivity(intent);
							} catch (Exception e) {
								showTips("没有打开它的程序");
							}
						else
							showTips("这种文件不能打开");
					}
				} else {
					showTips("没有读取的权限");
				}
			}
		});
	}

	/** 打印输出d */
	private void showTips(String string) {
		Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
	}

	/** 重新得到文件目录中的数据并对其进行刷新 */
	private void initData(String path) {
		tv_path.setText(path);
		FileManager.CurrPath = path;

		datas = FileManager.getFileLists(path);
		FileAdapter adapter = new FileAdapter(datas, MainActivity.this);
		lv_listView.setAdapter(adapter);

	}

	/** 找到控件并为其赋值 */
	private void initView() {
		tv_path = (TextView) this.findViewById(R.id.tv_path);
		lv_listView = (ListView) this.findViewById(R.id.lv_listView);
		gv_menu = (GridView) this.findViewById(R.id.gv_menu);

	}

}
