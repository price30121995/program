package com.usa.ri.gov.ies.dc.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usa.ri.gov.ies.dc.entity.DcCasePlanEntity;

@Repository("appPlanRepository")
public interface DcCasePlanRepository extends JpaRepository<DcCasePlanEntity, Serializable> {

}
