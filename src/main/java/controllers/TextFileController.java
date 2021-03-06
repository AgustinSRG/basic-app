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

import messages.ActionResultResponse;
import messages.TextFileListMessage;
import messages.TextFileMessage;
import messages.TextFileMinMessage;
import models.TextFile;
import models.User;
import utils.IdentificationUtils;

@RestController
public class TextFileController extends GeneralController {
	
	@GetMapping("/files/public")
	public TextFileListMessage findPublic(@RequestParam(value="page", defaultValue = "0") long page, @RequestParam(value="items", defaultValue = "25") long items) {
		if (items < 1) {
			items = 1;
		}
		
		long total = server.find(TextFile.class).findCount();
		
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
		
		List<TextFile> results = server.find(TextFile.class).orderBy().desc("creation_date").setFirstRow((int) (page * items)).setMaxRows((int) items).findList();
		
		TextFileListMessage msg = new TextFileListMessage(total, page, totalPages, items);
		
		for (TextFile f: results) {
			User user = server.find(User.class).where().eq("uuid", f.getUser()).findOne();
			if (user != null) {
				msg.getItems().add(new TextFileMinMessage(f, user));
			} else {
				System.out.println("WARNING: User not found: " + f.getUser());
			}
		}
		
		return msg;
	}
	
	@GetMapping("/files/own")
	public TextFileListMessage findOwn(@RequestParam(value="page", defaultValue = "0") long page, @RequestParam(value="items", defaultValue = "25") long items, @CookieValue("session") String sessionCookie) {
		
		User user = this.findUserBySessionCookie(sessionCookie);
		
		if (user == null) {
			return new TextFileListMessage(0, 0, 0, items);
		}
		
		if (items < 1) {
			items = 1;
		}
		
		long total = server.find(TextFile.class).where().eq("user", user.getID()).findCount();
		
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
		
		List<TextFile> results = server.find(TextFile.class).where().eq("user", user.getID()).orderBy().desc("creation_date").setFirstRow((int) (page * items)).setMaxRows((int) items).findList();
		
		TextFileListMessage msg = new TextFileListMessage(total, page, totalPages, items);
		
		for (TextFile f: results) {
			msg.getItems().add(new TextFileMinMessage(f, user));
		}
		
		return msg;
	}
	
	@GetMapping("/files/get")
	public TextFileMessage getFile(@RequestParam("id") String id) {
		if (id == null) {
			return new TextFileMessage("NOT_FOUND");
		}
		
		TextFile f = server.find(TextFile.class).where().eq("identifier", id).findOne();
		if (f == null) {
			return new TextFileMessage("NOT_FOUND");
		}
		
		User user = server.find(User.class).where().eq("uuid", f.getUser()).findOne();
		
		if (user == null) {
			return new TextFileMessage("NOT_FOUND");
		}
		
		return new TextFileMessage(f, user);
	}
	
	@RequestMapping(value = "/files/create", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public TextFileMessage createFile(@RequestParam(value = "title", defaultValue = "") String title, @RequestParam(value = "content", defaultValue = "") String content, @CookieValue("session") String sessionCookie) {
		if (content == null) {
			content = "";
		}
		
		if (title == null || title.length() < 1 || title.length() > 160) {
			return new TextFileMessage("BAD_REQUEST");
		}
		
		User user = this.findUserBySessionCookie(sessionCookie);
		
		if (user == null) {
			return new TextFileMessage("NO_ACCOUNT");
		}
		
		String id = IdentificationUtils.getRandomId();
		
		TextFile file = new TextFile(id);
		
		file.setUser(user.getID());
		file.setCreationDate(new Date());
		file.setLastEditDate(new Date());
		file.setTitle(title);
		file.setContent(content);
		
		try {
			file.save();
		} catch (Exception ex) {
			ex.printStackTrace();
			return new TextFileMessage("DATABASE_ERROR");
		}
		
		return new TextFileMessage(file, user);
	}
	
	@RequestMapping(value = "/files/edit", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ActionResultResponse editFile(@RequestParam(value = "id", defaultValue = "") String id, @RequestParam(value = "title", defaultValue = "") String title, @RequestParam(value = "content", defaultValue = "") String content, @CookieValue("session") String sessionCookie) {
		if (content == null) {
			content = "";
		}
		
		if (id == null || title == null || title.length() < 1 || title.length() > 160) {
			return new ActionResultResponse(false, "BAD_REQUEST");
		}
		
		TextFile f = server.find(TextFile.class).where().eq("identifier", id).findOne();
		if (f == null) {
			return new ActionResultResponse(false, "NOT_FOUND");
		}
		
		User user = this.findUserBySessionCookie(sessionCookie);
		
		if (user == null) {
			return new ActionResultResponse(false, "NO_ACCOUNT");
		}
		
		if (!f.getUser().equals(user.getID())) {
			return new ActionResultResponse(false, "ACCESS_DENIED");
		}
		
		f.setTitle(title);
		f.setContent(content);
		
		f.save();
		
		return new ActionResultResponse(true, "OK");
	}
}
