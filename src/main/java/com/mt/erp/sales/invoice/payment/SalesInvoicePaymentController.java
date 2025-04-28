package com.mt.erp.sales.invoice.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/sales/invoices/payments")
public class SalesInvoicePaymentController {
	@Autowired
	private SalesInvoicePaymentRepository salesInvoicePaymentRepository;
	
	private SalesInvoicePayment salesInvoicePayment;


	@GetMapping
	public List<SalesInvoicePayment> getPaiements() {
		return salesInvoicePaymentRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<SalesInvoicePayment> getAllSalesInvoicePaiements(@PathVariable Long id) {
		salesInvoicePayment = salesInvoicePaymentRepository.findById(id).get();
		return ResponseEntity.ok(salesInvoicePayment);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteSalesInvoicePaiementById(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		if (salesInvoicePaymentRepository.findById(id).isPresent()) {
			salesInvoicePaymentRepository.deleteById(id);
			response.put("deleted", Boolean.TRUE);
		} else {
			response.put("deleted", Boolean.FALSE);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<SalesInvoicePayment> createSalesInvoicePaiement(@RequestBody SalesInvoicePayment paiement) {
		salesInvoicePayment = salesInvoicePaymentRepository.save(paiement);
		return ResponseEntity.ok(salesInvoicePayment);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<SalesInvoicePayment> updateSalesInvoice(@PathVariable Long id,
			@RequestBody SalesInvoicePayment newPaiementDetails) {
		SalesInvoicePayment oldPaiement = salesInvoicePaymentRepository.findById(id).get();

		oldPaiement.setDate(newPaiementDetails.getDate());
		oldPaiement.setAmount(newPaiementDetails.getAmount());
		
		SalesInvoicePayment paiementUpdated = salesInvoicePaymentRepository.save(oldPaiement);
		return ResponseEntity.ok(paiementUpdated);
	}
}
