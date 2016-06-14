package com.shoping.mall.engine.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullScrollView;
import com.handmark.pulltorefresh.library.PullScrollView.OnMyScrollListener;
import com.handmark.pulltorefresh.library.PullScrollView.OnScrollToBottomListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.shoping.mall.R;
import com.shoping.mall.adapter.DiscoverInfiniteLoopViewPagerAdapter;
import com.shoping.mall.adapter.MyLoopViewPagerAdatper;
import com.shoping.mall.application.MallApp;
import com.shoping.mall.base.BaseFragment;
import com.shoping.mall.entity.Product;
import com.shoping.mall.ui.DiscoverInfiniteLoopViewPager;
import com.shoping.mall.util.LogUtil;
import com.shoping.mall.util.NetworkUtil;

public class HomeTabFragment extends BaseFragment {

	 public static final int REFRESH_DELAY = 2000;
	
	 private PullToRefreshScrollView mPullToRefreshScrollView;
	 private PullScrollView mScrollView;  
	 
	 private DiscoverInfiniteLoopViewPager mDiscoverInfiniteLoopViewPager;
	 private DiscoverInfiniteLoopViewPagerAdapter mDiscoverInfiniteLoopViewPagerAdapter;
	 private MyLoopViewPagerAdatper mMyLoopViewPagerAdatper;

	 private GridView mCategoryPlatform;
	 private ListView mListView;
	 
	 
	 private View foot;
	 
	 
	 private List<Product> mViewPagerList;
	 private List<MainData> mContent;
	 private MainAdapter mContentAdapter;
	 private PlatformAdapter mPlatformAdapter;
	 private List<PlatformData> mPlatformDatas;
	 private List<PlatformData> mPlatformShowDatas;
	 
	 private List<RmdData> mRmdDatas;
	 
	 public static HomeTabFragment newInstance(int index) {
		 HomeTabFragment mainFragment = new HomeTabFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("index", index);
			mainFragment.setArguments(bundle);
			return mainFragment;
		}

	 @Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mContext = activity;
	}
	 @Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		 mView = inflater.inflate(R.layout.fragment_home_tab_layout, container, false);
		 super.onCreateView(inflater, container, savedInstanceState);
		 return mView;
	}

	@Override
	public void initView() {
		mDiscoverInfiniteLoopViewPager = (DiscoverInfiniteLoopViewPager) mView.findViewById(R.id.fragment_main_top_viewpager);
		mDiscoverInfiniteLoopViewPager.setInfinateAdapter(mHander, mDiscoverInfiniteLoopViewPagerAdapter);
		makeViewTouchOnHorizontal(mDiscoverInfiniteLoopViewPager);
		initPullToRefreshScrollView();  
		
		foot = LayoutInflater.from(getActivity()).inflate(
				R.layout.home_list_footer_view, null);
		mListView = (ListView) mView.findViewById(R.id.list_view);
		mListView.addFooterView(foot);

		mContentAdapter = new MainAdapter(mContext,mRmdDatas);
		mListView.setAdapter(mContentAdapter);
		
		mCategoryPlatform = (GridView) mView.findViewById(R.id.fragment_main_grid_view);
	     //listView.setAdapter(new SampleAdapter(getActivity(), R.layout.list_item, mSampleList));
		mPlatformAdapter = new PlatformAdapter(mContext, mPlatformShowDatas);
		mCategoryPlatform.setAdapter(mPlatformAdapter);
		
	
	}

	
	
	private boolean isloading;
	private boolean nomoredata;
	private boolean firstTS;

	private void initPullToRefreshScrollView() {
		mPullToRefreshScrollView = (PullToRefreshScrollView) mView.findViewById(R.id.pull_refresh_scrollview);
		
		//这几个刷新Label的设置  
		 String label = DateUtils.formatDateTime(mContext, System.currentTimeMillis(),  
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL); 
		 mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新时间："+label);   
		 mPullToRefreshScrollView.getLoadingLayoutProxy().setPullLabel("下拉可以刷新");  
		 mPullToRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel("买东西  够容易...  ");  
		 mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel("松开立即刷新");
		 mPullToRefreshScrollView.setMode(Mode.PULL_FROM_START); 
		
		 
		 
       mScrollView = (PullScrollView) mPullToRefreshScrollView.getRefreshableView();
       
       
       
       mScrollView.setOnMyScrollListener(new OnMyScrollListener() {//滑动监听
			
			@Override
			public void onScroll(int paramInt1, int paramInt2, int paramInt3,
					int paramInt4) {
				if(paramInt2==0){
					//fl_title.setBackgroundResource(R.drawable.titlebackimage);
				}else if(paramInt2<=220&&paramInt2>0){
//					fl_title.setBackgroundResource(R.color.title_bg);
//					fl_title.getBackground().setAlpha(paramInt2);
				}
				//mPullToRefreshScrollView.onRefreshComplete();
				LogUtil.i("触发事件：OnMyScrollListener" );
			}
		});
       
       
       mScrollView.setOnScrollToBottomLintener(new OnScrollToBottomListener() {  
           
           @Override  
           public void onScrollBottomListener(boolean isBottom) {
        	   if(isBottom){
               	
   				if (isloading) {
   					return;
   				}else{
   					if (nomoredata ) {//&& firstTS
   						mListView.removeFooterView(foot);
   						return;
   					}else{
   						if(firstTS){
   							//商城页的最近浏览 
//   						QueryBHistory queryBHistory = new QueryBHistory();
//   						threadManager.addTask(queryBHistory);
   							
   							//查询历史记录
   							
   							//getRecommendData();
   							firstTS = false;
   						}
   					}
   				}
   				LogUtil.i("滑动到底部，开始加载推荐数据");
//   				if(null != mContentAdapter){
//   					mContentAdapter.updateAdapterData(mRmdDatas);
//   					mContentAdapter.notifyDataSetChanged();
//   				}
   				mListView.addFooterView(foot);
   				new GetRecommandDataTask().execute();

   				
               }
           }  
       });  
       
       
       mScrollView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
               switch (event.getAction()) {
               
               case MotionEvent.ACTION_DOWN:
                   //TODO                
               break;
               
               case MotionEvent.ACTION_MOVE:
               int scrollY=v.getScrollY();
               if(scrollY>220){
               	//fl_title.getBackground().setAlpha(220);
               }
//               if((scrollY+height)>scrollViewMeasuredHeight-40&&(scrollY+height)<=scrollViewMeasuredHeight+20){
//   				if (isloading) {
//   					break;
//   				}else{
//   					if (nomoredata ) {//&& firstTS
//   						listView.removeFooterView(foot);
//   						break;
//   					}else{
//   						index++;
//   						getRecommendData();
//   					}
//   				}	
//               	
//               	
//               }
               
               break;
                
               default:
               break;
               }
				return false;
			}
		});
		
       
       
       mPullToRefreshScrollView.setOnPullEventListener(new OnPullEventListener<ScrollView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ScrollView> refreshView,
					State state, Mode direction) {//PULL_TO_REFRESH RESET PULL_FROM_START
				if(direction == Mode.PULL_FROM_START){
					if(state == State.PULL_TO_REFRESH){
						//fl_title.setVisibility(View.INVISIBLE);
					}else if(state == State.RESET){
						//fl_title.setVisibility(View.VISIBLE);
					}
					else if(state == State.OVERSCROLLING){
						mPullToRefreshScrollView.onRefreshComplete();
					}
				}
				
				LogUtil.i("下拉事件状态：" + state);
			}
       	
		});
       //上拉监听函数  
       mPullToRefreshScrollView.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {  
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ScrollView> refreshView) {
	              if (NetworkUtil.checkNet(mContext)) {
	            	  new GetDataTask().execute();
	            	  LogUtil.i("下拉刷新");
	                 }else{
	                	 mPullToRefreshScrollView.onRefreshComplete();
	                 }
				
			}
			/*
			 * 先不用这个方法了下滑加载放在mScrollView.setOnScrollToBottomLintener
			 */
			@Override
			public void onPullUpToRefresh(
				PullToRefreshBase<ScrollView> refreshView) {
//						listView.removeFooterView(foot);
				
						Toast.makeText(getActivity(), "亲，没有更多数据了！", Toast.LENGTH_SHORT).show();
						mPullToRefreshScrollView.onRefreshComplete();
//						firstTS = false;
						LogUtil.i("上拉刷新界面");
			}  
       });
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// Do some stuff here
			String label = DateUtils.formatDateTime(mContext, System.currentTimeMillis(),  
                    DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL); 
			mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新时间："+label);
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshScrollView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}
	
	
	private class GetRecommandDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isloading = true;
		}
		
		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			Product product21 = new Product();
			product21.setImgUrl("http://img0.imgtn.bdimg.com/it/u=2841277940,3311317148&fm=21&gp=0.jpg");
			product21.setName("撑起头顶的天3");
			product21.setPrice("1,300.00");
			
			Product product22 = new Product();
			product22.setImgUrl("http://u1.tdimg.com/6/98/122/81593962229511818878652015234007094536.jpg");
			product22.setName("撑起头顶的天4");
			product22.setPrice("1,400.00");
			
			RmdData rmdData = new RmdData();
			rmdData.setProduct1(product21);
			rmdData.setProduct2(product22);
			mRmdDatas.add(rmdData);
			
			
			if(null != mContentAdapter){
				mContentAdapter.updateAdapterData(mRmdDatas);
				mContentAdapter.notifyDataSetChanged();
			}
			if(null != foot){
				mListView.removeFooterView(foot);
			}
			isloading = false;
			
		}
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		if(null == mContent){
			mContent = new ArrayList<MainData>();
		}
		MainData data;
		for(int i = 0; i < 10; i++)
		{
			data  = new MainData();
			data.setImgId(R.drawable.ic_launcher);
			data.setOneTitle("挑起头顶的天" + i);
			data.setTwoTitle("cqtddt" + i);
			mContent.add(data);
		}
		
		Product product = null;
		mViewPagerList = new ArrayList<Product>();
		
		product = new Product();
		product.setImgUrl("http://att.bbs.duowan.com/forum/201308/30/14254007a0q9toeojoc3a5.jpg");
		mViewPagerList.add(product);
		
		product = new Product();
		product.setImgUrl("http://u1.tdimg.com/6/98/122/81593962229511818878652015234007094536.jpg");
		mViewPagerList.add(product);

		product = new Product();
		product.setImgUrl("http://img0.imgtn.bdimg.com/it/u=2841277940,3311317148&fm=21&gp=0.jpg");
		mViewPagerList.add(product);

		product = new Product();
		product.setImgUrl("http://att.bbs.duowan.com/forum/201308/30/14254007a0q9toeojoc3a5.jpg");
		mViewPagerList.add(product);

		product = new Product();
		product.setImgUrl("http://u1.tdimg.com/6/98/122/81593962229511818878652015234007094536.jpg");
		mViewPagerList.add(product);

		product = new Product();
		product.setImgUrl("http://img0.imgtn.bdimg.com/it/u=2841277940,3311317148&fm=21&gp=0.jpg");
		mViewPagerList.add(product);
		
		mMyLoopViewPagerAdatper = new MyLoopViewPagerAdatper(mViewPagerList, mContext);
		mDiscoverInfiniteLoopViewPagerAdapter = new DiscoverInfiniteLoopViewPagerAdapter(mMyLoopViewPagerAdatper); 
		
		
		if(null == mPlatformDatas)
		{
			mPlatformDatas = new ArrayList<PlatformData>();
		}
		if(null == mPlatformShowDatas)
		{
			mPlatformShowDatas = new ArrayList<PlatformData>();
		}
		
		
		PlatformData data1;
		for(int i = 0; i < 5; i++){
			data1  = new PlatformData();
			data1.setTitle("安卓"+ i);
			mPlatformDatas.add(data1);
		}
		
		
		if(mPlatformDatas.size() > 8){
			mPlatformShowDatas = mPlatformDatas.subList(0, 7);
			PlatformData data2 = new PlatformData();
			data2.setTitle("更多");
			mPlatformShowDatas.add(data2);
		}
		else {
			mPlatformShowDatas.addAll(mPlatformDatas);
		}
		
		
		if(null == mRmdDatas)
		{
			mRmdDatas = new ArrayList<RmdData>();
		}
		
		Product product21 = new Product();
		product21.setImgUrl("http://img0.imgtn.bdimg.com/it/u=2841277940,3311317148&fm=21&gp=0.jpg");
		product21.setName("撑起头顶的天1");
		product21.setPrice("1,000.00");
		
		Product product22 = new Product();
		product22.setImgUrl("http://u1.tdimg.com/6/98/122/81593962229511818878652015234007094536.jpg");
		product22.setName("撑起头顶的天2");
		product22.setPrice("1,200.00");
		
		RmdData rmdData = new RmdData();
		rmdData.setProduct1(product21);
		rmdData.setProduct2(product22);
		mRmdDatas.add(rmdData);
		
	}
	 
	private void makeViewTouchOnHorizontal(View view) {
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				PointF downP = new PointF();
				PointF curP = new PointF();
				int act = event.getAction();
				if (act == MotionEvent.ACTION_DOWN
						|| act == MotionEvent.ACTION_MOVE
						|| act == MotionEvent.ACTION_UP) {
					((ViewGroup) v).requestDisallowInterceptTouchEvent(true);
					if (downP.x == curP.x && downP.y == curP.y) {
						return false;
					}
				}
				return false;
			}
		});
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//mHander.sendEmptyMessageDelayed(1, 200);
	}
	
	private static final int SILDE_TIME = 5000;
	private Handler mHander = new Handler(){
		public void dispatchMessage(Message msg) {
		switch (msg.what) {
		case 0:
			mDiscoverInfiniteLoopViewPager.setCurrentItem(
					mDiscoverInfiniteLoopViewPager.getCurrentItem() + 1, true);
			if (MallApp.getInstance().isRun && !MallApp.getInstance().isDown) {
				this.sendEmptyMessageDelayed(0, SILDE_TIME);
			}
			break;
		case 1:
			if (MallApp.getInstance().isRun && !MallApp.getInstance().isDown) {
				this.sendEmptyMessageDelayed(0, SILDE_TIME);
			}
		default:
			break;
		}
		}
	};

	@Override
	public void clearObject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearView() {
		// TODO Auto-generated method stub
		
	}
	
}
