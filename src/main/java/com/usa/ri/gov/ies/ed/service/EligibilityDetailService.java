package com.usa.ri.gov.ies.ed.service;

import org.springframework.stereotype.Service;

import com.usa.ri.gov.ies.ed.model.EligibilityDetailModel;

@Service
public interface EligibilityDetailService {

	public EligibilityDetailModel findByCaseNum(Long caseNum);

}
