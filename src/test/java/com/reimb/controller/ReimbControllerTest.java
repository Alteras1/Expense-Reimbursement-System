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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharSource;
import com.reimb.model.Reimb;
import com.reimb.model.ReimbStatus;
import com.reimb.model.ReimbType;
import com.reimb.model.User;
import com.reimb.model.UserRole;
import com.reimb.service.ReimbService;
import com.reimb.service.UserService;

public class ReimbControllerTest {

	private ReimbService rs;
	private UserService us;
	private HttpServletRequest req;
	private HttpServletResponse res;
	private HttpSession ses;
	private ObjectMapper omm;
	
	@Before
	public void setUp() throws Exception {
		rs = Mockito.mock(ReimbService.class);
		us = Mockito.mock(UserService.class);
		req = mock(HttpServletRequest.class);
		res = mock(HttpServletResponse.class);
		ses = mock(HttpSession.class);
		omm = mock(ObjectMapper.class);
	}

	@Test
	public void getUserTest() {
		ReimbController rc = new ReimbController(rs, us);
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"Manager"));
		when(req.getSession(false)).thenReturn(ses);
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		ObjectMapper om = new ObjectMapper();
		String reimb = "";
		try {
			reimb = om.writeValueAsString(new Reimb(20.0, "desc", manager, new ReimbStatus(1, "Pending"), new ReimbType(1, "lodging")));
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
		ReimbController rc = new ReimbController(rs, us);
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"Manager"));
		when(req.getSession(false)).thenReturn(ses);
		when(ses.getAttribute("User")).thenReturn(manager);
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		ObjectMapper om = new ObjectMapper();
		String reimb = "";
		Reimb reimbObj = new Reimb(20.0, "desc", manager, new ReimbStatus(1, "Pending"), new ReimbType(1, "lodging"));
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
		ReimbController rc = new ReimbController(rs, us);
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"Manager"));
		when(req.getSession(false)).thenReturn(ses);
		when(ses.getAttribute("User")).thenReturn(manager);
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		ObjectMapper om = new ObjectMapper();
		String reimb = "";
		Reimb reimbObj = new Reimb(20.0, "desc", manager, new ReimbStatus(1, "Pending"), new ReimbType(1, "lodging"));
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
		ReimbController rc = new ReimbController(rs, us);
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"Manager"));
		when(req.getSession(false)).thenReturn(ses);
		when(ses.getAttribute("User")).thenReturn(manager);
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		ObjectMapper om = new ObjectMapper();
		String json = "{\"reimb\":{\"reimbId\":1,\"amount\":20,\"submitted\":\"2020-10-29T07:00:00.000Z\",\"resolved\":null,\"description\":null,\"author\":{\"userId\":2,\"username\":\"test\",\"password\":\"0e7d43b3253e8f5ddceb9a07d1edfa52\",\"firstName\":\"firstname\",\"lastName\":\"lastname\",\"email\":\"email@email\",\"role\":{\"roleId\":1,\"role\":\"Employee\"}},\"resolver\":null,\"status\":{\"statusId\":1,\"status\":\"Pending\"},\"type\":{\"typeId\":1,\"type\":\"Lodging\"}},\"status\":{\"statusId\":\"2\",\"status\":\"Pending\"}}";
		JsonNode jsonNode = null;
		try {
			jsonNode = om.readTree(json);
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Reimb newReimb = om.convertValue(jsonNode.get("reimb"), Reimb.class);
		ReimbStatus newStatus = om.convertValue(jsonNode.get("status"), ReimbStatus.class);
		try {
			when(omm.readTree(req.getInputStream())).thenReturn(jsonNode);
			when(res.getWriter()).thenReturn(writer);
			when(rs.changeStatus(newReimb, newStatus, manager)).thenReturn(true);
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
		ReimbController rc = new ReimbController(rs, us);
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"Manager"));
		when(req.getSession(false)).thenReturn(ses);
		when(req.getPathInfo()).thenReturn("/reimb/id/1");
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		ObjectMapper om = new ObjectMapper();
		String reimb = "";
		Reimb reimbObj = new Reimb(20.0, "desc", manager, new ReimbStatus(1, "Pending"), new ReimbType(1, "lodging"));
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
		ReimbController rc = new ReimbController(rs, us);
		ObjectMapper om = new ObjectMapper();
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"Manager"));
		when(req.getSession(false)).thenReturn(ses);
		when(ses.getAttribute("User")).thenReturn(manager);
		when(req.getPathInfo()).thenReturn("/reimb/author/1");
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		when(us.findById(1)).thenReturn(manager);
		String reimbList = "";
		List<Reimb> reimbs = new LinkedList<Reimb>();
		reimbs.add(new Reimb(1,20.0, "desc", manager, new ReimbStatus(1, "Pending"), new ReimbType(1, "lodging")));
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
