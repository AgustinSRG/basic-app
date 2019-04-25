package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import io.ebean.EbeanServer;
import models.Session;
import models.User;

@RestController
public class GeneralController {

	@Autowired
    protected EbeanServer server;
	
	/**
	 * Finds the active user by the session cookie
	 * @param sessionCookie The session cookie
	 * @return The active user
	 */
	public User findUserBySessionCookie(String sessionCookie) {
		if (sessionCookie == null) {
			return null;
		}
		
		Session s = server.find(Session.class).where().eq("token", sessionCookie).findOne();
		
		if (s == null) {
			return null;
		}
		
		if (s.getExpires().getTime() > 0 && s.getExpires().getTime() < System.currentTimeMillis()) {
			s.delete();
			return null;
		}
		
		User user = server.find(User.class).where().eq("uuid", s.getUser()).findOne();
		
		return user;
	}
}
