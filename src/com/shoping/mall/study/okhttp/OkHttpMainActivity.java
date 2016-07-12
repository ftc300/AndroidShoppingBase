package com.shoping.mall.study.okhttp;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.shoping.mall.R;
import com.shoping.mall.study.okhttp.lib.callback.ResultCallback;
import com.shoping.mall.study.okhttp.lib.request.OkHttpRequest;
import com.shoping.mall.study.okhttp.volley.VolleyRequest;
import com.squareup.okhttp.Request;

public class OkHttpMainActivity extends Activity
{

    private TextView mTv;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    public abstract class MyResultCallback<T> extends ResultCallback<T>{

        @Override
        public void onBefore(Request request)
        {
            super.onBefore(request);
            setTitle("loading...");
        }

        @Override
        public void onAfter()
        {
            super.onAfter();
            setTitle("Sample-okHttp");
        }
    }

    private ResultCallback<String> stringResultCallback = new MyResultCallback<String>()//
    {
        @Override
        public void onError(Request request, Exception e)
        {
            Log.e("TAG", "onError , e = " + e.getMessage());
        }

        @Override
        public void onResponse(String response)
        {
            Log.e("TAG", "onResponse , response = " + response);
            mTv.setText("operate success");
        }

        @Override
        public void inProgress(float progress)
        {
            mProgressBar.setProgress((int) (100 * progress));
        }
    };
	
    
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttp_activity_main);
        
        /**
         * volley创建请求队列
         */
        mQueue=Volley.newRequestQueue(this);
        
        
        
        mTv = (TextView) findViewById(R.id.id_textview);
        mImageView = (ImageView) findViewById(R.id.id_imageview);
        mProgressBar = (ProgressBar) findViewById(R.id.id_progress);
        mProgressBar.setMax(100);
    }

    private boolean isVolleyRequest = true;
    
    public void getUser(View view){
    	if(isVolleyRequest){
    		getVolleyUser();
    		return ;
    	}

        String url = "https://raw.githubusercontent.com/hongyangAndroid/okhttp-utils/master/user.gson";
//        url = "http://192.168.56.1:8080/test/user.do?action=login&username=fusheng&password=123";
        new OkHttpRequest.Builder()
                .url(url)
                .get(new MyResultCallback<User>()
                {
                    @Override
                    public void onError(Request request, Exception e)
                    {
                        Log.e("TAG", "onError , e = " + e.getMessage());
                    }

                    @Override
                    public void onResponse(User response)
                    {
                        Log.e("TAG", "onResponse , user = " + response);
                        mTv.setText(response.username);
                    }
                });

//        new Thread()
//        {
//            @Override
//            public void run()
//            {
//                try
//                {
//                    User u = new OkHttpRequest.Builder().url(url).get(User.class);
//                    Log.e("TAG", "syn u = " + u);
//                } catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//
//            }
//        }.start();


    }

    private final Gson mGson = new Gson();
    private void getVolleyUser()
    {
    	  String url = "https://raw.githubusercontent.com/hongyangAndroid/okhttp-utils/master/user.gson";
    	  
    	  new VolleyRequest.Builder().url(url).tag(this).get(new ResultCallback<User>() {

    		@Override
    		public void onBefore(Request request) {
    			super.onBefore(request);
    			Log.i("volley before", "调用之前");
    		}

			@Override
			public void onResponse(User response) {
				 Log.e("TAG", "onResponse , user = " + response);
                 mTv.setText(response.username);
				
			}

			@Override
			public void onError(Request request, Exception e) {
				Log.e("TAG", "onError , e = " + e.getMessage());
			}
			
			@Override
			public void onAfter() {
				super.onAfter();
				Log.i("getVolleyUser", "调用之后");
			}
		});
    	  
    	  /*
    	  mQueue.add(new JsonRequest<User>(Method.GET,url,"",new Listener<User>() {

			@Override
			public void onResponse(User response) {
				// TODO Auto-generated method stub
				Log.i("MainActivity", "volley" + response.toString());
				mTv.setText(response.username);
			}
		},new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				Log.i("", "请求出错" + arg0.getMessage());
			}
		}) {

			@Override
			protected Response<User> parseNetworkResponse(NetworkResponse response) {
				 try {
		             String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
		             return Response.success(mGson.fromJson(json, User.class),
		                     HttpHeaderParser.parseCacheHeaders(response));
			        } catch (UnsupportedEncodingException e) {
			             return Response.error(new ParseError(e));
			        } catch (JsonSyntaxException e) {
			             return Response.error(new ParseError(e));
			         }
			}
		});
		*/
    }

    public void getUsers(View view)
    {
    	if(isVolleyRequest)
    	{
    		getVolleyUsers();
    		return ;
    	}
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "zhy");
        String url = "https://raw.githubusercontent.com/hongyangAndroid/okhttp-utils/master/users.gson";
        new OkHttpRequest.Builder().url(url).params(params).post(new MyResultCallback<List<User>>()
        {
            @Override
            public void onError(Request request, Exception e)
            {
                Log.e("TAG", "onError , e = " + e.getMessage());
            }

            @Override
            public void onResponse(List<User> users)
            {
                Log.e("TAG", "onResponse , users = " + users);
                mTv.setText(users.get(0).toString());
            }
        });


    }

    
    private void getVolleyUsers()
    {
    	String url = "https://raw.githubusercontent.com/hongyangAndroid/okhttp-utils/master/users.gson";
    	
    	new VolleyRequest.Builder().url(url).addParams("name", "shen").get(new ResultCallback<List<User>>() {

			@Override
			public void onResponse(List<User> response) {
				// TODO Auto-generated method stub
				Log.i("MainActivity", "volley" + response.toString());
				mTv.setText(response.get(0).toString());
			}

			@Override
			public void onError(Request request, Exception e) {
				// TODO Auto-generated method stub
				Log.i("", "请求出错" + e.getMessage());
			}
		});
    	
  	 
    	/*
    	mQueue.add(new JsonRequest<List<User>>(Method.POST,url,"",new Listener<List<User>>() {

			@Override
			public void onResponse(List<User> response) {
				// TODO Auto-generated method stub
				Log.i("MainActivity", "volley" + response.toString());
				mTv.setText(response.get(0).toString());
			}
		},new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				Log.i("", "请求出错" + arg0.getMessage());
			}
		}) {

  		
  		public Type mType = getSuperclassTypeParameter(getClass());

  	    private Type getSuperclassTypeParameter(Class<?> subclass)
  	    {
  	        Type superclass = subclass.getGenericSuperclass();
  	        if (superclass instanceof Class)
  	        {
  	            throw new RuntimeException("Missing type parameter.");
  	        }
  	        ParameterizedType parameterized = (ParameterizedType) superclass;
  	        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
  	    }
  		  
  		   @Override
  		protected Map<String, String> getParams() throws AuthFailureError {
  			Map<String, String> params = new HashMap<String, String>();
  			params.put("name", "zhy");
  			return params;
  		}
  		  	
			@Override
			protected Response<List<User>> parseNetworkResponse(NetworkResponse response) {
				 try {
		             String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
		             List<User> usrsList = mGson.fromJson(json,mType);
		             return Response.success(usrsList,HttpHeaderParser.parseCacheHeaders(response));
			        } catch (UnsupportedEncodingException e) {
			             return Response.error(new ParseError(e));
			        } catch (JsonSyntaxException e) {
			             return Response.error(new ParseError(e));
			         }
			}
		});*/
    }
    
    public void getSimpleString(View view)
    {
    	if(isVolleyRequest){
    		getVolleySimpleString();
    		return ;
    	}
        String url = "https://raw.githubusercontent.com/hongyangAndroid/okhttp-utils/master/user.gson";

        new OkHttpRequest.Builder().url(url)
                .get(new MyResultCallback<String>()
                {
                    @Override
                    public void onError(Request request, Exception e)
                    {
                        Log.e("TAG", "onError , e = " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response)
                    {
                        mTv.setText(response);
                    }
                });

    }
    
    
    private void getVolleySimpleString()
    {
    	String url = "https://raw.githubusercontent.com/hongyangAndroid/okhttp-utils/master/user.gson";
    	mQueue.add(new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				 mTv.setText(arg0);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Log.e("TAG", "onError , e = " + arg0.getMessage());
			}
		}));
       
    }

    public void getHtml(View view)
    {
        //https://192.168.56.1:8443/
        //https://kyfw.12306.cn/otn/
        //https://192.168.187.1:8443/
        String url = "http://www.csdn.net/";
        new OkHttpRequest.Builder().url(url).get(new MyResultCallback<String>()
        {
            @Override
            public void onError(Request request, Exception e)
            {
                Log.e("TAG", "onError" + e.getMessage());
            }

            @Override
            public void onResponse(String response)
            {
                mTv.setText(response);
            }
        });
    }

    public void getHttpsHtml(View view)
    {
        String url = "https://kyfw.12306.cn/otn/";
        new OkHttpRequest.Builder().url(url).get(new MyResultCallback<String>()
        {
            @Override
            public void onError(Request request, Exception e)
            {
                Log.e("TAG", "onError" + e.getMessage());
            }

            @Override
            public void onResponse(String response)
            {
                mTv.setText(response);
            }
        });
    }

    public void getImage(View view)
    {
        String url = "http://images.csdn.net/20150817/1.jpg";
        mTv.setText("");
        new OkHttpRequest.Builder().url(url).imageView(mImageView).displayImage(null);
    }


    public void uploadFile(View view)
    {

        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        if (!file.exists())
        {
            Toast.makeText(OkHttpMainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", "张鸿洋");
        params.put("password", "123");

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");

        String url = "http://192.168.56.1:8080/okHttpServer/fileUpload";
        new OkHttpRequest.Builder()//
                .url(url)//
                .params(params)
                .headers(headers)
                .files(new Pair<String, File>("mFile", file))//
                .upload(stringResultCallback);
    }


    public void multiFileUpload(View view)
    {
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test1.txt");
        if (!file.exists())
        {
            Toast.makeText(OkHttpMainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", "张鸿洋");
        params.put("password", "123");

        String url = "http://192.168.1.103:8080/okHttpServer/mulFileUpload";
        new OkHttpRequest.Builder()//
                .url(url)//
                .params(params)
                .files(new Pair<String, File>("mFile", file), new Pair<String, File>("mFile", file2))//
                .upload(stringResultCallback);


    }


    public void downloadFile(View view)
    {
        String url = "https://github.com/hongyangAndroid/okhttp-utils/blob/master/gson-2.2.1.jar?raw=true";
        new OkHttpRequest.Builder()
                .url(url)
                .destFileDir(Environment.getExternalStorageDirectory().getAbsolutePath())
                .destFileName("gson-2.2.1.jar")
                .download(stringResultCallback);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
//        OkHttpClientManager.cancelTag(this);
    }
    
    private RequestQueue mQueue;
    /**
     * volley
     */
    
    private void initGet(){//Get请求   Method为GET
		mQueue.add(new StringRequest(Method.GET,"url", new Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				Toast.makeText(OkHttpMainActivity.this, "Get请求成功"+arg0, 2).show();
				//成功，在这里写处理内容的代码
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(OkHttpMainActivity.this, "Get请求失败", 2).show();
				//失败
			}
		
		
		}));
	}
    
    
	private void initPost(){//Post请求
		//这写你自己的内部类PostResquest。。。。Method改成POST
		mQueue.add(new PostResquest(Method.POST,"url", new Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				Toast.makeText(OkHttpMainActivity.this, "Post请求成功" + arg0, 2).show();
				//成功，在这里写处理内容的代码
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(OkHttpMainActivity.this, "Post请求失败", 2).show();
				//失败
			}
		
		
		}));
		
		mQueue.add(new JsonRequest<User>(Method.POST,"url","",new Listener<User>() {

			@Override
			public void onResponse(User arg0) {
				// TODO Auto-generated method stub
				
			}
		},new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		}) {

			@Override
			protected Response<User> parseNetworkResponse(NetworkResponse arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
	//写个内部类，Post里面放一些服务器需要的参数
	class PostResquest extends StringRequest {

		public PostResquest(int method, String url,
				Listener<String> listener, ErrorListener errorListener) {
			super(method, url, listener, errorListener);
		}

		protected Map<String, String> getParams() throws AuthFailureError {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Name", "小源");//参数
			params.put("Age",22+"");//参数
			return params;
		}
	}
	
}
