package com.reimb.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimb.model.User;
import com.reimb.model.UserRole;
import com.reimb.service.UserService;

public class UserController {
	
	private UserService userService;
	private ObjectMapper om = new ObjectMapper();
	
	public UserController() {
		userService = new UserService();
	}

	public void create(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (!req.getMethod().equals("POST") || session == null) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			UserRole sessionRole = (UserRole) session.getAttribute("UserRole");
			if (sessionRole.getRole() == "manager") {
				User newUser = om.readValue(req.getReader(), User.class);
				Boolean success = userService.createUser(newUser);
				res.setStatus((success) ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_CONFLICT);
			} else {
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
	}

}
