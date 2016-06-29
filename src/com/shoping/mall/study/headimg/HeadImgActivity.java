package com.shoping.mall.study.headimg;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.shoping.mall.ConstantValue;
import com.shoping.mall.R;
import com.shoping.mall.util.LogUtil;
import com.shoping.mall.util.SharedPreUtil;
import com.shoping.mall.util.SharedPrefUtil;

public class HeadImgActivity extends Activity {
	private static final int PHOTO_REQUEST_CAREMA = 1;
	private static final int PHOTO_REQUEST_GALLERY = 2;
	private static final int PHOTO_REQUEST_CUT = 3;
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
	private File tempFile;
	private CircleImageView headIcon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_head_img);
		initView();
	}

	private void initView() {

		headIcon = (CircleImageView) findViewById(R.id.headIcon);
		headIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeHeadIcon();
			}
		});
		changeTheme();
		
		boolean isDownloadHeadImg  = SharedPreUtil.get(this, "is_download_head_img", true);
		if(isDownloadHeadImg){
			downloadHeadImg();
		}
		else {
			File file = new File(HeadImgActivity.this.getFilesDir(),
					"_head_icon.jpg");
			if (file.exists()) {
				headIcon.setImageURI(Uri.fromFile(file));
			}
			
			boolean isUploadHeadImg = SharedPreUtil.get(this, "is_upload_head_img", false);
			if(isUploadHeadImg){
				uploadHeadIcon(file);
			}
		}
	}

	private void downloadHeadImg() {
		// 下载APK，并且替换安装
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// sdcard存在
			// afnal
			String headUrlStr = ConstantValue.SERVER_URL + "/image/head"+ ConstantValue.DOWNLOAD_HEAD_IMG;
			LogUtil.i("下载头像地址为：" + headUrlStr);
			FinalHttp finalhttp = new FinalHttp();
			finalhttp.download(headUrlStr,
					HeadImgActivity.this.getFilesDir()
							+ ConstantValue.DOWNLOAD_HEAD_IMG,
					new AjaxCallBack<File>() {

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							super.onFailure(t, errorNo, strMsg);
							t.printStackTrace();
							
							File file = new File(HeadImgActivity.this.getFilesDir(),
									"_head_icon.jpg");
							if (file.exists()) {
								headIcon.setImageURI(Uri.fromFile(file));
							}
							LogUtil.i("用户头像，下载失败" + strMsg);

						}

						@Override
						public void onLoading(long count, long current) {
							// TODO Auto-generated method stub
							super.onLoading(count, current);
							//当前下载百分比
						}

						@Override
						public void onSuccess(File t) {
							super.onSuccess(t);
							File file = new File(HeadImgActivity.this.getFilesDir(),
									"_head_icon.jpg");
							if (file.exists()) {
								headIcon.setImageURI(Uri.fromFile(file));
							}
							SharedPreUtil.put(HeadImgActivity.this, "is_download_head_img", false);
							LogUtil.i("用户头像，下载成功");
						}
					});
		} else {
			Toast.makeText(this, "没有发现内存卡", Toast.LENGTH_SHORT).show();
		}
	}

	private void uploadHeadIcon(File file) {
		try {
			AjaxParams params = new AjaxParams();
			params.put("username", "michael yang");
			params.put("password", "123456");
			params.put("email", "test@tsz.net");
			params.put("profile_picture", file);
			// params.put("profile_picture2", inputStream); // 上传数据流
			// params.put("profile_picture3", new ByteArrayInputStream(bytes));
			// // 提交字节流
			FinalHttp fh = new FinalHttp();
			fh.post(ConstantValue.SERVER_URL + ConstantValue.UPLOAD_HEAD_IMG,
					params, new AjaxCallBack<String>() {
						@Override
						public void onSuccess(String t) {
							// TODO Auto-generated method stub
							super.onSuccess(t);
							SharedPreUtil.put(HeadImgActivity.this, "is_upload_head_img", false);
							LogUtil.i("上传用户头像成功！");
						}

						@Override
						public void onLoading(long count, long current) {
							// TODO Auto-generated method stub
							super.onLoading(count, current);
						}

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							// TODO Auto-generated method stub
							super.onFailure(t, errorNo, strMsg);
							SharedPreUtil.put(HeadImgActivity.this, "is_upload_head_img", true);
							LogUtil.i("上传用户头像失败！");
						}

						@Override
						public void onStart() {
							// TODO Auto-generated method stub
							super.onStart();
						}

					});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 上传文件
	}

	private void changeTheme() {
		Calendar c = Calendar.getInstance();
		System.out.println(c.get(Calendar.HOUR_OF_DAY));
		if (c.get(Calendar.HOUR_OF_DAY) < 18
				&& c.get(Calendar.HOUR_OF_DAY) >= 6) {
			headIcon.setImageResource(R.drawable.ic_hehe);
		} else {
			headIcon.setImageResource(R.drawable.ic_launcher);
		}
	}

	private void changeHeadIcon() {
		final CharSequence[] items = { "相册", "拍照" };
		AlertDialog dlg = new AlertDialog.Builder(HeadImgActivity.this)
				.setTitle("选择图片")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// 这里item是根据选择的方式，
						if (item == 0) {
							Intent intent = new Intent(Intent.ACTION_PICK);
							intent.setType("image/*");
							startActivityForResult(intent,
									PHOTO_REQUEST_GALLERY);
						} else {
							Intent intent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							if (Environment.getExternalStorageState().equals(
									Environment.MEDIA_MOUNTED)) {
								tempFile = new File(Environment
										.getExternalStorageDirectory(),
										PHOTO_FILE_NAME);
								Uri uri = Uri.fromFile(tempFile);
								intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
								startActivityForResult(intent,
										PHOTO_REQUEST_CAREMA);
							} else {
								Toast.makeText(HeadImgActivity.this,
										"未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT)
										.show();
							}
						}
					}
				}).create();
		dlg.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			if (data != null) {
				Uri uri = data.getData();
				Log.e("图片路径？？", data.getData() + "");
				crop(uri);
			}

		} else if (requestCode == PHOTO_REQUEST_CAREMA) {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				crop(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(HeadImgActivity.this, "未找到存储卡，无法存储照片！",
						Toast.LENGTH_SHORT).show();
			}

		} else if (requestCode == PHOTO_REQUEST_CUT) {
			if (data != null) {
				final Bitmap bitmap = data.getParcelableExtra("data");
				headIcon.setImageBitmap(bitmap);
				// 保存图片到internal storage
				FileOutputStream outputStream;
				try {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
					out.flush();
					// out.close();
					// final byte[] buffer = out.toByteArray();
					// outputStream.write(buffer);
					outputStream = HeadImgActivity.this.openFileOutput(
							"_head_icon.jpg", Context.MODE_PRIVATE);
					out.writeTo(outputStream);
					out.close();
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				if (tempFile != null && tempFile.exists())
					tempFile.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}

			File file = new File(HeadImgActivity.this.getFilesDir(),
					"_head_icon.jpg");
			uploadHeadIcon(file);
		}
	}

	private void crop(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}
}