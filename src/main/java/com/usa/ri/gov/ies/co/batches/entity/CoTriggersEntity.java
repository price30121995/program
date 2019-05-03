package com.usa.ri.gov.ies.co.batches.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "CO_TRIGGERS")
public class CoTriggersEntity {

	@Id
	@GeneratedValue
	@Column(name = "TRG_ID")
	private Integer triggerId;

	@Column(name = "CASE_NUM")
	private Long caseNum;

	@Column(name = "TRG_STATUS")
	private String triggerStatus;

	@CreationTimestamp
	@Column(name = "CREATE_DT")
	private Date createdDate;

	@Column(name = "UPDATE_DT")
	private Date updatedDate;

}
