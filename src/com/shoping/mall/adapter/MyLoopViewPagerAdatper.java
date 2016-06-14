package com.shoping.mall.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.shoping.mall.R;
import com.shoping.mall.entity.Product;
import com.shoping.mall.util.CustomPicasso;

public class MyLoopViewPagerAdatper extends DiscoverMPagerAdapter {
	
	protected final String TAG = getClass().getSimpleName();
	
	private List<Product> slides;
	private Context context;
	public MyLoopViewPagerAdatper(List<Product> slides,Context context) {
		this.slides = slides;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return slides.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == (View) object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
//		 super.destroyItem(container, position, object);
		container.removeView((View) object);
	}
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		
		View view = LayoutInflater.from(context).inflate(R.layout.image_item, null);
		ImageView imgView =  (ImageView) view.findViewById(R.id.imgView);
		LinearLayout ll_point_container =  (LinearLayout) view.findViewById(R.id.ll_point_container);
		
		ll_point_container.removeAllViews();
		final ImageView[] imageViews = new ImageView[slides.size()];
		for (int i = 0; i < slides.size(); i++) {
			ImageView imageView = new ImageView(context);

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			if (i == 0) {
				layoutParams.setMargins(0, 0, 0, 0);
//				imageView.setLayoutParams(layoutParams);
//				imageView.setScaleType(ScaleType.FIT_XY);
//				imageView.setImageResource(R.drawable.page);
			} else {
				layoutParams.setMargins(15, 0, 0, 0);
			}
			imageView.setLayoutParams(layoutParams);
			imageView.setScaleType(ScaleType.FIT_XY);
			imageView.setImageResource(R.drawable.page);
			imageViews[i] = imageView;
			ll_point_container.addView(imageViews[i]);
		}
//		ImageView iv_point1 =  (ImageView) view.findViewById(R.id.iv_point1);
//		ImageView iv_point2 =  (ImageView) view.findViewById(R.id.iv_point2);
//		ImageView iv_point3 =  (ImageView) view.findViewById(R.id.iv_point3);
//		List<ImageView> imageViews = new ArrayList<ImageView>();
//		imageViews.add(iv_point1);
//		imageViews.add(iv_point2);
//		imageViews.add(iv_point3);
		for(int i=0;i<imageViews.length;i++){
			if(i==position){
				imageViews[i].setImageResource(R.drawable.page_now);
			}
		}
		CustomPicasso.getImageLoader(context).load(slides.get(position).getImgUrl()).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(imgView);

//		PhoneApplication.mImageLoader.displayImage(ReplaceImageUrlUtil.replaceURL(slides.get(position).getImgUrl()), imgView, PhoneApplication.mOptions);
//		imgView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				UIHelper.goToWebviewCanShowMenu(slides.get(position).getAction(), context);
//				
//			}
//		});
		container.addView(view);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/**
				if (URLUtil.isHttpUrl(slides.get(position).getAction())) {
//					Intent intent = new Intent();
//					intent.setClass(context, WebViewCanShowMenu.class);
//					intent.putExtra("httpurl", slides.get(position).getAction());
//					f.startActivityForResult(intent, 1);
					String httpUrl = slides.get(position).getAction();
					if(httpUrl.contains("/mobile/mobileProduct/product_"))
					{
						Intent intent = new Intent(context,ProductDetailActivity.class);
						String product_id = UrlUtil.getProductIdByUrl(httpUrl);
						intent.putExtra("productId", product_id);
						LogUtil.i(TAG + " 首页轮播页面跳转商品详情页:" + product_id);
						f.startActivityForResult(intent, 1);
					}
					else if(httpUrl.contains("/mobile/mobileStroe/index.jhtml?shopId"))
					{
						Intent intent = new Intent();
						intent.setClass(context, StoreActivity.class);
						intent.putExtra("httpurl", slides.get(position).getAction());
						LogUtils.i(Constants.SHEN_TAG, TAG + " 首页轮播页面跳转商铺页");
						f.startActivityForResult(intent, 888);
						
					}else{
						Intent intent = new Intent();
						intent.setClass(context, WebViewCanShowMenu.class);
						intent.putExtra("httpurl", slides.get(position).getAction());
						f.startActivityForResult(intent, 1);
					}
				} else {
					Toast.makeText(context, "商品链接不正确或不存在", 1).show();
				}
				 */
			}
		}
		);
		return view;
	}

}
