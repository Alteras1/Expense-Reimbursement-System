package com.reimb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimb.model.ReimbStatus;
import com.reimb.model.ReimbType;
import com.reimb.service.ReimbService;

public class ReimbController {
	
	private ReimbService reimbService;

	public ReimbController() {
		reimbService = new ReimbService();
	}
	
	public void status(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		List<ReimbStatus> statuses = reimbService.findAllStatus();
		res.setContentType("application/json");
		res.getWriter().write(om.writeValueAsString(statuses));
		res.getWriter().close();
	}
	
	public void type(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		List<ReimbType> types = reimbService.findAllType();
		res.setContentType("application/json");
		res.getWriter().write(om.writeValueAsString(types));
		res.getWriter().close();
	}
}
