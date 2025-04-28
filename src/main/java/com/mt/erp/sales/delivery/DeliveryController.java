package com.mt.erp.sales.delivery;

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

import com.mt.erp.purchases.invoices.PurchaseInvoiceItem;
import com.mt.erp.purchases.invoices.PurchaseInvoiceItemRepository;
import com.mt.erp.sales.customer.Customer;
import com.mt.erp.sales.customer.CustomerRepository;
import com.mt.erp.settings.company.MyCompany;
import com.mt.erp.settings.company.MyCompanyReppository;
import com.mt.erp.stock.product.Product;

@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/sales/deliveries")
@RestController
public class DeliveryController {

	@Autowired
	private DeliveryRepository deliveryRepository;

	@Autowired
	private DeliveryItemRepository deliveryItemRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PurchaseInvoiceItemRepository purchaseInvoiceItemRepository;

	@Autowired
	private MyCompanyReppository myCompanyRepository;
	
	
	@GetMapping
	public List<Delivery> getAllDeliveries() {
		return deliveryRepository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Delivery> getOneDelivery(@PathVariable Long id) {
		return deliveryRepository.findById(id);
	}

	@PostMapping
	public Delivery createDelivery(@RequestBody Delivery delivery) {
		System.out.println(delivery);
		return deliveryRepository.save(delivery);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteDelivery(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<>();
		if (deliveryRepository.findById(id).isPresent()) {
			deliveryRepository.deleteById(id);
			response.put("delete", Boolean.TRUE);
		} else {
			response.put("delete", Boolean.FALSE);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Delivery> updateDelivery(@PathVariable Long id, @RequestBody Delivery deliveryDetails) {

		Optional<Delivery> delivery = deliveryRepository.findById(id);

		// details
		delivery.get().getDetails().setName(deliveryDetails.getDetails().getName());
		delivery.get().getDetails().setDescription(deliveryDetails.getDetails().getDescription());
		delivery.get().getDetails().setLastUpdatedAt(new Date());

		// other
		delivery.get().setReference(deliveryDetails.getReference());
		delivery.get().setDueDate(deliveryDetails.getDueDate());
		delivery.get().setCreationDate(deliveryDetails.getCreationDate());
		delivery.get().setTraduction(deliveryDetails.isTraduction());
		delivery.get().setTitle(deliveryDetails.getTitle());

		// delivery info
		delivery.get().setBilled(deliveryDetails.isBilled());
		delivery.get().setStatus(deliveryDetails.getStatus());
		delivery.get().setReceived(deliveryDetails.isReceived());
		delivery.get().setReceptionDate(deliveryDetails.getReceptionDate());


		// transporter info
		delivery.get().setTransporterName(deliveryDetails.getTransporterName());
		delivery.get().setTransporterCardIdNo(deliveryDetails.getTransporterCardIdNo());
		delivery.get().setTransporterRegistrationNo(deliveryDetails.getTransporterRegistrationNo());
		
		//stock control
		delivery.get().setStockControl(deliveryDetails.getStockControl());
		
		// customer
		Customer customer = customerRepository.findById(deliveryDetails.getCustomer().getId()).get();
		delivery.get().setCustomer(customer);

		// for each item in the old delivery
		for (DeliveryItem oldItem : delivery.get().getItems()) {
			// if the item not exist in the new delivery : delete it
			boolean exist = false;
			for (DeliveryItem newItem : deliveryDetails.getItems()) {
				if (oldItem.getId() == newItem.getId())
					exist = true;
			}
			if (!exist)
				deliveryItemRepository.delete(oldItem);
		}

		// items
		delivery.get().setItems(deliveryDetails.getItems());

		Delivery updatedDelivery = deliveryRepository.save(delivery.get());
		return ResponseEntity.ok(updatedDelivery);
	}

	// Delivery stock receptions APIs
	
	@GetMapping("/{id}/recepetion/save")
	public ResponseEntity<Delivery> receiveDelivery(@PathVariable Long id) {
		Optional<Delivery> delivery = deliveryRepository.findById(id);
		if (delivery.isPresent()) {
			// update qteInStock pour chaque produit livré et reçu de ce BL
			List<DeliveryItem> deliveryItems = delivery.get().getItems();
			PurchaseInvoiceItem item;
			
			// step 1: test if all will work correctly
			// to not have a stock <0
			for (DeliveryItem deliveryItem : deliveryItems) {
				Product product = deliveryItem.getProduct();
				String lotNo = deliveryItem.getLotNo();
				item = purchaseInvoiceItemRepository.findByProductAndLotNo(product, lotNo);
				if (item != null) {
					if (item.getQteInStock() - deliveryItem.getQuantity() < 0) {
						return ResponseEntity.ok(null);
					}
				}
			}


			// step 2: do the work
			// Increment the quantity saled (sortie de stock) pour chaque achat (purchaseInvoiceItem)
			// the stock will decrease 
			for (DeliveryItem deliveryItem : deliveryItems) {
				Product product = deliveryItem.getProduct();
				String lotNo = deliveryItem.getLotNo();
				item = purchaseInvoiceItemRepository.findByProductAndLotNo(product, lotNo);
				if (item != null) {
					item.setQteSaled(item.getQteSaled()  + deliveryItem.getQuantity());
					purchaseInvoiceItemRepository.save(item);
				}
			}
			
			//step 3: mark the delivery as received
			delivery.get().setReceived(true);
			delivery.get().setReceptionDate(new Date());

			// step 4: return the delivery updated
			return ResponseEntity.ok(deliveryRepository.save(delivery.get()));
		}
		return null;
	}

	
	@GetMapping("/{id}/recepetion/cancel")
	public ResponseEntity<Delivery> cancelReceiveDelivery(@PathVariable Long id) {
		Optional<Delivery> delivery = deliveryRepository.findById(id);
		if (delivery.isPresent()) {
			// update qteInStock pour chaque produit livré et reçu de ce BL
			List<DeliveryItem> deliveryItems = delivery.get().getItems();
			PurchaseInvoiceItem item;
			
			// step 1: do the work
			// decrement the quantity saled (sortie de stock) pour chaque achat (purchaseInvoiceItem)
			// The stock will increase
			for (DeliveryItem deliveryItem : deliveryItems) {
				Product product = deliveryItem.getProduct();
				String lotNo = deliveryItem.getLotNo();
				item = purchaseInvoiceItemRepository.findByProductAndLotNo(product, lotNo);
				if (item != null) {
					item.setQteSaled(item.getQteSaled()  - deliveryItem.getQuantity());
					purchaseInvoiceItemRepository.save(item);
				}
			}
			
			//step 3: mark the delivery as not received
			delivery.get().setReceived(false);
			delivery.get().setReceptionDate(new Date());

			// step 4: return the delivery updated
			return ResponseEntity.ok(deliveryRepository.save(delivery.get()));
		}
		return null;
	}
	
	// Delivery Reference APIs
	@GetMapping("/new-reference")
	public int generateNewDeliveryReference() {
		long nb = deliveryRepository.count();
		int newReference = (int) (nb + 1);
		while (deliveryRepository.findByReference(newReference) != null) {
			newReference += 1;
		}
		return newReference;
	}

	@GetMapping("/{reference}/exist")
	public  Optional<Boolean> getDeliveryByReference(@PathVariable int reference) {
		if (deliveryRepository.findByReference(reference) != null)
			return Optional.of(true);
		return Optional.of(false);
	}
	
	// Delivery PDF APIs

	@GetMapping("/{id}/pdf")
	public Optional<Boolean> createDeliveryPDF(@PathVariable Long id) {
		Optional<Delivery> delivery = deliveryRepository.findById(id);
		if (delivery.isPresent()) {
			MyCompany myCompany = myCompanyRepository.findAll().stream().findFirst().get();
			DeliveryPDF pdfGenerator = new DeliveryPDF(delivery.get(),myCompany);
			pdfGenerator.generateDeliveryPDF(delivery.get().getReference());
			return Optional.of(true);
		}
		return Optional.of(false);
	}
	
	

	

}
