package com.mt.erp.sales.report;


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

import com.mt.erp.purchases.supplier.Supplier;
import com.mt.erp.purchases.supplier.SupplierRepository;
import com.mt.erp.settings.company.MyCompany;
import com.mt.erp.settings.company.MyCompanyReppository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private MyCompanyReppository myCompanyRepository;


	@GetMapping
	public List<Report> getAllReports() {
		return reportRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Report> getReportById(@PathVariable Long id) {
		Report report = reportRepository.findById(id).get();
		return ResponseEntity.ok(report);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteReportById(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		if (reportRepository.findById(id).isPresent()) {
			reportRepository.deleteById(id);
			response.put("deleted", Boolean.TRUE);
		} else {
			response.put("deleted", Boolean.FALSE);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Report> createReport(@RequestBody Report report) {
		report = reportRepository.save(report);
		return ResponseEntity.ok(report);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report newReportDetails) {
		Report oldReport = reportRepository.findById(id).get();

		oldReport.setReference(newReportDetails.getReference());
		oldReport.setCreationDate(newReportDetails.getCreationDate());
		oldReport.setTraduction(newReportDetails.isTraduction());
		oldReport.setTitle(newReportDetails.getTitle());
		oldReport.setFileName(newReportDetails.getFileName());

		// details
		oldReport.getDetails().setLastUpdatedAt(new Date());
		oldReport.getDetails().setName(newReportDetails.getDetails().getName());// name
		oldReport.getDetails().setDescription(newReportDetails.getDetails().getDescription());// description

		// cusotmer
		Supplier supplier = supplierRepository.findById(newReportDetails.getSupplier().getId()).get();
		oldReport.setSupplier(supplier);

		oldReport.setSalesInvoices(newReportDetails.getSalesInvoices());

		Report reportUpdated = reportRepository.save(oldReport);

		return ResponseEntity.ok(reportUpdated);
		/*
		 * // copy sales invoices List<SalesInvoice> tempSalesInvoices = new
		 * ArrayList<SalesInvoice>();
		 * 
		 * for (int i = 0; i < oldReport.getSalesInvoices().size(); i++) { Long id1 =
		 * oldReport.getSalesInvoices().get(i).getId(); SalesInvoice invoice =
		 * salesInvoiceRepository.findById(id1).orElseThrow();
		 * tempSalesInvoices.add(invoice); }
		 * 
		 * 
		 * 
		 * // test sales invoices updated for (SalesInvoice oldInvoice :
		 * tempSalesInvoices) { boolean exist = false; for (SalesInvoicePayment
		 * newInvoice : reportUpdated.getSalesInvoices()) { if (oldInvoice.getId() ==
		 * newInvoice.getId()) exist = true; } if (!exist)
		 * salesInvoicePaymentRepository.delete(oldInvoice); }
		 */
	}

	@GetMapping("/new-reference")
	public int generateNewReportReference() {
		long nb = reportRepository.count();
		int newReference = (int) (nb + 1);
		while (reportRepository.findByReference(newReference) != null) {
			newReference += 1;
		}
		return newReference;
	}

	@GetMapping("/{reference}/exist")
	public Optional<Boolean> getReportByReference(@PathVariable int reference) {
		if (reportRepository.findByReference(reference) != null)
			return Optional.of(true);
		return Optional.of(false);
	}

	/*
	 * @GetMapping("/{id}/pdf") public Optional<Boolean>
	 * createReportPDFById(@PathVariable Long id) { Optional<Report> report =
	 * reportRepository.findById(id); if (report.isPresent()) { MyCompany myCompany
	 * = myCompanyRepository.findAll().stream().findFirst().get();
	 * 
	 * // extract from report // the supplier Supplier supplier =
	 * report.get().getSupplier();
	 * 
	 * List<PurchaseInvoice> PurchaseInvoices; List<PurchaseInvoiceItem>
	 * allPurchaseInvoiceItems = new ArrayList<PurchaseInvoiceItem>(); ;
	 * PurchaseInvoices = purchaseInvoiceRepository.findBySupplier(supplier); for
	 * (PurchaseInvoice purchaseInvoice : PurchaseInvoices) { for
	 * (PurchaseInvoiceItem purchaseInvoiceItem : purchaseInvoice.getItems()) {
	 * allPurchaseInvoiceItems.add(purchaseInvoiceItem); } }
	 * 
	 * ReportPDF pdfGenerator = new ReportPDF(report.get(), myCompany);
	 * pdfGenerator.generateReportPDF(report.get().getReference()); return
	 * Optional.of(true); } return Optional.of(false); }
	 */

	@PostMapping("/pdf")
	public void createReportPDF(@RequestBody Report report) {

		MyCompany myCompany = myCompanyRepository.findAll().stream().findFirst().get();

		ReportPDF pdfGenerator = new ReportPDF(report, myCompany);
		pdfGenerator.generateReportPDF(report.getReference());
		
	}
}
