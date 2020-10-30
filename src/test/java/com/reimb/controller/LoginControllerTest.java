package com.reimb.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;

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

import com.reimb.model.User;
import com.reimb.model.UserRole;
import com.reimb.service.UserService;

public class LoginControllerTest {

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
	public void loginTest() {
		LoginController ls = new LoginController(us);
		when(req.getMethod()).thenReturn("POST");
		when(req.getParameter("username")).thenReturn("admin");
		when(req.getParameter("password")).thenReturn("admin");
		when(req.getSession(true)).thenReturn(ses);
		when(us.verify("admin", "admin")).thenReturn(new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"Manager")));
		try {
			ls.login(req, res);
			Mockito.verify(res).sendRedirect(anyString());
		} catch (ServletException e) {
			fail();
			e.printStackTrace();
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void logoutTest() {
		LoginController ls = new LoginController(us);
		ses.setAttribute("User", new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"Manager")));
		when(req.getSession(false)).thenReturn(ses);
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		try {
			when(res.getWriter()).thenReturn(writer);
			ls.logout(req, res);
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.flush();
		assertEquals("true", stringWriter.toString());
		stringWriter.flush();
	}
	
	@Test
	public void getUserTest() {
		LoginController ls = new LoginController(us);
		ses.setAttribute("User", new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"Manager")));
		when(req.getSession(false)).thenReturn(ses);
		when(ses.getAttribute("User")).thenReturn(new User(1, "admin", "admin", "first", "last", "email", new UserRole(2,"Manager")));
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		try {
			when(res.getWriter()).thenReturn(writer);
			ls.getUser(req, res);
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.flush();
		assertEquals("{\"userId\":1,\"username\":\"admin\",\"password\":\"admin\",\"firstName\":\"first\",\"lastName\":\"last\",\"email\":\"email\",\"role\":{\"roleId\":2,\"role\":\"Manager\"}}", stringWriter.toString());
		stringWriter.flush();
	}

}
