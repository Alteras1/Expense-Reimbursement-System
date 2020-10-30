package com.reimb.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.reimb.model.ReimbType;
import com.reimb.repo.ReimbTypeDao;

public class ReimbTypeDaoTest {

	private ReimbTypeDao rtd;

	@Before
	public void setUp() throws Exception {
		rtd = new ReimbTypeDao();
	}

	@Test
	public void findAllTest() {
		List<ReimbType> statuses = rtd.findAll();
		assertFalse(statuses.size() == 0);
	}

	@Test
	public void findByIdTest() {
		ReimbType s = rtd.findById(1);
		assertNotNull(s);
	}
	
	@Test
	public void updateTest() {
		ReimbType s = new ReimbType(1, "Lodging");
		assertNotEquals(0, rtd.update(s));
	}
	
	@Test
	public void findByNameTest() {
		ReimbType s = rtd.findByName("Lodging");
		assertEquals(new ReimbType(1, "Lodging"), s);
	}

}
