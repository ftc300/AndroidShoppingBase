package com.shoping.mall.study.retrofit2;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shoping.mall.R;
import com.shoping.mall.study.retrofit2.api.ChoicenessService;
import com.shoping.mall.study.retrofit2.api.GitHubService;
import com.shoping.mall.study.retrofit2.api.ItemService;
import com.shoping.mall.study.retrofit2.api.PlatformService;
import com.shoping.mall.study.retrofit2.api.ProjectService;
import com.shoping.mall.study.retrofit2.api.RetrofitService;
import com.shoping.mall.study.retrofit2.api.SecKillService;
import com.shoping.mall.study.retrofit2.api.SlideService;
import com.shoping.mall.study.retrofit2.model.ChoicenessRes;
import com.shoping.mall.study.retrofit2.model.ItemRes;
import com.shoping.mall.study.retrofit2.model.PlatformRes;
import com.shoping.mall.study.retrofit2.model.ProjectRes;
import com.shoping.mall.study.retrofit2.model.RetrofitRes;
import com.shoping.mall.study.retrofit2.model.SecKillRes;
import com.shoping.mall.study.retrofit2.model.SlideRes;
import com.shoping.mall.study.retrofit2.model.User;
import com.shoping.mall.util.LogUtil;


public class RetrofitMainActivity extends Activity {

    Button click;
    TextView tv;
    EditText edit_user;
    ProgressBar pbar;
    String API = "https://api.github.com";
    public static String MY_API = "http://172.26.213.1:8080";
    String URL_SERVICE = "http://m.mall.icbc.com.cn";
    
    private final OkHttpClient okHttpClient = new OkHttpClient();;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_activity_main);
        
        click = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.tv);
        edit_user = (EditText) findViewById(R.id.edit);
        pbar = (ProgressBar) findViewById(R.id.pb);
        pbar.setVisibility(View.INVISIBLE);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String user = edit_user.getText().toString();
                pbar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                
                GitHubService git = retrofit.create(GitHubService.class);
                Call<User> call = git.getUser("cqtddt");
                call.enqueue(new Callback<User>() {

                	public void onResponse(Response<User> response) {
                        User model = response.body();

                        if (model==null) {
                            //404 or the response cannot be converted to User.
                            ResponseBody responseBody = response.errorBody();
                            if (responseBody!=null) {
                                try {
                                    tv.setText("responseBody = "+responseBody.string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                tv.setText("responseBody = null");
                            }
                        } else {
                            //200
                            tv.setText("Github Name :"+model.getLogin()+"\nHtmlUrl :"+model.getHtmlUrl()+"\nUpdate At:"+model.getUpdatedAt());
                        }
                        pbar.setVisibility(View.INVISIBLE);
                    }

					@Override
					public void onFailure(Call<User> arg0, Throwable arg1) {
						tv.setText("t = "+ arg1.getMessage());
						pbar.setVisibility(View.INVISIBLE);
						
					}

					@Override
					public void onResponse(Call<User> arg0, Response<User> arg1) {
						onResponse(arg1);
					}
					
                });

            }
        });
        
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LogUtil.i("button pressed");
				retrofitCookies();
				//getOkhttpData();
//				Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(MY_API)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//				RetrofitService retrofitService = retrofit.create(RetrofitService.class);
//				Call<RetrofitRes> call = retrofitService.getData("shen");
//				call.enqueue(new Callback<RetrofitRes>() {
//
//					@Override
//					public void onFailure(Throwable arg0) {
//						LogUtil.i(arg0.getStackTrace().toString());
//					}
//
//					@Override
//					public void onResponse(Response<RetrofitRes> response,
//							Retrofit arg1) {
//						LogUtil.i(response.body().toString());
//						tv.setText(response.body().toString());
//						 RetrofitRes model = response.body();
//
//	                        if (model==null) {
//	                            //404 or the response cannot be converted to User.
//	                            ResponseBody responseBody = response.errorBody();
//	                            if (responseBody!=null) {
//	                                try {
//	                                    tv.setText("responseBody = "+responseBody.string());
//	                                } catch (IOException e) {
//	                                    e.printStackTrace();
//	                                }
//	                            } else {
//	                                tv.setText("responseBody = null");
//	                            }
//	                        } else {
//	                            //200
//	                            tv.setText(model.getRetrofitData().getName() + model.getRetrofitData().getImgUrl());
//	                            LogUtil.i(model.getRetrofitData().getImgUrl());
//	                        }
//					}
//				});
			}
		});
        Retrofit retrofit = new Retrofit.Builder()
		.baseUrl(URL_SERVICE)
		.addConverterFactory(GsonConverterFactory.create())
		.build();
        getSlideData(retrofit);
        getPlatform(retrofit);
        getSecKillData(retrofit);
        getProjectData(retrofit);
        getChoicenessData(retrofit);
        getItemData(retrofit);
        getOkhttpData();
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


	private void retrofitCookies() {
		/**
		 * Somewhere you create a new OkHttpClient and use it on all your requests.
		 */
		OkHttpClient okHttpClient = new OkHttpClient();  
		okHttpClient.interceptors().add(new AddCookiesInterceptor());  
		okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());
		okHttpClient.interceptors().add(new UserAgentInterceptor("shenjianli version 1.1.0.9"));

	//		okHttpClient.setCookieHandler(new CookieHandler() {
//			
//			@Override
//			public void put(URI uri, Map<String, List<String>> responseHeaders)
//					throws IOException {
//				LogUtil.i("URI" + uri + ": responseHeaders"+ responseHeaders.size());
//			}
//			
//			@Override
//			public Map<String, List<String>> get(URI uri,
//					Map<String, List<String>> requestHeaders) throws IOException {
//				LogUtil.i("URI" + uri + ": requestHeaders"+ requestHeaders.size());
//				return null;
//			}
//		});
		
		Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(MY_API)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
		
		
		RetrofitService retrofitService = retrofit.create(RetrofitService.class);
		Call<RetrofitRes> call = retrofitService.getData("shen");
		call.enqueue(new Callback<RetrofitRes>() {

			@Override
			public void onFailure(Call<RetrofitRes> arg0, Throwable arg1) {
				LogUtil.i(arg1.getStackTrace().toString());
			}

			@Override
			public void onResponse(Call<RetrofitRes> arg0,
					Response<RetrofitRes> response) {
				LogUtil.i(response.body().toString());
				tv.setText(response.body().toString());
				RetrofitRes model = response.body();

				if (model == null) {
					// 404 or the response cannot be converted to User.
					ResponseBody responseBody = response.errorBody();
					if (responseBody != null) {
						try {
							tv.setText("responseBody = "
									+ responseBody.string());
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						tv.setText("responseBody = null");
					}
				} else {
					// 200
					tv.setText(model.getRetrofitData().getName()
							+ model.getRetrofitData().getImgUrl());
					LogUtil.i(model.getRetrofitData().getImgUrl());
				}
			}
		});
		
	}
	
	private void getOkhttpData(){
		 String url = MY_API + "/ECService/retrofit?version=11";
//       url = "http://192.168.56.1:8080/test/user.do?action=login&username=fusheng&password=123";
		 LogUtil.i("start ok http");
		
		 Request.Builder builder = new Request.Builder();
		 builder.url(url).tag("sss");
		 okHttpClient.newCall(builder.build()).enqueue(new okhttp3.Callback() {
			
			@Override
			public void onFailure(okhttp3.Call arg0, IOException arg1) {
				LogUtil.i("ok http fail" + arg1.getMessage());
			}

			@Override
			public void onResponse(okhttp3.Call arg0, okhttp3.Response response)
					throws IOException {
				if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

				ResponseBody body = response.body();
				String result = new String(body.bytes());
				 

		        Headers responseHeaders = response.headers();

		        for (int i = 0; i < responseHeaders.size(); i++) {

		          System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));

		        }

		 

		        System.out.println(result);

				LogUtil.i("ok http:" + result);
			}
		});
	}
	
	
	private void getSlideData(final Retrofit retrofit)
	{
		SlideService slideService = retrofit.create(SlideService.class);
		Call<SlideRes> call = slideService.getSlideData();
		LogUtil.i("开始请求轮播广告页面");
		call.enqueue(new Callback<SlideRes>() {

			@Override
			public void onFailure(Call<SlideRes> arg0, Throwable arg1) {
				LogUtil.i("轮播广告页面请求失败");
				LogUtil.i(arg1.getStackTrace().toString());
				LogUtil.i(arg1.toString());
			}

			@Override
			public void onResponse(Call<SlideRes> arg0, Response<SlideRes> response) {
				LogUtil.i(response.body().toString());
				tv.setText(response.body().toString());
				SlideRes model = response.body();
				LogUtil.i("轮播广告页面请求成功");

				if (model == null) {
					// 404 or the response cannot be converted to User.
					ResponseBody responseBody = response.errorBody();
					if (responseBody != null) {
						try {
							tv.setText("responseBody = "
									+ responseBody.string());
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						tv.setText("responseBody = null");
					}
				} else {
					// 200
					tv.setText(String.valueOf(model.getData().getProduct().size()));
					LogUtil.i(model.getData().getProduct().size() + "");
				}
			}
		
		});
	}
	
	
	private void getPlatform(Retrofit retrofit)
	{
		PlatformService slideService = retrofit.create(PlatformService.class);
		Call<PlatformRes> call = slideService.getPlatformData();
		LogUtil.i("开始请求快速入口");
		call.enqueue(new Callback<PlatformRes>() {

			@Override
			public void onFailure(Call<PlatformRes> arg0, Throwable arg1) {
				LogUtil.i("快速入口请求失败");
				LogUtil.i(arg1.getStackTrace().toString());
				LogUtil.i(arg1.toString());
			}

			@Override
			public void onResponse(Call<PlatformRes> arg0,
					Response<PlatformRes> response) {
				LogUtil.i(response.body().toString());
				tv.setText(response.body().toString());
				PlatformRes model = response.body();
				LogUtil.i("快速入口请求成功");

				if (model == null) {
					// 404 or the response cannot be converted to User.
					ResponseBody responseBody = response.errorBody();
					if (responseBody != null) {
						try {
							tv.setText("responseBody = "
									+ responseBody.string());
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						tv.setText("responseBody = null");
					}
				} else {
					// 200
					tv.setText(String.valueOf(model.getData().getPlatform().size()));
					LogUtil.i("" + model.getData().getPlatform().size());
				}
			}
		
		});
	}
	
	
	private void getSecKillData(Retrofit retrofit)
	{
			SecKillService slideService = retrofit.create(SecKillService.class);
			Call<SecKillRes> call = slideService.getSlideData();
			LogUtil.i("开始请求秒杀数据");
			call.enqueue(new Callback<SecKillRes>() {
				
				@Override
				public void onFailure(Call<SecKillRes> arg0, Throwable arg1) {
					LogUtil.i("秒杀数据请求失败");
					LogUtil.i(arg1.getStackTrace().toString());
					LogUtil.i(arg1.toString());
				}

				@Override
				public void onResponse(Call<SecKillRes> arg0,
						Response<SecKillRes> response) {
					LogUtil.i(response.body().toString());
					tv.setText(response.body().toString());
					SecKillRes model = response.body();
					LogUtil.i("秒杀数据请求成功");
					
					if (model == null) {
						// 404 or the response cannot be converted to User.
						ResponseBody responseBody = response.errorBody();
						if (responseBody != null) {
							try {
								tv.setText("responseBody = "
										+ responseBody.string());
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							tv.setText("responseBody = null");
						}
					} else {
						// 200
						tv.setText(String.valueOf(model.getData().getProduct().size()));
						LogUtil.i("" + model.getData().getProduct().size());
					}
				}
			});
	}
	
	private void getProjectData(Retrofit retrofit)
	{
		ProjectService slideService = retrofit.create(ProjectService.class);
		Call<ProjectRes> call = slideService.getProjectData();
		LogUtil.i("开始请求专题数据");
		call.enqueue(new Callback<ProjectRes>() {
			
			@Override
			public void onFailure(Call<ProjectRes> arg0, Throwable arg1) {
				LogUtil.i("秒杀专题请求失败");
				LogUtil.i(arg1.getStackTrace().toString());
				LogUtil.i(arg1.toString());
			}

			@Override
			public void onResponse(Call<ProjectRes> arg0,
					Response<ProjectRes> response) {
				LogUtil.i(response.body().toString());
				tv.setText(response.body().toString());
				ProjectRes model = response.body();
				LogUtil.i("专题数据请求成功");
				
				if (model == null) {
					// 404 or the response cannot be converted to User.
					ResponseBody responseBody = response.errorBody();
					if (responseBody != null) {
						try {
							tv.setText("responseBody = "
									+ responseBody.string());
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						tv.setText("responseBody = null");
					}
				} else {
					// 200
					tv.setText(model.getData().getTheme().getTheme1());
					LogUtil.i("" + model.getData().getTheme().getTheme1());
				}
				
			}
		});
	}
	
	private void getChoicenessData(Retrofit retrofit)
	{
		ChoicenessService slideService = retrofit.create(ChoicenessService.class);
		Call<ChoicenessRes> call = slideService.getSlideData("1");
		LogUtil.i("开始请求热卖商品数据");
		call.enqueue(new Callback<ChoicenessRes>() {
			
			@Override
			public void onFailure(Call<ChoicenessRes> arg0, Throwable arg1) {
				LogUtil.i("热卖商品请求失败");
				LogUtil.i(arg1.getStackTrace().toString());
				LogUtil.i(arg1.toString());
			}

			@Override
			public void onResponse(Call<ChoicenessRes> arg0,
					Response<ChoicenessRes> response) {
				LogUtil.i(response.body().toString());
				tv.setText(response.body().toString());
				ChoicenessRes model = response.body();
				LogUtil.i("热卖商品请求成功");
				
				if (model == null) {
					// 404 or the response cannot be converted to User.
					ResponseBody responseBody = response.errorBody();
					if (responseBody != null) {
						try {
							tv.setText("responseBody = "
									+ responseBody.string());
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						tv.setText("responseBody = null");
					}
				} else {
					// 200
					tv.setText(model.getData().getProduct().get(0).getProdName());
					LogUtil.i("" + model.getData().getProduct().size());
				}
			}
		});
	}
	
	
	private void getItemData(Retrofit retrofit) {

		ItemService slideService = retrofit.create(ItemService.class);
		Call<ItemRes> call = slideService.getItemData();
		LogUtil.i("开始请求行业精选区");
		call.enqueue(new Callback<ItemRes>() {
			
			@Override
			public void onFailure(Call<ItemRes> arg0, Throwable arg1) {
				LogUtil.i("行业精选区请求失败");
				LogUtil.i(arg1.getStackTrace().toString());
				LogUtil.i(arg1.toString());
			}

			@Override
			public void onResponse(Call<ItemRes> arg0, Response<ItemRes> response) {
				LogUtil.i(response.body().toString());
				tv.setText(response.body().toString());
				ItemRes model = response.body();
				LogUtil.i("行业精选区请求成功");
				
				if (model == null) {
					// 404 or the response cannot be converted to User.
					ResponseBody responseBody = response.errorBody();
					if (responseBody != null) {
						try {
							tv.setText("responseBody = "
									+ responseBody.string());
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						tv.setText("responseBody = null");
					}
				} else {
					// 200
					tv.setText(model.getData().getIndustry().get(0).getProdName());
					LogUtil.i("" + model.getData().getIndustry().size());
				}
			}
		});
	
	}
}
