package com.mt.erp.sales.quote;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mt.erp.sales.customer.Customer;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

	List<Quote> findByCustomer(Customer customer);

	Quote findByReference(int newReference);

}
