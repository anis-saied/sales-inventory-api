package com.mt.erp.stock.product.certificat;

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
@RequestMapping("/api/v1/certificats")
public class CertificatController {

	@Autowired
	private CertificatRepository certificatRepository;

	@Autowired
	private ProductRepository productRepository;

	@GetMapping
	public List<Certificat> getAllCertificats() {
		return certificatRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Certificat> getCertificatById(@PathVariable Long id) {
		Certificat certificat = certificatRepository.findById(id).get();
		return ResponseEntity.ok(certificat);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteCertificatById(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		if (certificatRepository.findById(id).isPresent()) {

			List<Certificat> certificats;
			// delete the association with product
			List<Product> products = productRepository.findAll();
			for (Product product : products) {
				certificats = new ArrayList<Certificat>(product.getCertificats());
				for (Certificat certificat : product.getCertificats()) {
					if (certificat.getId() == id) {
						certificats.remove(certificat);
					}
				}
				product.setCertificats(certificats);
				productRepository.save(product);
			}

			certificatRepository.deleteById(id);
			response.put("deleted", Boolean.TRUE);
		} else {
			response.put("deleted", Boolean.FALSE);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Certificat> createCertificat(@RequestBody Certificat certificat) {
		certificat = certificatRepository.save(certificat);
		return ResponseEntity.ok(certificat);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Certificat> updateCertificat(@PathVariable Long id,
			@RequestBody Certificat newCertificatDetails) {
		Certificat oldCertificat = certificatRepository.findById(id).get();

		// details
		oldCertificat.getDetails().setLastUpdatedAt(new Date());
		oldCertificat.getDetails().setName(newCertificatDetails.getDetails().getName());// name
		oldCertificat.getDetails().setDescription(newCertificatDetails.getDetails().getDescription());// description

		oldCertificat.setDeliveryDate(newCertificatDetails.getDeliveryDate());

		oldCertificat.setFileName(newCertificatDetails.getFileName());

		Certificat CertificatUpdated = certificatRepository.save(oldCertificat);

		return ResponseEntity.ok(CertificatUpdated);
	}

}
