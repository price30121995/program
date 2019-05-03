package com.usa.ri.gov.ies.dc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="DC_CASE_PLAN")
public class DcCasePlanEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CASE_ID", unique=true)
	private Integer caseId;
	
	@Column(name = "FNAME")
	private String firstName;

	@Column(name = "LNAME")
	private String lastName;

	@Column(name="PLAN_ID")
	private String planId;
	
	@Column(name="PLAN_NAME")
	private String planName;
	
	@Column(name="PLAN_DESC")
	private String planDescription;
	
	@Column(name="START_DT")
	private String planStartDate;
	
	@Column(name="END_DT")
	private String planEndDate;
	
}
