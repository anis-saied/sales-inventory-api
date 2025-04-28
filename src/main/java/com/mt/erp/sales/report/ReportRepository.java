package com.mt.erp.sales.report;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mt.erp.purchases.supplier.Supplier;
import com.mt.erp.sales.invoice.SalesInvoice;
import com.mt.erp.sales.invoice.SalesInvoiceItem;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

	Optional<List<Report>> findBySupplier(Supplier supplier);

	Report findByReference(int newReference);

	@Query(value = ""
			+ " select * from sales_invoice_item "
			+ " where item_id in "
			+ "("
			+ " select item_id "
			+ " from   report_sales_invoice t, SALES_INVOICE i,  sales_invoice_item item "
			+ " where  t.report_id = :id "
			+ " and    t.sales_invoice_id = i.id"
			+ " and    i.id = item.sales_invoice_id"
			+ ")", nativeQuery = true)
	List<SalesInvoiceItem> findSalesInvoicesByReportId(@Param("id")  Long id);

	
}
