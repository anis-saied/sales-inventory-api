package com.mt.erp.purchases.invoices;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import com.mt.erp.stock.product.Product;
import com.mt.erp.stock.product.ProductRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/purchase/invoices/items")
public class PurchaseInvoiceItemController {

	@Autowired
	private PurchaseInvoiceItemRepository purchaseInvoiceItemRepository;

	@Autowired
	ProductRepository productRepository;
	
	//
	@GetMapping
	public List<PurchaseInvoiceItem> getAllPurchaseInvoiceItems() {
		List<PurchaseInvoiceItem> items = purchaseInvoiceItemRepository.findAll();
		return items;
	}

	//
	@GetMapping("/products/{id}")
	public List<PurchaseInvoiceItem> getAllPurchaseInvoiceItemsByProduct(@PathVariable int id) {
		List<PurchaseInvoiceItem> items = new ArrayList<PurchaseInvoiceItem>();
		
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent())
			items = purchaseInvoiceItemRepository.findByProduct(product.get());

		return items;
	}

	@GetMapping("/{id}")
	public Optional<PurchaseInvoiceItem> getPurchaseInvoiceItemInfo(@PathVariable Long id) {
		return purchaseInvoiceItemRepository.findById(id);
	}

	@PostMapping
	public PurchaseInvoiceItem createPurchaseInvoiceItem(@RequestBody PurchaseInvoiceItem purchaseInvoiceItem) {
		return purchaseInvoiceItemRepository.save(purchaseInvoiceItem);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deletePurchaseInvoiceItem(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<>();

		Optional<PurchaseInvoiceItem> invoice = purchaseInvoiceItemRepository.findById(id);

		if (invoice.isPresent()) {
			purchaseInvoiceItemRepository.deleteById(id);
			response.put("delete", Boolean.TRUE);
		} else {
			response.put("delete", Boolean.FALSE);
		}

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PurchaseInvoiceItem> updatePurchaseInvoiceItem(@PathVariable Long id,
			@RequestBody PurchaseInvoiceItem invoiceItemDetails) {

		Optional<PurchaseInvoiceItem> invoiceItem = purchaseInvoiceItemRepository.findById(id);

		invoiceItem.get().setUnitPurchasePrice(invoiceItemDetails.getUnitPurchasePrice());
		invoiceItem.get().setProduct(invoiceItemDetails.getProduct());
		invoiceItem.get().setLotNo(invoiceItemDetails.getLotNo());
		invoiceItem.get().setQuantity(invoiceItemDetails.getQuantity());
		invoiceItem.get().setTotal(invoiceItemDetails.getTotal());

		PurchaseInvoiceItem updatedInvoiceItem = purchaseInvoiceItemRepository.save(invoiceItem.get());
		return ResponseEntity.ok(updatedInvoiceItem);
	}
}
