package com.usa.ri.gov.ies.dc.service;

import java.util.List;
import java.util.Map;

import com.usa.ri.gov.ies.ar.model.ApplicationMaster;
import com.usa.ri.gov.ies.dc.model.DcCaseChildDetails;
import com.usa.ri.gov.ies.dc.model.DcCaseEducationDetails;
import com.usa.ri.gov.ies.dc.model.DcCaseIncomeDetails;
import com.usa.ri.gov.ies.dc.model.DcCasePlan;

public interface DcService {

	public String[] searchAllAppId();

	public ApplicationMaster findByAppId(String appId);
	
	public Integer createCase(String appId);
	
	public Map<String, String> fetchAllPlans();

	public boolean saveSelectedPlan(DcCasePlan planModel);

	public DcCasePlan findCaseByCaseId(Integer caseId);

	public boolean saveIncomeDetails(DcCaseIncomeDetails incomeDetails);
	
	public boolean saveEducationDetails(DcCaseEducationDetails eduDetails);

	public List<DcCaseChildDetails> saveChildDetails(DcCaseChildDetails childDetails);

	public Map<String, Object> updateChildDetails(Integer childId);
	
	public DcCaseChildDetails findByChildId(String childId);

	public List<DcCaseChildDetails> deleteChildDetails(Integer childId, Integer caseId);
}
