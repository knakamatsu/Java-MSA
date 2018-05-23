package com.model;

import io.swagger.annotations.ApiModelProperty;

public class S3File {

	@ApiModelProperty(notes = "ファイル名" , example = "build.gradle")
	private String name;
	@ApiModelProperty(notes = "ファイルサイズ（単位付）" , example = "1KB")
	private String displaySize;
	@ApiModelProperty(notes = "ファイルサイズ（byte）" , example = "1024")
	private Long size;
	@ApiModelProperty(notes = "最終更新日" , example = "2018/05/23 19:00:20")
	private String lastUpdated;
	@ApiModelProperty(notes = "ファイルパス(downloadAPI利用時に指定する)" , example = "test/build.gradle")
	private String downloadPath;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplaySize() {
		return displaySize;
	}
	public void setDisplaySize(String displaySize) {
		this.displaySize = displaySize;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
}
