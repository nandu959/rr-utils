package com.ramyarecipes.entity;

import java.util.List;

public class Post {

	private String id;
	private String title;
	private String timestamp;
	private String link;
	private String content;
	private List<String> tags;
	private String comments;
	private List<Attachment> attachments;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	} 
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", timestamp=" + timestamp + ", link=" + link + ", content="
				+ content + ", tags=" + tags + ", comments=" + comments + ", attachments=" + attachments + "]";
	}
	
	



}
