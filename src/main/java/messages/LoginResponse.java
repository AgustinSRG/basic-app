package messages;

public class LoginResponse extends RegisterResponse {

	public LoginResponse(boolean success, String errorCode, String userId, String userName) {
		super(success, errorCode, userId, userName);
	}

}
