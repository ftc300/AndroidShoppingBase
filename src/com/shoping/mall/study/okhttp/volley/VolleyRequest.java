package com.shoping.mall.study.okhttp.volley;

import java.io.File;
import java.util.IdentityHashMap;
import java.util.Map;

import android.util.Pair;
import android.widget.ImageView;

import com.shoping.mall.study.okhttp.lib.callback.ResultCallback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;

/**
 * Created by zhy on 15/11/6.
 */
public abstract class VolleyRequest
{
    protected VolleyClientManager mVolleyClientManager = VolleyClientManager.getInstance();

    protected String requestBody;
    protected Request request;

    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected VolleyRequest(String url, Object tag,
                            Map<String, String> params, Map<String, String> headers)
    {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
    }

    protected abstract int getRequestMethod();
    
    protected abstract void buildRequest();

    protected abstract String buildRequestBody();

    protected void prepareInvoked(ResultCallback callback)
    {
        requestBody = buildRequestBody();
        requestBody = wrapRequestBody(requestBody, callback);
        buildRequest();
    }


    public void invokeAsyn(VolleyRequest request,ResultCallback callback)
    {
        prepareInvoked(callback);
        mVolleyClientManager.execute(request, callback);
    }

    protected String wrapRequestBody(String requestBody, final ResultCallback callback)
    {
        return requestBody;
    }


    protected void appendHeaders(Request.Builder builder, Map<String, String> headers){
        if (builder == null){
            throw new IllegalArgumentException("builder can not be empty!");
        }

        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()){
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    public void cancel(){
        if (tag != null)
        	mVolleyClientManager.cancelTag(tag);
    }


    public static class Builder
    {
        private String url;
        private Object tag;
        private Map<String, String> headers;
        private Map<String, String> params;
        private Pair<String, File>[] files;
        private MediaType mediaType;

        private String destFileDir;
        private String destFileName;

        private ImageView imageView;
        private int errorResId = -1;

        //for post
        private String content;
        private byte[] bytes;
        private File file;

        public Builder url(String url)
        {
            this.url = url;
            return this;
        }

        public Builder tag(Object tag)
        {
            this.tag = tag;
            return this;
        }

        public Builder params(Map<String, String> params)
        {
            this.params = params;
            return this;
        }

        public Builder addParams(String key, String val)
        {
            if (this.params == null){
                params = new IdentityHashMap<String,String>();
            }
            params.put(key, val);
            return this;
        }

        public Builder headers(Map<String, String> headers)
        {
            this.headers = headers;
            return this;
        }

        public Builder addHeader(String key, String val)
        {
            if (this.headers == null)
            {
                headers = new IdentityHashMap<String,String>();
            }
            headers.put(key, val);
            return this;
        }


        public Builder files(Pair<String, File>... files)
        {
            this.files = files;
            return this;
        }

        public Builder destFileName(String destFileName)
        {
            this.destFileName = destFileName;
            return this;
        }

        public Builder destFileDir(String destFileDir)
        {
            this.destFileDir = destFileDir;
            return this;
        }


        public Builder imageView(ImageView imageView)
        {
            this.imageView = imageView;
            return this;
        }

        public Builder errResId(int errorResId)
        {
            this.errorResId = errorResId;
            return this;
        }

        public Builder content(String content)
        {
            this.content = content;
            return this;
        }

        public Builder mediaType(MediaType mediaType)
        {
            this.mediaType = mediaType;
            return this;
        }

        public VolleyRequest get(ResultCallback callback)
        {
            VolleyRequest request = new VolleyGetRequest(url, tag, params, headers);
            request.invokeAsyn(request,callback);
            return request;
        }

        

    }


}
