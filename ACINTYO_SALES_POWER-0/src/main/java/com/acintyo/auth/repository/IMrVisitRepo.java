package com.acintyo.auth.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.acintyo.auth.model.MrVisit;


public interface IMrVisitRepo extends MongoRepository<MrVisit,String> {

	public List<MrVisit> findByMrIdAndRetailerId(String mrId,String retailerId);
}
