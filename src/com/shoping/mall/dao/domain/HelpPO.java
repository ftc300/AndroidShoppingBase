package com.shoping.mall.dao.domain;

import com.shoping.mall.dao.DBHelper;
import com.shoping.mall.dao.annotation.Column;
import com.shoping.mall.dao.annotation.ID;
import com.shoping.mall.dao.annotation.TableName;

@TableName(DBHelper.TABLE_HELP_NAME)
public class HelpPO {

	@ID(autoincrement=false)
	@Column(DBHelper.TABLE_HELP_ID)
	private int id;
	@Column(DBHelper.TABLE_HELP_TITLE)
	private String title;
	@Column(DBHelper.TABLE_HELP_VERSION)
	private String version;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
