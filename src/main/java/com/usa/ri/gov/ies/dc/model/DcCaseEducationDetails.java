package com.usa.ri.gov.ies.dc.model;

import lombok.Data;

@Data
public class DcCaseEducationDetails {
	private Integer caseId;
	
	private String indivName;

	private String highestQlfy;

	private Integer completedYear;

	private String grade;
}
