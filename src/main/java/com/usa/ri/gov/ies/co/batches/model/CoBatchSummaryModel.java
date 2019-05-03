package com.usa.ri.gov.ies.co.batches.model;

import lombok.Data;

@Data()
public class CoBatchSummaryModel {
	private Integer summaryId;
	private String batchName;
	private Long totalTriggerProcessed;
	private Long failureTriggerCount;
	private Long successTriggerCount;

}// BatchSummaryModel
