package com.reim.repo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.reim.util.ConnectionUtil;
import com.reimb.model.ReimbStatus;

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
		statuses.forEach(System.out::println);
		return statuses;
	}

	@Override
	public ReimbStatus findById(Integer i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(ReimbStatus t) {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

}
