package com.shoping.mall.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	/**
	 * md5加密的工具方法
	 * @param password
	 * @return
	 */
	public static String encode(String password){
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			//16进制的方式  把结果集byte数组 打印出来
			for(byte b :result){
				int number = (b&0xff);//加盐.
				String str =Integer.toHexString(number);
				if(str.length()==1){
					sb.append("0");
				}
				sb.append(str);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
	public static String encode(byte[] bytes){
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(bytes);
			StringBuilder sb = new StringBuilder();
			//16进制的方式  把结果集byte数组 打印出来
			for(byte b :result){
				int number = (b&0xff);//加盐.
				String str =Integer.toHexString(number);
				if(str.length()==1){
					sb.append("0");
				}
				sb.append(str);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
}
