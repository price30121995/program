package com.usa.ri.gov.ies.ar.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.usa.ri.gov.ies.ar.entity.ApplicationMasterEntity;

@Repository("appRepository")
public interface ApplicationMasterRepository extends JpaRepository<ApplicationMasterEntity, Serializable> {
	
	@Query(name = "from am ApplicationMasterEntity where am.ssn=:SSN")
	public ApplicationMasterEntity findBySsn(Long SSN);
}
