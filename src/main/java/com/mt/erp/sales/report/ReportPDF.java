package com.mt.erp.sales.report;

import java.text.SimpleDateFormat;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.mt.erp.purchases.supplier.Supplier;
import com.mt.erp.settings.company.MyCompany;

public class ReportPDF {
	private static final String DOCUMENT_TITLE = "Rapport";
	private Report report;
	private String fileName;
	private Document document;
	private MyCompany myCompany;


	public ReportPDF(Report Report, MyCompany myCompany) {
		this.report = Report;
		this.myCompany = myCompany;
	}

	public void generateReportPDF(int reference) {

		fileName = "rapport_" + reference;

		ReportPdfStructure pdfStructure = new ReportPdfStructure(fileName);

		document = pdfStructure.createDocumentInstance();

		document = pdfStructure.addHeaderTop(document, myCompany);

		Supplier supplier = report.getSupplier();

		document = pdfStructure.addHeaderBottom(document, DOCUMENT_TITLE, report, myCompany, supplier);

		// part 1 : Sales
		document = pdfStructure.addtitleRectangle(document, "Vendite – SARRA CHIMIE SARL");
		
		String title = report.getTitle();
		//title = "Mese di Ottobere 2020";
		if (title != null && !title.isEmpty()) {
			document = pdfStructure.addTitle(document, title);
		}
		
		document = pdfStructure.addContentSalesReportTable(document, report);

		// part 2 : Stock
		document.add(new AreaBreak());//write the next in a new page

		document = pdfStructure.addtitleRectangle(document, "STOCK Rimanente – SARRA CHIMIE SARL");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = "Date : " + formatter.format(report.getPeriodeEnd());
		document = pdfStructure.addTitle(document, strDate);
		
		document = pdfStructure.addContentStockStatusReportTable(document, report);

		document.close();

	}

}
