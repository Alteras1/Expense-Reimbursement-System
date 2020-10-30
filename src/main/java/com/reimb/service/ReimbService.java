package com.reimb.service;

import java.util.List;

import com.reimb.model.Reimb;
import com.reimb.model.ReimbStatus;
import com.reimb.model.ReimbType;
import com.reimb.model.User;
import com.reimb.model.UserRole;
import com.reimb.repo.ReimbDao;
import com.reimb.repo.ReimbStatusDao;
import com.reimb.repo.ReimbTypeDao;
import com.reimb.repo.UserRoleDao;

public class ReimbService {

	private ReimbDao rd;
	private ReimbStatusDao rsd;
	private ReimbTypeDao rtd;
	private UserRoleDao urd;
	private UserRole manager;
	
	public ReimbService(ReimbDao rd, ReimbStatusDao rsd, ReimbTypeDao rtd, UserRoleDao urd) {
		super();
		this.rd = rd;
		this.rsd = rsd;
		this.rtd = rtd;
		this.urd = urd;
		manager = this.urd.findByName("Manager");
	}
	
	public ReimbService() {
		this(new ReimbDao(), new ReimbStatusDao(), new ReimbTypeDao(), new UserRoleDao());
	}
	
	public List<Reimb> findAll() {
		return rd.findAll();
	}
	
	public Reimb findById(int i) {
		return rd.findById(i);
	}
	
	public boolean update(Reimb r, User u) {
		if (r.getAuthor().getUserId() == u.getUserId() || u.getRole().equals(manager)) {
			return (rd.update(r) != 0) ? true : false;
		}
		return false;
	}
	
	public boolean changeStatus(Reimb r, ReimbStatus rs, User u) {
		if (u.getRole().equals(manager) && !(r.getStatus().equals(rs))) {
			return (rd.update(r.getReimbId(), rs, u) != 0) ? true : false;
		}
		return false;
	}
	
	public boolean create(Reimb r) {
		return (rd.create(r) != 0) ? true : false;
	}
	
	public List<Reimb> findByAuthor(User a, User requester) {
		if (a.getUserId() == requester.getUserId() || requester.getRole().equals(manager)) {
			return rd.findByAuthor(a);
		}
		return null;
	}
	
	public List<ReimbStatus> findAllStatus() {
		return rsd.findAll();
	}
	
	public List<ReimbType> findAllType() {
		return rtd.findAll();
	}
	
	public boolean delete(Reimb r, User u) {
		if (r.getAuthor().getUserId() == u.getUserId() || u.getRole().equals(manager)) {
			return (rd.delete(r.getReimbId()) != 0) ? true : false;
		}
		return false;
	}
}
