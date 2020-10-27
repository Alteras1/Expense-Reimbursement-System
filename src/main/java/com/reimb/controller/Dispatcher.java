package com.reimb.controller;

import javax.servlet.http.*;

import java.io.IOException;

import javax.servlet.*;

public class Dispatcher {
	
	private LoginController loginController;
	private ReimbController reimbController;
	private UserController userController;
	
	public Dispatcher() {
		loginController = new LoginController();
		reimbController = new ReimbController();
		userController = new UserController();
	}
	
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Expected String request.getContextPath() = "/Reimbursement"
		//Expected String request.getServletPath() = "/api"
		//Expected String request.getPathInfo() = "/login" "/reimb/..." "/status" "/type" "/user/..."
		
		String path = request.getPathInfo();
		
		switch (path) {
		case "/login":			//login
			loginController.login(request, response);
			break;
		case "/logout":			//logout
			loginController.logout(request, response);
			break;
		case "/reimb":			//add, update, delete, view all
			
			break;
		case "/status":			//view statuses
			break;
		case "/type":			//view types
			break;
		case "/reimbVerify":	//approve/deny reimb
			break;
		case "/user":			//get current user, update, delete user
			switch (request.getMethod()) {
			case "GET":
				loginController.getUser(request, response);
				break;
			}
			break;
		case "/user/new":		//create new user
			break;
		case "/user/avail":		//check username availability
			break;
		default: {				//special endpoint
			if (path.split("/").length == 3) {
				String specialPath = path.substring(0, path.indexOf("/", 8));
				switch(specialPath) {
				case "/reimb/id":		//view by ID
					break;
				case "/reimb/author":
					break;
				}
			}
			}
		}
	}
}
