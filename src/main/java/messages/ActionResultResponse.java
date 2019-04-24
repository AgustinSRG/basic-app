package messages;

public class ActionResultResponse {
	
	private final boolean success;
	private final String errorCode;
	
	public ActionResultResponse(boolean success, String errorCode) {
		this.success = success;
		this.errorCode = errorCode;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public String getErrorCode() {
		return errorCode;
	}
	
}
