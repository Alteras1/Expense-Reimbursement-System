package com.reimb.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import com.reim.repo.UserDao;
import com.reimb.model.User;
import com.reimb.model.UserRole;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest {
	
	private UserService us;
	private UserDao ud;

	@Before
	public void setUp() throws Exception {
		us = new UserService();
		ud = Mockito.mock(UserDao.class);
	}

	@Test
	public void AAAcreateUserSuccessTest() {
		User u = new User(0, "jtest", "jtest", "testname", "lastname", "jtestemail", new UserRole(1, "employee"));
		Mockito.when(ud.findByUsername(u.getUsername())).thenReturn(null);
		Mockito.when(ud.create(u)).thenReturn(1);
		assertTrue(us.createUser(u));
	}
	
	@Test
	public void BBBcreateUserFailTest() {
		User u = new User(0, "jtest", "jtest", "testname", "lastname", "jtestemail", new UserRole(1, "employee"));
		Mockito.when(ud.findByUsername(u.getUsername())).thenReturn(u);
		Mockito.when(ud.create(u)).thenReturn(0);
		assertFalse(us.createUser(u));
	}
	
	@Test
	public void updateUserTest() {
		User u = new User(0, "jtest", "jtest", "testname", "lastname", "jtestemail", new UserRole(1, "employee"));
		User r = new User(1, "jtest", "jtest", "testname", "lastname", "jtestemail", new UserRole(1, "employee"));
		Mockito.when(ud.findByUsername(u.getUsername())).thenReturn(u);
		Mockito.when(ud.create(u)).thenReturn(0);
		assertFalse(us.updateUser(u, r));
	}
	
	@Test
	public void CCCdeleteUserTest() {
		User u = us.verify("jtest", "jtest");
		User r = new User(1, "jtest", "jtest", "testname", "lastname", "jtestemail", new UserRole(2, "manager"));
		assertTrue(us.deleteUser(u, r));
	}
	
	@Test
	public void verifyTest() {
		User u = new User(1, "admin", "9b0aa47997ca0fdd817a574b099b9149", "firstadmin", "lastname", "adminemail", new UserRole(2, "manager"));
		assertEquals(u, us.verify("admin", "admin"));
	}
	
	@Test
	public void checkUsernameTest() {
		assertFalse(us.checkUsername("admin"));
	}

}