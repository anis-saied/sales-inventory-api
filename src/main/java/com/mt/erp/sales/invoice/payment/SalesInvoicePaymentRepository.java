package com.mt.erp.sales.invoice.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesInvoicePaymentRepository extends JpaRepository<SalesInvoicePayment, Long> {

}
