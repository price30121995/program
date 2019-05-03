package com.usa.ri.gov.ies.admin.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class IESPlan {

	private String planId;
	
	private String planName;
	
	private String planDesc;
	
	private String startDate;
	
	private String endDate;
	
	private String activeSw;
	
	private Timestamp createDate;
	
	private Timestamp updateDate;
	
	private String createtedBy;
	
	private String updatedBy;

}
