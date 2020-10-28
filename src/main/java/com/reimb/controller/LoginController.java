package com.reimb.controller;

import javax.servlet.http.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimb.model.User;
import com.reimb.service.UserService;

import java.io.IOException;

import javax.servlet.*;

public class LoginController {
	
	private UserService userService;
	
	public LoginController() {
		userService = new UserService();
	}
	
	public LoginController(UserService us) {
		userService = us;
	}
	
	public void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if (!req.getMethod().equals("POST")) {
			res.sendRedirect(req.getContextPath() + "/login.html");
		} else {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			User user = userService.verify(username, username);
			
			if (username.isEmpty() || password.isEmpty() || (user == null)) {
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				res.sendRedirect(req.getContextPath() + "/LoginFail.html");
			} else {
				HttpSession session = req.getSession(true);
				user.setPassword(null);
				session.setAttribute("User", user);
				session.setAttribute("UserRole", user.getRole());
				
				res.sendRedirect(req.getContextPath() + "/main");
			}
		}
	}
	
	public void logout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session == null) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			res.setContentType("text/plain");
			res.getWriter().write("There was no user logged into the session");
			res.getWriter().close();
		} else {
			User user = (User) session.getAttribute("User");
			session.invalidate();
			res.setContentType("text/plain");
			res.getWriter().write("You have successfully logged out of " + user.getFirstName() + " " + user.getLastName());
			res.getWriter().close();
		}
	}
	
	public void getUser(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session == null) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			res.setContentType("text/plain");
			res.getWriter().write("There was no user logged into the session");
			res.getWriter().close();
		} else {
			ObjectMapper om = new ObjectMapper();
			User user = (User) session.getAttribute("User");
			res.setContentType("application/json");
			res.getWriter().write(om.writeValueAsString(user));
		}
	}
}
