package com.usa.ri.gov.ies.co.batches.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usa.ri.gov.ies.co.batches.entity.CoPdfEntity;

@Repository("coPdfRepository")
public interface CoPdfRepository extends JpaRepository<CoPdfEntity, Serializable> {

}
