package controllers;

import java.util.Date;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import messages.ActionResultResponse;
import messages.RegisterResponse;
import models.User;
import utils.IdentificationUtils;
import utils.PasswordUtils;

@RestController
public class UserController extends GeneralController {
	
	@RequestMapping(value = "/register", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public RegisterResponse register(@RequestParam(value = "name", defaultValue = "") String name, @RequestParam(value = "password", defaultValue = "") String password) {
		
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
	
	@RequestMapping(value = "/password/change", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ActionResultResponse changePassword(@RequestBody Map<String,Object> body) {
		String uuid = null;
		String password = null;
		String newPassword = null;
		
		try {
			uuid = body.get("user").toString();
			password = body.get("password").toString();
			newPassword = body.get("new_password").toString();
		} catch (Exception ex) {}
		
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
