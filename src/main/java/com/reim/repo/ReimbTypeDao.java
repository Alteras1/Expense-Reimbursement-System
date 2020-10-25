package com.reim.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.reim.util.ConnectionUtil;
import com.reimb.model.ReimbType;

public class ReimbTypeDao implements DaoContract<ReimbType, Integer> {

	@Override
	public List<ReimbType> findAll() {
		List<ReimbType> typees = new LinkedList<>();
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			Statement s = conn.createStatement();
			String sql = "select * from ers_reimbursement_type";
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				typees.add(new ReimbType(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return typees;
	}

	@Override
	public ReimbType findById(Integer i) {
		ReimbType type = null;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from ers_reimbursement_type where reimb_type_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				type = new ReimbType(rs.getInt(1), rs.getString(2));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return type;
	}

	@Override
	public int update(ReimbType t) {
		int count = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "update ers_reimbursement_type set reimb_type = ? where reimb_type_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, t.getType());
			ps.setInt(2, t.getTypeId());
			count = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int create(ReimbType t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ReimbType findByName(String name) {
		ReimbType type = null;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from ers_reimbursement_type where reimb_type = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				type = new ReimbType(rs.getInt(1), rs.getString(2));
				System.out.println(type);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return type;
	}


}
