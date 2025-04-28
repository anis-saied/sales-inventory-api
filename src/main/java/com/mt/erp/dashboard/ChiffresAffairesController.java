package com.mt.erp.dashboard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mt.erp.sales.invoice.SalesInvoice;
import com.mt.erp.sales.invoice.SalesInvoiceRepository;
import com.mt.erp.util.DateUtil;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/chiffres-affaires")
public class ChiffresAffairesController {

	String dateStart;
	String dateEnd;
	
	private ChiffreAffaire ca;
	private List<ChiffreAffaire> response;

	@Autowired
	private SalesInvoiceRepository salesInvoiceRepository;

	//001
	@GetMapping("/periode")
	public ResponseEntity<List<ChiffreAffaire>> getChiffresAffairesByPeriode(
			@RequestParam(value = "date-start", required = true) String date1,
			@RequestParam(value = "date-end", required = true) String date2) throws ParseException {
		response = new ArrayList<ChiffreAffaire>();

		DateTimeFormatter mmddyyyyFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

		LocalDate localDate1 = LocalDate.parse(date1);
		localDate1.format(mmddyyyyFormatter);

		LocalDate localDate2 = LocalDate.parse(date2);
		localDate2.format(mmddyyyyFormatter);

		List<List<String>> periodes = DateUtil.prepareTabOfPeriodes(localDate1.toString(), localDate2.toString());
		for (List<String> periode : periodes) {
			dateStart = periode.get(0);
			dateEnd = periode.get(1);		
			ca = new ChiffreAffaire();
			try {
				Optional<Float> r = salesInvoiceRepository.findSumChiffreAffaireByPeriode(dateStart, dateEnd);
				ca.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStart));
				if (r.isPresent()) {
					ca.setValue(r.get());
				} else {
					ca.setValue(0);
				}
				response.add(ca);
			} catch (Exception e) {
				return ResponseEntity.ok(new ArrayList<ChiffreAffaire>());
			}
		}
		return ResponseEntity.ok(response);
	}


	//011	
	@GetMapping("/products/{id}/periode")
	public ResponseEntity<List<ChiffreAffaire>> getChiffresAffairesByPeriodeAndProductId(
			@PathVariable int id,
			@RequestParam(value = "date-start", required = true) String date1,
			@RequestParam(value = "date-end", required = true) String date2) throws ParseException {
		response = new ArrayList<ChiffreAffaire>();

		DateTimeFormatter mmddyyyyFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

		LocalDate localDate1 = LocalDate.parse(date1);
		localDate1.format(mmddyyyyFormatter);

		LocalDate localDate2 = LocalDate.parse(date2);
		localDate2.format(mmddyyyyFormatter);

		List<List<String>> periodes = DateUtil.prepareTabOfPeriodes(localDate1.toString(), localDate2.toString());
		System.out.println("periodes="+periodes);
		for (List<String> periode : periodes) {			
			System.out.println("dateStart = " + periode.get(0) + "end=" + periode.get(1));
			dateStart = periode.get(0);
			dateEnd = periode.get(1);
			System.out.println("productid="+ id);
			ca = new ChiffreAffaire();
			try {
				Optional<Float> r = salesInvoiceRepository.findSumChiffreAffaireByPeriodeAndProduct(dateStart, dateEnd,id);
				System.out.println("r=");
				ca.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStart));
				if (r.isPresent()) {
					System.out.println(r.get());
					ca.setValue(r.get());
				} else {
					ca.setValue(0f);
				}
				response.add(ca);
				System.out.println("response="+response.size());
			} catch (Exception e) {
				System.out.println("Exception=");
				System.out.println(e.getMessage());
				return ResponseEntity.ok(new ArrayList<ChiffreAffaire>());
			}
		}
		return ResponseEntity.ok(response);
	}
	
	//101	
	@GetMapping("/customers/{id}/periode")
	public ResponseEntity<List<ChiffreAffaire>> getChiffresAffairesByPeriodeAndCustomer(
			@PathVariable int id,
			@RequestParam(value = "date-start", required = true) String date1,
			@RequestParam(value = "date-end", required = true) String date2) throws ParseException {
		response = new ArrayList<ChiffreAffaire>();

		DateTimeFormatter mmddyyyyFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

		LocalDate localDate1 = LocalDate.parse(date1);
		localDate1.format(mmddyyyyFormatter);

		LocalDate localDate2 = LocalDate.parse(date2);
		localDate2.format(mmddyyyyFormatter);

		List<List<String>> periodes = DateUtil.prepareTabOfPeriodes(localDate1.toString(), localDate2.toString());
		for (List<String> periode : periodes) {			
			dateStart = periode.get(0);
			dateEnd = periode.get(1);
			ca = new ChiffreAffaire();
			try {
				Optional<Float> r = salesInvoiceRepository.findSumChiffreAffaireByPeriodeAndCustomer(dateStart, dateEnd,id);
				ca.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStart));
				if (r.isPresent()) {
					ca.setValue(r.get());
				} else {
					ca.setValue(0f);
				}
				response.add(ca);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return ResponseEntity.ok(new ArrayList<ChiffreAffaire>());
			}
		}
		return ResponseEntity.ok(response);
	}	
	
	
	//111	
	@GetMapping("/customers/{customerId}/products/{ProductId}/periode")
	public ResponseEntity<List<ChiffreAffaire>> getChiffresAffairesByPeriodeAndCustomerAndProduct(
			@PathVariable int customerId, @PathVariable int ProductId,
			@RequestParam(value = "date-start", required = true) String date1,
			@RequestParam(value = "date-end", required = true) String date2) throws ParseException {
		response = new ArrayList<ChiffreAffaire>();

		DateTimeFormatter mmddyyyyFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

		LocalDate localDate1 = LocalDate.parse(date1);
		localDate1.format(mmddyyyyFormatter);

		LocalDate localDate2 = LocalDate.parse(date2);
		localDate2.format(mmddyyyyFormatter);

		List<List<String>> periodes = DateUtil.prepareTabOfPeriodes(localDate1.toString(), localDate2.toString());
		for (List<String> periode : periodes) {			
			dateStart = periode.get(0);
			dateEnd = periode.get(1);
			ca = new ChiffreAffaire();
			try {
				Optional<Float> r = salesInvoiceRepository.findSumChiffreAffaireByPeriodeAndCustomerAndProduct(dateStart, dateEnd,customerId,ProductId);
				ca.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStart));
				if (r.isPresent()) {
					ca.setValue(r.get());
				} else {
					ca.setValue(0f);
				}
				response.add(ca);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return ResponseEntity.ok(new ArrayList<ChiffreAffaire>());
			}
		}
		return ResponseEntity.ok(response);
	}		
}
