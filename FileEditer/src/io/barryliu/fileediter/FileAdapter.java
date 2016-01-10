package io.barryliu.fileediter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 文件管理器的适配器
 * @author Barry
 *
 */
public class FileAdapter extends BaseAdapter {
	private List<FileBean> datas;
	private Context context;

	public FileAdapter(List<FileBean> datas, Context context) {
		super();
		this.datas = datas;
		this.context = context;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.file_layout, null);
			holder.iv_res = (ImageView) convertView.findViewById(R.id.iv_resId);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		FileBean fb = datas.get(position);
		holder.iv_res.setImageResource(ResUtils.getIconRes(fb));
		holder.tv_name.setText(fb.getFileName());

		convertView.setTag(holder);
		return convertView;
	}

	private ViewHolder holder;

	private class ViewHolder {
		private ImageView iv_res;
		private TextView tv_name;
	}
}
