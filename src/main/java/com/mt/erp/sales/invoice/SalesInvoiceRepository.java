package com.mt.erp.sales.invoice;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mt.erp.dashboard.ChiffreAffaire;
import com.mt.erp.sales.customer.Customer;
import com.mt.erp.stock.product.Product;

@Repository
public interface SalesInvoiceRepository extends JpaRepository<SalesInvoice, Long> {

	@Query(value = "from SalesInvoice i where i.creationDate between ?1 and ?2 ")
	Optional<List<SalesInvoice>> findByCreationDateBetween(Date dateStart, Date dateEnd);

	@Query(value = "select * from SALES_INVOICE invoice " + "where ?1 in " + "("
			+ "select item.PRODUCT_ID from SALES_INVOICE_ITEM item " + "where item.sales_invoice_id = invoice.id"
			+ ") ", nativeQuery = true)
	Optional<List<SalesInvoice>> findByProduct(int productId);

	Optional<List<SalesInvoice>> findByCustomer(Customer customer);

	@Query(value = "from SalesInvoice i where i.customer = ?1 and i.creationDate between ?2 and ?3 ")
	Optional<List<SalesInvoice>> findByCustomerAndCreationDateBetween(Customer customer, Date dateStart, Date dateEnd);

	/*
	 * @Query(value = "select * from SALES_INVOICE invoice " +
	 * "where invoice.CREATION_DATE between ?2 and ?3 " + "and ?1 in " + "(" +
	 * "select item.PRODUCT_ID from SALES_INVOICE_ITEM item " +
	 * "where item.sales_invoice_id = invoice.id" + ") ", nativeQuery = true)
	 * Optional<List<SalesInvoice>> findByProductAndCreationDateBetween(int
	 * productId, Date dateStart, Date dateEnd);
	 */

	@Query(value = "select * from SALES_INVOICE invoice " + "where invoice.CUSTOMER_ID = ?1 " + "and ?2 in " + "("
			+ "select item.PRODUCT_ID from SALES_INVOICE_ITEM item " + "where item.sales_invoice_id = invoice.id"
			+ ") ", nativeQuery = true)
	Optional<List<SalesInvoice>> findByCustomerAndProductAnd(int customerId, int productId);

	@Query(value = "select * from SALES_INVOICE invoice " + "where invoice.CUSTOMER_ID = ?1 "
			+ "and invoice.CREATION_DATE between ?3 and ?4 " + "and ?2 in " + "("
			+ "select item.PRODUCT_ID from SALES_INVOICE_ITEM item " + "where item.sales_invoice_id = invoice.id"
			+ ") ", nativeQuery = true)
	Optional<List<SalesInvoice>> findByCustomerAndProductAndCreationDateBetween(int customerId, int productId,
			Date dateStart, Date dateEnd);

	SalesInvoice findByReference(int newReference);

	@Query(value = "select i.id from SalesInvoice i where i.creationDate between ?1 and ?2 ")
	Optional<List<Long>> findSalesInvoiceIdByCreationDateBetween(Date dateStart, Date dateEnd);

	
	@Query(value = "select sum((QUANTITY * UNIT_SELLING_PRICE)*(1-DISCOUNT*0.01))  "
			+ "from SALES_INVOICE invoice, SALES_INVOICE_ITEM item "
			+ " where invoice.id = item.sales_invoice_id "
			+ " and invoice.CREATION_DATE between :dateStart and  :dateEnd", nativeQuery = true)	
	Optional<Float> findSumChiffreAffaireByPeriode(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd);

	@Query(value = "select sum((QUANTITY * UNIT_SELLING_PRICE)*(1-DISCOUNT*0.01))  from SALES_INVOICE invoice, SALES_INVOICE_ITEM item "
			+ " where item.PRODUCT_ID = :id and invoice.id = item.sales_invoice_id "
			+ " and invoice.CREATION_DATE between :dateStart and  :dateEnd", nativeQuery = true)
	Optional<Float> findSumChiffreAffaireByPeriodeAndProduct(@Param("dateStart") String string, @Param("dateEnd") String string2,@Param("id") int id);

	//101
	@Query(value = "select sum(total_invoice)  from SALES_INVOICE invoice "
			+ " where customer_id = :customerId and invoice.CREATION_DATE between :dateStart and  :dateEnd", nativeQuery = true)
	Optional<Float> findSumChiffreAffaireByPeriodeAndCustomer(@Param("dateStart") String string, @Param("dateEnd") String string2,@Param("customerId") int customerId);

	@Query(value = "select sum((QUANTITY * UNIT_SELLING_PRICE)*(1-DISCOUNT*0.01))  from SALES_INVOICE invoice, SALES_INVOICE_ITEM item "
			+ " where customer_id = :customerId and item.PRODUCT_ID = :productId and invoice.id = item.sales_invoice_id "
			+ " and invoice.CREATION_DATE between :dateStart and  :dateEnd", nativeQuery = true)
	Optional<Float> findSumChiffreAffaireByPeriodeAndCustomerAndProduct(@Param("dateStart") String string, @Param("dateEnd") String string2,
			@Param("customerId") int customerId, @Param("productId") int productId);

	
	//dashborad : simulation quantities
	@Query(value = "select sum(QUANTITY)  from SALES_INVOICE invoice, SALES_INVOICE_ITEM item "
			+ " where invoice.id = item.sales_invoice_id "
			+ " and invoice.CREATION_DATE between :dateStart and  :dateEnd", nativeQuery = true)	
	Optional<Float> findSumQuantityByPeriode(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd);
	
	@Query(value = "select sum(QUANTITY)  from SALES_INVOICE invoice, SALES_INVOICE_ITEM item "
			+ " where customer_id = :customerId and invoice.id = item.sales_invoice_id "
			+ " and invoice.CREATION_DATE between :dateStart and  :dateEnd", nativeQuery = true)	
	Optional<Float> findSumQuantityByPeriodeAndCustomer(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd, @Param("customerId") int customerId);

	@Query(value = "select sum(QUANTITY)  from SALES_INVOICE invoice, SALES_INVOICE_ITEM item "
			+ " where item.PRODUCT_ID = :productId and invoice.id = item.sales_invoice_id "
			+ " and invoice.CREATION_DATE between :dateStart and  :dateEnd", nativeQuery = true)	
	Optional<Float> findSumQuantityByPeriodeAndProduct(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd, @Param("productId") int productId);

	@Query(value = "select sum(QUANTITY)  from SALES_INVOICE invoice, SALES_INVOICE_ITEM item "
			+ " where customer_id = :customerId and item.PRODUCT_ID = :productId and invoice.id = item.sales_invoice_id "
			+ " and invoice.CREATION_DATE between :dateStart and  :dateEnd", nativeQuery = true)	
	Optional<Float> findSumQuantityByPeriodeAndCustomerAndProduct(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd, @Param("customerId") int customerId, @Param("productId") int productId);



}
