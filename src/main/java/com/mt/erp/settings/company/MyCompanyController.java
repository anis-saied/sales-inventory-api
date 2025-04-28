package com.mt.erp.settings.company;

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
@RequestMapping("/api/v1/my-company")
public class MyCompanyController {

	@Autowired
	private MyCompanyReppository myCompanyRepository;

	// get myCompany information
	@GetMapping
	public ResponseEntity<MyCompany> getMyCompanyInfo() {
		Optional<MyCompany> company = myCompanyRepository.findAll().stream().findFirst();
		if (company.isEmpty()){
			/*
			 * company = Optional.of(new MyCompany()); myCompanyRepository.save(new
			 * MyCompany());
			 */
			return ResponseEntity.ok(myCompanyRepository.save(new MyCompany()));
			//return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(company.get());
	}

	// update myCompany information
	@PutMapping("/update")
	public ResponseEntity<MyCompany> updateMyCompanyInfo(@RequestBody MyCompany company) {
		company.getDetails().setLastUpdatedAt(new Date());
		myCompanyRepository.save(company);
		return ResponseEntity.ok(company);
	}
}
