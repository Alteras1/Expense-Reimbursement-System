package com.reim.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.reim.util.ConnectionUtil;
import com.reimb.model.User;

public class UserDao implements DaoContract<User, Integer> {

	@Override
	public List<User> findAll() {
		List<User> users = new LinkedList<User>();
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from ers_users";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			UserRoleDao urd = new UserRoleDao();
			while (rs.next()) {
				users.add(new User(rs.getInt("ers_users_id"), rs.getString("ers_username"),
						rs.getString("ers_password"), rs.getString("user_first_name"), rs.getString("user_last_name"),
						rs.getString("user_email"), urd.findById(rs.getInt("user_role_id"))));
				
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public User findById(Integer i) {
		User user = null;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from ers_users where ers_users_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			ResultSet rs = ps.executeQuery();
			UserRoleDao urd = new UserRoleDao();
			if (rs.next()) {
				user = new User(rs.getInt("ers_users_id"), rs.getString("ers_username"),
						rs.getString("ers_password"), rs.getString("user_first_name"), rs.getString("user_last_name"),
						rs.getString("user_email"), urd.findById(rs.getInt("user_role_id")));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public int update(User t) {
		int count = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "update ers_users set ers_username = ?,"
					+ "ers_password = ?,"
					+ "user_first_name = ?,"
					+ "user_last_name = ?,"
					+ "user_email = ?,"
					+ "user_role_id = ?  where ers_users_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getFirstName());
			ps.setString(4, t.getLastName());
			ps.setString(5, t.getEmail());
			ps.setInt(6, t.getRole().getRoleId());
			ps.setInt(7, t.getUserId());
			count = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int create(User t) {
		int count = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "insert into ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id) values\n" + 
					"	(?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getFirstName());
			ps.setString(4, t.getLastName());
			ps.setString(5, t.getEmail());
			ps.setInt(6, t.getRole().getRoleId());
			count = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int delete(Integer i) {
		int count = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "delete from ers_users where ers_users_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			count = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public User findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public User findByUsername(String u) {
		User user = null;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from ers_users where ers_username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u);
			ResultSet rs = ps.executeQuery();
			UserRoleDao urd = new UserRoleDao();
			if (rs.next()) {
				user = new User(rs.getInt("ers_users_id"), rs.getString("ers_username"),
						rs.getString("ers_password"), rs.getString("user_first_name"), rs.getString("user_last_name"),
						rs.getString("user_email"), urd.findById(rs.getInt("user_role_id")));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public User findByUsernamePassword(String u, String p) {
		User user = null;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from validateUser(?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u);
			ps.setString(2, p);
			ResultSet rs = ps.executeQuery();
			UserRoleDao urd = new UserRoleDao();
			if (rs.next()) {
				user = new User(rs.getInt("ers_users_id"), rs.getString("ers_username"),
						rs.getString("ers_password"), rs.getString("user_first_name"), rs.getString("user_last_name"),
						rs.getString("user_email"), urd.findById(rs.getInt("user_role_id")));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public boolean findUsernameAvailability(String u) {
		boolean avail = false;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from usernameAvailability(?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u);
			ResultSet rs = ps.executeQuery();
			UserRoleDao urd = new UserRoleDao();
			if (rs.next()) {
				avail = rs.getBoolean(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return avail;
	}

}
