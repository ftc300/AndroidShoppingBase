package com.shoping.mall.util;

import java.io.IOException;
import java.net.HttpURLConnection;

import android.content.Context;
import android.net.Uri;

import com.shoping.mall.ConstantValue;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;

public class CustomPicasso {
	 private static Picasso sPicasso;
	    private CustomPicasso() {
	   
	    }

	    public static Picasso getImageLoader(final Context context) {
	        if (sPicasso == null) {
	            Picasso.Builder builder = new Picasso.Builder(context);
	            builder.downloader(new CustomOkHttpDownloader(context));
	            sPicasso = builder.build();
	        }
	        return sPicasso;
	    }
	    private static class CustomOkHttpDownloader extends UrlConnectionDownloader{

			public CustomOkHttpDownloader(Context context) {
				super(context);
			}
	    	@Override
	    	protected HttpURLConnection openConnection(Uri path)
	    			throws IOException {
	    		 HttpURLConnection connection = super.openConnection(path);
	             connection.setRequestProperty("Referer",ConstantValue.SERVER_URL);
	             return connection;
	    	}
	    }
}
