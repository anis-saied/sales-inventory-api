package com.mt.erp.purchases.invoices.payment;

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
@RequestMapping("/api/v1/purchase/invoices/payments")
public class PurchaseInvoicePaymentController {
	@Autowired
	private PurchaseInvoicePaymentRepository purchaseInvoicePaymentRepository;

	private PurchaseInvoicePayment PurchaseInvoicePayment;

	@GetMapping
	public List<PurchaseInvoicePayment> getPaiements() {
		return purchaseInvoicePaymentRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<PurchaseInvoicePayment> getAllPurchaseInvoicePayments(@PathVariable Long id) {
		PurchaseInvoicePayment = purchaseInvoicePaymentRepository.findById(id).get();
		return ResponseEntity.ok(PurchaseInvoicePayment);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deletePurchaseInvoicePaiementById(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		if (purchaseInvoicePaymentRepository.findById(id).isPresent()) {
			purchaseInvoicePaymentRepository.deleteById(id);
			response.put("deleted", Boolean.TRUE);
		} else {
			response.put("deleted", Boolean.FALSE);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<PurchaseInvoicePayment> createPurchaseInvoicePaiement(
			@RequestBody PurchaseInvoicePayment payment) {
		PurchaseInvoicePayment = purchaseInvoicePaymentRepository.save(payment);
		return ResponseEntity.ok(PurchaseInvoicePayment);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PurchaseInvoicePayment> updatePurchaseInvoice(@PathVariable Long id,
			@RequestBody PurchaseInvoicePayment newPaymentDetails) {
		PurchaseInvoicePayment oldPayment = purchaseInvoicePaymentRepository.findById(id).get();

		oldPayment.setDate(newPaymentDetails.getDate());
		oldPayment.setAmount(newPaymentDetails.getAmount());

		PurchaseInvoicePayment paymentUpdated = purchaseInvoicePaymentRepository.save(oldPayment);
		return ResponseEntity.ok(paymentUpdated);
	}
}
