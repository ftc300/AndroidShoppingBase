package com.shoping.mall.engine.update;

import java.io.File;

public class Update {
	
	private boolean isUpdateVersion;
	private String version;
	private String description;
	private String title;
	private String apkurl;
	private File apkFile;
	private int progress;

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getApkurl() {
		return apkurl;
	}
	public void setApkurl(String apkurl) {
		this.apkurl = apkurl;
	}
	public boolean isUpdateVersion() {
		return isUpdateVersion;
	}
	public void setUpdateVersion(boolean isUpdateVersion) {
		this.isUpdateVersion = isUpdateVersion;
	}
	public File getApkFile() {
		return apkFile;
	}
	public void setApkFile(File apkFile) {
		this.apkFile = apkFile;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	@Override
	public String toString() {
		return "" + isUpdateVersion+":"+version+":"+description+":"+apkurl+":"+apkFile+":"+progress;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
