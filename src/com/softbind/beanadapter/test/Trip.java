package com.softbind.beanadapter.test;

public class Trip {
	private String name;
	private String desc;
	private String positionRef;
	private String shareSuggestion;
	private String district;
	private String url;
	
	public Trip setName(String name) {
		this.name = name;
		return this;
	}
	public Trip setDesc(String desc) {
		this.desc = desc;
		return this;
	}
	public Trip setPositionRef(String positionRef) {
		this.positionRef = positionRef;
		return this;
	}
	public Trip setShareSuggestion(String shareSuggestion) {
		this.shareSuggestion = shareSuggestion;
		return this;
	}
	public Trip setDistrict(String district) {
		this.district = district;
		return this;
	}
	public Trip setUrl(String url) {
		this.url = url;
		return this;
	}

	
}
