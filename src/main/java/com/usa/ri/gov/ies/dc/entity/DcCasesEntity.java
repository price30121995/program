package com.usa.ri.gov.ies.dc.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name="DC_CASES")
public class DcCasesEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(sequenceName = "case_seq", name = "gen1", initialValue = 100001, allocationSize = 1)
	@GeneratedValue(generator = "gen1", strategy = GenerationType.SEQUENCE)
	@Column(name = "CASE_ID", unique=true)
	private Integer caseId;
	
	@Column(name = "APP_ID")
	private String appId;
	
	@Column(name = "FNAME")
	private String firstName;

	@Column(name = "LNAME")
	private String lastName;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "SSN", unique=true)
	private Long ssn;
	
	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PHNO")
	private String phno;

	@CreationTimestamp
	@Column(name = "CREATE_DT")
	private Timestamp createDate;

	@UpdateTimestamp
	@Column(name = "UPDATE_DT")
	private Timestamp updateDate;
	
}
