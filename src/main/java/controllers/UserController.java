package controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.ebean.EbeanServer;
import messages.ActionResultResponse;
import messages.RegisterResponse;
import models.User;
import utils.IdentificationUtils;
import utils.PasswordUtils;

@RestController
public class UserController extends GeneralController {
	
	@PostMapping("/register")
	public RegisterResponse register(@RequestParam("name") String name, @RequestParam("password") String password) {
		if (name == null || password == null || name.length() < 1 || password.length() < 1 || name.length() > 80) {
			return new RegisterResponse(false, "BAD_REQUEST", null, null);
		}
		
		String uuid = IdentificationUtils.getUserId(name);
		String salt = PasswordUtils.randomSalt();
		String hash = PasswordUtils.hashPassword(password, salt);
		
		User user = new User(uuid);
		
		user.setName(name);
		user.setPasswordHash(hash);
		user.setPasswordSalt(salt);
		user.setPasswordAlgo("SHA-256");
		user.setRegisterDate(new Date());
		
		try {
			user.save();
		} catch (Exception e) {
			return new RegisterResponse(false, "NAME_TAKEN", null, null);
		}
		
		return new RegisterResponse(true, "OK", user.getID(), user.getName());
	}
	
	@PostMapping("/password/change")
	public ActionResultResponse changePassword(@RequestParam("user") String uuid, @RequestParam("password") String password, @RequestParam("newPassword") String newPassword) {
		if (uuid == null || password == null || newPassword == null) {
			return new ActionResultResponse(false, "BAD_REQUEST");
		}
		
		User user = server.find(User.class).where().eq("uuid", uuid).findOne();
		
		if (user == null) {
			return new ActionResultResponse(false, "USER_NOT_FOUND");
		}
		
		String hash = PasswordUtils.hashPassword(password, user.getPasswordSalt());
		
		if (hash.equals(user.getPasswordHash())) {
			String salt = PasswordUtils.randomSalt();
			user.setPasswordHash(hash);
			user.setPasswordSalt(salt);
			user.setPasswordAlgo("SHA-256");
			
			user.save();
			
			return new ActionResultResponse(true, "OK");
		} else {
			return new ActionResultResponse(false, "INVALID_PASSWORD");
		}
	}
	
}
