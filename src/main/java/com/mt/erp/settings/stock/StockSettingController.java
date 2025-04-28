package com.mt.erp.settings.stock;

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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/stock-settings")
public class StockSettingController {

	@Autowired
	private StockSettingRepository repository;

	// get myCompany information
	@GetMapping
	public ResponseEntity<StockSetting> getStockSettingInfo() {
		Optional<StockSetting> stockSetting = repository.findAll().stream().findFirst();
		if (stockSetting.isEmpty()){
			stockSetting = Optional.of(new StockSetting());
			repository.save(stockSetting.get());
			return ResponseEntity.ok(stockSetting.get());
			//return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(stockSetting.get());
	}

	// update myCompany information
	@PutMapping("/update")
	public ResponseEntity<StockSetting> updateStockSettingInfo(@RequestBody StockSetting stockSetting) {
		stockSetting.getDetails().setLastUpdatedAt(new Date());
		repository.save(stockSetting);
		return ResponseEntity.ok(stockSetting);
	}
	
	@GetMapping("/default")
	public StockSetting getDefaultStockSetting(){
		return repository.findByAppliedByDefaultTrue();
		//return null;
	}
}
