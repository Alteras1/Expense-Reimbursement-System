package com.reimb.util;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.reim.repo.ReimbDao;
import com.reimb.model.Reimb;
import com.reimb.model.ReimbStatus;
import com.reimb.model.ReimbType;
import com.reimb.model.User;
import com.reimb.model.UserRole;

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
		String s = "Reimb [reimbId=1, amount=20.0, submitted=2020-10-25, resolved=null, description=null, author=User [userId=2, username=test, password=0e7d43b3253e8f5ddceb9a07d1edfa52, firstName=firstname, lastName=lastname, email=email, role=UserRole [roleId=1, role=employee]], resolver=null, status=ReimbStatus [statusId=1, status=pending], type=ReimbType [typeId=1, type=lodging]]";
		assertEquals(s, rd.findById(1).toString());
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
		System.out.println(reimb.get(reimb.size() - 1).getReimbId());
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
