package messages;

import java.text.SimpleDateFormat;

import models.TextFile;
import models.User;

public class TextFileMinMessage {
	private String id;
	private String title;
	private String creationDate;
	private String userId;
	private String userName;
	
	public TextFileMinMessage(TextFile file, User publisher) {
		SimpleDateFormat f = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
		this.id = file.getIdentifier();
		this.title = file.getTitle();
		this.creationDate = f.format(file.getCreationDate());
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

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
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
}
