package com.mt.erp.sales.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesInvoiceItemRepository extends JpaRepository<SalesInvoiceItem, Long> {

}
