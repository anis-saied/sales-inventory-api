package com.mt.erp.taxes;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mt.erp.sales.invoice.SalesInvoice;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/taxes")
public class TaxController {

	@Autowired
	private TaxRepository taxRepository;

	@GetMapping
	public List<Tax> getAllTaxes() {
		return taxRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tax> getTaxById(@PathVariable Long id) {
		Tax tax = taxRepository.findById(id).get();
		return ResponseEntity.ok(tax);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteTaxById(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		if (taxRepository.findById(id).isPresent()) {
			taxRepository.deleteById(id);
			response.put("deleted", Boolean.TRUE);
		} else {
			response.put("deleted", Boolean.FALSE);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Tax> createTax(@RequestBody Tax tax) {
		tax = taxRepository.save(tax);
		return ResponseEntity.ok(tax);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Tax> updateTax(@PathVariable Long id, @RequestBody Tax newTaxDetails) {
		Tax oldTax = taxRepository.findById(id).get();

		// details
		oldTax.getDetails().setLastUpdatedAt(new Date());

		oldTax.getDetails().setName(newTaxDetails.getDetails().getName());// libelle

		oldTax.setTaxType(newTaxDetails.getTaxType());
		oldTax.setAmount(newTaxDetails.getAmount());
		oldTax.setPaymentDate(newTaxDetails.getPaymentDate());

		Tax taxUpdated = taxRepository.save(oldTax);

		return ResponseEntity.ok(taxUpdated);
	}

	@GetMapping("/periode")
	public ResponseEntity<Float> getTaxesByPeriode(@RequestParam(value = "date-start", required = true) Long d1, // time
																													// in
																													// milliseconds
			@RequestParam(value = "date-end", required = true) Long d2 // time in milliseconds
	) {
		Date dateStart = new Date(d1);
		Date dateEnd = new Date(d2);
		List<Tax> other = new ArrayList<Tax>();

		float result = 0;
		try {
			List<Tax> taxes = taxRepository.findByCreationDateBetween(dateStart, dateEnd).orElse(other);
			for (Tax tax : taxes) {
				result += tax.getAmount();
			}
		} catch (Exception e) {
			return ResponseEntity.ok(0f);
		}
		return ResponseEntity.ok(result);
	}

}
