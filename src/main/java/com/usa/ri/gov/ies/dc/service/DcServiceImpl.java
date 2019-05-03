package com.usa.ri.gov.ies.dc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usa.ri.gov.ies.admin.entity.PlanDetailsEntity;
import com.usa.ri.gov.ies.admin.repository.PlanDetailsRepository;
import com.usa.ri.gov.ies.ar.entity.ApplicationMasterEntity;
import com.usa.ri.gov.ies.ar.model.ApplicationMaster;
import com.usa.ri.gov.ies.ar.repository.ApplicationMasterRepository;
import com.usa.ri.gov.ies.dc.entity.DcCaseChildDetailsEntity;
import com.usa.ri.gov.ies.dc.entity.DcCaseEducationDetailsEntity;
import com.usa.ri.gov.ies.dc.entity.DcCaseIncomeDetailsEntity;
import com.usa.ri.gov.ies.dc.entity.DcCasePlanEntity;
import com.usa.ri.gov.ies.dc.entity.DcCasesEntity;
import com.usa.ri.gov.ies.dc.model.DcCaseChildDetails;
import com.usa.ri.gov.ies.dc.model.DcCaseEducationDetails;
import com.usa.ri.gov.ies.dc.model.DcCaseIncomeDetails;
import com.usa.ri.gov.ies.dc.model.DcCasePlan;
import com.usa.ri.gov.ies.dc.repository.DcCaseChildDetailsRepository;
import com.usa.ri.gov.ies.dc.repository.DcCaseEducatoinDetailsRepository;
import com.usa.ri.gov.ies.dc.repository.DcCaseIncomeDetailsRepository;
import com.usa.ri.gov.ies.dc.repository.DcCasePlanRepository;
import com.usa.ri.gov.ies.dc.repository.DcCasesRepository;

/**
 * This class is used to handler all business operations related to admin module
 * 
 * @author admin
 *
 */
@Service("dcService")
public class DcServiceImpl implements DcService {

	private static Logger logger = LoggerFactory.getLogger(DcServiceImpl.class);

	@Autowired(required = true)
	private ApplicationMasterRepository appRepository;

	@Autowired(required = true)
	private PlanDetailsRepository pdRepository;

	@Autowired(required = true)
	private DcCasesRepository cmRepository;

	@Autowired(required = true)
	private DcCasePlanRepository planRepository;

	@Autowired(required = true)
	private DcCaseIncomeDetailsRepository incomeDetailsRepository;

	@Autowired(required = true)
	private DcCaseEducatoinDetailsRepository eduDetailsRepository;

	@Autowired(required = true)
	private DcCaseChildDetailsRepository childDetailsRepository;

	/**
	 * This method is used for searching all appId
	 */
	@Override
	public String[] searchAllAppId() {
		logger.debug("**DcService::searchAllAppId() method called**");
		List<ApplicationMasterEntity> listEntity = appRepository.findAll();
		String[] appIds = new String[listEntity.size()];
		int i = 0;
		for (ApplicationMasterEntity entity : listEntity) {
			appIds[i++] = entity.getAppId();
		}
		logger.debug("**DcService::searchAllAppId() method ended**");
		return appIds;
	}

	/**
	 * This method is used for searching by AppID
	 */
	@Override
	public ApplicationMaster findByAppId(String appId) {
		logger.debug("**DcService::findByAppId() method called**");
		ApplicationMaster appModel = new ApplicationMaster();
		try {
			ApplicationMasterEntity entity = appRepository.getOne(appId);

			BeanUtils.copyProperties(entity, appModel);
			logger.debug("**DcService::findByAppId() method ended**");
		} catch (Exception e) {
			appModel = new ApplicationMaster();
			logger.error("**DcService::findByAppId() method error**", e);
		}
		return appModel;
	}

	/**
	 * This method is used for creating case number
	 */
	@Override
	public Integer createCase(String appId) {
		logger.debug("**DcService::createCase() method called**");
		ApplicationMaster appModel = findByAppId(appId);
		DcCasesEntity entity = new DcCasesEntity();
		BeanUtils.copyProperties(appModel, entity);
		entity.setUpdateDate(null);
		entity.setCreateDate(null);
		entity = cmRepository.save(entity);
		logger.debug("**DcService::createCase() method ended**");
		return entity.getCaseId();
	}

	/**
	 * This method is used for fetching plans from plandtls table
	 */
	@Override
	public Map<String, String> fetchAllPlans() {
		logger.debug("**DcService::fetchAllPlans() method called**");
		List<PlanDetailsEntity> planListEntity = pdRepository.findAll();
		Map<String, String> planList = new HashMap<>();
		planListEntity.forEach(entity -> {
			if (entity.getActiveSw().equalsIgnoreCase("Y"))
				planList.put(entity.getPlanId(), entity.getPlanName());
		});
		logger.debug("**DcService::fetchAllPlans() method ended**");
		return planList;
	}

	/**
	 * This method is used to save select plan details
	 */
	@Override
	public boolean saveSelectedPlan(DcCasePlan planModel) {
		logger.debug("**DcService::saveSelectedPlan() method called**");
		PlanDetailsEntity pdEntity = pdRepository.getOne(planModel.getPlanId());
		BeanUtils.copyProperties(pdEntity, planModel);
		DcCasePlanEntity entity = new DcCasePlanEntity();

		BeanUtils.copyProperties(planModel, entity);
		try {
			entity = planRepository.save(entity);
			logger.debug("**DcService::saveSelectedPlan() method ended with success**");
			return true;
		} catch (Exception e) {
			logger.error("**DcService::saveSelectedPlan() method exception:", e);
		}
		logger.debug("**DcService::saveSelectedPlan() method ended with failure**");
		return false;
	}

	/**
	 * This method is used for searching by case id
	 */
	@Override
	public DcCasePlan findCaseByCaseId(Integer caseId) {
		logger.debug("**DcService::findCaseByCaseId() method called**");
		DcCasesEntity cmEntity = cmRepository.getOne(caseId);
		DcCasePlan planModel = new DcCasePlan();
		BeanUtils.copyProperties(cmEntity, planModel);
		logger.debug("**DcService::findCaseByCaseId() method ended**");
		return planModel;
	}

	/**
	 * This method is used for saving income details
	 */
	@Override
	public boolean saveIncomeDetails(DcCaseIncomeDetails incomeDetails) {
		logger.debug("**DcService::saveIncomeDetails() method called**");
		DcCaseIncomeDetailsEntity entity = new DcCaseIncomeDetailsEntity();
		BeanUtils.copyProperties(incomeDetails, entity);
		try {
			entity = incomeDetailsRepository.save(entity);
			logger.debug("**DcService::saveIncomeDetails() method ended with success**");
			return true;
		} catch (Exception e) {
			logger.error("**DcService::saveIncomeDetails() method exception:", e);
		}
		logger.debug("**DcService::saveIncomeDetails() method ended with failure**");
		return false;
	}

	@Override
	public boolean saveEducationDetails(DcCaseEducationDetails eduDetails) {
		logger.debug("**DcService::saveEducationDetails() method called**");
		DcCaseEducationDetailsEntity entity = new DcCaseEducationDetailsEntity();
		BeanUtils.copyProperties(eduDetails, entity);
		try {
			entity = eduDetailsRepository.save(entity);
			logger.debug("**DcService::saveEducationDetails() method ended with success**");
			return true;
		} catch (Exception e) {
			logger.error("**DcService::saveEducationDetails() method exception:", e);
		}
		logger.debug("**DcService::saveEducationDetails() method ended with failure**");
		return false;
	}

	/**
	 * This method is used for saving child details
	 */
	@Override
	public List<DcCaseChildDetails> saveChildDetails(DcCaseChildDetails childDetails) {
		logger.debug("**DcService::saveEducationDetails() method called**");
		DcCaseChildDetailsEntity entity = new DcCaseChildDetailsEntity();
		List<DcCaseChildDetails> listModel = new ArrayList<>();
		BeanUtils.copyProperties(childDetails, entity);
		List<DcCaseChildDetailsEntity> listEntity = childDetailsRepository.findByCaseId(childDetails.getCaseId());

		try {
			entity = childDetailsRepository.save(entity);
			logger.info("***DcService:: child details saved into table***");
			listEntity = childDetailsRepository.findByCaseId(childDetails.getCaseId());
			listEntity.forEach(entityChild -> {
				DcCaseChildDetails dcChildModel = new DcCaseChildDetails();
				BeanUtils.copyProperties(entityChild, dcChildModel);
				listModel.add(dcChildModel);
			});
			logger.debug("**DcService::saveEducationDetails() method ended with success**");
			return listModel;
		} catch (Exception e) {
			logger.error("**DcService::saveEducationDetails() method exception:", e);
		}
		logger.debug("**DcService::saveEducationDetails() method ended with failure**");
		return listModel;
	}

	/**
	 * This method is used for updatingChildDetails
	 */
	@Override
	public Map<String, Object> updateChildDetails(Integer childId) {
		Map<String, Object> mapResult=new HashMap<>();
		try {
			logger.debug("**DcService::updateChildDetails() method called**");
			//getting child details to update 
			DcCaseChildDetailsEntity entity = childDetailsRepository.findById(childId).get();
			DcCaseChildDetails childModel=new DcCaseChildDetails();
			BeanUtils.copyProperties(entity, childModel);
			mapResult.put("childModel", childModel);
			//getting all child records related to provided caseId
			List<DcCaseChildDetails> listModel = new ArrayList<>();
			List<DcCaseChildDetailsEntity> listEntity = childDetailsRepository.findByCaseId(childModel.getCaseId());
			listEntity.forEach(entityChild -> {
				DcCaseChildDetails dcChildModel = new DcCaseChildDetails();
				BeanUtils.copyProperties(entityChild, dcChildModel);
				listModel.add(dcChildModel);
			});
			mapResult.put("listModel", listModel);
			logger.debug("**DcService::updateChildDetails() method ended with success**");
			return mapResult;
		} catch (Exception e) {
			logger.error("**DcService::updateChildDetails() method exception:", e);
		}
		logger.debug("**DcService::updateChildDetails() method ended with failure**");
		return mapResult;
	}

	@Override
	public List<DcCaseChildDetails> deleteChildDetails(Integer childId, Integer caseId) {
		List<DcCaseChildDetails> listModel = new ArrayList<>();
		try {
			logger.debug("**DcService::deleteChildDetails() method called**");
			//deleting record of passed childId
			childDetailsRepository.deleteById(childId);
			
			//getting all child records based on caseId
			List<DcCaseChildDetailsEntity> listEntity = childDetailsRepository.findByCaseId(caseId);
			listEntity.forEach(entityChild -> {
				DcCaseChildDetails dcChildModel = new DcCaseChildDetails();
				BeanUtils.copyProperties(entityChild, dcChildModel);
				listModel.add(dcChildModel);
			});
			logger.debug("**DcService::deleteChildDetails() method ended with success**");
			return listModel;
		} catch (Exception e) {
			logger.error("**DcService::deleteChildDetails() method exception:", e);
		}
		logger.debug("**DcService::deleteChildDetails() method ended with failure**");
		return listModel;
	}
	
	/** 
	 * this method is used for retrieving records based on ChilId
	 */
	@Override
	public DcCaseChildDetails findByChildId(String childId) {
		Optional<DcCaseChildDetailsEntity> optEntity = childDetailsRepository.findById(childId);
		DcCaseChildDetailsEntity entity=null;
		if(optEntity.isPresent())
			entity=optEntity.get();
		DcCaseChildDetails childModel=new DcCaseChildDetails();
		BeanUtils.copyProperties(entity, childModel);
		return childModel;
	}
}// class
