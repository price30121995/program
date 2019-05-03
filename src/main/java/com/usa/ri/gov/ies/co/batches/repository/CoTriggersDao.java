package com.usa.ri.gov.ies.co.batches.repository;

import java.util.List;

import com.usa.ri.gov.ies.co.batches.entity.CoTriggersEntity;

public interface CoTriggersDao {

	public List<CoTriggersEntity> findPendTrgrsWithOraHash(String status, Integer tb, Integer ci);

}
