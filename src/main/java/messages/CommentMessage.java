package messages;

import java.text.SimpleDateFormat;

import models.Comment;
import models.User;

public class CommentMessage {
	private String id;
	
	private String userId;
	private String userName;
	
	private String date;
	private String message;
	
	public CommentMessage(Comment comment, User publisher) {
		this.id = comment.getID();
		SimpleDateFormat f = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
		this.userId = publisher.getID();
		this.userName = publisher.getName();
		this.date = f.format(comment.getDate());
		this.message = comment.getContent();
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
