package com.shoping.mall.study.bmob.bean;

import cn.bmob.v3.BmobObject;

public class Person extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -520944256538410158L;

	private String name;
    private String address;
    private String pwd;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
