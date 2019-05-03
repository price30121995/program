package com.usa.ri.gov.ies.dc.model;

import lombok.Data;

@Data
public class DcCaseIncomeDetails {

	private Integer caseId;
	
	private String indivName;
	
	private String isEmployee;
	
	private Float weeklyIncome;
	
	private Float otherIncome;
}
