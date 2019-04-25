package messages;

import java.util.ArrayList;
import java.util.List;

public class CommentListMessage {
	private long totalResults;
	private long page;
	private long totalPages;
	private long itemsPage;
	
	private final List<CommentMessage> items;
	
	public CommentListMessage(long totalResults, long page, long totalPages, long itemsPage) {
		this.totalResults = totalResults;
		this.page = page;
		this.totalPages = totalPages;
		this.itemsPage = itemsPage;
		this.items = new ArrayList<>();
	}
	
	public long getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(long totalResults) {
		this.totalResults = totalResults;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public long getItemsPage() {
		return itemsPage;
	}

	public void setItemsPage(long itemsPage) {
		this.itemsPage = itemsPage;
	}

	public List<CommentMessage> getItems() {
		return items;
	}

}
