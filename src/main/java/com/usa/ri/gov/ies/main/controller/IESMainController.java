package com.usa.ri.gov.ies.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IESMainController {
	private static Logger logger = LoggerFactory.getLogger(IESMainController.class);
	/**
	 * This method is used for displaying IES home
	 * @return String
	 */
	@RequestMapping("/")
	public String displayHome() {
		logger.info("****IES home is launched  successfully****");
		return "index";
	}
}
