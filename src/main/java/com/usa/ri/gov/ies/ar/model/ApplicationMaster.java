package com.usa.ri.gov.ies.ar.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ApplicationMaster {

	private String appId;

	private String firstName;

	private String lastName;

	private String dob;

	private String gender="Male";

	private Long ssn;
	
	private String email;

	private String phno;

	private Timestamp createDate;

	private Timestamp updateDate;

}
