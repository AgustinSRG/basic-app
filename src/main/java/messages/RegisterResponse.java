package messages;

public class RegisterResponse extends ActionResultResponse {
	private String userId;
	private String userName;
	
	public RegisterResponse(boolean success, String errorCode, String userId, String userName) {
		super(success, errorCode);
		this.userId = userId;
		this.userName = userName;
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
