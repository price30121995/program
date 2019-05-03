package com.usa.ri.gov.ies.ar.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.usa.ri.gov.ies.ar.client.model.SsnProfile;
import com.usa.ri.gov.ies.ar.entity.ApplicationMasterEntity;
import com.usa.ri.gov.ies.ar.model.ApplicationMaster;
import com.usa.ri.gov.ies.ar.repository.ApplicationMasterRepository;
import com.usa.ri.gov.ies.constants.AppConstants;
import com.usa.ri.gov.ies.properties.AppProperties;

/**
 * This class is used to handler all business operations related to admin module
 * 
 * @author admin
 *
 */
@Service("arService")
public class ArServiceImpl implements ArService {

	private static Logger logger = LoggerFactory.getLogger(ArServiceImpl.class);

	@Autowired(required = true)
	private ApplicationMasterRepository appRepository;

	@Autowired(required = true)
	private AppProperties appProperties;

	/**
	 * This method is used for registering Applicant Here we are using Restful
	 * service to validate SSN
	 * 
	 * @param appModel
	 * @return Map<Boolean, String>
	 */
	@Override
	public Map<Boolean, String> registerApplicant(ApplicationMaster appModel) {
		logger.debug("***ArService::registerApplicant() method executed***");
		RestTemplate template = new RestTemplate();
		Map<Boolean, String> map = new HashMap<>();
		ApplicationMasterEntity entity = new ApplicationMasterEntity();
		try {
			// call findBySSN() method
			entity = appRepository.findBySsn(appModel.getSsn());
			if (entity == null) {
				entity = new ApplicationMasterEntity();
				URI uri = new URI("http://localhost:2020/SSAWeb/validateSSN/" + appModel.getSsn());
				ResponseEntity<SsnProfile> responseEntity = template.getForEntity(uri, SsnProfile.class);

				int statusCode = responseEntity.getStatusCodeValue();
				SsnProfile profile = responseEntity.getBody();
				System.out.println(statusCode);
				/*
				 * if (statusCode == 400) { map.put(false, "Invalid SSN provided!!"); return
				 * map; }
				 */
				if (statusCode == 200) {
					if (profile.getState().equalsIgnoreCase("Rhode Island")) {
						// covert Model to Entity
						BeanUtils.copyProperties(appModel, entity);

						entity = appRepository.save(entity);
						if (entity.getAppId() != null) {
							map.put(true, appProperties.getProperties().get(AppConstants.APPLICANT_REG_SUCCESS)
									+ entity.getAppId());
							logger.debug("***ArService::registerApplicant() method ended***");
							logger.info("***ArService::Registration Successful***");
							return map;
						}
					} else {
						map.put(false, appProperties.getProperties().get(AppConstants.UN_AUTHORISED_APPLICANT));
						logger.debug("***ArService::registerApplicant() method ended***");
						logger.info("***ArService::Applicant is UnAuthorised to apply***");
						return map;
					}
				}
			} else {
				map.put(true,
						appProperties.getProperties().get(AppConstants.APPLICANT_ALREADY_REG) + entity.getAppId());
				logger.debug("***ArService::registerApplicant() method ended***");
				logger.info("***ArService::Applicant is Already Registered***");
				return map;
			}
		} catch (Exception e) {
			if(e instanceof HttpClientErrorException.BadRequest) {
			logger.error("***ArService::registerApplicant() method error:", e);
			map.put(false, appProperties.getProperties().get(AppConstants.INVALID_SSN_MSG));
			return map;
			}
		}
		logger.debug("***ArService::registerApplicant() method ended with error***");
		map.put(false, appProperties.getProperties().get(AppConstants.INTERNAL_PROBLEM));
		return map;
	}

	/**
	 * This method is used to return All accounts details
	 */
	@Override
	public List<ApplicationMaster> findAllApplicants() {
		logger.debug("findAllAppAccounts() method started");
		List<ApplicationMaster> models = new ArrayList<ApplicationMaster>();
		try {
			// call Repository method
			List<ApplicationMasterEntity> entities = appRepository.findAll();

			if (entities.isEmpty()) {
				logger.warn("***No Accounts found in Application****");
			} else {
				// convert Entities to models
				for (ApplicationMasterEntity entity : entities) {
					ApplicationMaster model = new ApplicationMaster();
					BeanUtils.copyProperties(entity, model);
					models.add(model);
				}
				logger.info("All Applicants details loaded successfully");
			}
		} catch (Exception e) {
			logger.error("Exception occured in findAllApplicants()::", e);
		}
		logger.debug("findAllApplicants() method ended");
		return models;
	}// method

	/**
	 * This method is used edit application details
	 *
	 * @Param accModel
	 * @return boolean
	 */
	@Override
	public boolean editApplication(ApplicationMaster appModel) {
		logger.debug(" editAppAccount() started");
		// create AppAccount Entity object
		ApplicationMasterEntity entity = new ApplicationMasterEntity();

		// convert model object to entity
		BeanUtils.copyProperties(appModel, entity);

		// call repository method
		entity = appRepository.save(entity);

		if (entity.getAppId() != null)
			return true;
		logger.debug("editAppAccount() ended");
		logger.info("editAppAccount completed Successfull");
		return false;
	}

	@Override
	public ApplicationMaster findByAppId(String accId) {
		ApplicationMasterEntity entity = appRepository.findById(accId).get();

		ApplicationMaster model = new ApplicationMaster();

		BeanUtils.copyProperties(entity, model);

		return model;
	}
}// class
