package com.usa.ri.gov.ies.ed.model;

import java.util.Date;

import lombok.Data;

@Data
public class EligibilityDetailModel {

	private Integer edTraceId;

	private Long caseNum;

	private String planName;

	private String planStatus;

	private String planStartDate;

	private String planEndDate;

	private String benefitAmt;

	private String denialReason;

	private Date createdDate;

	private Date updatedDate;

}
