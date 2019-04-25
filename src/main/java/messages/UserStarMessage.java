package messages;

public class UserStarMessage {
	private boolean star;
	
	public UserStarMessage(boolean star) {
		this.star = star;
	}

	public boolean isStar() {
		return star;
	}

	public void setStar(boolean star) {
		this.star = star;
	}
}
