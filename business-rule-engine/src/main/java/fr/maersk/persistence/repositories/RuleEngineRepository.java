package fr.maersk.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.maersk.persistence.entities.PaymentTransaction;
import fr.maersk.persistence.entities.RulesEngine;

public interface RuleEngineRepository extends JpaRepository<PaymentTransaction, Long>{

	@Query("from Account where order_type = ?1")
	RulesEngine findByOrderType(String order_type);
}
