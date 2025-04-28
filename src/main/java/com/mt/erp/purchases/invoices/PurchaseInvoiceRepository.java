package com.mt.erp.purchases.invoices;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mt.erp.purchases.supplier.Supplier;

@Repository
public interface PurchaseInvoiceRepository extends JpaRepository<PurchaseInvoice, Long> {

	List<PurchaseInvoice> findBySupplier(Supplier supplier);
	
	PurchaseInvoice findByReference(int newReference);

	@Query(value = "from PurchaseInvoice i where supplier = ?1 and i.creationDate between ?2 and ?3 ")
	Optional<List<PurchaseInvoice>> findBySupplierAndCreationDateBetween(Supplier supplier,Date dateStart, Date dateEnd);
}
