package com.reimb.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.anyInt;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import com.reimb.model.User;
import com.reimb.model.UserRole;
import com.reimb.repo.UserDao;
import com.reimb.repo.UserRoleDao;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTest {

	private UserRoleDao dao;
	private UserDao ud;

	@Before
	public void setUp() throws Exception {
		dao = Mockito.mock(UserRoleDao.class);
		ud = new UserDao();
	}

	@Test
	public void findAllTest() {
		Mockito.when(dao.findById(anyInt())).thenReturn(new UserRole(1, "Employee"));
		List<User> users = ud.findAll();
		assertFalse(users.size() == 0);
	}
	
	@Test
	public void findByIdTest() {
		Mockito.when(dao.findById(anyInt())).thenReturn(new UserRole(2, "Manager"));
		User user = ud.findById(1);
		assertEquals(new User(1, "admin", "9b0aa47997ca0fdd817a574b099b9149", "firstadmin", "lastname", "adminemail@email", new UserRole(2, "Manager")), user);
	}

	@Test
	public void updateTest() {
		User u = new User(1, "admin", "admin", "firstadmin", "lastname", "adminemail", new UserRole(2, "Manager"));
		assertNotEquals(0, ud.update(u));
	}
	
	@Test
	public void AAAcreateTest() {
		User u = new User(0, "jtest", "jtest", "testname", "lastname", "testemail", new UserRole(1, "Employee"));
		assertNotEquals(0, ud.create(u));
	}
	
	@Test
	public void BBBdeleteTest() {
		List<User> users = ud.findAll();
		assertNotEquals(0, ud.delete(users.get(users.size() - 1).getUserId()));
	}
	
	@Test
	public void findByUsernameTest() {
		Mockito.when(dao.findById(anyInt())).thenReturn(new UserRole(2, "Manager"));
		User u = new User(1, "admin", "9b0aa47997ca0fdd817a574b099b9149", "firstadmin", "lastname", "adminemail@email", new UserRole(2, "Manager"));
		assertEquals(u, ud.findByUsername("admin"));
	}
	
	@Test
	public void findByUsernamePasswordTest() {
		Mockito.when(dao.findById(anyInt())).thenReturn(new UserRole(2, "Manager"));
		User u = new User(1, "admin", "9b0aa47997ca0fdd817a574b099b9149", "firstadmin", "lastname", "adminemail@email", new UserRole(2, "Manager"));
		assertEquals(u, ud.findByUsernamePassword("admin", "admin"));
	}
	
	@Test
	public void findUsernameAvailabilityTest() {
		assertFalse(ud.findUsernameAvailability("admin"));
	}
}
