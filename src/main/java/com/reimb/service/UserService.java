package com.reimb.service;

import java.util.List;

import com.reimb.model.User;
import com.reimb.model.UserRole;
import com.reimb.repo.UserDao;
import com.reimb.repo.UserRoleDao;

public class UserService {
	
	private UserDao ud;
	private UserRoleDao urd;
	
	public UserService() {
		this(new UserDao(), new UserRoleDao());
	}

	public UserService(UserDao ud, UserRoleDao urd) {
		super();
		this.ud = ud;
		this.urd = urd;
	}
	
	public boolean createUser(User u) {
		if (ud.findByUsername(u.getUsername()) == null) {
			return (ud.create(u) != 0) ? true : false;
		} else {
			return false;
		}
	}
	
	public boolean updateUser(User u, User requester) {
		UserRole manager = urd.findByName("manager");
		if (u.getUserId() == requester.getUserId() || requester.getRole().equals(manager)) {
			return (ud.update(u) != 0) ? true : false;
		}
		return false;
	}
	
	public List<User> findAllUsers() {
		return ud.findAll();
	}
	
	public User verify(String u, String p) {
		return ud.findByUsernamePassword(u, p);
	}
	
	public boolean checkUsername(String u) {
		return ud.findUsernameAvailability(u);
	}
	
	public boolean deleteUser(User u, User requester) {
		UserRole manager = urd.findByName("manager");
		if (requester.getRole().equals(manager)) {
			return (ud.delete(u.getUserId()) != 0) ? true : false;
		}
		return false;
	}
	
	public User findById(int i) {
		return ud.findById(i);
	}
	
}
