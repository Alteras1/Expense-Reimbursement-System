package com.reim.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.reim.util.ConnectionUtil;
import com.reimb.model.UserRole;

public class UserRoleDao implements DaoContract<UserRole, Integer> {

	@Override
	public List<UserRole> findAll() {
		List<UserRole> roles = new LinkedList<>();
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			Statement s = conn.createStatement();
			String sql = "select * from ers_user_roles";
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				roles.add(new UserRole(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roles;
	}

	@Override
	public UserRole findById(Integer i) {
		UserRole role = null;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from ers_user_roles where ers_user_role_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				role = new UserRole(rs.getInt(1), rs.getString(2));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return role;
	}

	@Override
	public int update(UserRole t) {
		int count = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "update ers_user_roles set user_role = ? where ers_user_role_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, t.getRole());
			ps.setInt(2, t.getRoleId());
			count = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int create(UserRole t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UserRole findByName(String name) {
		UserRole role = null;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from ers_user_roles where user_role = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				role = new UserRole(rs.getInt(1), rs.getString(2));
				System.out.println(role);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return role;
	}


}
