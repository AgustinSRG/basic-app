package controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import messages.CommentListMessage;
import messages.CommentMessage;

@RestController
public class CommentController extends GeneralController {
	@GetMapping("/comments/find")
	public CommentListMessage findComents(@RequestParam("file") String fileId, @RequestParam(value="page", defaultValue = "0") long page, @RequestParam(value="items", defaultValue = "25") long items) {
		return null;
	}
	
	@PostMapping("/comments/post")
	public CommentMessage postComment(@RequestBody Map<String,Object> body) {
		return null;
	}
}
