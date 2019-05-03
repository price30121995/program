package com.usa.ri.gov.ies.dc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="DC_CASE_EDUCATION_DETAILS")
public class DcCaseEducationDetailsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CASE_ID")
	private Integer caseId;
	
	@Column(name="INDIV_NAME")
	private String indivName;
	
	@Column(name="HIGHEST_QLFY")
	private String highestQlfy;

	@Column(name="COMPLETED_YEAR")
	private Integer completedYear;

	@Column(name="GRADE")
	private String grade;
}
