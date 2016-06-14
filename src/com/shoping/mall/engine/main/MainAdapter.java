package com.shoping.mall.engine.main;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shoping.mall.R;
import com.shoping.mall.entity.Product;
import com.shoping.mall.util.CustomPicasso;
import com.shoping.mall.util.LogUtil;

public class MainAdapter extends BaseAdapter {

	private List<RmdData> mList;
	private Context mContext;
	
	public MainAdapter(Context context,List<RmdData> list)
	{
		this.mContext = context;
		this.mList = list;
	}
	
	public void updateAdapterData(List<RmdData> data)
	{
		this.mList = data;
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
        	 convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_list_layout, parent, false);
         }
         viewHolder = getViewHolder(convertView);
         RmdData data = mList.get(arg0);
        
         Product product = data.getProduct1();
         
         if(null != product){
        	 CustomPicasso.getImageLoader(mContext).load(product.getImgUrl()).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(viewHolder.mProduct1Img);
        	 viewHolder.mProduct1TitleTv.setText(product.getName());
        	 viewHolder.mProduct1PriceTv.setText(product.getPrice());
        	 viewHolder.mProductLayout1.setOnClickListener(new ItemOnClickListener(product));
         }
         
         product = data.getProduct2();
         if(null != product){
        	 CustomPicasso.getImageLoader(mContext).load(product.getImgUrl()).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(viewHolder.mProduct2Img);
        	 viewHolder.mProduct2TitleTv.setText(product.getName());
        	 viewHolder.mProduct2PriceTv.setText(product.getPrice());
        	 viewHolder.mProductLayout2.setOnClickListener(new ItemOnClickListener(product));
         }
         else {
			
		}
         
		return convertView;
	}
	
	private void onUrlClick() {
		
	}
	
	private ViewHolder getViewHolder(View view)
	{
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		if(null == viewHolder){
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		}
		return viewHolder;
	}
	
	 private static class ViewHolder {
		 
		 private LinearLayout mProductLayout1;
		 private ImageView mProduct1Img;
		 private TextView mProduct1TitleTv;
		 private TextView mProduct1PriceTv;
		 
		 private LinearLayout mProductLayout2;
		 private ImageView mProduct2Img;
		 private TextView mProduct2TitleTv;
		 private TextView mProduct2PriceTv;
		 
		 public ViewHolder(View view){
			 
			 mProductLayout1 = (LinearLayout) view.findViewById(R.id.item_main_recommand_list_layout1);
			 mProduct1Img = (ImageView) view.findViewById(R.id.item_main_recommand_list_img1);
			 mProduct1TitleTv = (TextView) view.findViewById(R.id.item_main_recommand_list_name1);
			 mProduct1PriceTv = (TextView) view.findViewById(R.id.item_main_recommand_list_price1);

			 mProductLayout2 = (LinearLayout) view.findViewById(R.id.item_main_recommand_list_layout2);;
			 mProduct2Img = (ImageView) view.findViewById(R.id.item_main_recommand_list_img2);
			 mProduct2TitleTv = (TextView) view.findViewById(R.id.item_main_recommand_list_name2);
			 mProduct2PriceTv = (TextView) view.findViewById(R.id.item_main_recommand_list_price2);
		 }
     }

	 private class ItemOnClickListener implements OnClickListener{

		 private Product mProduct;
		 
		public ItemOnClickListener(Product product){
			this.mProduct = product;
		}
		 
		@Override
		public void onClick(View arg0) {
			LogUtil.i("点击了" + mProduct.getName());
		}
		 
	 }
	 
}
