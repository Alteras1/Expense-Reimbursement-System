package com.reimb.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

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
import com.reimb.model.User;
import com.reimb.model.UserRole;
import com.reimb.service.UserService;

public class UserControllerTest {

	private UserService us;
	private HttpServletRequest req;
	private HttpServletResponse res;
	private HttpSession ses;
	
	@Before
	public void setUp() throws Exception {
		us = Mockito.mock(UserService.class);
		req = mock(HttpServletRequest.class);
		res = mock(HttpServletResponse.class);
		ses = mock(HttpSession.class);
	}

	@Test
	public void createTest() {
		UserController uc = new UserController(us);
		when(req.getMethod()).thenReturn("POST");
		when(req.getSession(false)).thenReturn(ses);
		when(ses.getAttribute("UserRole")).thenReturn(new UserRole(2,"manager"));
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"manager"));
		ObjectMapper om = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		String userStr = "";
		User newUser = null;
		try {
			userStr = om.writeValueAsString(manager);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			when(req.getReader()).thenReturn(new BufferedReader(CharSource.wrap(userStr).openStream()));
			newUser = om.readValue(new BufferedReader(CharSource.wrap(userStr).openStream()), User.class);
			when(res.getWriter()).thenReturn(writer);
			when(us.createUser(newUser)).thenReturn(true);
			uc.create(req, res);
			
		}  catch (IOException e) {

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
		UserController uc = new UserController(us);
		when(req.getMethod()).thenReturn("POST");
		when(req.getSession(false)).thenReturn(ses);
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"manager"));
		when(ses.getAttribute("User")).thenReturn(manager);
		ObjectMapper om = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		String userStr = "";
		User newUser = null;
		try {
			userStr = om.writeValueAsString(manager);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			when(req.getReader()).thenReturn(new BufferedReader(CharSource.wrap(userStr).openStream()));
			newUser = om.readValue(new BufferedReader(CharSource.wrap(userStr).openStream()), User.class);
			when(res.getWriter()).thenReturn(writer);
			when(us.updateUser(newUser, manager)).thenReturn(true);
			uc.update(req, res);
			
		}  catch (IOException e) {

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
		UserController uc = new UserController(us);
		when(req.getMethod()).thenReturn("DELETE");
		when(req.getSession(false)).thenReturn(ses);
		User manager = new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"manager"));
		when(ses.getAttribute("User")).thenReturn(manager);
		ObjectMapper om = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		String userStr = "";
		User newUser = null;
		try {
			userStr = om.writeValueAsString(manager);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			when(req.getReader()).thenReturn(new BufferedReader(CharSource.wrap(userStr).openStream()));
			newUser = om.readValue(new BufferedReader(CharSource.wrap(userStr).openStream()), User.class);
			when(res.getWriter()).thenReturn(writer);
			when(us.deleteUser(newUser, manager)).thenReturn(true);
			uc.delete(req, res);
			
		}  catch (IOException e) {

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
	public void checkUsernameTest() {
		UserController uc = new UserController(us);
		when(req.getParameter("username")).thenReturn("username");
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		try {
			when(res.getWriter()).thenReturn(writer);
			when(us.checkUsername("username")).thenReturn(true);
			uc.checkUsername(req, res);
			
		}  catch (IOException e) {

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
}
