package com.shoping.mall.study.pullpushlayout;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shoping.mall.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class MyPullUpToShowLayout extends RelativeLayout {

	private final String TAG = this.getClass().getSimpleName();
	private Context mContext;
	private View mView;

	private int mWidth;
	private int mHeight;

	private float mX;
	private float mY;

	private PullPushLayout mTopView;
	private ScrollView mBottomView;
	private RelativeLayout mTopContentLayout;
	private RelativeLayout mBottomContentLayout;
	private TextView mTopBottomHintTv;

	/**
	 * 记录当前展示的是哪个view，0是topView，1是bottomView
	 */
	private int mCurrentViewIndex = 0;
	private boolean canPullDown;
	private boolean canPullUp;
	/**
	 * 自动上滑
	 */
	public static final int AUTO_UP = 0;
	/**
	 * 自动下滑
	 */
	public static final int AUTO_DOWN = 1;
	/**
	 * 动画完成
	 */
	public static final int DONE = 2;
	
	/**
	 * 手滑动距离，这个是控制布局的主要变量
	 */
	private float mMoveLen;

	public MyPullUpToShowLayout(Context context) {
		super(context);
		this.mContext = context;
		initLayout();
	}

	public MyPullUpToShowLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initLayout();
	}

	public MyPullUpToShowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initLayout();
	}

	private void initLayout() {
		// 在构造函数中将Xml中定义的布局解析出来。
		mView = LayoutInflater.from(mContext).inflate(
				R.layout.pullup_show_layout, this, true);

		mTopView = (PullPushLayout) mView.findViewById(R.id.top_scrollview);
		mBottomView = (ScrollView) mView.findViewById(R.id.bottom_scrollview);

		mTopContentLayout = (RelativeLayout) mView
				.findViewById(R.id.top_content_layout);
		mBottomContentLayout = (RelativeLayout) mView
				.findViewById(R.id.bottom_content_layout);
		mTopBottomHintTv = (TextView) mView
				.findViewById(R.id.top_layout_hint_tv);

		mTimer = new MyTimer(handler);
		mBottomView.setOnTouchListener(bottomViewTouchListener);
		mTopView.setOnTouchListener(topViewTouchListener);
		
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			if (vt == null)
				vt = VelocityTracker.obtain();
			else
				vt.clear();
			mLastY = ev.getY();
			vt.addMovement(ev);
			mEvents = 0;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
		case MotionEvent.ACTION_POINTER_UP:
			// 多一只手指按下或抬起时舍弃将要到来的第一个事件move，防止多点拖拽的bug
			mEvents = -1;
			break;
		case MotionEvent.ACTION_MOVE:
			vt.addMovement(ev);
			if (canPullUp && mCurrentViewIndex == 0 && mEvents == 0) {
				mMoveLen += (ev.getY() - mLastY);
				// 防止上下越界
				if (mMoveLen > 0) {
					mMoveLen = 0;
					mCurrentViewIndex = 0;
				} else if (mMoveLen < -mHeight) {
					mMoveLen = -mHeight;
					mCurrentViewIndex = 1;

				}
				if (mMoveLen < -8) {
					// 防止事件冲突
					ev.setAction(MotionEvent.ACTION_CANCEL);
				}
			} else if (canPullDown && mCurrentViewIndex == 1 && mEvents == 0) {
				mMoveLen += (ev.getY() - mLastY);
				// 防止上下越界
				if (mMoveLen < -mHeight) {
					mMoveLen = -mHeight;
					mCurrentViewIndex = 1;
				} else if (mMoveLen > 0) {
					mMoveLen = 0;
					mCurrentViewIndex = 0;
				}
				if (mMoveLen > 8 - mHeight) {
					// 防止事件冲突
					ev.setAction(MotionEvent.ACTION_CANCEL);
				}
			} else
				mEvents++;
			mLastY = ev.getY();
			requestLayout();
			break;
		case MotionEvent.ACTION_UP:
			mLastY = ev.getY();
			vt.addMovement(ev);
			vt.computeCurrentVelocity(700);
			// 获取Y方向的速度
			float mYV = vt.getYVelocity();
			if (mMoveLen == 0 || mMoveLen == -mHeight)
				break;
			if (Math.abs(mYV) < 500) {
				// 速度小于一定值的时候当作静止释放，这时候两个View往哪移动取决于滑动的距离
				if (mMoveLen <= -mHeight / 2) {
					state = AUTO_UP;
				} else if (mMoveLen > -mHeight / 2) {
					state = AUTO_DOWN;
				}
			} else {
				// 抬起手指时速度方向决定两个View往哪移动
				if (mYV < 0)
					state = AUTO_UP;
				else
					state = AUTO_DOWN;
			}
			mTimer.schedule(2);
			try {
				vt.recycle();
				vt = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		}
		super.dispatchTouchEvent(ev);
		return true;
	}


	/**
	 * 用于计算手滑动的速度
	 */
	private VelocityTracker vt;
	private float mLastY;
	/**
	 * 用于控制是否变动布局的另一个条件，mEvents==0时布局可以拖拽了，mEvents==-1时可以舍弃将要到来的第一个move事件，
	 * 这点是去除多点拖动剧变的关键
	 */
	private int mEvents;
	private int state = DONE;
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mWidth = getWidth();
		mHeight = getHeight();
		mPaddingLeft = getPaddingLeft();
		mPaddingTop = getPaddingTop();
		mPaddingRight = getPaddingRight();
		mPaddingBottom = getPaddingBottom();
		mX = getX();
		mY = getY();
		Log.i(TAG, "dispatchTouchEvent 宽：高：" + mWidth + ":" + mHeight);
		Log.i(TAG, "dispatchTouchEvent Padding:" + mPaddingLeft + ":"
				+ mPaddingTop + ":" + mPaddingRight + ":" + mPaddingBottom);
		Log.i(TAG, "dispatchTouchEvent 坐标:" + mX + ":" + mY);
		return super.onTouchEvent(event);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getWidth();
		mHeight = getHeight();
		Log.i(TAG, "onMeasure 宽：高：" + mWidth + ":" + mHeight);
	}

	private int mPaddingLeft;
	private int mPaddingTop;
	private int mPaddingRight;
	private int mPaddingBottom;

	@SuppressLint("NewApi")
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		mWidth = getWidth();
		mHeight = getHeight();
		mPaddingLeft = getPaddingLeft();
		mPaddingTop = getPaddingTop();
		mPaddingRight = getPaddingRight();
		mPaddingBottom = getPaddingBottom();
		mX = getX();
		mY = getY();
		Log.i(TAG, "onLayout 宽：高：" + mWidth + ":" + mHeight);
		Log.i(TAG, "onLayout Padding:" + mPaddingLeft + ":" + mPaddingTop + ":"
				+ mPaddingRight + ":" + mPaddingBottom);
		Log.i(TAG, "onLayout 坐标:" + mX + ":" + mY);
		
		Log.i("onLayout", "---------------onLayout");

		mTopView.layout(0, (int) mMoveLen, mWidth,
				mTopView.getMeasuredHeight() + (int) mMoveLen);
		mBottomView.layout(0, mTopView.getMeasuredHeight() + (int) mMoveLen,
				mWidth, mTopView.getMeasuredHeight() + (int) mMoveLen
						+ mBottomView.getMeasuredHeight());
		
	}


	private OnTouchListener topViewTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			ScrollView sv = (ScrollView) v;
			if (sv.getScrollY() == (sv.getChildAt(0).getMeasuredHeight() - sv
					.getMeasuredHeight()) && mCurrentViewIndex == 0)
				canPullUp = true;
			else
				canPullUp = false;
			return false;
		}
	};
	private OnTouchListener bottomViewTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			ScrollView sv = (ScrollView) v;
			if (sv.getScrollY() == 0 && mCurrentViewIndex == 1)
				canPullDown = true;
			else
				canPullDown = false;
			return false;
		}
	};

	/**
	 * 动画速度
	 */
	public static final float SPEED = 6.5f;
	private MyTimer mTimer;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (mMoveLen != 0) {
				if (state == AUTO_UP) {
					mMoveLen -= SPEED;
					if (mMoveLen <= -mHeight) {
						mMoveLen = -mHeight; //120为标题的高度
						state = DONE;
						mCurrentViewIndex = 1;
					}
				} else if (state == AUTO_DOWN) {
					mMoveLen += SPEED;
					if (mMoveLen >= 0) {
						mMoveLen = 0;
						state = DONE;
						mCurrentViewIndex = 0;
					}
				} else {
					mTimer.cancel();
				}
				requestLayout();
			}
			else{
				state = DONE;
				mTimer.cancel();
			}
			Log.i("Handler", "---------------move:" + mMoveLen);
		}

	};
	
	
	class MyTimer {
		private Handler handler;
		private Timer timer;
		private MyTask mTask;

		public MyTimer(Handler handler) {
			this.handler = handler;
			timer = new Timer();
		}

		public void schedule(long period) {
			if (mTask != null) {
				mTask.cancel();
				mTask = null;
			}
			mTask = new MyTask(handler);
			timer.schedule(mTask, 0, period);
		}

		public void cancel() {
			if (mTask != null) {
				mTask.cancel();
				mTask = null;
			}
		}

		class MyTask extends TimerTask {
			private Handler handler;

			public MyTask(Handler handler) {
				this.handler = handler;
			}

			@Override
			public void run() {
				handler.obtainMessage().sendToTarget();
			}

		}
	}
	
}
