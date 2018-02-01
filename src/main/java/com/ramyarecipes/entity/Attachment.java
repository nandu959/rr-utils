package com.ramyarecipes.entity;

public class Attachment {
	private String id;
	private String link;
	private String pid;
	private boolean featured;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link; 
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public boolean isFeatured() {
		return featured;
	}
	public void setFeatured(boolean featured) {
		this.featured = featured;
	}
	
	@Override
	public String toString() {
		return "Attachment [id=" + id + ", link=" + link + ", pid=" + pid + ", featured=" + featured + "]";
	}
	
	
	
	
}
