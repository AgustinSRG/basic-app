package controllers;

import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import messages.ActionResultResponse;
import messages.LoginResponse;
import models.Session;
import models.User;
import utils.IdentificationUtils;
import utils.PasswordUtils;

@RestController
public class SessionController extends GeneralController {
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody Map<String,Object> body) {
		String name = null;
		String password = null;
		boolean expires = true;
		
		try {
			name = body.get("name").toString();
			password = body.get("password").toString();
			expires = body.get("expiration").toString().equalsIgnoreCase("yes");
		} catch (Exception ex) {}
		
		if (name == null || password == null || name.length() < 1 || password.length() < 1 || name.length() > 80) {
			return new LoginResponse(false, "BAD_REQUEST", null, null, null);
		}
		
		String uuid = IdentificationUtils.getUserId(name);
		
		User user = server.find(User.class).where().eq("uuid", uuid).findOne();
		
		if (user == null) {
			return new LoginResponse(false, "USER_NOT_FOUND", null, null, null);
		}
		
		String hash = PasswordUtils.hashPassword(password, user.getPasswordSalt());
		
		if (hash.equals(user.getPasswordHash())) {
			// Create session
			
			String sessionId = IdentificationUtils.getRandomId();
			
			Session s = new Session(sessionId);
			
			if (expires) {
				s.setExpires(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)));
			} else {
				s.setExpires(new Date(0L));
			}
			
			user.setName(name);
			
			try {
				s.save();
				user.save();
			} catch (Exception ex) {
				return new LoginResponse(false, "DATABASE_ERROR", null, null, null);
			}
			
			return new LoginResponse(true, "OK", user.getID(), user.getName(), s.getID());
		} else {
			return new LoginResponse(false, "INVALID_PASSWORD", null, null, null);
		}
	}
	
	@PostMapping("/logout")
	public ActionResultResponse logout(@RequestBody Map<String,Object> body) {
		String token = null;
		
		try {
			token = body.get("token").toString();
		} catch (Exception ex) {}
		
		if (token == null) {
			return new ActionResultResponse(false, "BAD_REQUEST");
		}
		
		Session s = server.find(Session.class).where().eq("token", token).findOne();
		
		if (s == null) {
			return new ActionResultResponse(true, "NOT_SESSION");
		}
		
		s.delete();
		
		return new ActionResultResponse(true, "OK");
	}
}
