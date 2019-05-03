/**
 * 
 */
package com.usa.ri.gov.ies.co.batches.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author vinay
 *
 */

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "CO_PDFS")
public class CoPdfEntity {
	@Id()
	@GeneratedValue
	@Column(name = "CO_PDF_ID")
	Integer coPdfId;

	@Column(name = "CASE_NUMBER")
	String caseNumber;

	@Lob
	@Column(name = "PDF_DOCUMENT", length = 100000)
	byte[] pdfDocument;

	@Column(name = "PLAN_NAME")
	String planName;

	@Column(name = "PLAN_STATUS")
	String PlanStatus;

}// CoPdfEntity
