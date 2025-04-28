package com.mt.erp.settings.currency;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mt.erp.settings.company.MyCompany;

@CrossOrigin(origins = "*")
@RestController 
@RequestMapping("/api/v1/currency")
public class CurrencyController {

	@Autowired
	private CurrencyReppository currencyReppository;
	
	/**
	 * 
	 * @return the last applied currency, order by: last_update_date
	 */
	@GetMapping("/applied/latest")
	public ResponseEntity<Currency> getAppliedCurrency(){
		Optional<Currency> currency = currencyReppository.findAll().stream().findFirst();

		if (currency.isEmpty()){
			return ResponseEntity.ok(currencyReppository.save(new Currency()));
		}
		return ResponseEntity.ok(currency.get());		
	}
	
	// update myCompany information
		@PutMapping("/update")
		public ResponseEntity<Currency> updateMyCompanyInfo(@RequestBody Currency currency) {
			currency.getDetails().setLastUpdatedAt(new Date());
			currency=currencyReppository.save(currency);
			return ResponseEntity.ok(currency);
		}
	

}
