package com.usa.ri.gov.ies.dc.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class DcCases {

	private String appId;
	
	private Integer caseId;
	
	private String firstName;

	private String lastName;

	private String dob;

	private String gender;

	private Long ssn;
	
	private String email;

	private String phno;

	private Timestamp createDate;

	private Timestamp updateDate;
}
