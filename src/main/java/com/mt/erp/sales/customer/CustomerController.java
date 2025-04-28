package com.mt.erp.sales.customer;

import java.util.ArrayList;
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

import com.mt.erp.sales.delivery.Delivery;
import com.mt.erp.sales.delivery.DeliveryRepository;
import com.mt.erp.sales.invoice.SalesInvoice;
import com.mt.erp.sales.invoice.SalesInvoiceRepository;
import com.mt.erp.sales.quote.Quote;
import com.mt.erp.sales.quote.QuoteRepository;
import com.mt.erp.sales.report.Report;
import com.mt.erp.sales.report.ReportRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private DeliveryRepository deliveryRepository;

	@Autowired
	private SalesInvoiceRepository salesInvoiceRepository;

	@Autowired
	private ReportRepository reportRepository;

	// Get one
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Customer>> getCustomerInfo(@PathVariable int id) {
		Optional<Customer> customer = customerRepository.findById(id);
		return ResponseEntity.ok(customer);
	}

	// Get all
	@GetMapping("")
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	// delete customer rest api

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable Integer id) {
		Map<String, Boolean> response = new HashMap<>();

		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isEmpty()) {
			response.put("deleted", Boolean.FALSE);
		} else {
			// quote
			List<Quote> quotes = quoteRepository.findByCustomer(customer.get());
			for (Quote quote : quotes) {
				quoteRepository.delete(quote);
			}

			// delivery
			List<Delivery> deliveries = deliveryRepository.findByCustomer(customer.get());
			for (Delivery delivery : deliveries) {
				deliveryRepository.delete(delivery);
			}

			// sales invoices
			Optional<List<SalesInvoice>> invoices = salesInvoiceRepository.findByCustomer(customer.get());
			if (invoices.isPresent()) {
				for (SalesInvoice invoice : invoices.get()) {

					// before deleting this salesInvoice : delete there association from all reports
					List<SalesInvoice> salesInvoices;
					List<Report> reports = reportRepository.findAll();

					for (Report report : reports) {
						salesInvoices = new ArrayList<SalesInvoice>(report.getSalesInvoices());
						for (SalesInvoice salesInvoice : report.getSalesInvoices()) {
							if (salesInvoice.getId() == invoice.getId()) {
								salesInvoices.remove(salesInvoice);
							}
						}
						report.setSalesInvoices(salesInvoices);
						reportRepository.save(report);
					}

					salesInvoiceRepository.delete(invoice);

				}
			}

			// finally : delete customer
			customerRepository.deleteById(customer.get().getId());
			response.put("deleted", Boolean.TRUE);
		}
		return ResponseEntity.ok(response);
	}

	// create employee rest api
	@PostMapping("")
	public Customer createEmployee(@RequestBody Customer customer) {
		System.out.println(customer);
		// return null;
		return customerRepository.save(customer);
	}

	// update employee rest api

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer customerDetails) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {
			// details
			customer.get().getDetails().setName(customerDetails.getDetails().getName());
			customer.get().getDetails().setDescription(customerDetails.getDetails().getDescription());
			customer.get().getDetails().setLastUpdatedAt(new Date());
			// contact
			customer.get().getContact().setAddress(customerDetails.getContact().getAddress());
			customer.get().getContact().setCity(customerDetails.getContact().getCity());
			customer.get().getContact().setCountry(customerDetails.getContact().getCountry());
			customer.get().getContact().setEmail(customerDetails.getContact().getEmail());
			customer.get().getContact().setMobile(customerDetails.getContact().getMobile());
			customer.get().getContact().setPostalCode(customerDetails.getContact().getPostalCode());
			// company contact
			customer.get().getCompanyContact().setAddress(customerDetails.getCompanyContact().getAddress());
			customer.get().getCompanyContact().setCity(customerDetails.getCompanyContact().getCity());
			customer.get().getCompanyContact().setCountry(customerDetails.getCompanyContact().getCountry());
			customer.get().getCompanyContact().setEmail(customerDetails.getCompanyContact().getEmail());
			customer.get().getCompanyContact().setFax1(customerDetails.getCompanyContact().getFax1());
			customer.get().getCompanyContact().setFax2(customerDetails.getCompanyContact().getFax2());
			customer.get().getCompanyContact().setMobile(customerDetails.getCompanyContact().getMobile());
			customer.get().getCompanyContact().setPhone(customerDetails.getCompanyContact().getPhone());
			customer.get().getCompanyContact().setPostalCode(customerDetails.getCompanyContact().getPostalCode());
			customer.get().getCompanyContact().setWebsite(customerDetails.getCompanyContact().getWebsite());
			// other
			customer.get().setCustomerActivity(customerDetails.getCustomerActivity());
			customer.get().setTypeCustomer(customerDetails.getTypeCustomer());
			customer.get().setIban(customerDetails.getIban());
			customer.get().setResponsible(customerDetails.getResponsible());
			customer.get().setSwiftCode(customerDetails.getSwiftCode());
			customer.get().setTaxRegistrationNo(customerDetails.getTaxRegistrationNo());
			customer.get().setTradeRegisterNo(customerDetails.getTradeRegisterNo());

			// update
			Customer updatedCustomer = customerRepository.saveAndFlush(customer.get());
			return ResponseEntity.ok(updatedCustomer);
		}
		return ResponseEntity.ok(null);
	}
}
