package com.usa.ri.gov.ies.ed.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ELIGIBILITY_DETAILS")
public class EligibilityDetailEntity {

	@Id
	@GeneratedValue
	@Column(name = "ED_TRACE_ID")
	private Integer edTraceId;

	@Column(name = "CASE_NUM")
	private Long caseNum;

	@Column(name = "PLAN_NAME")
	private String planName;

	@Column(name = "PLAN_STATUS")
	private String planStatus;

	@Column(name = "PLAN_START_DT")
	private String planStartDate;

	@Column(name = "PLAN_END_DT")
	private String planEndDate;

	@Column(name = "BENEFIT_AMT")
	private String benefitAmt;

	@Column(name = "DENIAL_REASON")
	private String denialReason;

	@Column(name = "CREATE_DT")
	private Date createdDate;

	@Column(name = "UPDATE_DT")
	private Date updatedDate;

}
