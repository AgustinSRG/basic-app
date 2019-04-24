package controllers;

import org.springframework.web.bind.annotation.RestController;

import messages.ActionResultResponse;
import messages.LoginResponse;

@RestController
public class SessionController extends GeneralController {
	public LoginResponse login() {
		return null;

	}
	
	public ActionResultResponse logout() {
		return null;
	}
}
