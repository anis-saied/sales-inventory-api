package com.mt.erp.purchases.supplier;

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
import org.springframework.web.bind.annotation.RestController;

import com.mt.erp.purchases.invoices.PurchaseInvoice;
import com.mt.erp.purchases.invoices.PurchaseInvoiceItem;
import com.mt.erp.purchases.invoices.PurchaseInvoiceRepository;
import com.mt.erp.sales.report.Report;
import com.mt.erp.sales.report.ReportRepository;
import com.mt.erp.stock.product.Product;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private PurchaseInvoiceRepository purchaseInvoiceRepository;
	
	@Autowired
	private ReportRepository reportRepository;
	
	// Get all suppliers
	@GetMapping("")
	public List<Supplier> getAllSuppliers() {
		return supplierRepository.findAll();
	}

	// delete customer rest api

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteSupplier(@PathVariable Integer id) {
		Map<String, Boolean> response = new HashMap<>();

		Optional<Supplier> supplier = supplierRepository.findById(id);
		if (supplier.isEmpty()) {
			response.put("not deleted", Boolean.FALSE);
		} else {
			//delete purchase invoices for this supplier
			List<PurchaseInvoice> invoices = purchaseInvoiceRepository.findBySupplier(supplier.get());
			for (PurchaseInvoice purchaseInvoice : invoices) {
				purchaseInvoiceRepository.delete(purchaseInvoice);
			}
			
			//delete all the reports for this supplier 
			Optional<List<Report>> reports = reportRepository.findBySupplier(supplier.get());
			if (reports.isPresent()) {
				for (Report report : reports.get()) {
					reportRepository.delete(report);
				}
			}
			
			//delete supplier
			supplierRepository.deleteById(supplier.get().getId());
			response.put("deleted", Boolean.TRUE);
		}
		return ResponseEntity.ok(response);
	}

	// create supplier rest api
	@PostMapping("")
	public Supplier createSupplier(@RequestBody Supplier supplier) {
		System.out.println(supplier);
		// return null;
		return supplierRepository.save(supplier);
	}

	// Get one
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Supplier>> getCustomerInfo(@PathVariable int id) {
		Optional<Supplier> supplier = supplierRepository.findById(id);
		return ResponseEntity.ok(supplier);
	}

	// update Supplier rest api

	@PutMapping("/{id}")
	public ResponseEntity<Supplier> updateSupplier(@PathVariable Integer id, @RequestBody Supplier supplierDetails) {
		Optional<Supplier> supplier = supplierRepository.findById(id);
		if (supplier.isPresent()) {
			// details
			supplier.get().getDetails().setName(supplierDetails.getDetails().getName());
			supplier.get().getDetails().setDescription(supplierDetails.getDetails().getDescription());
			supplier.get().getDetails().setLastUpdatedAt(new Date());
			// contact
			supplier.get().getContact().setAddress(supplierDetails.getContact().getAddress());
			supplier.get().getContact().setCity(supplierDetails.getContact().getCity());
			supplier.get().getContact().setCountry(supplierDetails.getContact().getCountry());
			supplier.get().getContact().setEmail(supplierDetails.getContact().getEmail());
			supplier.get().getContact().setMobile(supplierDetails.getContact().getMobile());
			supplier.get().getContact().setPostalCode(supplierDetails.getContact().getPostalCode());
			// company contact
			supplier.get().getCompanyContact().setAddress(supplierDetails.getCompanyContact().getAddress());
			supplier.get().getCompanyContact().setCity(supplierDetails.getCompanyContact().getCity());
			supplier.get().getCompanyContact().setCountry(supplierDetails.getCompanyContact().getCountry());
			supplier.get().getCompanyContact().setEmail(supplierDetails.getCompanyContact().getEmail());
			supplier.get().getCompanyContact().setFax1(supplierDetails.getCompanyContact().getFax1());
			supplier.get().getCompanyContact().setFax2(supplierDetails.getCompanyContact().getFax2());
			supplier.get().getCompanyContact().setMobile(supplierDetails.getCompanyContact().getMobile());
			supplier.get().getCompanyContact().setPhone(supplierDetails.getCompanyContact().getPhone());
			supplier.get().getCompanyContact().setPostalCode(supplierDetails.getCompanyContact().getPostalCode());
			supplier.get().getCompanyContact().setWebsite(supplierDetails.getCompanyContact().getWebsite());
			// other
			supplier.get().setIban(supplierDetails.getIban());
			supplier.get().setResponsible(supplierDetails.getResponsible());
			supplier.get().setSwiftCode(supplierDetails.getSwiftCode());
			supplier.get().setTaxRegistrationNo(supplierDetails.getTaxRegistrationNo());
			supplier.get().setTradeRegisterNo(supplierDetails.getTradeRegisterNo());

			// update
			Supplier updatedSupplier = supplierRepository.saveAndFlush(supplier.get());
			return ResponseEntity.ok(updatedSupplier);
		}
		return ResponseEntity.ok(null);
	}
	
	// get all products by supplier Id
	@GetMapping("/{id}/products")
	public List<Product> getAllPoductsBySupplierId(@PathVariable int id) {
		List<Product> products = new ArrayList<Product>();
		List<PurchaseInvoice> invoices;
		List<PurchaseInvoiceItem> items;
		// get supplier by id
		Optional<Supplier> supplier = supplierRepository.findById(id);
		if (supplier.isPresent()) {
			invoices = purchaseInvoiceRepository.findBySupplier(supplier.get());
			for (PurchaseInvoice purchaseInvoice : invoices) {
				items = purchaseInvoice.getItems();
				for (PurchaseInvoiceItem purchaseInvoiceItem : items) {
					boolean exist = false;
					for (Product product : products) {
						if (product.getId() == purchaseInvoiceItem.getProduct().getId()) {
							exist = true;
							break;
						}
					}
					if (!exist)
						products.add(purchaseInvoiceItem.getProduct());
				}
			}
		}
		return products;
	}
}
