package com.mt.erp.purchases.invoices.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseInvoicePaymentRepository extends JpaRepository<PurchaseInvoicePayment, Long> {

}
