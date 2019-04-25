package messages;

public class LoginResponse extends RegisterResponse {
	
	private String token;

	public LoginResponse(boolean success, String errorCode, String userId, String userName, String token) {
		super(success, errorCode, userId, userName);
		this.setToken(token);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
