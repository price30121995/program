package com.usa.ri.gov.ies.dc.model;

import lombok.Data;

@Data
public class DcCasePlan {

	private Integer caseId;
	
	private String firstName;

	private String lastName;

	private String planId;
	
	private String planName;
	
	private String planDescription;
	
	private String planStartDate;
	
	private String planEndDate;
	
}
