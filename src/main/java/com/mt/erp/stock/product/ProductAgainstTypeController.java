package com.mt.erp.stock.product;

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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/products-against-type")
public class ProductAgainstTypeController {

	@Autowired
	private ProductAgainstTypeRepository productAgainstTypeRepository;

	@Autowired
	private ProductRepository productRepository;
	
	// Get one
	@GetMapping("/{id}")
	public ResponseEntity<Optional<ProductAgainstType>> getProductInfo(@PathVariable int id) {
		Optional<ProductAgainstType> product = productAgainstTypeRepository.findById(id);
		return ResponseEntity.ok(product);
	}

	// Get all
	@GetMapping("")
	public List<ProductAgainstType> getAllProducts() {
		return productAgainstTypeRepository.findAll();
	}

	// delete Product rest api

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Integer id) {
		Map<String, Boolean> response = new HashMap<>();

		Optional<ProductAgainstType> product = productAgainstTypeRepository.findById(id);
		if (product.isEmpty()) {
			response.put("not deleted", Boolean.FALSE);
		} else {
			
			List<ProductAgainstType> pagts;
			// delete the association with product
			List<Product> products = productRepository.findAll();
			for (Product p : products) {
				pagts = new ArrayList<ProductAgainstType>(p.getProductsAgainstType());
				for (ProductAgainstType pagt : p.getProductsAgainstType()) {
					if (pagt.getId() == id) {
						pagts.remove(pagt);
					}
				}
				p.setProductsAgainstType(pagts);
				productRepository.save(p);
			}
			
			productAgainstTypeRepository.deleteById(product.get().getId());
			response.put("deleted", Boolean.TRUE);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<ProductAgainstType> createProductAgainstType(@RequestBody ProductAgainstType product) {
		product = productAgainstTypeRepository.save(product);
		return ResponseEntity.ok(product);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductAgainstType> updateProductAgainstType(@PathVariable int id,
			@RequestBody ProductAgainstType newProductDetails) {
		
		ProductAgainstType oldProduct = productAgainstTypeRepository.findById(id).get();

		// details
		oldProduct.getDetails().setLastUpdatedAt(new Date());
		oldProduct.getDetails().setName(newProductDetails.getDetails().getName());// name
		oldProduct.getDetails().setDescription(newProductDetails.getDetails().getDescription());// description

		oldProduct.setProducer(newProductDetails.getProducer());

		ProductAgainstType productUpdated = productAgainstTypeRepository.save(oldProduct);

		return ResponseEntity.ok(productUpdated);
	}

}
