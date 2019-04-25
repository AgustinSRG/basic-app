package messages;

import java.text.SimpleDateFormat;

import models.TextFile;
import models.User;

public class TextFileMessage extends ActionResultResponse {
	
	private String id;
	private String title;
	private String content;
	
	private String userId;
	private String userName;
	
	private String creationDate;
	private String lastEditDate;
	
	public TextFileMessage(String errorCode) {
		super(false, errorCode);
	}
	
	public TextFileMessage(TextFile file, User publisher) {
		super(true, "OK");
		SimpleDateFormat f = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
		this.id = file.getIdentifier();
		this.title = file.getTitle();
		this.content = file.getContent();
		this.creationDate = f.format(file.getCreationDate());
		this.lastEditDate = f.format(file.getLastEditDate());
		this.userId = publisher.getID();
		this.userName = publisher.getName();
	}
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getLastEditDate() {
		return lastEditDate;
	}
	public void setLastEditDate(String lastEditDate) {
		this.lastEditDate = lastEditDate;
	}
}
