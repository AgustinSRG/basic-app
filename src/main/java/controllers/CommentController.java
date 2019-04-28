package controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import messages.CommentListMessage;
import messages.CommentMessage;
import messages.CommentPostMessage;
import models.Comment;
import models.TextFile;
import models.User;
import utils.IdentificationUtils;

@RestController
public class CommentController extends GeneralController {
	@GetMapping("/comments/find")
	public CommentListMessage findComents(@RequestParam(value = "file", defaultValue = "") String fileId, @RequestParam(value="page", defaultValue = "0") long page, @RequestParam(value="items", defaultValue = "25") long items) {
		if (items < 1) {
			items = 1;
		}
		
		long total = server.find(Comment.class).where().eq("text_file", fileId).findCount();
		
		long totalPages = total / items;
		if (total % items != 0) {
			totalPages++;
		}
		
		if (page >= totalPages) {
			page = totalPages - 1;
		}
		
		if (page < 0) {
			page = 0;
		}
		
		List<Comment> results = server.find(Comment.class).where().eq("text_file", fileId).orderBy().desc("date").setFirstRow((int) (page * items)).setMaxRows((int) items).findList();
		
		CommentListMessage msg = new CommentListMessage(total, page, totalPages, items);
		
		for (Comment c: results) {
			User user = server.find(User.class).where().eq("uuid", c.getUser()).findOne();
			if (user != null) {
				msg.getItems().add(new CommentMessage(c, user));
			} else {
				System.out.println("WARNING: User not found: " + c.getUser());
			}
		}
		
		return msg;
	}
	
	@RequestMapping(value = "/comments/post", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public CommentPostMessage postComment(@RequestParam(value = "file", defaultValue = "") String fileId, @RequestParam(value = "content", defaultValue = "") String text, @CookieValue("session") String sessionCookie) {
		
		if (fileId == null || text == null || text.length() < 1 || text.length() > 300) {
			return new CommentPostMessage("BAD_REQUEST");
		}
		
		TextFile f = server.find(TextFile.class).where().eq("identifier", fileId).findOne();
		if (f == null) {
			return new CommentPostMessage("NOT_FOUND");
		}
		
		User user = this.findUserBySessionCookie(sessionCookie);
		
		if (user == null) {
			return new CommentPostMessage("NO_ACCOUNT");
		}
		
		String id = IdentificationUtils.getRandomId();
		
		Comment comment = new Comment(id);
		
		comment.setUser(user.getID());
		comment.setTextFile(f.getID());
		comment.setDate(new Date());
		comment.setContent(text);
		
		try {
			comment.save();
		} catch (Exception ex) {
			return new CommentPostMessage("DATABASE_ERROR");
		}
		
		return new CommentPostMessage(comment, user);
	}
}
