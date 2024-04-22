package com.acintyo.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acintyo.auth.dto.MrVisitForm;
import com.acintyo.auth.response.ApiResponse;
import com.acintyo.auth.service.IMrVisitService;

import jakarta.validation.Valid;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sales-power")
@Slf4j
@Validated
@Setter
@ConfigurationProperties(prefix = "sales.mrvisit")
public class MrVisitController {

	private String registeredSuccessfully;
	private String getAllDataMessage;
	private String getDataMessage;
	private String updateMessage;

	@Autowired
	private IMrVisitService mrVisitService;

	@PostMapping("/save-mrVisit")
	public ResponseEntity<?> saveMrVisit(@RequestBody @Valid MrVisitForm visitForm) {
		log.info("Attempting to save the MrVisit");
		return ResponseEntity
				.ok(new ApiResponse<>(true, registeredSuccessfully, mrVisitService.registerMrVisit(visitForm)));
	}

	@GetMapping("/get-all-mrVisit")
	public ResponseEntity<?> getAllMrVisitData(@PageableDefault Pageable pageable) {
		log.info("Attempting to get all the MrVisit Details");

			return ResponseEntity.ok(new ApiResponse<>(true, getAllDataMessage, mrVisitService.fetchAllMrVisit(pageable)));
	}

	@GetMapping("/get-mrVisit/{id}")
	public ResponseEntity<?> getMrVisit(@PathVariable(name = "id") String id) {
		log.info("Attempting to get the MrVisit using id");

		return ResponseEntity.ok(new ApiResponse<>(true, getDataMessage, mrVisitService.fetchMrVisitById(id)));
	}

	@PutMapping("/update-mrVisit/{id}")
	public ResponseEntity<?> updateMrVisit(@PathVariable(name = "id") String id,
			@RequestBody @Valid MrVisitForm visitForm) {
		log.info("Attempting to update the MrVisit");

		return ResponseEntity
				.ok(new ApiResponse<>(true, updateMessage, mrVisitService.updateMrVisitById(id, visitForm)));
	}
	
	@GetMapping("/get-mrVisit/mrIdBy/{mrId}/retailerIdBy/{retailerId}")
	public ResponseEntity<?> getMrVisitByMrIdRetailerId(@PathVariable String mrId,@PathVariable String retailerId)
	{
		return ResponseEntity.ok(new ApiResponse<>(true,getDataMessage,mrVisitService.findMrVisitDataByMrIdAndRetailerId(mrId, retailerId)));
	}
	//http://devapi.thelocal.co.in/b2b/api-oms/user/AL-R202401-170/orders?startDate=2024-04-20&endDate=2024-04-20&page=0&size=10"
	@GetMapping("/get-mrVisit-dates/mrIdBy/{mrId}/retailerIdBy/{retailerId}")
	public ResponseEntity<?> getMrVisitDetailsBetweenTwoDates(@PathVariable String mrId,@PathVariable String retailerId ,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate)
	{
		return ResponseEntity.ok(new ApiResponse<>(true,getDataMessage,mrVisitService.fetchMrVisitDataBetweenTwoDates(mrId, retailerId, startDate, endDate, 0, 0)));
	}
	
	@GetMapping("/get-mrVisit-toplist/mrIdBy/{mrId}/retailerIdBy/{retailerId}")
	public ResponseEntity<?> getMrVisitDetailsTopList(@PathVariable String mrId,@PathVariable String retailerId)
	{
		return ResponseEntity.ok(new ApiResponse<>(true,getDataMessage,mrVisitService.fetchMrVisitDataTopList(mrId, retailerId)));
	}
}
