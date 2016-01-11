package io.barryLiu.accountingmanager.adapter;

import io.barryLiu.accountingmanager.R;
import io.barryLiu.accountingmanager.R.id;
import io.barryLiu.accountingmanager.R.layout;
import io.barryLiu.accountingmanager.entity.AccountBean;
import io.barryLiu.accountingmanager.utils.ResUtils;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	private Context context;
	private List<AccountBean> aBeans;
	
	public MyAdapter(Context context, List<AccountBean> aBeans) {
		super();
		this.context = context;
		this.aBeans = aBeans;
	}

	@Override
	public int getCount() {
		return aBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return aBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView ==null){
			holder =new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.lv_bean, null);
			holder.iv_iconType = (ImageView) convertView.findViewById(R.id.iv_iconType);
			holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
			holder.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
			holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
			holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		AccountBean aBean = aBeans.get(position);

		holder.iv_iconType.setImageResource(ResUtils.getPayTypeIcon(aBean.getType()));
		
		holder.tv_type.setText(aBean.getType());
		holder.tv_data.setText(aBean.getData());
		holder.tv_comment.setText(aBean.getComment());
		holder.tv_money.setText(aBean.getMoney()+"");
		if(aBean.getPayType()==1)
			holder.tv_money.setTextColor(Color.GREEN);
		else if(aBean.getPayType()==0)
			holder.tv_money.setTextColor(Color.RED);
		
		convertView.setTag(holder);
		return convertView;
	}
	
	private ViewHolder holder;
	class ViewHolder{
		ImageView iv_iconType;
		TextView tv_type;
		TextView tv_data;
		TextView tv_comment;
		TextView tv_money;
	}

}
