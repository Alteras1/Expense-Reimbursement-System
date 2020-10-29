package com.reimb.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.reimb.model.ReimbStatus;
import com.reimb.repo.ReimbStatusDao;

public class ReimbStatusDaoTest {
	
	private ReimbStatusDao rsd;

	@Before
	public void setUp() throws Exception {
		rsd = new ReimbStatusDao();
	}

	@Test
	public void findAllTest() {
		List<ReimbStatus> statuses = rsd.findAll();
		assertFalse(statuses.size() == 0);
	}

	@Test
	public void findByIdTest() {
		ReimbStatus s = rsd.findById(1);
		assertNotNull(s);
	}
	
	@Test
	public void updateTest() {
		ReimbStatus s = new ReimbStatus(1, "pending");
		assertNotEquals(0, rsd.update(s));
	}
	
	@Test
	public void findByNameTest() {
		ReimbStatus s = rsd.findByName("pending");
		assertEquals(new ReimbStatus(1, "pending"), s);
	}
	
}
