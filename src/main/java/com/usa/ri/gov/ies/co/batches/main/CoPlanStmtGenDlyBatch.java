package com.usa.ri.gov.ies.co.batches.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.usa.ri.gov.ies.co.batches.model.CoBatchRunDetailsModel;
import com.usa.ri.gov.ies.co.batches.model.CoBatchSummaryModel;
import com.usa.ri.gov.ies.co.batches.model.CoPdfModel;
import com.usa.ri.gov.ies.co.batches.model.CoTriggersModel;
import com.usa.ri.gov.ies.co.batches.service.CoBatchService;
import com.usa.ri.gov.ies.ed.model.EligibilityDetailModel;
import com.usa.ri.gov.ies.ed.service.EligibilityDetailServiceImpl;

@Service("coPlanStmtGenDly")
public class CoPlanStmtGenDlyBatch {

	public static void main(String[] args) {
		CoPlanStmtGenDlyBatch batch = new CoPlanStmtGenDlyBatch();

		if (args.length < 2) {
			// log stmts
			System.exit(0);
		}
		Integer totalBuckets = Integer.parseInt(args[0]);
		Integer currentInstance = Integer.parseInt(args[1]);

		batch.init(totalBuckets, currentInstance);
	}

	public void init(Integer totalBuckets, Integer instanceNum) {
		Integer runSeq = preProcess(instanceNum);
		start(totalBuckets, instanceNum);
		postProcess(runSeq);
	}

	@Autowired
	private CoBatchService coBatchService;

	@Autowired
	private EligibilityDetailServiceImpl edDetailService;

	private static final String BATCH_ID = "CO-PLN-STMT-DLY";
	private static Long SUCCESSFUL_TRG_CNT = 0L;
	private static Long FAILURE_TRG_CNT = 0L;

	public Integer preProcess(Integer instanceNum) {
		// insert batch run details with ST status
		CoBatchRunDetailsModel model = new CoBatchRunDetailsModel();
		model.setBatchName(BATCH_ID);
		model.setBatchRunStatus("ST");
		model.setStartDate(new Date());
		model.setInstanceNum(instanceNum);

		// inserting Run details
		model = coBatchService.insertBatchRunDetails(model);

		return model.getRunSeq();
	}

	/**
	 * This method is used to start batch processing
	 */
	public void start(Integer totalBuckets, Integer instanceNum) {
		// read all pending triggers
		List<CoTriggersModel> triggers = coBatchService.findPendingTriggers(totalBuckets, instanceNum);

		if (!triggers.isEmpty()) {

			ExecutorService exService = Executors.newFixedThreadPool(20);
			CompletionService<String> pool = new ExecutorCompletionService<String>(exService);

			// processing each trigger
			for (CoTriggersModel model : triggers) {
				try {
					pool.submit(new Callable<String>() {

						@Override
						public String call() throws Exception {
							process(model);
							return null;
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} 
		else {
			System.exit(0);
		}
		
	}

	/**
	 * This method is used to process each trigger
	 * 
	 * @param trgModel
	 */
	public void process(CoTriggersModel coTrgModel) {
		// using trigger case num read eligibility data
		Long caseNum = coTrgModel.getCaseNum();

		try {
			// Fetching Elig details for a case
			EligibilityDetailModel edModel = edDetailService.findByCaseNum(caseNum);

			// Getting Plan Status
			String planStatus = edModel.getPlanStatus();

			// generate pdf based on plan_status
			if (planStatus.equalsIgnoreCase("AP")) {
				// generate approved plan pdf
				buildPlanApPdf(edModel);
			} else if (planStatus.equalsIgnoreCase("DN")) {
				// generate denied plan pdf
				buildPlanDnPdf(edModel);
			}
			// Store Pdf to DB
			storePdf(edModel);

			// increment trigger count variable (success|failure)
			updatePendingTrigger(coTrgModel);
			SUCCESSFUL_TRG_CNT++;
		} catch (Exception e) {
			e.printStackTrace();
			FAILURE_TRG_CNT++;
		}

	}

	/**
	 * This method method is used to execute after batch processing
	 * 
	 * @param runSeq
	 */
	public void postProcess(Integer runSeq) {
		// update batch run status as EN with EndDate
		CoBatchRunDetailsModel model = coBatchService.findByRunSeqNum(runSeq);
		model.setBatchRunStatus("EN");
		model.setEndDate(new Date());
		coBatchService.updateBatchRunDetails(model);

		// save Batch Summary
		CoBatchSummaryModel summaryModel = new CoBatchSummaryModel();
		summaryModel.setBatchName(BATCH_ID);
		summaryModel.setSuccessTriggerCount(SUCCESSFUL_TRG_CNT);
		summaryModel.setFailureTriggerCount(FAILURE_TRG_CNT);
		summaryModel.setTotalTriggerProcessed(SUCCESSFUL_TRG_CNT + FAILURE_TRG_CNT);
		coBatchService.saveBatchSummary(summaryModel);

	}

	/**
	 * This method is used to update trigger as completed
	 * 
	 * @param coTrgModel
	 */
	private void updatePendingTrigger(CoTriggersModel coTrgModel) {
		coTrgModel.setTriggerStatus("C");
		coTrgModel.setUpdatedDate(new Date());
		coBatchService.updatePendingTrigger(coTrgModel);
	}

	/**
	 * This method is used to create pdf with plan details
	 * 
	 * @param edModel
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public void buildPlanApPdf(EligibilityDetailModel edModel) throws FileNotFoundException, DocumentException {

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(edModel.getCaseNum().toString() + ".pdf"));

		// open document
		document.open();

		// Creating paragraph
		Paragraph p = new Paragraph();
		p.add("Approved Plan Details");
		p.setAlignment(Element.ALIGN_CENTER);

		// adding paragraph to document
		document.add(p);

		// Create Table object, Here 2 specify the no. of columns
		PdfPTable pdfPTable = new PdfPTable(2);

		// First row in table
		PdfPCell pdfPCell1 = new PdfPCell(new Paragraph(edModel.getCaseNum().toString() + ".pdf"));
		PdfPCell pdfPCell2 = new PdfPCell(new Paragraph(edModel.getCaseNum().toString()));

		// Add cells to table
		pdfPTable.addCell(pdfPCell1);
		pdfPTable.addCell(pdfPCell2);

		// Second row in table
		PdfPCell pdfPCell3 = new PdfPCell(new Paragraph("Plan Name"));
		PdfPCell pdfPCell4 = new PdfPCell(new Paragraph(edModel.getPlanName().toString()));

		// Add cells to table
		pdfPTable.addCell(pdfPCell3);
		pdfPTable.addCell(pdfPCell4);

		// Third Row
		pdfPTable.addCell(new PdfPCell(new Paragraph("Plan Status")));
		pdfPTable.addCell(new PdfPCell(new Paragraph(edModel.getPlanStatus())));

		// Fourth Row
		pdfPTable.addCell(new PdfPCell(new Paragraph("Plan Start Date")));
		pdfPTable.addCell(new PdfPCell(new Paragraph(edModel.getPlanStartDate())));

		// Fifth Row
		pdfPTable.addCell(new PdfPCell(new Paragraph("Plan End Date")));
		pdfPTable.addCell(new PdfPCell(new Paragraph(edModel.getPlanEndDate())));

		// sixth Row
		pdfPTable.addCell(new PdfPCell(new Paragraph("Benfit Amount")));
		pdfPTable.addCell(new PdfPCell(new Paragraph(edModel.getBenefitAmt())));

		// Add content to the document using Table objects.

		document.add(pdfPTable);
		document.close();
	}

	/**
	 * This method is used to create with denied plan details
	 * 
	 * @param edModel
	 */
	public void buildPlanDnPdf(EligibilityDetailModel edModel) throws Exception {

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("new2.pdf"));

		// open document
		document.open();

		// Creating paragraph
		Paragraph p = new Paragraph();
		p.add("Denied Plan Details");
		p.setAlignment(Element.ALIGN_CENTER);

		// adding paragraph to document
		document.add(p);

		// Create Table object, Here 2 specify the no. of columns
		PdfPTable pdfPTable = new PdfPTable(2);

		// First row in table
		PdfPCell pdfPCell1 = new PdfPCell(new Paragraph("Case Number"));
		PdfPCell pdfPCell2 = new PdfPCell(new Paragraph(edModel.getCaseNum().toString()));

		// Add cells to table
		pdfPTable.addCell(pdfPCell1);
		pdfPTable.addCell(pdfPCell2);

		// Second row in table
		PdfPCell pdfPCell3 = new PdfPCell(new Paragraph("Plan Name"));
		PdfPCell pdfPCell4 = new PdfPCell(new Paragraph(edModel.getPlanName().toString()));

		// Add cells to table
		pdfPTable.addCell(pdfPCell3);
		pdfPTable.addCell(pdfPCell4);

		// Third Row
		pdfPTable.addCell(new PdfPCell(new Paragraph("Plan Status")));
		pdfPTable.addCell(new PdfPCell(new Paragraph(edModel.getPlanStatus())));

		// Fourth Row
		pdfPTable.addCell(new PdfPCell(new Paragraph("Denial Reason")));
		pdfPTable.addCell(new PdfPCell(new Paragraph(edModel.getDenialReason())));

		// Add content to the document using Table objects.

		document.add(pdfPTable);
		document.close();

	}

	/**
	 * This is used to fetch dummy data
	 * 
	 * @return
	 */
	public EligibilityDetailModel getEdData() {
		EligibilityDetailModel model = new EligibilityDetailModel();
		model.setCaseNum(78956l);
		model.setPlanName("SNAP");
		model.setPlanStartDate("18-Feb-2019");
		model.setPlanEndDate("18-March-2019");
		model.setPlanStatus("AP");
		model.setBenefitAmt("$350.00");
		return model;
	}

	public String storePdf(EligibilityDetailModel model) {
		CoPdfModel pdfModel = null;
		byte[] casePdf = null;
		FileSystemResource pdfFile = null;
		pdfModel = new CoPdfModel();
		try {
			pdfFile = new FileSystemResource(model.getCaseNum().toString() + ".pdf");
			casePdf = new byte[(int) pdfFile.contentLength()];
			pdfFile.getInputStream().read(casePdf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pdfModel.setCaseNumber(model.getCaseNum());
		pdfModel.setPlanName(model.getPlanName());
		pdfModel.setPlanStatus(model.getPlanStatus());
		pdfModel.setPdfDocument(casePdf);
		// call service class method
		coBatchService.savePdf(pdfModel);
		return model.getCaseNum().toString();
	}
}
