package ai.acintyo.sales.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ai.acintyo.sales.binding.MrVisitForm;
import ai.acintyo.sales.collection.MrVisit;
import ai.acintyo.sales.exception.DataNotFoundException;
import ai.acintyo.sales.repository.IMrVisitRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MrVisitServiceImpl implements IMrVisitService {
	@Autowired
	private IMrVisitRepo mrVisitRepo;

	@Override
	public MrVisit registerMrVisit(MrVisitForm visitForm) {
       
	    log.info("ai.acintyo.sales.controller.MrVisitServiceImpl:: registerMrVisit method is executed");
			
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
	public List<MrVisit> fetchAllMrVisit(Pageable pageable) {

		log.info("ai.acintyo.sales.controller.MrVisitServiceImpl::fetchAllMrVisit method is executed");
		
	   Page<MrVisit> page = mrVisitRepo.findAll(pageable);

	   log.info("ai.acintyo.sales.controller.MrVisitServiceImpl:: Page object is created:");
		
		if (page.isEmpty()) {
			throw new DataNotFoundException("MrVisit Data not available");
		} else {
			return page.getContent();
		}
	}

	@Override
	public MrVisit fetchMrVisitById(String id) {
		log.info("ai.acintyo.sales.controller.MrVisitServiceImpl::fetchMrVisitById method is executed");
		
		Optional<MrVisit> mrVisit = mrVisitRepo.findById(id);

		log.info("ai.acintyo.sales.controller.MrVisitServiceImpl::  Optional<MrVisit> object  is created");
		
		if (mrVisit.isEmpty()) {
			throw new IllegalArgumentException("MrVisit Data is not Available by Given id:" + id);
		} else {
			return mrVisit.get();
		}
	}
	@Override
	public MrVisit updateMrVisitById(String id, MrVisitForm visitForm) {
		
		log.info("ai.acintyo.sales.controller.MrVisitServiceImpl:: updateMrVisitById method is executed");
		
	    Optional<MrVisit> opt = mrVisitRepo.findById(id);
	    log.info("ai.acintyo.sales.controller.MrVisitServiceImpl:: Optional<MrVisit> obj is created");
		
	    if(opt.isEmpty())
	    {
	    	throw new IllegalArgumentException("MrVisit is not available by given Id:"+id);
	    }
	    else
	    {
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
}
