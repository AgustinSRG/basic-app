package controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import messages.UserStarMessage;
import models.TextFile;
import models.User;
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
	public UserStarMessage setStar(@RequestBody Map<String,Object> body, @CookieValue("session") String sessionCookie) {
		String fileId = null;
		boolean star = false;
		
		try {
			fileId = body.get("file").toString();
			star = body.get("star").toString().equalsIgnoreCase("yes");
		} catch (Exception ex) {}
		
		TextFile f = server.find(TextFile.class).where().eq("identifier", fileId).findOne();
		if (f == null) {
			return new UserStarMessage(false);
		}
		
		User user = this.findUserBySessionCookie(sessionCookie);
		
		if (user == null) {
			return new UserStarMessage(false);
		}
		
		UserStar starO = server.find(UserStar.class).where().eq("user", user.getID()).eq("text_file", fileId).findOne();
		
		if (starO == null) {
			starO = new UserStar();
			starO.setTextfile(f.getID());
			starO.setUser(user.getID());
		}

		starO.setStar(star);
		
		try {
			starO.save();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return new UserStarMessage(star);
	}
}
