package com.usa.ri.gov.ies.co.batches.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usa.ri.gov.ies.co.batches.entity.CoBatchRunDetailsEntity;
import com.usa.ri.gov.ies.co.batches.entity.CoBatchSummaryEntity;
import com.usa.ri.gov.ies.co.batches.entity.CoPdfEntity;
import com.usa.ri.gov.ies.co.batches.entity.CoTriggersEntity;
import com.usa.ri.gov.ies.co.batches.model.CoBatchRunDetailsModel;
import com.usa.ri.gov.ies.co.batches.model.CoBatchSummaryModel;
import com.usa.ri.gov.ies.co.batches.model.CoPdfModel;
import com.usa.ri.gov.ies.co.batches.model.CoTriggersModel;
import com.usa.ri.gov.ies.co.batches.repository.CoBatchRunDetailsRepository;
import com.usa.ri.gov.ies.co.batches.repository.CoBatchSummaryRepository;
import com.usa.ri.gov.ies.co.batches.repository.CoPdfRepository;
import com.usa.ri.gov.ies.co.batches.repository.CoTriggersDao;
import com.usa.ri.gov.ies.co.batches.repository.CoTriggersRepository;

@Service("coBatchService")
public class CoBatchServiceImpl implements CoBatchService {

	@Autowired
	private CoBatchRunDetailsRepository coBatchRunDetailRepo;

	@Autowired
	private CoTriggersRepository coTrgRepository;
	
	@Autowired
	private CoTriggersDao coTrgDao;

	@Autowired
	private CoBatchSummaryRepository coBatchSummaryRepo;

	@Autowired
	private CoPdfRepository coPdfRepository;

	@Override
	public CoBatchRunDetailsModel insertBatchRunDetails(CoBatchRunDetailsModel model) {
		CoBatchRunDetailsEntity entity = new CoBatchRunDetailsEntity();
		BeanUtils.copyProperties(model, entity);
		entity.setStartDate(new java.util.Date());
		CoBatchRunDetailsEntity savedEntity = coBatchRunDetailRepo.save(entity);

		// setting pk value to model
		model.setRunSeq(entity.getRunSeq());
		return model;
	}

	@Override
	public CoBatchRunDetailsModel findByRunSeqNum(Integer runSeqNum) {
		CoBatchRunDetailsEntity entity = coBatchRunDetailRepo.findById(runSeqNum).get();
		CoBatchRunDetailsModel model = new CoBatchRunDetailsModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	@Override
	public List<CoTriggersModel> findPendingTriggers(Integer totalBuckets, Integer instanceNum) {

		List<CoTriggersEntity> entities = coTrgDao.findPendTrgrsWithOraHash("p", totalBuckets, instanceNum);

		List<CoTriggersModel> models = new ArrayList();

		for (CoTriggersEntity entity : entities) {
			CoTriggersModel model = new CoTriggersModel();
			BeanUtils.copyProperties(entity, model);
			models.add(model);
		}

		return models;
	}

	@Override
	public CoPdfModel savePdf(CoPdfModel model) {
		CoPdfEntity entity = null;
		Integer pdfId = null;
		entity = new CoPdfEntity();
		// convert model to entity
		BeanUtils.copyProperties(model, entity);
		// call repository method
		pdfId = coPdfRepository.save(entity).getCoPdfId();
		model.setCoPdfId(pdfId);
		return model;
	}

	@Override
	public boolean updatePendingTrigger(CoTriggersModel model) {
		CoTriggersEntity trgEntity = coTrgRepository.findById(model.getTriggerId()).get();
		trgEntity.setUpdatedDate(new java.util.Date());
		trgEntity.setTriggerStatus(model.getTriggerStatus());
		coTrgRepository.save(trgEntity);
		return true;
	}

	@Override
	public CoBatchRunDetailsModel updateBatchRunDetails(CoBatchRunDetailsModel model) {
		CoBatchRunDetailsEntity entity = new CoBatchRunDetailsEntity();
		BeanUtils.copyProperties(model, entity);
		coBatchRunDetailRepo.save(entity);
		return model;
	}

	@Override
	public CoBatchSummaryModel saveBatchSummary(CoBatchSummaryModel model) {
		CoBatchSummaryEntity entity = new CoBatchSummaryEntity();
		BeanUtils.copyProperties(model, entity);
		entity = coBatchSummaryRepo.save(entity);
		model.setSummaryId(entity.getSummaryId());
		return model;
	}

}
