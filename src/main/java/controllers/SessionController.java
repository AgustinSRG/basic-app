package controllers;

import java.util.Date;
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
import messages.LoginResponse;
import models.Session;
import models.User;
import utils.IdentificationUtils;
import utils.PasswordUtils;

@RestController
public class SessionController extends GeneralController {
	
	@RequestMapping(value = "/login", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public LoginResponse login(@RequestParam(value = "name", defaultValue = "") String name, @RequestParam(value = "password", defaultValue = "") String password, @RequestParam(value = "expiration", defaultValue = "") String expireStr) {
		boolean expires = expireStr.equalsIgnoreCase("yes");
		
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
			
			s.setUser(user.getID());
			
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
				ex.printStackTrace();
				return new LoginResponse(false, "DATABASE_ERROR", null, null, null);
			}
			
			return new LoginResponse(true, "OK", user.getID(), user.getName(), s.getID());
		} else {
			return new LoginResponse(false, "INVALID_PASSWORD", null, null, null);
		}
	}
	
	@GetMapping("/session")
	public LoginResponse session(@CookieValue("session") String sessionCookie) {
		if (sessionCookie == null) {
			return new LoginResponse(false, "NO_SESSION", null, null, null);
		}
		
		User user = this.findUserBySessionCookie(sessionCookie);
		
		if (user == null) {
			return new LoginResponse(false, "SESSION_EXPIRED", null, null, null);
		}
		
		return new LoginResponse(true, "SESSION_ACTIVE", user.getID(), user.getName(), sessionCookie);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ActionResultResponse logout(@RequestParam(value = "token", defaultValue = "") String token) {
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
