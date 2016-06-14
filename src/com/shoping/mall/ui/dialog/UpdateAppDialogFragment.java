package com.shoping.mall.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shoping.mall.R;
import com.shoping.mall.engine.update.Update;
import com.shoping.mall.util.LogUtil;
import com.shoping.mall.util.NetworkUtil;

public class UpdateAppDialogFragment extends DialogFragment implements OnClickListener{

	private Context mContext;
	private View mView;

	private TextView mTitleTv;
	private TextView mContentTv;
	private TextView mProgressTv;
	
	private ProgressBar mDownloadProgress;
	
	private Button mOkBtn;
	private Button mCancelBtn;

	private LinearLayout mProgressLayout;
	/**
	 *对话框回调接口 
	 */
	private DialogClickListener mDialogClickListener;

	private static UpdateAppDialogFragment mUpdateAppDialogFragment;
	private static Update update;
	/**
	 * 创建Fragment对话框实例
	 * 
	 * @param title
	 *            ：指定对话框的标题。
	 * @return：Fragment对话框实例。
	 */
	public static UpdateAppDialogFragment newInstance(String title,String content) {
		if(null == mUpdateAppDialogFragment){
			mUpdateAppDialogFragment = new UpdateAppDialogFragment();
			Bundle args = new Bundle();
			// 自定义的标题
			args.putString("title", title);
			args.putString("content", content);
			mUpdateAppDialogFragment.setArguments(args);
			return mUpdateAppDialogFragment;
			
		}
		else {
			return null;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mContext = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_FRAME, R.style.dialog_fragment_style);
		setCancelable(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		mView = inflater.inflate(
				R.layout.dialog_fragment_update_app_layout, container,
				false);
		initView();
		return mView;
	}


	/**
	 * 初始化UI界面
	 */
	private void initView() {

		mTitleTv = (TextView) mView
				.findViewById(R.id.dialog_title_tv);
		String title = getArguments().getString("title");
		if(!TextUtils.isEmpty(title)){
			mTitleTv.setText(title);
		}
		
		mOkBtn = (Button) mView
				.findViewById(R.id.dialog_confirm_button);
		mOkBtn.setOnClickListener(this);
		mCancelBtn = (Button) mView
				.findViewById(R.id.dialog_cancel_button);
		mCancelBtn.setOnClickListener(this);
		
		mContentTv = (TextView) mView
				.findViewById(R.id.dialog_update_content);
		mContentTv.setMovementMethod(ScrollingMovementMethod.getInstance()); 
		String content = getArguments().getString("content");
		if(!TextUtils.isEmpty(content)){
			mContentTv.setText(content);
		}
		
		mProgressTv = (TextView) mView
				.findViewById(R.id.dialog_update_progress_text);

		mProgressLayout =  (LinearLayout) mView
				.findViewById(R.id.dialog_progress_layout);
		
		mDownloadProgress = (ProgressBar) mView.findViewById(R.id.dialog_progress_bar);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		Window window = getDialog().getWindow();
		LayoutParams params = window.getAttributes();
		int width = (int) getResources().getDimension(
				R.dimen.update_dialog_width);
		int height = (int) getResources().getDimension(
				R.dimen.update_dialog_height);
		params.width = width;
		params.height = height;
		window.setAttributes(params);
		LogUtil.i(" 秒杀验证码Dialog的宽高：" + width + ":"
				+ height);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mUpdateAppDialogFragment = null;
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	public void setDialogClickListener(DialogClickListener mDialogClickListener) {
		this.mDialogClickListener = mDialogClickListener;
	}

	public interface DialogClickListener {

		public void onOkBtnClick();

		public void onCancelBtnClick();
		
		public void startDownloadApk();

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.dialog_confirm_button:
			if(isDownloadSuccess)
			{
				startDownLoadApk();
			}
			else {
				if(null != mDialogClickListener){
					mDialogClickListener.onOkBtnClick();
				}
				this.dismissAllowingStateLoss();
			}
			break;
		case R.id.dialog_cancel_button:
			if(null != mDialogClickListener){
				mDialogClickListener.onCancelBtnClick();
			}
			dismissAllowingStateLoss();
			break;
		default:
			break;
		}
	}

	public void updateDownloadProgress(int precentage){
		
		mDownloadProgress.setProgress(precentage);
		mProgressTv.setText(precentage + "%");
	}

	/**
	 * 开始下载apk
	 */
	private void startDownLoadApk() {
		if (!NetworkUtil.checkNet(mContext)) {
			Toast.makeText(mContext, "没有网络，请确保网络连接正常", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		mContentTv.setVisibility(View.GONE);
		mProgressLayout.setVisibility(View.VISIBLE);
		mCancelBtn.setEnabled(false);
		mOkBtn.setEnabled(false);
		if(null != mDialogClickListener){
			mDialogClickListener.startDownloadApk();
		}
	}
	
	
	private boolean isDownloadSuccess = true;
	public void updateDownloadFail(String errMsg) {
		mProgressTv.setText("下载失败");
		isDownloadSuccess = false;
		mOkBtn.setEnabled(true);
		mCancelBtn.setEnabled(true);
	}

}
