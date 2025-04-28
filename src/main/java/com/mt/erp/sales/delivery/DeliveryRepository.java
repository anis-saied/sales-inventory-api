package com.mt.erp.sales.delivery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mt.erp.sales.customer.Customer;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>{
	
	List<Delivery> findByCustomer(Customer customer);

	Delivery findByReference(int newReference);

}
