package com.mt.erp.sales.quote;

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

import com.mt.erp.sales.customer.Customer;
import com.mt.erp.sales.customer.CustomerRepository;
import com.mt.erp.settings.company.MyCompany;
import com.mt.erp.settings.company.MyCompanyReppository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/sales/quotes")
public class QuoteController {

	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private QuoteItemRepository quoteItemRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private MyCompanyReppository myCompanyRepository;
	
	@GetMapping
	public List<Quote> getAllQuotes() {
		return quoteRepository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Quote> getQuoteById(@PathVariable Long id) {
		return quoteRepository.findById(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteById(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		if (quoteRepository.findById(id).isPresent()) {
			quoteRepository.deleteById(id);
			response.put("deleted", true);
		} else {
			response.put("deleted", false);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public Quote createQuote(@RequestBody Quote quote) {
		//System.out.println(quote);
		for (QuoteItem quoteItem : quote.getItems()) {
			if(quoteItem.getCertificat().getId()==0) {
				quoteItem.setCertificat(null);
			}
			if(quoteItem.getProductAgainstType().getId()==0) {
				quoteItem.setProductAgainstType(null);
			}
		}
		Quote savedQuote = quoteRepository.save(quote);
		return savedQuote;
		
	}

	@PutMapping("/{id}")
	public Quote updateQutoe(@PathVariable Long id, @RequestBody Quote newQuoteDetails) {

		Quote oldQuote = quoteRepository.findById(id).get();
		// details
		oldQuote.getDetails().setDescription(newQuoteDetails.getDetails().getDescription());
		oldQuote.getDetails().setLastUpdatedAt(new Date());

		// specific to quote entity
		oldQuote.setCreationDate(newQuoteDetails.getCreationDate());
		oldQuote.setReference(newQuoteDetails.getReference());
		oldQuote.setPriceValidityDate(newQuoteDetails.getPriceValidityDate());
		oldQuote.setTitle(newQuoteDetails.getTitle());
		oldQuote.setTraduction(newQuoteDetails.isTraduction());
		

		// customer
		Customer customer = customerRepository.findById(newQuoteDetails.getCustomer().getId()).get();
		oldQuote.setCustomer(customer);

		// check if there is some items deleted
		// for each item in the old quote
		for (QuoteItem oldItem : oldQuote.getItems()) {
			// if the current item does not exist in the new quote : delete it
			boolean exist = false;
			for (QuoteItem newItem : newQuoteDetails.getItems()) {
				if (oldItem.getId() == newItem.getId())
					{exist = true;break;}
			}
			if (!exist)
				quoteItemRepository.delete(oldItem);
		}

		oldQuote.setItems(newQuoteDetails.getItems());

		for (QuoteItem quoteItem : oldQuote.getItems()) {
			if(quoteItem.getCertificat().getId()==0) {
				quoteItem.setCertificat(null);
			}
			if(quoteItem.getProductAgainstType().getId()==0) {
				quoteItem.setProductAgainstType(null);
			}
		}
		
		Quote quoteUpdated = quoteRepository.save(oldQuote);
		return quoteUpdated;
	}

	
	@GetMapping("/new-reference")
	public int generateNewQuoteReference() {
		long nb = quoteRepository.count();
		int newReference = (int) (nb + 1);
		while (quoteRepository.findByReference(newReference) != null) {
			newReference+=1;
		}
		return newReference;
	}
	

	@GetMapping("/{reference}/exist")
	public Optional<Boolean> getQuoteByReference(@PathVariable int reference) {
		if (quoteRepository.findByReference(reference)!=null)
			return Optional.of(true);
		return Optional.of(false);
	}

	@GetMapping("/{id}/pdf")
	public Optional<Boolean> createQuotePDF(@PathVariable Long id) {
		Optional<Quote> delivery = quoteRepository.findById(id);
		if (delivery.isPresent()) {
			MyCompany myCompany = myCompanyRepository.findAll().stream().findFirst().get();
			QuotePDF pdfGenerator = new QuotePDF(delivery.get(),myCompany);
			pdfGenerator.generateQuotePDF(delivery.get().getReference());
			return Optional.of(true);
		}
		return Optional.of(false);
	}

	
}
