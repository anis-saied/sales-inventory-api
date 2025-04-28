package com.mt.erp.purchases.invoices;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mt.erp.purchases.invoices.payment.PurchaseInvoicePayment;
import com.mt.erp.purchases.invoices.payment.PurchaseInvoicePaymentRepository;
import com.mt.erp.purchases.supplier.Supplier;
import com.mt.erp.purchases.supplier.SupplierRepository;
import com.mt.erp.sales.invoice.SalesInvoice;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/purchase/invoices")
public class PurchaseInvoiceController {

	@Autowired
	private PurchaseInvoiceRepository purchaseInvoiceRepository;

	@Autowired
	private PurchaseInvoiceItemRepository purchaseInvoiceItemRepository;

	@Autowired
	private PurchaseInvoicePaymentRepository purchaseInvoicePaymentRepository;

	@Autowired
	SupplierRepository supplierRepository;

	private List<PurchaseInvoice> invoices;

	@GetMapping
	public List<PurchaseInvoice> getAllPurchaseInvoices() {
		List<PurchaseInvoice> items = purchaseInvoiceRepository.findAll();
		return items;
	}

	@GetMapping("/{id}")
	public Optional<PurchaseInvoice> getPurchaseInvoiceInfo(@PathVariable Long id) {
		return purchaseInvoiceRepository.findById(id);
	}

	@PostMapping
	public PurchaseInvoice createPurchaseInvoice(@RequestBody PurchaseInvoice purchaseInvoice) {
		purchaseInvoice.getDetails().setCreatedAt(new Date());
		purchaseInvoice.getDetails().setLastUpdatedAt(new Date());
		return purchaseInvoiceRepository.save(purchaseInvoice);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deletePurchaseInvoice(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<>();

		Optional<PurchaseInvoice> invoice = purchaseInvoiceRepository.findById(id);

		if (invoice.isPresent()) {
			purchaseInvoiceRepository.deleteById(id);
			response.put("delete", Boolean.TRUE);
		} else {
			response.put("delete", Boolean.FALSE);
		}

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PurchaseInvoice> updatePurchaseInvoice(@PathVariable Long id,
			@RequestBody PurchaseInvoice invoiceDetails) {

		Optional<PurchaseInvoice> oldInvoice = purchaseInvoiceRepository.findById(id);

		// details
		oldInvoice.get().getDetails().setName(invoiceDetails.getDetails().getName());
		oldInvoice.get().getDetails().setDescription(invoiceDetails.getDetails().getDescription());
		oldInvoice.get().getDetails().setLastUpdatedAt(new Date());

		// other
		oldInvoice.get().setReference(invoiceDetails.getReference());
		oldInvoice.get().setDueDate(invoiceDetails.getDueDate());
		oldInvoice.get().setPaymentStatus(invoiceDetails.getPaymentStatus());
		oldInvoice.get().setCreationDate(invoiceDetails.getCreationDate());
		oldInvoice.get().setTraduction(invoiceDetails.isTraduction());
		oldInvoice.get().setFileName(invoiceDetails.getFileName());
		oldInvoice.get().setTitle(invoiceDetails.getTitle());
		oldInvoice.get().setNetWeight(invoiceDetails.getNetWeight());
		oldInvoice.get().setNbOfPackages(invoiceDetails.getNbOfPackages());
		oldInvoice.get().setInvoiceStatus(invoiceDetails.isInvoiceStatus());

		// supplier
		Supplier supplier = supplierRepository.findById(invoiceDetails.getSupplier().getId()).get();
		oldInvoice.get().setSupplier(supplier);

		// for each item in the old invoice
		for (PurchaseInvoiceItem oldItem : oldInvoice.get().getItems()) {
			// if the item not exist in the new invoice : delete it
			boolean exist = false;
			for (PurchaseInvoiceItem newItem : invoiceDetails.getItems()) {
				if (oldItem.getId() == newItem.getId())
					exist = true;
			}
			if (!exist)
				purchaseInvoiceItemRepository.delete(oldItem);
		}

		// items
		oldInvoice.get().setItems(invoiceDetails.getItems());

		// copy payments
		List<PurchaseInvoicePayment> tempPaiements = new ArrayList<PurchaseInvoicePayment>();
		for (int i = 0; i < oldInvoice.get().getPayments().size(); i++) {
			Long id1 = oldInvoice.get().getPayments().get(i).getId();

			PurchaseInvoicePayment paiement = purchaseInvoicePaymentRepository.findById(id1).orElseThrow();
			tempPaiements.add(paiement);
		}
		oldInvoice.get().setPayments(invoiceDetails.getPayments());

		PurchaseInvoice updatedInvoice = purchaseInvoiceRepository.save(oldInvoice.get());

		// test paiements updated
		for (PurchaseInvoicePayment oldPaiement : tempPaiements) {
			boolean exist = false;
			for (PurchaseInvoicePayment newPaiement : updatedInvoice.getPayments()) {
				if (oldPaiement.getId() == newPaiement.getId())
					exist = true;
			}
			if (!exist)
				purchaseInvoicePaymentRepository.delete(oldPaiement);
		}

		return ResponseEntity.ok(updatedInvoice);
	}

	@GetMapping("/{reference}/exist")
	public Optional<Boolean> getPurchaseInvoiceByReference(@PathVariable int reference) {
		if (purchaseInvoiceRepository.findByReference(reference) != null)
			return Optional.of(true);
		return Optional.of(false);
	}

	// getPurchaseInvoices By Supplier And Period
	@GetMapping("/suppliers/{id}/period")
	public ResponseEntity<List<PurchaseInvoice>> getPurchaseInvoicesBySupplierAndPeriode(@PathVariable int id,
			@RequestParam(value = "date-start", required = true) Long d1, // time in milliseconds
			@RequestParam(value = "date-end", required = true) Long d2 // time in milliseconds
	) {

		Date dateStart = new Date(d1);
		Date dateEnd = new Date(d2);
		List<PurchaseInvoice> invoices = new ArrayList<PurchaseInvoice>();
		List<PurchaseInvoice> other = new ArrayList<PurchaseInvoice>();
		Optional<Supplier> supplier = supplierRepository.findById(id);

		if (supplier.isPresent()) {
			try {
				invoices = purchaseInvoiceRepository
						.findBySupplierAndCreationDateBetween(supplier.get(), dateStart, dateEnd).orElse(other);

			} catch (Exception e) {
			}
		}
		return ResponseEntity.ok(invoices);
	}

	// getPurchaseInvoices By Supplier
	@GetMapping("/suppliers/{id}")
	public ResponseEntity<List<PurchaseInvoice>> getPurchaseInvoicesBySupplier(@PathVariable int id) {
		invoices = new ArrayList<PurchaseInvoice>();		
		Optional<Supplier> supplier = supplierRepository.findById(id);
		if (supplier.isPresent()) {
			try {
				invoices = purchaseInvoiceRepository.findBySupplier(supplier.get());
			} catch (Exception e) {
			}
		}
		return ResponseEntity.ok(invoices);
	}
	


}
