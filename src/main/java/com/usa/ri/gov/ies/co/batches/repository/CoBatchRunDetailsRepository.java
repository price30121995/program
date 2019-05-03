package com.usa.ri.gov.ies.co.batches.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usa.ri.gov.ies.co.batches.entity.CoBatchRunDetailsEntity;

@Repository("coBatchRunDetailRepo")
public interface CoBatchRunDetailsRepository extends JpaRepository<CoBatchRunDetailsEntity, Serializable> {

}// CoBatchRunDetailsRepositor
