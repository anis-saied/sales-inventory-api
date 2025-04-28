package com.mt.erp.stock.product.family;

import java.util.ArrayList;
import java.util.Date;
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

import com.mt.erp.stock.product.Product;
import com.mt.erp.stock.product.ProductRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/families")
public class FamilyController {

	@Autowired
	private FamilyRepository familyRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping
	public List<Family> getAllFamilies() {
		return familyRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Family> getFamilyById(@PathVariable Long id) {
		Family Family = familyRepository.findById(id).get();
		return ResponseEntity.ok(Family);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteFamilyById(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		if (familyRepository.findById(id).isPresent()) {
			List<Family> families ;
			//delete the association with product		
			List<Product> products =productRepository.findAll();
			for (Product product : products) {
				families = new  ArrayList<Family>(product.getFamilies());			
				for (Family family : product.getFamilies()) {
					if(family.getId()==id) {
						families.remove(family);						
					}
				}
				product.setFamilies(families);
				productRepository.save(product);
			}
			
			familyRepository.deleteById(id);
			
			response.put("deleted", Boolean.TRUE);
		} else {
			response.put("deleted", Boolean.FALSE);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Family> createFamily(@RequestBody Family family) {
		family = familyRepository.save(family);
		return ResponseEntity.ok(family);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Family> updateFamily(@PathVariable Long id, @RequestBody Family newFamilyDetails) {
		Family oldFamily = familyRepository.findById(id).get();

		// details
		oldFamily.getDetails().setLastUpdatedAt(new Date());
		oldFamily.getDetails().setName(newFamilyDetails.getDetails().getName());// name
		oldFamily.getDetails().setDescription(newFamilyDetails.getDetails().getDescription());// description

		oldFamily.setCustomTarif(newFamilyDetails.getCustomTarif());

		Family FamilyUpdated = familyRepository.save(oldFamily);

		return ResponseEntity.ok(FamilyUpdated);
	}

}
