package com.mt.erp.sales.delivery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mt.erp.stock.product.Product;

@Repository
public interface DeliveryItemRepository extends JpaRepository<DeliveryItem, Long> {
	
	@Query("FROM DeliveryItem  WHERE quantity > 0 and product = ?1")
	public List<DeliveryItem> findByProduct(Product product);
}

