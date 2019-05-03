package com.usa.ri.gov.ies.ar.service;

import java.util.List;
import java.util.Map;

import com.usa.ri.gov.ies.ar.model.ApplicationMaster;

public interface ArService {

	public Map<Boolean, String> registerApplicant(ApplicationMaster appModel);

	public List<ApplicationMaster> findAllApplicants();

	boolean editApplication(ApplicationMaster appModel);

	public ApplicationMaster findByAppId(String appId);

}
