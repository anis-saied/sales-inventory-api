package com.mt.erp.sales.invoice;

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

import com.mt.erp.purchases.invoices.PurchaseInvoice;
import com.mt.erp.purchases.invoices.PurchaseInvoiceItem;
import com.mt.erp.purchases.invoices.PurchaseInvoiceItemRepository;
import com.mt.erp.purchases.invoices.PurchaseInvoiceRepository;
import com.mt.erp.purchases.supplier.Supplier;
import com.mt.erp.purchases.supplier.SupplierRepository;
import com.mt.erp.sales.customer.Customer;
import com.mt.erp.sales.customer.CustomerRepository;
import com.mt.erp.sales.customer.CustomerType;
import com.mt.erp.sales.delivery.Delivery;
import com.mt.erp.sales.delivery.DeliveryItem;
import com.mt.erp.sales.delivery.DeliveryRepository;
import com.mt.erp.sales.invoice.payment.SalesInvoicePayment;
import com.mt.erp.sales.invoice.payment.SalesInvoicePaymentRepository;
import com.mt.erp.sales.report.Report;
import com.mt.erp.sales.report.ReportRepository;
import com.mt.erp.settings.company.MyCompany;
import com.mt.erp.settings.company.MyCompanyReppository;
import com.mt.erp.settings.general.GeneralSetting;
import com.mt.erp.settings.general.GeneralSettingReppository;
import com.mt.erp.stock.product.Product;
import com.mt.erp.stock.product.ProductRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/sales/invoices")
public class SalesInvoiceController {

	@Autowired
	private SalesInvoiceRepository salesInvoiceRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SalesInvoiceItemRepository salesInvoiceItemRepository;

	@Autowired
	private SalesInvoicePaymentRepository salesInvoicePaymentRepository;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private MyCompanyReppository myCompanyRepository;

	@Autowired
	private GeneralSettingReppository generalSettingReppository;

	@Autowired
	private DeliveryRepository deliveryRepository;

	@Autowired
	SupplierRepository supplierRepository;

	@Autowired
	private PurchaseInvoiceRepository purchaseInvoiceRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private PurchaseInvoiceItemRepository purchaseInvoiceItemRepository;
	
	@GetMapping
	public List<SalesInvoice> getAllSalesInvoices() {
		return salesInvoiceRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<SalesInvoice> getSalesInvoiceById(@PathVariable Long id) {
		SalesInvoice salesInvoice = salesInvoiceRepository.findById(id).get();
		return ResponseEntity.ok(salesInvoice);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteSalesInvoiceById(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();

		if (salesInvoiceRepository.findById(id).isPresent()) {

			// delete this sales invoice from all reports
			List<SalesInvoice> salesInvoices;
			List<Report> reports = reportRepository.findAll();

			// remove sales invoice from all reports
			for (Report report : reports) {
				salesInvoices = new ArrayList<SalesInvoice>(report.getSalesInvoices());
				for (SalesInvoice invoice : report.getSalesInvoices()) {
					if (invoice.getId() == id) {
						salesInvoices.remove(invoice);
					}
				}
				report.setSalesInvoices(salesInvoices);
				reportRepository.save(report);
			}

			// mark all deliveries relative to this sales invoice as not billed
			SalesInvoice invoice = salesInvoiceRepository.findById(id).get();
			for (Delivery delivery : invoice.getDeliveries()) {
				// mark this delivery as not billed
				delivery.setBilled(false);
				deliveryRepository.save(delivery);
			}

			// delete sales invoice
			salesInvoiceRepository.deleteById(id);
			response.put("deleted", Boolean.TRUE);
		} else {
			response.put("deleted", Boolean.FALSE);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<SalesInvoice> createSalesInvoice(@RequestBody SalesInvoice invoice) {
		invoice = salesInvoiceRepository.save(invoice);
		return ResponseEntity.ok(invoice);
	}

	// create sales invoice from delivery
	@PostMapping("/convert-to-sales-invoice")
	public ResponseEntity<SalesInvoice> createSalesInvoiceFromDelivery(@RequestBody List<Delivery> selectedDeliveries) {
		SalesInvoice invoice = new SalesInvoice();

		int newReference = generateNewSalesInvoiceReference();
		invoice.setReference(newReference);

		// set attributes from delivery
		invoice.setCustomer(selectedDeliveries.get(0).getCustomer());
		invoice.setCreationDate(new Date());

		for (Delivery delivery : selectedDeliveries) {
			Delivery d = deliveryRepository.findById(delivery.getId()).get();

			// mark this delivery as billed
			d.setBilled(true);
			d = deliveryRepository.save(d);

			// add the updated delivery to this sales invoice
			invoice.getDeliveries().add(d);
		}

		SalesInvoiceItem salesInvoiceItem;

		// for every selected delivery to include in the new sales invoice
		for (Delivery delivery : selectedDeliveries) {
			// convert each delivery item to sales invoice item
			for (DeliveryItem deliveryItem : delivery.getItems()) {
				// check if there is one sales invoice item with the same product and the same
				// lot number exist before
				boolean exist = false;
				for (SalesInvoiceItem invoiceItem : invoice.getItems()) {
					if (deliveryItem.getProduct().getId() == invoiceItem.getProduct().getId()
							&& deliveryItem.getLotNo().equals(invoiceItem.getLotNo())) {
						exist = true;
						// just update the quantity
						invoiceItem.setQuantity(invoiceItem.getQuantity() + deliveryItem.getQuantity());
						break;
					}
				}
				if (exist == false) {
					salesInvoiceItem = new SalesInvoiceItem();
					salesInvoiceItem.setLotNo(deliveryItem.getLotNo());
					salesInvoiceItem.setProduct(deliveryItem.getProduct());
					salesInvoiceItem.setQuantity(deliveryItem.getQuantity());
					salesInvoiceItem.setNbOfPackages((int) Math.ceil(deliveryItem.getQuantity()
							/ deliveryItem.getProduct().getStockSetting().getNetWeightPerPackage()));
					invoice.getItems().add(salesInvoiceItem);
				}
			}
		}

		return ResponseEntity.ok(salesInvoiceRepository.save(invoice));
	}

	@PutMapping("/{id}")
	public ResponseEntity<SalesInvoice> updateSalesInvoice(@PathVariable Long id,
			@RequestBody SalesInvoice newInvoiceDetails) {
		SalesInvoice oldInvoice = salesInvoiceRepository.findById(id).get();

		oldInvoice.setReference(newInvoiceDetails.getReference());
		oldInvoice.setCreationDate(newInvoiceDetails.getCreationDate());
		oldInvoice.setDueDate(newInvoiceDetails.getDueDate());
		oldInvoice.setFileName(newInvoiceDetails.getFileName());

		oldInvoice.setTimbreAmount(newInvoiceDetails.getTimbreAmount());
		oldInvoice.setTitle(newInvoiceDetails.getTitle());

		oldInvoice.setTotalAmount(newInvoiceDetails.getTotalAmount());
		oldInvoice.setTraduction(newInvoiceDetails.isTraduction());

		oldInvoice.setVatPourcentage(newInvoiceDetails.getVatPourcentage());

		// details
		oldInvoice.getDetails().setLastUpdatedAt(new Date());
		oldInvoice.getDetails().setDescription(newInvoiceDetails.getDetails().getDescription());

		// paiement
		oldInvoice.setInvoiceStatus(newInvoiceDetails.isInvoiceStatus());
		oldInvoice.setPaymentChoice(newInvoiceDetails.getPaymentChoice());
		oldInvoice.setPaymentStatus(newInvoiceDetails.getPaymentStatus());

		// cusotmer
		Customer customer = customerRepository.findById(newInvoiceDetails.getCustomer().getId()).get();
		oldInvoice.setCustomer(customer);

		// exoneration tva
		oldInvoice.setExonerationTVA(newInvoiceDetails.getExonerationTVA());
		oldInvoice.setBonCommandeVise(newInvoiceDetails.getBonCommandeVise());

		// deliveries
		// check if there is some deliveries deleted
		// for each delivery in the old invoice
		for (Delivery oldDelivery : oldInvoice.getDeliveries()) {
			// if the current delivery does not exist in the new invoice : delete it
			boolean exist = false;
			for (Delivery newDelivery : newInvoiceDetails.getDeliveries()) {
				if (oldDelivery.getId() == newDelivery.getId())
					exist = true;
			}
			if (!exist)
				newInvoiceDetails.getDeliveries().remove(oldDelivery);
			// deliveryRepository.delete(oldDelivery);

		}

		oldInvoice.setDeliveries(newInvoiceDetails.getDeliveries());

		// items
		// copy
		List<SalesInvoiceItem> tempItems = new ArrayList<SalesInvoiceItem>();
		for (int i = 0; i < oldInvoice.getItems().size(); i++) {
			Long id1 = oldInvoice.getItems().get(i).getId();
			SalesInvoiceItem item = salesInvoiceItemRepository.findById(id1).orElseThrow();
			tempItems.add(item);
		}

		oldInvoice.setItems(newInvoiceDetails.getItems());

		// copy payments
		List<SalesInvoicePayment> tempPaiements = new ArrayList<SalesInvoicePayment>();
		for (int i = 0; i < oldInvoice.getPayments().size(); i++) {
			Long id1 = oldInvoice.getPayments().get(i).getId();
			SalesInvoicePayment paiement = salesInvoicePaymentRepository.findById(id1).orElseThrow();
			tempPaiements.add(paiement);
		}
		oldInvoice.setPayments(newInvoiceDetails.getPayments());

		SalesInvoice invoiceUpdated = salesInvoiceRepository.save(oldInvoice);

		// test items updated
		for (SalesInvoiceItem oldItem : tempItems) {
			boolean exist = false;
			for (SalesInvoiceItem newItem : invoiceUpdated.getItems()) {
				if (oldItem.getId() == newItem.getId())
					exist = true;
			}
			if (!exist)
				salesInvoiceItemRepository.delete(oldItem);
		}

		// test paiements updated
		for (SalesInvoicePayment oldPaiement : tempPaiements) {
			boolean exist = false;
			for (SalesInvoicePayment newPaiement : invoiceUpdated.getPayments()) {
				if (oldPaiement.getId() == newPaiement.getId())
					exist = true;
			}
			if (!exist)
				salesInvoicePaymentRepository.delete(oldPaiement);
		}

		return ResponseEntity.ok(invoiceUpdated);
	}

	@GetMapping("/new-reference")
	public int generateNewSalesInvoiceReference() {
		long nb = salesInvoiceRepository.count();
		int newReference = (int) (nb + 1);
		while (salesInvoiceRepository.findByReference(newReference) != null) {
			newReference += 1;
		}
		return newReference;
	}

	@GetMapping("/{reference}/exist")
	public Optional<Boolean> getSalesInvoiceByReference(@PathVariable int reference) {
		if (salesInvoiceRepository.findByReference(reference) != null)
			return Optional.of(true);
		return Optional.of(false);
	}

	@GetMapping("/{id}/pdf")
	public Optional<Boolean> createSalesInvoicePDF(@PathVariable Long id) {
		Optional<SalesInvoice> invoice = salesInvoiceRepository.findById(id);
		if (invoice.isPresent()) {
			MyCompany myCompany = myCompanyRepository.findAll().stream().findFirst().get();
			Optional<GeneralSetting> generalSetting = generalSettingReppository.findAll().stream().findFirst();
			float tva;
			if (invoice.get().getCustomer().getTypeCustomer() == CustomerType.OFFSHORE) {
				tva = 0;
			} else {
				tva = generalSetting.get().getTva();
			}
			SalesInvoicePDF pdfGenerator = new SalesInvoicePDF(invoice.get(), myCompany, tva);
			pdfGenerator.generateSalesInvoicePDF(invoice.get().getReference());
			return Optional.of(true);
		}
		return Optional.of(false);
	}

	// getSalesInvoicesByDates
	@GetMapping("/by-dates")
	public List<SalesInvoice> getSalesInvoicesByDates(@RequestParam(value = "date-start", required = true) Long d1, // time
																													// in
																													// milliseconds
			@RequestParam(value = "date-end", required = true) Long d2 // time in milliseconds
	) {

		Date dateStart = new Date(d1);
		Date dateEnd = new Date(d2);
		List<SalesInvoice> invoices = new ArrayList<SalesInvoice>();

		ArrayList<SalesInvoice> other = new ArrayList<SalesInvoice>();
		try {
			invoices = salesInvoiceRepository.findByCreationDateBetween(dateStart, dateEnd).orElse(other);

		} catch (Exception e) {
		}
		return invoices;
	}

	// getSalesInvoicesByDates
	@GetMapping("/suppliers/{supplierId}/by-dates")
	public List<ProductSaled> filterProductsSaledBySupplier(@PathVariable int supplierId,
			@RequestParam(value = "date-start", required = true) Long d1, // time in milliseconds
			@RequestParam(value = "date-end", required = true) Long d2 // time in milliseconds
	) {

		List<SalesInvoice> invoices = getSalesInvoicesByDates(d1, d2);
		List<ProductSaled> allProductsSaled = new ArrayList<ProductSaled>();
		List<ProductSaled> filtredProductsSaled ;
		
		// extract the products sailed from the sales invoices between the 2 dates
		for (SalesInvoice salesInvoice : invoices) {
			for (SalesInvoiceItem salesInvoiceItem : salesInvoice.getItems()) {
				boolean exist = false;
				for (ProductSaled productSaled : allProductsSaled) {
					if (productSaled.getId() == salesInvoiceItem.getProduct().getId()) {
						productSaled.setQteSaled(productSaled.getQteSaled() + salesInvoiceItem.getQuantity());
						productSaled.setTotal(productSaled.getQteSaled() * salesInvoiceItem.getUnitPurchasePriceEuro());
						exist = true;
						break;
					}
				}
				if (!exist) {
					ProductSaled p = new ProductSaled();
					p.setId(salesInvoiceItem.getProduct().getId());
					p.setLotNo(salesInvoiceItem.getLotNo());
					p.setName(salesInvoiceItem.getProduct().getDetails().getName());
					p.setQteSaled(salesInvoiceItem.getQuantity());
					p.setTotal(salesInvoiceItem.getQuantity() * salesInvoiceItem.getUnitPurchasePriceEuro());
					p.setUnitPurchasePrice(salesInvoiceItem.getUnitPurchasePriceEuro());
					allProductsSaled.add(p);
				}
			}
		}
		// return only the product bayed from this supplier
		filtredProductsSaled = new ArrayList<ProductSaled>();
		for (ProductSaled productSaled : allProductsSaled) {						
			if(isProductPurchasedFromThisSupplier(supplierId, productSaled.getId())) {
				double qteInStock = getQteInStockByProductId(productSaled.getId());
				productSaled.setQteInStock(qteInStock);
				filtredProductsSaled.add(productSaled);
			}
		}
		
		return filtredProductsSaled;
	}
	
	@GetMapping("/suppliers/{supplierId}/products/{productId}")
	public boolean isProductPurchasedFromThisSupplier(@PathVariable int supplierId,@PathVariable int productId) {
		List<PurchaseInvoice> invoices = new ArrayList<PurchaseInvoice>();
		Optional<Supplier> supplier = supplierRepository.findById(supplierId);
		if (supplier.isPresent()) {
			try {
				invoices = purchaseInvoiceRepository.findBySupplier(supplier.get());
				for (PurchaseInvoice purchaseInvoice : invoices) {
					for (PurchaseInvoiceItem purchaseInvoiceItem : purchaseInvoice.getItems()) {
						if (purchaseInvoiceItem.getProduct().getId()==productId)
							return true;
					}
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	public float getQteInStockByProductId(@PathVariable int id) {

		float qteInStock = 0;
		Product product = productRepository.findById(id).get();
		
		if (product != null) {
			List<PurchaseInvoiceItem> purchases = purchaseInvoiceItemRepository.findByProduct(product);
			for (PurchaseInvoiceItem purchaseInvoiceItem : purchases) {
				qteInStock += purchaseInvoiceItem.getQteInStock();
			}
		}
		
		return qteInStock;
	}
}
