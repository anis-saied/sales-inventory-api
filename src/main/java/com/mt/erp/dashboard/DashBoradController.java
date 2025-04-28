package com.mt.erp.dashboard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mt.erp.purchases.invoices.PurchaseInvoiceItem;
import com.mt.erp.purchases.invoices.PurchaseInvoiceItemRepository;
import com.mt.erp.sales.invoice.SalesInvoice;
import com.mt.erp.sales.invoice.SalesInvoiceRepository;
import com.mt.erp.stock.product.Product;
import com.mt.erp.stock.product.ProductRepository;
import com.mt.erp.util.DateUtil;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashBoradController {
	@Autowired
	private SalesInvoiceRepository salesInvoiceRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private PurchaseInvoiceItemRepository purchaseInvoiceItemRepository;

	// chiffres d'affaires
	// works: ok
	// tous les ca pour toutes les périodes
	@GetMapping("/chiffres-affaires/periode/all")
	public ResponseEntity<Float> getAllChiffresAffaires() {
		float ca = 0;
		try {
			List<SalesInvoice> invoices = salesInvoiceRepository.findAll();
			for (SalesInvoice salesInvoice : invoices) {
				ca += salesInvoice.getTotalAmount();
			}
		} catch (Exception e) {
			return ResponseEntity.ok(0f);
		}
		return ResponseEntity.ok(ca);
	}

	@GetMapping("/chiffres-affaires/periode")
	public ResponseEntity<Float> getChiffresAffairesByPeriode(
			@RequestParam(value = "date-start", required = true) String date1,
			@RequestParam(value = "date-end", required = true) String date2) throws ParseException {

		DateTimeFormatter mmddyyyyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate localDate1 = LocalDate.parse(date1);
		localDate1.format(mmddyyyyFormatter);

		LocalDate localDate2 = LocalDate.parse(date2);
		localDate2.format(mmddyyyyFormatter);

		float response = 0;

		String dateStart = localDate1.toString();
		String dateEnd = localDate2.toString();
		ChiffreAffaire ca = new ChiffreAffaire();
		try {
			Optional<Float> r = salesInvoiceRepository.findSumChiffreAffaireByPeriode(dateStart, dateEnd);
			ca.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStart));
			if (r.isPresent()) {
				ca.setValue(r.get());
			} else {
				ca.setValue(0);
			}
			response += ca.getValue();
		} catch (Exception e) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.ok(response);
	}

	// Qte en stock pour tous les produits
	@GetMapping("/qte-in-stock")
	public ResponseEntity<Float> getQuantityInStockForAllProducts() {
		float qteInStock = 0;
		try {
			List<Product> products = productRepository.findAll();
			for (Product product : products) {
				List<PurchaseInvoiceItem> purchases = purchaseInvoiceItemRepository.findByProduct(product);
				for (PurchaseInvoiceItem purchaseInvoiceItem : purchases) {
					qteInStock += purchaseInvoiceItem.getQteInStock();
				}
			}
		} catch (Exception e) {
			return ResponseEntity.ok(0f);
		}
		return ResponseEntity.ok(qteInStock);
	}
}
