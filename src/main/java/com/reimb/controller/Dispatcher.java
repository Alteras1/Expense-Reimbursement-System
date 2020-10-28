package com.reimb.controller;

import javax.servlet.http.*;

import java.io.IOException;

import javax.servlet.*;

public class Dispatcher {
	
	private LoginController loginController;
	private ReimbController reimbController;
	private UserController userController;
	
	public Dispatcher() {
	}
	
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Expected String request.getContextPath() = "/Reimbursement"
		//Expected String request.getServletPath() = "/api"
		//Expected String request.getPathInfo() = "/login" "/reimb/..." "/status" "/type" "/user/..."
		
		String path = request.getPathInfo();
		
		switch (path) {
		case "/login":			//login
			loginController = new LoginController();
			loginController.login(request, response);
			break;
		case "/logout":			//logout
			loginController = new LoginController();
			loginController.logout(request, response);
			break;
		case "/reimb":			//add, delete, view all
			reimbController = new ReimbController();
			switch (request.getMethod()) {
			case "GET":
				reimbController.viewAll(request, response);
				break;
			case "POST":
				reimbController.newReimb(request, response);
				break;
			case "DELETE":
				reimbController.delete(request, response);
				break;
			}
			break;
		case "/reimbupdate":	//update
			reimbController = new ReimbController();
			if (request.getMethod().equals("POST")) reimbController.update(request, response);
			break;
		case "/status":			//view statuses
			reimbController = new ReimbController();
			reimbController.status(request, response);
			break;
		case "/type":			//view types
			reimbController = new ReimbController();
			reimbController.type(request, response);
			break;
		case "/reimbVerify":	//approve/deny reimb
			reimbController = new ReimbController();
			reimbController.verify(request, response);
			break;
		case "/user":			//get current user, update, delete user
			switch (request.getMethod()) {
			case "GET":
				loginController = new LoginController();
				loginController.getUser(request, response);
				break;
			}
			break;
		case "/user/new":		//create new user
			userController = new UserController();
			userController.create(request, response);
			break;
		case "/user/avail":		//check username availability
			userController = new UserController();
			userController.checkUsername(request, response);
			break;
		case "/reimb/author":	//view by author
			reimbController = new ReimbController();
			if (request.getMethod().equals("GET")) reimbController.viewByAuthor(request, response);
			break;
		default: {				//special endpoint
			if (path.split("/").length == 4) {
				reimbController = new ReimbController();
				String specialPath = path.substring(0, path.indexOf("/", 8));
				switch(specialPath) {
				case "/reimb/id":		//view by ID
					reimbController.viewById(request, response);
					break;
				}
			}
			}
		}
	}
}
