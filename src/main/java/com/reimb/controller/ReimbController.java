package com.reimb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimb.model.Reimb;
import com.reimb.model.ReimbStatus;
import com.reimb.model.ReimbType;
import com.reimb.model.User;
import com.reimb.service.ReimbService;

public class ReimbController {
	
	private ReimbService reimbService;
	private ObjectMapper om = new ObjectMapper();

	public ReimbController() {
		reimbService = new ReimbService();
	}
	
	public ReimbController(ReimbService rs) {
		reimbService = rs;
	}

	public void status(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<ReimbStatus> statuses = reimbService.findAllStatus();
		res.setContentType("application/json");
		res.getWriter().write(om.writeValueAsString(statuses));
		res.getWriter().close();
	}
	
	public void type(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<ReimbType> types = reimbService.findAllType();
		res.setContentType("application/json");
		res.getWriter().write(om.writeValueAsString(types));
		res.getWriter().close();
	}
	
	public void viewAll(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<Reimb> statuses = reimbService.findAll();
		res.setContentType("application/json");
		res.getWriter().write(om.writeValueAsString(statuses));
		res.getWriter().close();
	}
	
	public void newReimb(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if (req.getSession(false) != null) {
			Reimb newReimb = om.readValue(req.getReader(), Reimb.class);
			Boolean success = reimbService.create(newReimb);
			res.setContentType("application/json");
			res.getWriter().write(om.writeValueAsString(success));
			res.getWriter().close();
		} else {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
	
	public void update(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession ses = req.getSession(false);
		if (ses != null) {
			User currentUser = (User) ses.getAttribute("User");
			Reimb newReimb = om.readValue(req.getReader(), Reimb.class);
			Boolean success = reimbService.update(newReimb, currentUser);
			res.setContentType("application/json");
			res.getWriter().write(om.writeValueAsString(success));
			res.getWriter().close();
		} else {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
	
	public void delete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession ses = req.getSession(false);
		if (ses != null) {
			User currentUser = (User) ses.getAttribute("User");
			Reimb newReimb = om.readValue(req.getReader(), Reimb.class);
			Boolean success = reimbService.delete(newReimb, currentUser);
			res.setContentType("application/json");
			res.getWriter().write(om.writeValueAsString(success));
			res.getWriter().close();
		} else {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
	
	public void verify(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession ses = req.getSession(false);
		if (ses != null) {
			User currentUser = (User) ses.getAttribute("User");
			String reimb = req.getParameter("reimb");
			String reimbStatus = req.getParameter("status");
			Reimb newReimb = om.readValue(reimb, Reimb.class);
			ReimbStatus newReimbStatus = om.readValue(reimbStatus, ReimbStatus.class);
			Boolean success = reimbService.changeStatus(newReimb, newReimbStatus, currentUser);
			res.setContentType("application/json");
			res.getWriter().write(om.writeValueAsString(success));
			res.getWriter().close();
		} else {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
	
	public void viewById(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession ses = req.getSession(false);
		int id = 0;
		try {
			id = Integer.parseInt(req.getPathInfo().split("/")[3]);
		} catch (NumberFormatException e) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		if (ses != null && id != 0) {
			Reimb foundReimb = reimbService.findById(id);
			res.setContentType("application/json");
			res.getWriter().write(om.writeValueAsString(foundReimb));
			res.getWriter().close();
		} else {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
	
	public void viewByAuthor(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession ses = req.getSession(false);
		String author = req.getParameter("author");
		if (ses != null && !author.isEmpty()) {
			User requester = (User) ses.getAttribute("User");
			List<Reimb> foundReimbs = reimbService.findByAuthor(om.readValue(author, User.class), requester);
			res.setContentType("application/json");
			res.getWriter().write(om.writeValueAsString(foundReimbs));
			res.getWriter().close();
		} else {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
	
}
