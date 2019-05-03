package com.usa.ri.gov.ies.co.batches.service;

import java.util.List;

import com.usa.ri.gov.ies.co.batches.model.CoBatchRunDetailsModel;
import com.usa.ri.gov.ies.co.batches.model.CoBatchSummaryModel;
import com.usa.ri.gov.ies.co.batches.model.CoPdfModel;
import com.usa.ri.gov.ies.co.batches.model.CoTriggersModel;

public interface CoBatchService {

	// Before starting batch this method should execute
	public CoBatchRunDetailsModel insertBatchRunDetails(CoBatchRunDetailsModel model);

	public CoBatchRunDetailsModel findByRunSeqNum(Integer runSeqNum);

	// After batch started we need to read triggers which are in pending
	public List<CoTriggersModel> findPendingTriggers(Integer totalBuckets, Integer instanceNum);

	// after trigger is processed insert pdf
	public CoPdfModel savePdf(CoPdfModel model);

	// after processing completed mark trigger as completed
	public boolean updatePendingTrigger(CoTriggersModel model);

	// update batch execution status as EN
	public CoBatchRunDetailsModel updateBatchRunDetails(CoBatchRunDetailsModel model);

	// Insert Batch summary with totalTrgs,succTrgsrs,failureTrgs
	public CoBatchSummaryModel saveBatchSummary(CoBatchSummaryModel model);

}
