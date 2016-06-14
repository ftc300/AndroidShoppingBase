package com.shoping.mall.engine.main;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoping.mall.R;
import com.shoping.mall.util.CustomPicasso;

public class PlatformAdapter extends BaseAdapter {

	private List<PlatformData> mList;
	private Context mContext;

	public PlatformAdapter(Context context, List<PlatformData> list) {
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_platform_list_layout, parent, false);
		}
		viewHolder = getViewHolder(convertView);
		PlatformData data = mList.get(arg0);

		if (null != data) {
			String imgUrl = data.getImgUrl();
			if (!TextUtils.isEmpty(imgUrl)) {
				CustomPicasso.getImageLoader(mContext).load(imgUrl)
						.placeholder(R.drawable.ic_launcher)
						.error(R.drawable.ic_launcher)
						.into(viewHolder.mPlatformImg);
			} else {
				viewHolder.mPlatformImg
						.setImageResource(R.drawable.ic_launcher);
			}
			String title = data.getTitle();
			if (!TextUtils.isEmpty(title)) {
				viewHolder.mPlatformTitleTv.setText(data.getTitle());
			} else {
				viewHolder.mPlatformTitleTv.setText("");
			}
		}

		return convertView;
	}

	private ViewHolder getViewHolder(View view) {
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		if (null == viewHolder) {
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		}
		return viewHolder;
	}

	private static class ViewHolder {

		private ImageView mPlatformImg;
		private TextView mPlatformTitleTv;

		public ViewHolder(View view) {
			mPlatformImg = (ImageView) view
					.findViewById(R.id.item_platform_img);
			mPlatformTitleTv = (TextView) view
					.findViewById(R.id.item_platform_title_tv);
		}
	}

}
