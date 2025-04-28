package com.mt.erp.stock.product;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mt.erp.purchases.invoices.PurchaseInvoiceItem;
import com.mt.erp.purchases.invoices.PurchaseInvoiceItemRepository;
import com.mt.erp.sales.delivery.DeliveryItem;
import com.mt.erp.sales.delivery.DeliveryItemRepository;
import com.mt.erp.settings.stock.StockSetting;
import com.mt.erp.settings.stock.StockSettingRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StockSettingRepository stockSettingRepository;

	@Autowired
	private PurchaseInvoiceItemRepository purchaseInvoiceItemRepository;

	@Autowired
	private DeliveryItemRepository deliveryItemRepository;

	// Get one
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Product>> getProductInfo(@PathVariable int id) {
		Optional<Product> product = productRepository.findById(id);
		return ResponseEntity.ok(product);
	}

	// Get all
	@GetMapping("")
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	// delete Product rest api

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Integer id) {
		Map<String, Boolean> response = new HashMap<>();

		Optional<Product> product = productRepository.findById(id);
		if (product.isEmpty()) {
			response.put("not deleted", Boolean.FALSE);
		} else {
			productRepository.deleteById(product.get().getId());
			response.put("deleted", Boolean.TRUE);
		}
		return ResponseEntity.ok(response);
	}

	// create Product rest api
	@PostMapping("")
	public Product createProduct(@RequestBody Product product) {
		System.out.println(product);
		// return null;
		if (product.getStockSetting().isAppliedByDefault() == false)
			product.setStockSetting(stockSettingRepository.save(product.getStockSetting()));
		return productRepository.save(product);
	}

	// update Product rest api

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			// details
			product.get().getDetails().setName(productDetails.getDetails().getName());
			product.get().getDetails().setDescription(productDetails.getDetails().getDescription());
			product.get().getDetails().setLastUpdatedAt(new Date());

			// other
			product.get().setOriginCountry(productDetails.getOriginCountry());

			product.get().setCertificats(productDetails.getCertificats());
			product.get().setFamilies(productDetails.getFamilies());
			product.get().setProductsAgainstType(productDetails.getProductsAgainstType());

			StockSetting s = productDetails.getStockSetting();

			// save new specific stock setting
			if (productDetails.getStockSetting().isAppliedByDefault() == false)
				s = stockSettingRepository.save(productDetails.getStockSetting());

			product.get().setStockSetting(s);

			// update
			Product updatedProduct = productRepository.save(product.get());
			return ResponseEntity.ok(updatedProduct);
		}
		return ResponseEntity.ok(null);
	}

	// Get one

	@GetMapping("/{id}/qte-in-stock")
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
