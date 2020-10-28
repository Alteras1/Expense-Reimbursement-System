package com.reim.repo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import com.reim.util.ConnectionUtil;
import com.reimb.model.Reimb;
import com.reimb.model.ReimbStatus;
import com.reimb.model.ReimbType;
import com.reimb.model.User;
import com.reimb.model.UserRole;

public class ReimbDao implements DaoContract<Reimb, Integer> {

	@Override
	public List<Reimb> findAll() {
		List<Reimb> reimbs = new LinkedList<Reimb>();
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from getReimbursement()";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			//This single SQL function contains all necessary information
			//to generate every POJO without needing to call a million other
			//JDBC calls
			//Sanity was lost the day this method was created
			while (rs.next()) {
				User author = new User(rs.getInt("author_id"), rs.getString("author_username"), rs.getString("author_password"), rs.getString("author_first_name"), 
						rs.getString("author_last_name"), rs.getString("author_email"), new UserRole(rs.getInt("author_role_id"), rs.getString("author_role")));
				User resolver = (rs.getInt("resolver_id") > 0) ? new User(rs.getInt("resolver_id"), rs.getString("resolver_username"), rs.getString("resolver_password"), rs.getString("resolver_first_name"), 
						rs.getString("resolver_last_name"), rs.getString("resolver_email"), new UserRole(rs.getInt("resolver_role_id"), rs.getString("resolver_role"))) : null;
				reimbs.add(new Reimb(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"), rs.getDate("reimb_submitted"), 
						rs.getDate("reimb_resolved"), rs.getString("reimb_description"), author, resolver, 
						new ReimbStatus(rs.getInt("reimb_status_id"), rs.getString("reimb_status")), new ReimbType(rs.getInt("reimb_type_id"), rs.getString("reimb_type"))));
				
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//reimbs.forEach(System.out::println);
		return reimbs;
	}

	@Override
	public Reimb findById(Integer i) {
		Reimb reimb = null;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from getReimbursement() where reimb_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User author = new User(rs.getInt("author_id"), rs.getString("author_username"), rs.getString("author_password"), rs.getString("author_first_name"), 
						rs.getString("author_last_name"), rs.getString("author_email"), new UserRole(rs.getInt("author_role_id"), rs.getString("author_role")));
				User resolver = (rs.getInt("resolver_id") > 0) ? new User(rs.getInt("resolver_id"), rs.getString("resolver_username"), rs.getString("resolver_password"), rs.getString("resolver_first_name"), 
						rs.getString("resolver_last_name"), rs.getString("resolver_email"), new UserRole(rs.getInt("resolver_role_id"), rs.getString("resolver_role"))) : null;
				reimb = new Reimb(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"), rs.getDate("reimb_submitted"), 
						rs.getDate("reimb_resolved"), rs.getString("reimb_description"), author, resolver, 
						new ReimbStatus(rs.getInt("reimb_status_id"), rs.getString("reimb_status")), new ReimbType(rs.getInt("reimb_type_id"), rs.getString("reimb_type")));
				
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reimb;
	}

	@Override
	public int update(Reimb t) {
		int count = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "update ers_reimbursement set reimb_amount = ?,"
					+ "reimb_description = ?,"
					+ "reimb_author = ?,"
					+ "reimb_resolver = ?,"
					+ "reimb_status_id = ?,"
					+ "reimb_type_id = ?"
					+ "where reimb_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, t.getAmount());
			ps.setString(2, t.getDescription());
			ps.setInt(3, t.getAuthor().getUserId());
			if ((t.getResolver() == null)) {
				ps.setNull(4, Types.INTEGER);
			} else {
				ps.setInt(4, t.getResolver().getUserId());
			}
			ps.setInt(5, t.getStatus().getStatusId());
			ps.setInt(6, t.getType().getTypeId());
			ps.setInt(7, t.getReimbId());
			count = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public int update(int id, ReimbStatus rs, User resolver) {
		int count = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "call updateReimbursementStatus(?,?,?)";
			CallableStatement pc = conn.prepareCall(sql);
			pc.setInt(1, id);
			pc.setInt(2, rs.getStatusId());
			pc.setInt(3, resolver.getUserId());
			ResultSet rs1 = pc.executeQuery();
			rs1.next();
			count = (rs1.getInt(1) == 1) ? 1 : 0;
			//count = pc.getUpdateCount();/
			System.out.print(count);
			pc.close();
			rs1.close();
		} catch (SQLException e) {
			count = 0;
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int create(Reimb t) {
		int count = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "insert into ers_reimbursement (reimb_amount, reimb_description, reimb_author, reimb_status_id, reimb_type_id) values"
					+ "(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, t.getAmount());
			ps.setString(2, t.getDescription());
			ps.setInt(3, t.getAuthor().getUserId());
			ps.setInt(4, t.getStatus().getStatusId());
			ps.setInt(5, t.getType().getTypeId());
			count = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int delete(Integer i) {
		int count = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "delete from ers_reimbursement where reimb_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			count = ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Reimb findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Reimb> findByAuthor(User a) {
		List<Reimb> reimbs = new LinkedList<Reimb>();
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from getReimbursement() where author_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getUserId());
			ResultSet rs = ps.executeQuery();
			//This single SQL function contains all necessary information
			//to generate every POJO without needing to call a million other
			//JDBC calls
			//Sanity was lost the day this method was created
			while (rs.next()) {
				User author = new User(rs.getInt("author_id"), rs.getString("author_username"), rs.getString("author_password"), rs.getString("author_first_name"), 
						rs.getString("author_last_name"), rs.getString("author_email"), new UserRole(rs.getInt("author_role_id"), rs.getString("author_role")));
				User resolver = (rs.getInt("resolver_id") > 0) ? new User(rs.getInt("resolver_id"), rs.getString("resolver_username"), rs.getString("resolver_password"), rs.getString("resolver_first_name"), 
						rs.getString("resolver_last_name"), rs.getString("resolver_email"), new UserRole(rs.getInt("resolver_role_id"), rs.getString("resolver_role"))) : null;
				reimbs.add(new Reimb(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"), rs.getDate("reimb_submitted"), 
						rs.getDate("reimb_resolved"), rs.getString("reimb_description"), author, resolver, 
						new ReimbStatus(rs.getInt("reimb_status_id"), rs.getString("reimb_status")), new ReimbType(rs.getInt("reimb_type_id"), rs.getString("reimb_type"))));
				
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//reimbs.forEach(System.out::println);
		return reimbs;
	}

}
