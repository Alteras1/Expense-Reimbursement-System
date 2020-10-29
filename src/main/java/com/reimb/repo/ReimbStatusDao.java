package com.reimb.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.reimb.model.ReimbStatus;
import com.reimb.util.ConnectionUtil;

public class ReimbStatusDao implements DaoContract<ReimbStatus, Integer> {

	@Override
	public List<ReimbStatus> findAll() {
		List<ReimbStatus> statuses = new LinkedList<>();
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			Statement s = conn.createStatement();
			String sql = "select * from ers_reimbursement_status";
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				statuses.add(new ReimbStatus(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statuses;
	}

	@Override
	public ReimbStatus findById(Integer i) {
		ReimbStatus status = null;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from ers_reimbursement_status where reimb_status_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				status = new ReimbStatus(rs.getInt(1), rs.getString(2));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public int update(ReimbStatus t) {
		int count = 0;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "update ers_reimbursement_status set reimb_status = ? where reimb_status_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, t.getStatus());
			ps.setInt(2, t.getStatusId());
			count = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int create(ReimbStatus t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ReimbStatus findByName(String name) {
		ReimbStatus status = null;
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			String sql = "select * from ers_reimbursement_status where reimb_status = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				status = new ReimbStatus(rs.getInt(1), rs.getString(2));
				System.out.println(status);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

}
