package com.usa.ri.gov.ies.dc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="DC_CASE_CHILD_DETAILS")
public class DcCaseChildDetailsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@SequenceGenerator(sequenceName = "child_seq", name = "gen1", initialValue = 100001, allocationSize = 1)
	@GeneratedValue(generator = "gen1", strategy = GenerationType.SEQUENCE)
	@Column(name="CHILD_ID")
	private Integer childId;
	
	@Column(name="CASE_ID")
	private Integer caseId;
	
	@Column(name="INDIV_NAME")
	private String indivName;
	
	@Column(name="CHILD_NAME")
	private String childName;

	@Column(name="CHILD_GENDER")
	private String childGender;
	
	@Column(name="CHILD_DOB")
	private Date childDob;
	
	@Column(name="CHILD_SSN")
	private Long childSSN;
}
