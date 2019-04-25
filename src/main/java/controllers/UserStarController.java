package controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import messages.UserStarMessage;
import models.UserStar;

@RestController
public class UserStarController extends GeneralController {
	@GetMapping("/star/get")
	public UserStarMessage getStar(@RequestParam("user") String uuid, @RequestParam("file") String fileId) {
		if (uuid == null || fileId == null) {
			return new UserStarMessage(false);
		}
		UserStar star = server.find(UserStar.class).where().eq("user", uuid).eq("text_file", fileId).findOne();
		
		if (star == null) {
			return new UserStarMessage(false);
		} else {
			return new UserStarMessage(star.getStar());
		}
	}
	
	@PostMapping("/star/set")
	public UserStarMessage setStar(@RequestBody Map<String,Object> body) {
		return null;
	}
}
