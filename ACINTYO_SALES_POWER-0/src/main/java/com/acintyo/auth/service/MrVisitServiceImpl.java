package com.acintyo.auth.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.acintyo.auth.dto.MrVisitForm;
import com.acintyo.auth.exception.MrVisitsNotFoundException;
import com.acintyo.auth.model.MrVisit;
import com.acintyo.auth.repository.IMrVisitRepo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
@ConfigurationProperties(prefix = "sales.mrvisit")
public class MrVisitServiceImpl implements IMrVisitService {

	private String visitDataNotFound;

	@Autowired
	private IMrVisitRepo mrVisitRepo;

	@Override
	public MrVisit registerMrVisit(MrVisitForm visitForm) {

		log.info(" registerMrVisit method is executed");

		MrVisit mrVisit = new MrVisit();
		mrVisit.setSupplierId(visitForm.getSupplierId());
		mrVisit.setRetailerId(visitForm.getRetailerId());
		mrVisit.setMrId(visitForm.getMrId());
		mrVisit.setAsmId(visitForm.getAsmId());
		mrVisit.setVisitTime(visitForm.getVisitTime());
		mrVisit.setVisitLocationLatitude(visitForm.getVisitLocationLatitude());
		mrVisit.setVisitLocationLongitude(visitForm.getVisitLocationLongitude());
		mrVisit.setInsertedOn(LocalDateTime.now());
		return mrVisitRepo.save(mrVisit);
	}

	@Override
	public Page<MrVisit> fetchAllMrVisit(Pageable pageable) {

		log.info("fetchAllMrVisit method is executed");

		Page<MrVisit> page = mrVisitRepo.findAll(pageable);

		log.debug("Page object created with size: {}", page.getSize());
		if (page.isEmpty()) {
			log.info("No MrVisit data found for the provided pageable");
			throw new MrVisitsNotFoundException("MrVisit data not available");
		} else {
			return page;
		}
	}

	@Override
	public MrVisit fetchMrVisitById(String id) {
		log.info("ai.acintyo.sales.controller.MrVisitServiceImpl::fetchMrVisitById method is executed");

		Optional<MrVisit> mrVisit = mrVisitRepo.findById(id);

		log.info(" Optional<MrVisit> object  is created");

		if (mrVisit.isEmpty()) {
			throw new IllegalArgumentException(visitDataNotFound + id);
		} else {
			return mrVisit.get();
		}
	}

	@Override
	public MrVisit updateMrVisitById(String id, MrVisitForm visitForm) {

		log.info("updateMrVisitById method is executed");

		Optional<MrVisit> opt = mrVisitRepo.findById(id);
		log.info("Optional<MrVisit> obj is created");

		if (opt.isEmpty()) {
			throw new IllegalArgumentException(visitDataNotFound + id);
		} else {
			MrVisit existMrVisit = opt.get();
			existMrVisit.setSupplierId(visitForm.getSupplierId());
			existMrVisit.setRetailerId(visitForm.getRetailerId());
			existMrVisit.setMrId(visitForm.getMrId());
			existMrVisit.setAsmId(visitForm.getAsmId());
			existMrVisit.setVisitTime(visitForm.getVisitTime());
			existMrVisit.setVisitLocationLatitude(visitForm.getVisitLocationLatitude());
			existMrVisit.setVisitLocationLongitude(visitForm.getVisitLocationLongitude());
			existMrVisit.setUpdatedOn(LocalDateTime.now());
			return mrVisitRepo.save(existMrVisit);
		}

	}

	@Override
	public List<MrVisit> findMrVisitDataByMrIdAndRetailerId(String mrId, String retailerId) {

		List<MrVisit> visits = mrVisitRepo.findByMrIdAndRetailerId(mrId, retailerId);
		if (visits.isEmpty()) {
			throw new MrVisitsNotFoundException("Mr visit Data not available");
		}
		return visits;
	}
	
	 public static List<MrVisit> filterVisitsByDate(List<MrVisit> visits, 
			 String fromDate, String toDate) {
	        return visits.stream()
	            .filter(visit -> visit.getVisitTime() != null &&
	                             !visit.getVisitTime().toLocalDate().isBefore(LocalDate.parse(fromDate)) &&
	                             !visit.getVisitTime().toLocalDate().isAfter(LocalDate.parse(toDate)))
	            .collect(Collectors.toList());
	    }

	@Override
	public List<MrVisit> fetchMrVisitDataBetweenTwoDates(String mrId, String retailerId, String fromDate, String toDate,
			int page, int size) {
		Page<MrVisit> all = mrVisitRepo.findAll(PageRequest.of(0, 10,Direction.DESC,"visitTime"));
		//all.getContent().stream().filter((x)->{x.getMrId().equals(mrId)));
		List<MrVisit> listOfMrVisit = mrVisitRepo.findByMrIdAndRetailerId(mrId, retailerId);
		
		List<MrVisit> filterVisitsByDate = MrVisitServiceImpl.filterVisitsByDate(listOfMrVisit, fromDate, toDate);
		
		//PageRequest pages= PageRequest.of(page, size, Direction.DESC, "ere");
	
		return filterVisitsByDate;
	}

	@Override
	public List<MrVisit> fetchMrVisitDataTopList(String mrId, String retailerId) {
		List<MrVisit> listOfMrVisit = mrVisitRepo.findByMrIdAndRetailerId(mrId, retailerId);
		List<MrVisit> collect = listOfMrVisit.stream()
				.sorted((v1, v2) -> v2.getVisitTime().compareTo(v1.getVisitTime())).limit(5)
				.collect(Collectors.toList());
		return collect;
	}

}
