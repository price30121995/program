package com.usa.ri.gov.ies.ar.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.usa.ri.gov.ies.ar.model.ApplicationMaster;
import com.usa.ri.gov.ies.ar.service.ArService;
import com.usa.ri.gov.ies.constants.AppConstants;
import com.usa.ri.gov.ies.properties.AppProperties;

/**
 * This class is used to Handle Admin module related functionalities
 * 
 * @author admin
 *
 */
@Controller
public class ArController {

	private static Logger logger = LoggerFactory.getLogger(ArController.class);

	@Autowired(required = true)
	private ArService arService;

	@Autowired
	private AppProperties appProperties;
	
	

	/**
	 * This method is used to Display Applicant reg form
	 * 
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/appReg", method = RequestMethod.GET)
	public String appRegForm(Model model) {
		logger.debug("ArController::appRegForm() started");
		// Creating empty model object
		ApplicationMaster appModel = new ApplicationMaster();

		// add cwModel object to Model scope
		model.addAttribute("appModel", appModel);

		initForm(model);

		logger.debug("ArController::appRegForm() ended");
		logger.info("Applicant Reg Form loaded Successfully");

		return "appReg";
	}

	/**
	 * This method is used to register applicant with given values
	 * 
	 * @param appAccModel
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/appReg", method = RequestMethod.POST)
	public String appReg(@ModelAttribute("appModel") ApplicationMaster appModel, Model model) {
		try {
			logger.debug("ArController::user account creation started");

			// call Service layer method
			Map<Boolean, String> map = arService.registerApplicant(appModel);

			//Map<String, String> map = appProperties.getProperties();
			if (!map.isEmpty()) {
				// Display success message
				if(map.containsKey(true))
					model.addAttribute(AppConstants.SUCCESS, map.get(true));
				else 
					// Display failure message
					model.addAttribute(AppConstants.FAILURE, map.get(false));
			}
			initForm(model);
			logger.debug("ArController::user account creation ended");
			logger.info("ArController::User Account creation completed successfully");
		} catch (Exception e) {
			logger.error("User Account Creation Failed :: " + e.getMessage());
		}
		return "appReg";
	}

	/**
	 * This method is used to load genders for application
	 * 
	 * @param model
	 */
	private void initForm(Model model) {
		logger.debug("**ArController::initForm() method is executed**");
		List<String> gendersList = new ArrayList<>();
		gendersList.add("Male");
		gendersList.add("Fe-Male");
		model.addAttribute("gendersList", gendersList);
		logger.debug("**initForm() method is executed**");
		logger.debug("**genders list has set to Model Attribute**");
	}

	/**
	 * This method is used to display all app accounts in table
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/viewApplicant")
	public String viewApplicants(Model model) {
		logger.debug("viewApplicants() method started");

		// calling service layer method
		List<ApplicationMaster> appModel = arService.findAllApplicants();

		// store accounts in model scope
		model.addAttribute(AppConstants.APP_MODEL, appModel);

		logger.debug("viewApplicants() method ended");
		return "viewApplicant"; // view name
	}

	
	@RequestMapping(value = "/editApp")
	public String editApplicationForm(HttpServletRequest req, Model model) {

		String appId = req.getParameter("appId");

		ApplicationMaster appModel = arService.findByAppId(appId);

		model.addAttribute(AppConstants.APP_MODEL, appModel);
		initForm(model);
		return "editApp";
	}
	/**
	 * This method is used for Updating Account Details
	 * 
	 * @param planDTLS
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/editApp", method = RequestMethod.POST)
	public String editApplication(@ModelAttribute("appModel") ApplicationMaster appModel, Model model) {
		logger.debug("ArController:: editApplication() method started");
		boolean status = false;
		// Invoke Service method
		status = arService.editApplication(appModel);

		if (status) {
			// get SuccessMessage value
			String successMsg = appProperties.getProperties().get(AppConstants.UPDATE_APP_SUCCESS_MSG);
			model.addAttribute(AppConstants.SUCCESS, successMsg);
		} else {
			// get Failure Message
			String failureMsg = appProperties.getProperties().get(AppConstants.UPDATE_APP_ERR_MSG);
			model.addAttribute(AppConstants.FAILURE, failureMsg);
		}
		//add roles Details
		initForm(model);
		logger.debug("AdminController:: editAccount() method started");
		logger.info("editAccount  Method completed Successfully");

		return "editApp";
	}


}
