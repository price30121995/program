package com.usa.ri.gov.ies.dc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.usa.ri.gov.ies.ar.model.ApplicationMaster;
import com.usa.ri.gov.ies.constants.AppConstants;
import com.usa.ri.gov.ies.dc.model.DcCaseChildDetails;
import com.usa.ri.gov.ies.dc.model.DcCaseEducationDetails;
import com.usa.ri.gov.ies.dc.model.DcCaseIncomeDetails;
import com.usa.ri.gov.ies.dc.model.DcCasePlan;
import com.usa.ri.gov.ies.dc.service.DcService;
import com.usa.ri.gov.ies.properties.AppProperties;

/**
 * This class is used to Handle Admin module related functionalities
 * 
 * @author admin
 *
 */
@Controller
public class DcController {

	private static Logger logger = LoggerFactory.getLogger(DcController.class);

	@Autowired(required = true)
	private DcService dcService;

	@Autowired
	private AppProperties appProperties;

	/**
	 * This method is used to Display Applicant reg form
	 * 
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/createCaseForm", method = RequestMethod.GET)
	public String createCaseForm(Model model) {
		ApplicationMaster appModel = new ApplicationMaster();
		model.addAttribute("appModel", appModel);
		return "createCase";
	}

	/**
	 * This method is used for searching appId
	 * 
	 * @return String array
	 */
	@RequestMapping(value = "/createCaseForm/searchAppId", method = RequestMethod.POST)
	public @ResponseBody String[] searchAppId() {
		logger.debug("**DcController:: searchAppId() method started**");
		String[] vals = dcService.searchAllAppId();
		logger.debug("**DcController:: searchAppId() method ended**");
		return vals;
	}

	/**
	 * This method is used for searching Applicant By App Id
	 * 
	 * @param appId
	 * @param model
	 * @return String
	 */
	@RequestMapping("searchApplicant")
	public String searchApplicant(@RequestParam(name = "appId") String appId, Model model) {
		logger.debug("**DcController:: searchApplicant() method started**");
		ApplicationMaster appModel = dcService.findByAppId(appId);
		model.addAttribute("appModel", appModel);
		if (appModel.getAppId() == null)
			model.addAttribute(AppConstants.FAILURE,
					appProperties.getProperties().get(AppConstants.APP_ID_SEARCH_ERR_MSG));
		logger.debug("**DcController:: searchApplicant() method ended**");
		return "createCase";
	}

	/**
	 * This method is used for showing Create case Form
	 * 
	 * @param appId
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/createCase", method = RequestMethod.GET)
	public String createCase(@RequestParam(name = "appId") String appId, Model model) {
		logger.debug("**DcController:: createCase() method Started**");
		Integer caseId = dcService.createCase(appId);
		if (caseId != null) {
			DcCasePlan planModel = dcService.findCaseByCaseId(caseId);
			model.addAttribute("planModel", planModel);
			initFormPlan(model);
			logger.debug("**DcController:: createCase() method ended**");
			logger.info("**Case Created Successfully**");
			logger.info("**Redirecting to Select Plan Screen**");
			return "selectPlan";
		} else {
			model.addAttribute(AppConstants.FAILURE,
					appProperties.getProperties().get(AppConstants.CASE_CREATION_ERR_MSG));
			logger.debug("**DcController:: createCase() method ended**");
			logger.info("**Case Creation Failed**");
			return "createCase";
		}
	}

	/**
	 * This is for setting Plan name and id to model scope
	 * 
	 * @param model
	 */
	private void initFormPlan(Model model) {
		Map<String, String> planList = dcService.fetchAllPlans();
		model.addAttribute("planList", planList);
	}

	/**
	 * This method is used for saving data from select Plan form
	 * 
	 * @param planModel
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/selectPlan", method = RequestMethod.POST)
	public String selectPlan(@ModelAttribute("planModel") DcCasePlan planModel, Model model) {
		logger.debug("**DcController:: selectPlan() method started**");
		boolean isSaved = dcService.saveSelectedPlan(planModel);
		if (isSaved) {
			
			
			logger.debug("**DcController:: selectPlan() method ended**");
			logger.info("**income Details saved Successfully**");
			if (planModel.getPlanName().equalsIgnoreCase("SNAP")) {
				DcCaseIncomeDetails incomeDetails = new DcCaseIncomeDetails();
				incomeDetails.setCaseId(planModel.getCaseId());
				incomeDetails.setIndivName(planModel.getFirstName()+" "+planModel.getLastName());
				model.addAttribute("incomeDetails", incomeDetails);
				initFormIncome(model);
				logger.info("**Redirecting to Income Details Screen**");
				return "incomeDetails";
			}else if(planModel.getPlanName().equalsIgnoreCase("RIW")) {
				DcCaseEducationDetails educationDetails = new DcCaseEducationDetails();
				educationDetails.setCaseId(planModel.getCaseId());
				educationDetails.setIndivName(planModel.getFirstName()+" "+planModel.getLastName());
				model.addAttribute("educationDetails", educationDetails);
				initFormEducation(model);
				logger.info("**Redirecting to Education Details Screen**");
				return "educationDetails";
			}else if(planModel.getPlanName().equalsIgnoreCase("CCAP")) {
				DcCaseChildDetails childDetails = new DcCaseChildDetails();
				childDetails.setCaseId(planModel.getCaseId());
				childDetails.setIndivName(planModel.getFirstName()+" "+planModel.getLastName());
				model.addAttribute("childDetails", childDetails);
				initFormChild(model);
				logger.info("**Redirecting to Child Details Screen**");
				return "childDetails";
			}else{
				return "#";
			}
		} else {
			model.addAttribute(AppConstants.FAILURE,
					appProperties.getProperties().get(AppConstants.CASE_PLAN_SELECTION_ERR_MSG));
			logger.debug("**DcController:: selectPlan() method ended**");
			logger.info("**Plan Selection saving is Failed**");
			return "selectPlan";
		}
	}

	/**
	 * This method is used form setting list for employee status
	 * 
	 * @param model
	 */
	public void initFormIncome(Model model) {
		List<String> listIsEmp = new ArrayList<>();
		listIsEmp.add("Yes");
		listIsEmp.add("No");
		model.addAttribute("listIsEmp", listIsEmp);
	}
	/**
	 * This method is used form setting list for child gender
	 * 
	 * @param model
	 */
	public void initFormChild(Model model) {
		List<String> listGender = new ArrayList<>();
		listGender.add("Male");
		listGender.add("Fe-Male");
		model.addAttribute("listGender", listGender);
	}
	/**
	 * This method is used form setting list for Education
	 * 
	 * @param model
	 */
	public void initFormEducation(Model model) {
		List<String> listHgstQlfy = new ArrayList<>();
		listHgstQlfy.add("10+2");
		listHgstQlfy.add("10+3");
		listHgstQlfy.add("BE/B.Tech/Degree");
		listHgstQlfy.add("Ph.D");
		model.addAttribute("listHgstQlfy", listHgstQlfy);
		List<String> listGrade = new ArrayList<>();
		listGrade.add("A+");
		listGrade.add("A");
		listGrade.add("B+");
		listGrade.add("B");
		listGrade.add("C");
		listGrade.add("D");
		model.addAttribute("listGrade", listGrade);
	}

	/**
	 * This method is used for saving income details
	 * 
	 * @param incomeDetails
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/incomeDetails", method = RequestMethod.POST)
	public String incomeDetails(@ModelAttribute("incomeDetails") DcCaseIncomeDetails incomeDetails, Model model) {
		logger.debug("**DcController:: incomeDetails() method started**");
		boolean isSaved = dcService.saveIncomeDetails(incomeDetails);
		initFormIncome(model);
		if (isSaved) {
			logger.debug("**DcController:: incomeDetails() method ended**");
			logger.info("**income Details saved Successfully**");
			logger.info("**Redirecting to Determine Elegibility Screen**");
			return "determineEligibility";
		}
		model.addAttribute(AppConstants.FAILURE,
				appProperties.getProperties().get(AppConstants.CASE_INCOME_SAVING_ERR_MSG));
		logger.debug("**DcController:: incomeDetails() method ended**");
		logger.info("**income Details saving has failed**");
		return "incomeDetails";
	}// method
	
	/**
	 * This method is used for saving income details
	 * 
	 * @param incomeDetails
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/childDetails", method = RequestMethod.POST)
	public String savechildDetails(@ModelAttribute("childDetails") DcCaseChildDetails childDetail, Model model) {
		logger.debug("**DcController:: childDetails() method started**");
		List<DcCaseChildDetails> listChildModels = dcService.saveChildDetails(childDetail);
		initFormChild(model);
		if (!listChildModels.isEmpty()) {
			logger.debug("**DcController:: childDetails() method ended**");
			logger.info("**child Details saved Successfully**");
			model.addAttribute(AppConstants.SUCCESS, appProperties.getProperties().get(AppConstants.CASE_CHILD_SAVING_SUCCESS_MSG));
			model.addAttribute("listChildModels", listChildModels);
			DcCaseChildDetails childDetails=new DcCaseChildDetails();
			childDetails.setCaseId(childDetail.getCaseId());
			childDetails.setIndivName(childDetail.getIndivName());
			model.addAttribute("childDetails", childDetails);
			return "childDetails";
		}
		model.addAttribute(AppConstants.FAILURE, appProperties.getProperties().get(AppConstants.CASE_CHILD_SAVING_ERR_MSG));
		model.addAttribute("listChildModels", listChildModels);
		DcCaseChildDetails childDetails=new DcCaseChildDetails();
		childDetails.setCaseId(childDetail.getCaseId());
		childDetails.setIndivName(childDetail.getIndivName());
		model.addAttribute("childDetails", childDetails);
		logger.debug("**DcController:: childDetails() method ended**");
		logger.info("**Child Details saving has failed**");
		return "childDetails";
	}// method

	/**
	 * This method is used for editing child details
	 * 
	 * @param childId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/editChildDetails", method = RequestMethod.GET)
	public String editChildDetails(@RequestParam(name="childId")Integer childId, Model model) {
		logger.debug("**DcController:: editChildDetails() method started**");
		Map<String,Object> resultMap = dcService.updateChildDetails(childId);
		initFormChild(model);
		if (!resultMap.isEmpty()) {
			model.addAttribute("listChildModels", resultMap.get("listModel"));
			model.addAttribute("childDetails", resultMap.get("childModel"));
			logger.debug("**DcController:: editChildDetails() method ended**");
			return "childDetails";
		}
		model.addAttribute(AppConstants.FAILURE, appProperties.getProperties().get(AppConstants.CASE_CHILD_RETREIVING_ERR_MSG));
		List<DcCaseChildDetails> listChildModels =new ArrayList<>();
		model.addAttribute("listChildModels", listChildModels);
		DcCaseChildDetails childDetails=new DcCaseChildDetails();
		model.addAttribute("childDetails", childDetails);
		logger.debug("**DcController:: editChildDetails() method ended**");
		logger.info("**Child Details editing failed**");
		return "childDetails";
	}// method
	
	/**
	 * This method is used for editing child details
	 * 
	 * @param childId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteChildDetails", method = RequestMethod.GET)
	public String deleteChildDetails(@RequestParam(name="childId")Integer childId, @RequestParam(name="caseId")Integer caseId, @RequestParam(name="indivName")String indivName, Model model) {
		logger.debug("**DcController:: editChildDetails() method started**");
		List<DcCaseChildDetails> listModel = dcService.deleteChildDetails(childId, caseId);
		initFormChild(model);
			model.addAttribute(AppConstants.SUCCESS, appProperties.getProperties().get(AppConstants.CASE_CHILD_DELETE_SUCCESS_MSG));
			model.addAttribute("listChildModels", listModel);
			DcCaseChildDetails childDetails=new DcCaseChildDetails();
			childDetails.setCaseId(caseId);
			childDetails.setIndivName(indivName);
			model.addAttribute("childDetails", childDetails);
			logger.debug("**DcController:: editChildDetails() method ended**");
			return "childDetails";
	}// method
	
	
	/**
	 * This method is used for saving education details
	 * 
	 * @param incomeDetails
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/educationDetails", method = RequestMethod.POST)
	public String eduDetails(@ModelAttribute("educationDetails") DcCaseEducationDetails eduDetails, Model model) {
		logger.debug("**DcController:: eduDetails() method started**");
		boolean isSaved = dcService.saveEducationDetails(eduDetails);
		initFormIncome(model);
		if (isSaved) {
			logger.debug("**DcController:: eduDetails() method ended**");
			logger.info("**education Details saved Successfully**");
			logger.info("**Redirecting to Determine Elegibility Screen**");
			return "determineEligibility";
		}
		model.addAttribute(AppConstants.FAILURE,
				appProperties.getProperties().get(AppConstants.CASE_EDUCATION_SAVING_ERR_MSG));
		logger.debug("**DcController:: eduDetails() method ended**");
		logger.info("**education Details saving has failed**");
		return "educationDetails";
	}// method
	
	/**
	 * This method is used for determine Eligibility
	 * 
	 * @param incomeDetails
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/determineEligibility", method = RequestMethod.GET)
	public String determineEligibility(Model model) {
		logger.debug("**DcController:: determineEligibility() method started**");
		
		
		
		logger.debug("**DcController:: determineEligibility() method ended**");
		return "determineEligibility";
	}// method

}
