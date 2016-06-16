package com.shoping.mall.study.bmob;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.datatype.BmobSmsState;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.QuerySMSStateListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

import com.shoping.mall.R;
import com.shoping.mall.study.bmob.bean.Person;

public class BmobAcitvity extends Activity implements OnClickListener {

	private Button mRegisterBtn;
	private Button mLoginBtn;
	private Button mModifyBtn;
	private Button mDeleteBtn;
	private Button mTestBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, "9d30569e071ca25e4145a1ba2dda90ba");
        setContentView(R.layout.activity_bomb_main);
        
        mRegisterBtn = (Button) findViewById(R.id.btn_register);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mModifyBtn = (Button) findViewById(R.id.btn_modify);
        mDeleteBtn = (Button) findViewById(R.id.btn_delete);
        
        mRegisterBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mModifyBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);
        mTestBtn = (Button) findViewById(R.id.btn_test);
        mTestBtn.setOnClickListener(this);
        
	}
	Person p2 = new Person();
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_register:
			p2.setName("cqtddt");
			p2.setPwd("233619298662");
			p2.setAddress("北京海淀");
			p2.save(this, new SaveListener() {
			    @Override
			    public void onSuccess() {
			        // TODO Auto-generated method stub
			        toast("添加数据成功，返回objectId为："+p2.getObjectId());
			    }

			    @Override
			    public void onFailure(int code, String msg) {
			        // TODO Auto-generated method stub
			        toast("创建数据失败：" + msg);
			    }
			});
			break;
		case R.id.btn_login:
			//查找Person表里面id为6b6c11c537的数据
			BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
			bmobQuery.addWhereEqualTo("name", "cqtddt");
			bmobQuery.addWhereEqualTo("pwd", "233619298662");
			bmobQuery.findObjects(this, new FindListener<Person>() {
				
				@Override
				public void onSuccess(List<Person> arg0) {
					// TODO Auto-generated method stub
					toast("查询成功" + arg0.size());
				}
				
				@Override
				public void onError(int arg0, String arg1) {
					// TODO Auto-generated method stub
					toast("查询失败");
				}
			});
//			bmobQuery.getObject(this, p2.getObjectId(), new GetListener<Person>() {
//			    @Override
//			    public void onSuccess(Person object) {
//			        // TODO Auto-generated method stub
//			        toast("登录成功");
//			    }
//
//			    @Override
//			    public void onFailure(int code, String msg) {
//			        // TODO Auto-generated method stub
//			        toast("登录失败：" + msg);
//			    }
//			});
			break;
		case R.id.btn_modify:
			p2.setAddress("北京朝阳");
			p2.update(this, p2.getObjectId(), new UpdateListener() {
			    @Override
			    public void onSuccess() {
			        // TODO Auto-generated method stub
			        toast("修改成功：" + p2.getUpdatedAt());
			    }
			    @Override
			    public void onFailure(int code, String msg) {
			        // TODO Auto-generated method stub
			        toast("修改失败：" + msg);
			    }
			});
			break;
		case R.id.btn_delete:
			Person p2 = new Person();
			p2.setObjectId(p2.getObjectId());
			p2.delete(this, new DeleteListener() {
			    @Override
			    public void onSuccess() {
			        // TODO Auto-generated method stub
			        toast("删除成功");
			    }

			    @Override
			    public void onFailure(int code, String msg) {
			        // TODO Auto-generated method stub
			        toast("删除失败：" + msg);
			    }
			});
			break;
		case R.id.btn_test:
//			BmobSMS.requestSMSCode(this, "18211166905", "登录短信通知", new RequestSMSCodeListener() {
//				
//				@Override
//				public void done(Integer arg0, BmobException arg1) {
//					toast("收到验证码" + arg0);
//					Log.i("验证码", "" + arg0);
//				}
//				
//				
//			});
			BmobSMS.verifySmsCode(this, "18211166905", "857745", new VerifySMSCodeListener() {
				
				@Override
				public void done(BmobException arg0) {
					// TODO Auto-generated method stub
					if(null == arg0){
						Log.i("验证码", "验证码正确");
					}
					else{
						toast(arg0.toString());
						Log.i("验证码", "验证码不正确" + arg0.toString());
					}
				}
				
				@Override
				public void internalDone(Void paramT, BmobException ex) {
					// TODO Auto-generated method stub
					super.internalDone(paramT, ex);
				}
				
			});
			break;

		default:
			break;
		}
	}
	protected void toast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}
}
