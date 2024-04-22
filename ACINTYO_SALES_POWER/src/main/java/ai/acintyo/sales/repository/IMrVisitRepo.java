package ai.acintyo.sales.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.acintyo.sales.collection.MrVisit;

public interface IMrVisitRepo extends MongoRepository<MrVisit,String> {

}
