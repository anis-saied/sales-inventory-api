package com.mt.erp.taxes;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {
	
	@Query(value = "from Tax t where t.paymentDate between ?1 and ?2 ")
	Optional<List<Tax>> findByCreationDateBetween(Date dateStart, Date dateEnd);

}
