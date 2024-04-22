package ai.acintyo.sales.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import ai.acintyo.sales.binding.MrVisitForm;
import ai.acintyo.sales.collection.MrVisit;

public interface IMrVisitService {

	MrVisit registerMrVisit(MrVisitForm visitForm);
	
	List<MrVisit> fetchAllMrVisit(Pageable pageable);
	
	MrVisit fetchMrVisitById(String id);
	
	MrVisit updateMrVisitById(String id,MrVisitForm visitForm);
}

