package ai.acintyo.sales.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import ai.acintyo.sales.api.ApiResponse;
import ai.acintyo.sales.binding.MrVisitForm;
import ai.acintyo.sales.service.IMrVisitService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sales-power")
@Slf4j
@Validated
public class MrVisitController {

	
	@Autowired
	private IMrVisitService mrVisitService;
	
	@PostMapping("/save-mrVisit")
	public ResponseEntity<?> saveMrVisit(@RequestBody @Valid MrVisitForm visitForm)
	{
	    log.info("ai.acintyo.sales.controller.MrVisitController::Attempting to save the MrVisit");
		return ResponseEntity.ok(new ApiResponse<>(true,"MrVisit Data has been saved successfully ",mrVisitService.registerMrVisit(visitForm)));
	}
	
	@GetMapping("/get-all-mrVisit")
	public ResponseEntity<?> getAllMrVisitData(@PageableDefault Pageable pageable)
	{
		  log.info("ai.acintyo.sales.controller.MrVisitController::Attempting to get all the MrVisit Details");
			
		return ResponseEntity.ok(new ApiResponse<>(true,"MrVisit Data has been fetched successfully",mrVisitService.fetchAllMrVisit(pageable)));
	}
	@GetMapping("/get-mrVisit/{id}")
	public ResponseEntity<?> getMrVisit(@PathVariable(name="id") String id)
	{
		  log.info("ai.acintyo.sales.controller.MrVisitController::Attempting to get the MrVisit using id");
			
		return ResponseEntity.ok(new ApiResponse<>(true,"MrVisit Data has been fetched successfully",mrVisitService.fetchMrVisitById(id)));
	}
	@PutMapping("/update-mrVisit/{id}")
	public ResponseEntity<?> updateMrVisit(@PathVariable(name="id") String id,@RequestBody @Valid MrVisitForm visitForm)
	{
		  log.info("ai.acintyo.sales.controller.MrVisitController::Attempting to update the MrVisit");
			
		return ResponseEntity.ok(new ApiResponse<>(true,"MrVisit Data has been updated successfully",mrVisitService.updateMrVisitById(id, visitForm)));
	}
}
