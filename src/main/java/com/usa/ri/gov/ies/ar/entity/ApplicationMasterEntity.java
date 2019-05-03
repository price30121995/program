package com.usa.ri.gov.ies.ar.entity;

import java.io.Serializable;
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
@Table(name = "APPLICATION_MASTER")
public class ApplicationMasterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_seq")
    @GenericGenerator(
        name = "app_seq", 
        strategy = "com.usa.ri.gov.ies.util.CustomSequenceIdGenerator", 
        parameters = {
            @Parameter(name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "AR"),
            @Parameter(name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%03d"),
            @Parameter(name = CustomSequenceIdGenerator.VALUE_PREFIX_DEFAULT, value = "100")})
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
