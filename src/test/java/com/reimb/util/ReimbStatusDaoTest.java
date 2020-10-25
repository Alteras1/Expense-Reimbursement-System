package com.reimb.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.reim.repo.ReimbStatusDao;
import com.reimb.model.ReimbStatus;

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

}
