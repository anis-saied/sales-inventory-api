package com.mt.erp.purchases.invoices;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mt.erp.stock.product.Product;

@Repository
public interface PurchaseInvoiceItemRepository extends JpaRepository<PurchaseInvoiceItem, Long> {

	public List<PurchaseInvoiceItem> findByProduct(Product product);

	@Query("FROM PurchaseInvoiceItem  WHERE product = ?1 and lotNo = ?2")
	public PurchaseInvoiceItem findByProductAndLotNo(Product product, String lotNo);

}
