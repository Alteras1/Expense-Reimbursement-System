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

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	public void create(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (!req.getMethod().equals("POST") || session == null) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			UserRole sessionRole = (UserRole) session.getAttribute("UserRole");
			System.out.println(sessionRole);
			if (sessionRole.getRole().equals("Manager")) {
				User newUser = om.readValue(req.getReader(), User.class);
				Boolean success = userService.createUser(newUser);
				res.setStatus((success) ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_CONFLICT);
				res.getWriter().write(om.writeValueAsString(success));
				res.getWriter().close();
			} else {
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
	}

	public void update(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (!req.getMethod().equals("POST") || session == null) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			User requester = (User) session.getAttribute("User");
			User newUser = om.readValue(req.getReader(), User.class);
			Boolean success = userService.updateUser(newUser, requester);
			res.setStatus((success) ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_UNAUTHORIZED);
			res.getWriter().write(om.writeValueAsString(success));
			res.getWriter().close();
		}
	}
	
	public void delete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (!req.getMethod().equals("DELETE") || session == null) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			User requester = (User) session.getAttribute("User");
			User user = om.readValue(req.getReader(), User.class);
			Boolean success = userService.deleteUser(user, requester);
			res.setStatus((success) ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_UNAUTHORIZED);
			res.getWriter().write(om.writeValueAsString(success));
			res.getWriter().close();
		}
	}
	
	public void checkUsername(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			String username = req.getParameter("username");
			Boolean success = userService.checkUsername(username);
			res.getWriter().write(om.writeValueAsString(success));
			res.getWriter().close();
	}
}
