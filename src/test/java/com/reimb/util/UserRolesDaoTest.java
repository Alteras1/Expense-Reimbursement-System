package com.reimb.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.reimb.model.UserRole;
import com.reimb.repo.UserRoleDao;

public class UserRolesDaoTest {

	private UserRoleDao urd;

	@Before
	public void setUp() throws Exception {
		urd = new UserRoleDao();
	}

	@Test
	public void findAllTest() {
		List<UserRole> roles = urd.findAll();
		assertFalse(roles.size() == 0);
	}

	@Test
	public void findByIdTest() {
		UserRole s = urd.findById(1);
		assertNotNull(s);
	}
	
	@Test
	public void updateTest() {
		UserRole s = new UserRole(1, "Employee");
		assertNotEquals(0, urd.update(s));
	}
	
	@Test
	public void findByNameTest() {
		UserRole s = urd.findByName("Employee");
		assertEquals(new UserRole(1, "Employee"), s);
	}

}
