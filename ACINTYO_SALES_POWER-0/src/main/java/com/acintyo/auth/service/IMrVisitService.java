package com.acintyo.auth.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.acintyo.auth.dto.MrVisitForm;
import com.acintyo.auth.model.MrVisit;

public interface IMrVisitService {

	MrVisit registerMrVisit(MrVisitForm visitForm);
	
	Page<MrVisit> fetchAllMrVisit(Pageable pageable);
	
	MrVisit fetchMrVisitById(String id);
	
	MrVisit updateMrVisitById(String id,MrVisitForm visitForm);
	
	List<MrVisit> findMrVisitDataByMrIdAndRetailerId(String mrId,String retailerId);
	
	List<MrVisit> fetchMrVisitDataBetweenTwoDates(String mrId,String retailerId,String fromDate,String toDate,int page,int size );
	
	List<MrVisit> fetchMrVisitDataTopList(String mrId,String retailerId);
	
}

