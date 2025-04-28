package com.mt.erp.settings.general;

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
@RequestMapping("/api/v1/settings/general")
public class GeneralSettingController {

	@Autowired
	private GeneralSettingReppository generalSettingReppository;
	
	/**
	 * 
	 * @return the last applied currency, order by: last_update_date
	 */
	@GetMapping
	public ResponseEntity<Optional<GeneralSetting>> getGeneralSetting(){
		Optional<GeneralSetting> generalSetting = null;
		generalSetting = generalSettingReppository.findAll().stream().findFirst();		
		if (generalSetting.isEmpty()){
			generalSetting = Optional.of(new GeneralSetting());
			generalSetting = Optional.of(generalSettingReppository.save(generalSetting.get()));
		}
		return ResponseEntity.ok(generalSetting);		
	}
	
	// update myCompany information
		@PutMapping("/update")
		public ResponseEntity<GeneralSetting> updateGeneralSetting(@RequestBody GeneralSetting generalSetting) {
			generalSetting.getDetails().setLastUpdatedAt(new Date());
			generalSetting=generalSettingReppository.save(generalSetting);
			return ResponseEntity.ok(generalSetting);
		}
	

}
