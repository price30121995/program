package com.usa.ri.gov.ies.admin.model;

import java.sql.Timestamp;


import lombok.Data;

@Data
public class PlanDetails {
	
	private String planId;
	
	private String planName;
	
	private String planDescription;
	
	//@DateTimeFormat(pattern="MM-dd-yyyy")
	private String planStartDate;
	
	//@DateTimeFormat(pattern="MM-dd-yyyy")
	private String planEndDate;
	
	private String activeSw;
	
	private String createdBy;
	
	private String updatedBy;
	
	//@DateTimeFormat(pattern = "dd-MM-yyyy")
	//@Temporal(TemporalType.DATE)
	private Timestamp createDate;
	
	//@DateTimeFormat(pattern = "dd-MM-yyyy")
	//@Temporal(TemporalType.DATE)
	private Timestamp updateDate;

	
}
