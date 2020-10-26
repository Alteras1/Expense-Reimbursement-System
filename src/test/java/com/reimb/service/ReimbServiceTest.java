package com.reimb.service;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.reim.repo.ReimbDao;
import com.reim.repo.ReimbStatusDao;
import com.reim.repo.ReimbTypeDao;
import com.reim.repo.UserRoleDao;
import com.reimb.model.Reimb;
import com.reimb.model.ReimbStatus;
import com.reimb.model.ReimbType;
import com.reimb.model.User;
import com.reimb.model.UserRole;

public class ReimbServiceTest {

	private ReimbService rs;
	private ReimbStatusDao rsd;
	private ReimbTypeDao rtd;
	private ReimbDao rd;
	private UserRoleDao urd;
	
	@Before
	public void setUp() throws Exception {
		rsd = Mockito.mock(ReimbStatusDao.class);
		rtd = Mockito.mock(ReimbTypeDao.class);
		rd = Mockito.mock(ReimbDao.class);
		urd = Mockito.mock(UserRoleDao.class);
		Mockito.when(urd.findByName("manager")).thenReturn(new UserRole(2, "manager"));
		rs = new ReimbService(rd, rsd, rtd, urd);
	}

	@Test
	public void createTest() {
		User u = new User(0, "jtest", "jtest", "testname", "lastname", "jtestemail", new UserRole(1, "employee"));
		Reimb t = new Reimb(20.0, "jtest", u, new ReimbStatus(1, "pending"), new ReimbType(1, "lodging"));
		Mockito.when(rd.create(t)).thenReturn(1);
		assertTrue(rs.create(t));
	}
	
	@Test
	public void updateTest() {
		User u = new User(0, "jtest", "jtest", "testname", "lastname", "jtestemail", new UserRole(1, "employee"));
		Reimb t = new Reimb(20.0, "jtest", u, new ReimbStatus(1, "pending"), new ReimbType(1, "lodging"));
		t.setReimbId(1);
		Mockito.when(rd.update(t)).thenReturn(1);
		assertTrue(rs.update(t, u));
	}
	
	@Test
	public void findByAuthorTest() {
		User u = new User(1, "jtest", "jtest", "testname", "lastname", "jtestemail", new UserRole(1, "employee"));
		User ru = new User(2, "jtest", "jtest", "testname", "lastname", "jtestemail", new UserRole(2, "manager"));
		List<Reimb> list = new LinkedList<Reimb>();
		Reimb t = new Reimb(20.0, "jtest", u, new ReimbStatus(1, "pending"), new ReimbType(1, "lodging"));
		list.add(t);
		Mockito.when(rd.findByAuthor(u)).thenReturn(list);
		assertEquals(list, rs.findByAuthor(u, ru));
	}

	@Test
	public void changeStatusTest() {
		User ru = new User(2, "jtest", "jtest", "testname", "lastname", "jtestemail", new UserRole(2, "manager"));
		User u = new User(1, "jtest", "jtest", "testname", "lastname", "jtestemail", new UserRole(1, "employee"));
		Reimb t = new Reimb(20.0, "jtest", u, new ReimbStatus(1, "pending"), new ReimbType(1, "lodging"));
		t.setReimbId(1);
		ReimbStatus reimbStatus = new ReimbStatus(2, "approved");
		Mockito.when(rd.update(1, reimbStatus, ru)).thenReturn(1);
		assertTrue(rs.changeStatus(t, reimbStatus, ru));
	}
	
	@Test
	public void deleteTest() {
		User u = new User(1, "jtest", "jtest", "testname", "lastname", "jtestemail", new UserRole(1, "employee"));
		Reimb t = new Reimb(20.0, "jtest", u, new ReimbStatus(1, "pending"), new ReimbType(1, "lodging"));
		t.setReimbId(1);
		Mockito.when(rd.delete(1)).thenReturn(1);
		assertTrue(rs.delete(t, u));
	}
}
