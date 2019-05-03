package com.usa.ri.gov.ies.ed.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usa.ri.gov.ies.ed.entity.EligibilityDetailEntity;
import com.usa.ri.gov.ies.ed.model.EligibilityDetailModel;
import com.usa.ri.gov.ies.ed.repository.EligibilityDetailsRepository;

@Service("edDetailService")
public class EligibilityDetailServiceImpl implements EligibilityDetailService {

	@Autowired
	private EligibilityDetailsRepository eligRepository;

	@Override
	public EligibilityDetailModel findByCaseNum(Long caseNum) {
		EligibilityDetailEntity entity = eligRepository.findByCaseNum(caseNum);
		EligibilityDetailModel model = null;
		if (entity != null) {
			model = new EligibilityDetailModel();
			BeanUtils.copyProperties(entity, model);
		}

		return model;
	}

}
