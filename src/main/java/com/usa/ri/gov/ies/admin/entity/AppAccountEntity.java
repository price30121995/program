package com.usa.ri.gov.ies.admin.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;

import com.usa.ri.gov.ies.util.CustomSequenceIdGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "APP_ACCOUNTS")
public class AppAccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @GenericGenerator(
        name = "account_seq", 
        strategy = "com.usa.ri.gov.ies.util.CustomSequenceIdGenerator", 
        parameters = {
            @Parameter(name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "IES_ACC_"),
            @Parameter(name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	@Column(name = "ACC_ID")
	private String accId;

	@Column(name = "FNAME")
	private String firstName;

	@Column(name = "LNAME")
	private String lastName;

	@Column(name = "EMAIL", unique = true)
	private String email;

	@Column(name = "PWD")
	private String password;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "SSN")
	private Long ssn;

	@Column(name = "PHNO")
	private String phno;

	@Column(name = "ACTIVE_SW")
	private String activeSw;

	@Column(name = "ROLE")
	private String role;

	@CreationTimestamp
	@Column(name = "CREATE_DT")
	private Timestamp createDate;

	@UpdateTimestamp
	@Column(name = "UPDATE_DT")
	private Timestamp updateDate;

}
