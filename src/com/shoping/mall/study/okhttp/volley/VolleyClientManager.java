package com.shoping.mall.study.okhttp.volley;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.shoping.mall.application.MallApp;
import com.shoping.mall.study.okhttp.MyApplication;
import com.shoping.mall.study.okhttp.lib.callback.ResultCallback;
import com.squareup.okhttp.Request;

/**
 * Created by zhy on 15/8/17.
 */
public class VolleyClientManager
{

    private static VolleyClientManager mInstance;
    private RequestQueue mQueue;
    private Handler mDelivery;
    private Gson mGson;

    private VolleyClientManager()
    {
    	mQueue = Volley.newRequestQueue(MallApp.getInstance());
        mDelivery = new Handler(Looper.getMainLooper());

        final int sdk = Build.VERSION.SDK_INT;
        if (sdk >= 23)
        {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .excludeFieldsWithModifiers(
                            Modifier.FINAL,
                            Modifier.TRANSIENT,
                            Modifier.STATIC);
            mGson = gsonBuilder.create();
        } else
        {
            mGson = new Gson();
        }
    }

    public static VolleyClientManager getInstance(){
        if (mInstance == null)
        {
            synchronized (VolleyClientManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new VolleyClientManager();
                }
            }
        }
        return mInstance;
    }

    public Handler getDelivery()
    {
        return mDelivery;
    }



    public <T> void execute(final VolleyRequest request, ResultCallback<T> callback)
    {
    	 if (callback == null) callback = (ResultCallback<T>) ResultCallback.DEFAULT_RESULT_CALLBACK;
        
    	 final ResultCallback<T> resCallBack = callback;
          resCallBack.onBefore(null);
            if (callback.mType == String.class){
            	 mQueue.add(new StringRequest(Method.GET,request.url,new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						
					}
            		 
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						
					}
					
				}));
            } else
            {
            	mQueue.add(new JsonRequest<T>(request.getRequestMethod(),request.url,request.requestBody,new Listener<T>() {

        			@Override
        			public void onResponse(T arg0) {
        				Log.i("onResponse", arg0.toString());
        				resCallBack.onResponse(arg0);
        				
        			}
        			
        			
        		},new ErrorListener() {

        			@Override
        			public void onErrorResponse(VolleyError arg0) {
        				Log.i("onErrorResponse", arg0.toString());
        				resCallBack.onError(null, arg0);
        			}
        		}) {

        			private int SOCKET_TIMEOUT = 10000;

					@Override
        			protected Response<T> parseNetworkResponse(NetworkResponse response) {
        				//String jsonString = new String(response.data);
        				//Object o = mGson.fromJson(jsonString, );
        				try {
        		             String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        		             return (Response<T>) Response.success(mGson.fromJson(json, resCallBack.mType),
        		                     HttpHeaderParser.parseCacheHeaders(response));
        			        } catch (UnsupportedEncodingException e) {
        			             return Response.error(new ParseError(e));
        			        } catch (JsonSyntaxException e) {
        			             return Response.error(new ParseError(e));
        			         }
        			}
        			
        			
        			@Override
        			public Map<String, String> getHeaders()
        					throws AuthFailureError {

        				Map<String, String> headers = new HashMap<String, String>();
        				headers.put("Charset", "UTF-8");
        				headers.put("Content-Type", "application/x-javascript");
        				headers.put("Accept-Encoding", "gzip,deflate");
        				return headers;
        			}
        			
        			@Override
        			public byte[] getBody() {
        				// TODO Auto-generated method stub
        				//return request.params == null ? super.getBody() : request.params.getBytes();

        				return super.getBody();
        			}
        			
        			@Override
        			public RetryPolicy getRetryPolicy() {
        				RetryPolicy retryPolicy = new DefaultRetryPolicy(SOCKET_TIMEOUT , DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        				return retryPolicy;
        			}
        			
        			
        		});
            }
    	
    }

   
    public void sendFailResultCallback(final Request request, final Exception e, final ResultCallback callback)
    {
        if (callback == null) return;

        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onError(request, e);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final ResultCallback callback)
    {
        if (callback == null) return;
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onResponse(object);
                callback.onAfter();
            }
        });
    }


    public void cancelTag(Object tag){
        mQueue.cancelAll(tag);
    }


    public void setCertificates(InputStream... certificates)
    {
        setCertificates(certificates, null, null);
    }

    private TrustManager[] prepareTrustManager(InputStream... certificates)
    {
        if (certificates == null || certificates.length <= 0) return null;
        try
        {

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates)
            {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try
                {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e)

                {
                }
            }
            TrustManagerFactory trustManagerFactory = null;

            trustManagerFactory = TrustManagerFactory.
                    getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            return trustManagers;
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (CertificateException e)
        {
            e.printStackTrace();
        } catch (KeyStoreException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    private KeyManager[] prepareKeyManager(InputStream bksFile, String password)
    {
        try
        {
            if (bksFile == null || password == null) return null;

            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();

        } catch (KeyStoreException e)
        {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e)
        {
            e.printStackTrace();
        } catch (CertificateException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void setCertificates(InputStream[] certificates, InputStream bksFile, String password)
    {
        
    }

    private X509TrustManager chooseTrustManager(TrustManager[] trustManagers)
    {
        for (TrustManager trustManager : trustManagers)
        {
            if (trustManager instanceof X509TrustManager)
            {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }


    private class MyTrustManager implements X509TrustManager
    {
        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;

        public MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException
        {
            TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            var4.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
            this.localTrustManager = localTrustManager;
        }


        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {
            try
            {
                defaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException ce)
            {
                localTrustManager.checkServerTrusted(chain, authType);
            }
        }


        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return new X509Certificate[0];
        }
    }


    private class MyJsonRequest<T> extends JsonRequest<T>{

		public MyJsonRequest(int method, String url, String requestBody,
				com.android.volley.Response.Listener<T> listener,
				com.android.volley.Response.ErrorListener errorListener) {
			super(method, url, requestBody, listener, errorListener);
		}

		@Override
		protected com.android.volley.Response<T> parseNetworkResponse(
				NetworkResponse arg0) {
			
			return null;
		}
    	
    }
}

