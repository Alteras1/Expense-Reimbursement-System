package com.reimb.util;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.reimb.model.Reimb;
import com.reimb.model.ReimbStatus;
import com.reimb.model.ReimbType;
import com.reimb.model.User;
import com.reimb.model.UserRole;
import com.reimb.repo.ReimbDao;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReimbDaoTest {

	private ReimbDao rd;

	@Before
	public void setUp() throws Exception {
		rd = new ReimbDao();
	}

	@Test
	public void findAllTest() {
		List<Reimb> reimb = rd.findAll();
		assertFalse(reimb.size() == 0);
	}
	
	@Test
	public void findById() {
		String s = "Reimb";
		assertTrue(rd.findById(1).toString().contains(s));
	}
	
	@Test
	public void AAAcreateTest() {
		Reimb r = new Reimb(20.0, "desc", new User(1, "admin", "admin", "firstadmin", "lastname", "adminemail", new UserRole(2, "manager")), new ReimbStatus(1, "pending"), new ReimbType(1, "lodging"));
		assertNotEquals(0, rd.create(r));
	}
	
	@Test
	public void BBBupdateTest() {
		Reimb r = new Reimb(20.0, "desc", new User(1, "admin", "admin", "firstadmin", "lastname", "adminemail", new UserRole(2, "manager")), new ReimbStatus(1, "pending"), new ReimbType(1, "lodging"));
		r.setReimbId(2);
		assertNotEquals(0, rd.update(r));
	}

	@Test
	public void CCCdeleteTest() {
		List<Reimb> reimb = rd.findAll();
		assertNotEquals(0, rd.delete(reimb.get(reimb.size() - 1).getReimbId()));
	}
	
	@Test
	public void simpleUpdateTest() {
		assertNotEquals(0, rd.update(1, new ReimbStatus(3, "denied"), new User(1, "admin", "admin", "firstadmin", "lastname", "adminemail", new UserRole(2, "manager"))));
	}
	
	@Test
	public void findByAuthorTest() {
		List<Reimb> reimbs = rd.findByAuthor(new User(2, "test", "test", "firstname", "lastname", "email", new UserRole(1, "employee")));
		assertFalse(reimbs.size() == 0);
	}
}
