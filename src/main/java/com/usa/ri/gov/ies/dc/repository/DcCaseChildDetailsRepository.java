package com.usa.ri.gov.ies.dc.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.usa.ri.gov.ies.dc.entity.DcCaseChildDetailsEntity;

@Repository("childDetailsRepository")
public interface DcCaseChildDetailsRepository extends JpaRepository<DcCaseChildDetailsEntity, Serializable> {

	@Query(name="from dc DcCaseChildDetailsEntity where dc.caseId=:caseNo")
	public List<DcCaseChildDetailsEntity> findByCaseId(Integer caseNo);
	
}
