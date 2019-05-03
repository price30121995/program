package com.usa.ri.gov.ies.co.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.usa.ri.gov.ies.co.batches.main.CoPlanStmtGenDlyBatch;

@RestController
public class CoController {

	@Autowired
	private CoPlanStmtGenDlyBatch batch;

	@GetMapping("/coBatchTest/{tb}/{ci}")
	public String test(@PathVariable("tb") String tb, @PathVariable("ci") String ci) {
		batch.init(Integer.parseInt(tb), Integer.parseInt(ci));
		return "Testing";
	}
}
