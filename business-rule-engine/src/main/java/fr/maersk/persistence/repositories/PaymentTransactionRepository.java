package fr.maersk.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.maersk.persistence.entities.PaymentTransaction;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long>{
	
	@Query("from Account where customerId = ?1")
	List<PaymentTransaction> findByCustomerId(Long orderId);

}
