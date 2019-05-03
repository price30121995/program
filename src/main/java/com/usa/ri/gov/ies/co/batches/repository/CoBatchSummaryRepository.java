package com.usa.ri.gov.ies.co.batches.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usa.ri.gov.ies.co.batches.entity.CoBatchSummaryEntity;

@Repository("coBatchSummaryRepo")
public interface CoBatchSummaryRepository extends JpaRepository<CoBatchSummaryEntity, Serializable> {

}// CoBatchSummaryRepository
