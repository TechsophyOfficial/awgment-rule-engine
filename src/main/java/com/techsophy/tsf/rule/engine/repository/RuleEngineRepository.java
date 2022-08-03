package com.techsophy.tsf.rule.engine.repository;

import com.techsophy.tsf.rule.engine.entity.RuleEngineDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface RuleEngineRepository extends MongoRepository<RuleEngineDefinition, String>
{
	RuleEngineDefinition findById(BigInteger id);
	boolean existsById(BigInteger id);
	List<RuleEngineDefinition> findAll();
	RuleEngineDefinition findByIdAndVersion(BigInteger id, Integer version);
	int deleteById(BigInteger id);
}
