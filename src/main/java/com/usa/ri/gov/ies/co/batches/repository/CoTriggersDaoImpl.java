package com.usa.ri.gov.ies.co.batches.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.usa.ri.gov.ies.co.batches.entity.CoTriggersEntity;

@Repository("coTrgDao")
public class CoTriggersDaoImpl implements CoTriggersDao {

	@Override
	public List<CoTriggersEntity> findPendTrgrsWithOraHash(String status, Integer tb, Integer ci) {

		List<CoTriggersEntity> entities = new ArrayList();

		String sql = "SELECT * FROM CO_TRIGGERS cot WHERE cot.TRG_STATUS=? and ora_hash(cot.TRG_ID , ? ) = ?";

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "IES_DEV", "IES_DEV");
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, status);
			pstmt.setInt(2, tb);
			pstmt.setInt(3, ci);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				CoTriggersEntity entity = new CoTriggersEntity();
				
				entity.setCaseNum(rs.getLong("CASE_NUM"));
				entity.setTriggerId(rs.getInt("TRG_ID"));
				entity.setTriggerStatus(rs.getString("TRG_STATUS"));
				
				entities.add(entity);
			}

		} catch (Exception e) {

		}

		return entities;
	}

}
