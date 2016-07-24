package com.shoping.mall.engine.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.shoping.mall.R;
import com.shoping.mall.base.BaseFragment;
import com.shoping.mall.engine.category.CategoryTabFragment;
import com.shoping.mall.study.cardslidepanel.CardViewMainActivity;
import com.shoping.mall.study.pullpushlayout.PullPushActivity;
import com.shoping.mall.study.retrofit2.RetrofitMainActivity;
import com.shoping.mall.study.web.WebViewActivity;

public class MainFragment extends BaseFragment implements OnClickListener {

	private static final int FRAGMENT_CNT = 2;
	private static int HOME_INDEX = 0xaab661;
	private static int CATEGORY_INDEX = HOME_INDEX + 1;

	private HomeTabFragment mHomeTabFragment;
	private CategoryTabFragment mCategoryTabFragment;
	
	private Button mHomeMenuBtn;
	private Button mCatagoryMenuBtn;
	private Button mShoppingCartMenuBtn;
	private Button mMyShoppingMenuBtn;
	private Button mMoreMenuBtn;
	private int mCurrentSelectedId;
	
	public static MainFragment newInstance(int index) {
		MainFragment mainFragment = new MainFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("index", index);
		mainFragment.setArguments(bundle);
		return mainFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (null != savedInstanceState) {
			mCurrentSelectedId = savedInstanceState.getInt("position");
		}

		if (null == mCurrentFragment) {
			showDefaultFragment();
		} else {
			hideOtherFragment();
		}
	}
	
	private void hideOtherFragment() {
		for (int i = 0; i < FRAGMENT_CNT; i++) {
			if (mCurrentSelectedId != i) {
				Fragment fragment = getChildFragmentManager()
						.findFragmentByTag(String.valueOf(i));
				if (null != fragment) {
					getChildFragmentManager().beginTransaction().hide(fragment)
							.commit();
				}
			}
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.fragment_main_layout, container,
				false);
		super.onCreateView(inflater, container, savedInstanceState);
		showDefaultFragment();
		return mView;
	}

	private void showDefaultFragment() {
		mHomeTabFragment = (HomeTabFragment) getChildFragmentManager()
				.findFragmentByTag(String.valueOf(HOME_INDEX));
		if (null == mHomeTabFragment) {
			mHomeTabFragment = HomeTabFragment.newInstance(HOME_INDEX);
		}
		mCurrentSelectedId = HOME_INDEX;
		mCurrentFragment = mHomeTabFragment;
		getChildFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment_main_content_layout, mHomeTabFragment,
						String.valueOf(HOME_INDEX)).commit();
		
	}

	@Override
	public void initView() {
		mHomeMenuBtn = (Button) mView.findViewById(R.id.fragment_home_menu_btn);
		mHomeMenuBtn.setOnClickListener(this);
		mCatagoryMenuBtn = (Button) mView.findViewById(R.id.fragment_catagory_menu_btn);
		mCatagoryMenuBtn.setOnClickListener(this);
		mShoppingCartMenuBtn = (Button) mView.findViewById(R.id.fragment_shopping_cart_menu_btn);
		mShoppingCartMenuBtn.setOnClickListener(this);
		mMyShoppingMenuBtn = (Button) mView.findViewById(R.id.fragment_my_shopping_menu_btn);
		mMyShoppingMenuBtn.setOnClickListener(this);
		mMoreMenuBtn = (Button) mView.findViewById(R.id.fragment_more_menu_btn);
		mMoreMenuBtn.setOnClickListener(this);
	}

	@Override
	public void initData() {

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
		// mHander.sendEmptyMessageDelayed(1, 200);
	}

	private void switchHomeTabFragment() {
		mHomeTabFragment = (HomeTabFragment) getChildFragmentManager()
				.findFragmentByTag(String.valueOf(HOME_INDEX));
		if (null == mHomeTabFragment) {
			mHomeTabFragment = HomeTabFragment
					.newInstance(HOME_INDEX);
		}
		boolean isLeftAnim = mCurrentSelectedId > HOME_INDEX? true:false;
		switchToDifferentFragment(mHomeTabFragment,
				R.id.fragment_main_content_layout,
				String.valueOf(HOME_INDEX),isLeftAnim);
		mCurrentSelectedId = HOME_INDEX;
	}
	
	private void switchCategoryTabFragment() {
		mCategoryTabFragment = (CategoryTabFragment) getChildFragmentManager()
				.findFragmentByTag(String.valueOf(CATEGORY_INDEX));
		if (null == mCategoryTabFragment) {
			mCategoryTabFragment = CategoryTabFragment
					.newInstance(CATEGORY_INDEX);
		}
		boolean isLeftAnim = mCurrentSelectedId > CATEGORY_INDEX? true:false;
		switchToDifferentFragment(mCategoryTabFragment,
					R.id.fragment_main_content_layout,
					String.valueOf(CATEGORY_INDEX),isLeftAnim);
			
		mCurrentSelectedId = CATEGORY_INDEX;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fragment_home_menu_btn:
			switchHomeTabFragment();
			break;
		case R.id.fragment_catagory_menu_btn:
			switchCategoryTabFragment();
			break;
		case R.id.fragment_shopping_cart_menu_btn:
//			Intent intent = new Intent(mContext,RetrofitMainActivity.class);
//			Intent intent = new Intent(mContext,OkHttpMainActivity.class);
//			Intent intent = new Intent(mContext,BmobAcitvity.class);
			Intent intent = new Intent(mContext, WebViewActivity.class);
			startActivity(intent);
			break;
		case R.id.fragment_my_shopping_menu_btn:
//			Intent intent1 = new Intent(mContext,PullPushActivity.class);
//			startActivity(intent1);
			break;
		case R.id.fragment_more_menu_btn:
//			Intent intent2 = new Intent(mContext,CardViewMainActivity.class);
//			startActivity(intent2);
		default:
			break;
		}
	}
	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("position", mCurrentSelectedId);
	}

	@Override
	public void clearObject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearView() {
		
	}
}
