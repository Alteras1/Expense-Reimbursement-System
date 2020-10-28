package com.reimb.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharSource;
import com.reimb.model.Reimb;
import com.reimb.model.ReimbStatus;
import com.reimb.model.ReimbType;
import com.reimb.model.User;
import com.reimb.model.UserRole;
import com.reimb.service.ReimbService;

public class ReimbControllerTest {

	private ReimbService rs;
	HttpServletRequest req;
	HttpServletResponse res;
	HttpSession ses;
	
	@Before
	public void setUp() throws Exception {
		rs = Mockito.mock(ReimbService.class);
		req = mock(HttpServletRequest.class);
		res = mock(HttpServletResponse.class);
		ses = mock(HttpSession.class);
	}

	@Test
	public void getUserTest() {
		ReimbController rc = new ReimbController(rs);
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"manager"));
		when(req.getSession(false)).thenReturn(ses);
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		ObjectMapper om = new ObjectMapper();
		String reimb = "";
		try {
			reimb = om.writeValueAsString(new Reimb(20.0, "desc", manager, new ReimbStatus(1, "pending"), new ReimbType(1, "lodging")));
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			when(req.getReader()).thenReturn(new BufferedReader(CharSource.wrap(reimb).openStream()));
			when(res.getWriter()).thenReturn(writer);
			when(rs.create(any())).thenReturn(true);
			rc.newReimb(req, res);
		} catch (IOException e) {

			e.printStackTrace();
			fail();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.flush();
		assertEquals("true", stringWriter.toString());
		stringWriter.flush();
	}
	
	@Test
	public void updateTest() {
		ReimbController rc = new ReimbController(rs);
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"manager"));
		when(req.getSession(false)).thenReturn(ses);
		when(ses.getAttribute("User")).thenReturn(manager);
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		ObjectMapper om = new ObjectMapper();
		String reimb = "";
		Reimb reimbObj = new Reimb(20.0, "desc", manager, new ReimbStatus(1, "pending"), new ReimbType(1, "lodging"));
		reimbObj.setReimbId(1);
		try {
			reimb = om.writeValueAsString(reimbObj);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			when(req.getReader()).thenReturn(new BufferedReader(CharSource.wrap(reimb).openStream()));
			Reimb reimbObjNew = om.readValue(new BufferedReader(CharSource.wrap(reimb).openStream()), Reimb.class);
			when(res.getWriter()).thenReturn(writer);
			when(rs.update(reimbObjNew, manager)).thenReturn(true);
			rc.update(req, res);
		} catch (IOException e) {

			e.printStackTrace();
			fail();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.flush();
		assertEquals("true", stringWriter.toString());
		stringWriter.flush();
	}
	
	@Test
	public void deleteTest() {
		ReimbController rc = new ReimbController(rs);
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"manager"));
		when(req.getSession(false)).thenReturn(ses);
		when(ses.getAttribute("User")).thenReturn(manager);
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		ObjectMapper om = new ObjectMapper();
		String reimb = "";
		Reimb reimbObj = new Reimb(20.0, "desc", manager, new ReimbStatus(1, "pending"), new ReimbType(1, "lodging"));
		reimbObj.setReimbId(1);
		try {
			reimb = om.writeValueAsString(reimbObj);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			when(req.getReader()).thenReturn(new BufferedReader(CharSource.wrap(reimb).openStream()));
			Reimb reimbObjNew = om.readValue(new BufferedReader(CharSource.wrap(reimb).openStream()), Reimb.class);
			when(res.getWriter()).thenReturn(writer);
			when(rs.delete(reimbObjNew, manager)).thenReturn(true);
			rc.delete(req, res);
		} catch (IOException e) {

			e.printStackTrace();
			fail();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.flush();
		assertEquals("true", stringWriter.toString());
		stringWriter.flush();
	}
	
	@Test
	public void verifyTest() {
		ReimbController rc = new ReimbController(rs);
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"manager"));
		when(req.getSession(false)).thenReturn(ses);
		when(ses.getAttribute("User")).thenReturn(manager);
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		ObjectMapper om = new ObjectMapper();
		String reimb = "";
		String reimbStatus = "";
		Reimb reimbObj = new Reimb(20.0, "desc", manager, new ReimbStatus(1, "pending"), new ReimbType(1, "lodging"));
		ReimbStatus reimbStatusObj = new ReimbStatus(2, "approved");
		reimbObj.setReimbId(1);
		try {
			reimb = om.writeValueAsString(reimbObj);
			reimbStatus = om.writeValueAsString(reimbStatusObj);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			when(req.getParameter("reimb")).thenReturn(reimb);
			when(req.getParameter("status")).thenReturn(reimbStatus);
			Reimb reimbObjNew = om.readValue(new BufferedReader(CharSource.wrap(reimb).openStream()), Reimb.class);
			ReimbStatus reimbStatusObjNew = om.readValue(new BufferedReader(CharSource.wrap(reimbStatus).openStream()), ReimbStatus.class);
			when(res.getWriter()).thenReturn(writer);
			when(rs.changeStatus(reimbObjNew, reimbStatusObjNew, manager)).thenReturn(true);
			rc.verify(req, res);
		} catch (IOException e) {

			e.printStackTrace();
			fail();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.flush();
		assertEquals("true", stringWriter.toString());
		stringWriter.flush();
	}
	
	@Test
	public void viewByIdTest() {
		ReimbController rc = new ReimbController(rs);
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"manager"));
		when(req.getSession(false)).thenReturn(ses);
		when(req.getPathInfo()).thenReturn("/reimb/id/1");
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		ObjectMapper om = new ObjectMapper();
		String reimb = "";
		Reimb reimbObj = new Reimb(20.0, "desc", manager, new ReimbStatus(1, "pending"), new ReimbType(1, "lodging"));
		reimbObj.setReimbId(1);
		try {
			reimb = om.writeValueAsString(reimbObj);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			when(res.getWriter()).thenReturn(writer);
			when(rs.findById(1)).thenReturn(reimbObj);
			rc.viewById(req, res);
		} catch (IOException e) {

			e.printStackTrace();
			fail();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.flush();
		assertEquals(reimb, stringWriter.toString());
		stringWriter.flush();
	}
	
	@Test
	public void viewByAuthorTest() {
		ReimbController rc = new ReimbController(rs);
		ObjectMapper om = new ObjectMapper();
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"manager"));
		when(req.getSession(false)).thenReturn(ses);
		when(ses.getAttribute("User")).thenReturn(manager);
		try {
			when(req.getParameter("author")).thenReturn(om.writeValueAsString(manager));
		} catch (JsonProcessingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		
		String reimbList = "";
		List<Reimb> reimbs = new LinkedList<Reimb>();
		reimbs.add(new Reimb(1,20.0, "desc", manager, new ReimbStatus(1, "pending"), new ReimbType(1, "lodging")));
		try {
			reimbList = om.writeValueAsString(reimbs);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			when(res.getWriter()).thenReturn(writer);
			when(rs.findByAuthor(manager, manager)).thenReturn(reimbs);
			rc.viewByAuthor(req, res);
		} catch (IOException e) {

			e.printStackTrace();
			fail();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.flush();
		assertEquals(reimbList, stringWriter.toString());
		stringWriter.flush();
	}
}
