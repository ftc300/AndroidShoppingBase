package com.shoping.mall.study.retrofit2.my;

import retrofit2.Retrofit;

import com.google.gson.Gson;
import com.shoping.mall.study.retrofit2.my.Converter.AvatarConverter;
import com.squareup.okhttp.OkHttpClient;


/**
 * Created by LiCola on  2016/04/16  0:08
 */
public class RetrofitClient {

    //所有的联网地址 统一成https
    public final static String mBaseUrl = "https://api.huaban.com/";

    public static Gson gson = new Gson();

   // private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

//    public static Retrofit.Builder builder = new Retrofit.Builder()
//            .baseUrl(mBaseUrl)
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
//
//    public static <S> S createService(Class<S> serviceClass) {
//        Retrofit retrofit = builder
//                .client(addLogClient(httpClient).build())
//                .addConverterFactory(AvatarConverter.create(gson))
//                .build();
//        return retrofit.create(serviceClass);
//    }
//
//    public static <S> S createService(Class<S> serviceClass,OnProgressResponseListener listener) {
//        Retrofit retrofit = builder
//                .client(addProgressClient(httpClient,listener).build())
//                .build();
//        return retrofit.create(serviceClass);
//    }


}
