package com.usa.ri.gov.ies.admin.model;

import java.sql.Timestamp;


import lombok.Data;

@Data
public class AppAccount {

	private String accId;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String dob;

	private String gender="Male";

	private Long ssn;

	private String phno;

	private String activeSw;

	private Timestamp createDate;

	private Timestamp updateDate;

	private String role;

}
