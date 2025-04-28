package com.mt.erp.sales.report;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.mt.erp.pdf.PDFUtil;
import com.mt.erp.pdf.PdfStructure;
import com.mt.erp.purchases.invoices.PurchaseInvoiceItem;
import com.mt.erp.purchases.supplier.Supplier;
import com.mt.erp.sales.invoice.SalesInvoice;
import com.mt.erp.sales.invoice.SalesInvoiceController;
import com.mt.erp.sales.invoice.SalesInvoiceItem;
import com.mt.erp.settings.company.MyCompany;
import com.mt.erp.stock.product.Product;

public class ReportPdfStructure extends PdfStructure {
	PDFUtil pdfUtil = new PDFUtil();
	
	DecimalFormat df = new DecimalFormat("####.###");

	private String pagamento = "SEP";

	
	public ReportPdfStructure(String fileName) {
		super("report", fileName);
	}

	public Document addHeaderBottom(Document document, String documentTitle, Report report, MyCompany myCompany,
			Supplier supplier) {
		// TODO Auto-generated method stub
		return document;
	}

	// part : commons
	public Document addtitleRectangle(Document document, String titleRectangle) {
		table = new Table(1);
		table.useAllAvailableWidth();
		table.setMarginTop(15f);
		table.setHorizontalAlignment(HorizontalAlignment.LEFT);
		cell = new Cell();
		text = new Text(titleRectangle);
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(styleBlack10BlueFontGrayBackGround);
		cell.setBackgroundColor(new DeviceRgb(247, 247, 247));
		cell.setPadding(5f);
		cell.setMinWidth(200F);
		table.addCell(cell);
		document.add(table);

		return document;
	}

	// part 1 : sales
	public Document addTitle(Document document, String title) {
		table = new Table(1);
		table.useAllAvailableWidth();
		table.setMarginTop(10f);
		table.setHorizontalAlignment(HorizontalAlignment.LEFT);
		table.setBorder(Border.NO_BORDER);
		cell = new Cell();
		text = new Text(title);
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(styleRed12);
		cell.setBorder(Border.NO_BORDER);
		cell.setPadding(5f);
		cell.setMinWidth(200F);
		table.addCell(cell);
		document.add(table);
		return document;
	}

	/**
	 * content : table of products saled
	 */

	public Document addContentSalesReportTable(Document document, Report report) {
		table = new Table(new float[] { 300F, 120F, 120F, 120F, 120F });
		table.useAllAvailableWidth();
		table.setMarginTop(15f);
		table.setBorder(Border.NO_BORDER);

		// table of products header

		// line 1 : cell 1 : description
		cell = new Cell();
		text = new Text("Colorants");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(ReportTableHeaderCellStyle);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// line 1 : cell : Contre type
		cell = new Cell();
		text = new Text("TOTAL Kg");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(ReportTableHeaderCellStyle);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// line 1 : cell : UM
		cell = new Cell();
		text = new Text("P.U - Euro");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(ReportTableHeaderCellStyle);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// line 1 : cell : PU. HT (DT)
		cell = new Cell();
		text = new Text("Total Euro");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(ReportTableHeaderCellStyle);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// line 1 : cell : Discount
		cell = new Cell();
		text = new Text("Pagamento");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(ReportTableHeaderCellStyle);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);



		// products sales in this period
		// lines table of products : content
		int nbOfLines = report.getProductsSaled().size();

		for (int i = 0; i < nbOfLines; i++) {
			

			// col 0
			String description = report.getProductsSaled().get(i).getName();
			description = description != null ? description.toUpperCase() : " ";

			// col 1
			double totalKg = report.getProductsSaled().get(i).getQteSaled();
			String totalKgString = String.valueOf(df.format(totalKg));

			// col 2
			
			double purchaseUnitPrice=report.getProductsSaled().get(i).getUnitPurchasePrice();
			String purchaseUnitPriceString = pdfUtil.formatMoney(String.valueOf(df.format(purchaseUnitPrice)));

			// col 3
			double totalSalesEuro = report.getProductsSaled().get(i).getTotal();
			String totalSalesEuroString = pdfUtil.formatMoney(String.valueOf(df.format(totalSalesEuro)));

			// col 4 : empty

			for (int j = 0; j < 5; j++) {
				cell = new Cell();
				cell.setTextAlignment(TextAlignment.CENTER);
				cell.addStyle(ReportTableContentCellStyle);
				cell.setBackgroundColor(headerRowBackgroundColor);
				cell.setBorder(Border.NO_BORDER);
				cell.setBorderLeft(new SolidBorder(ColorConstants.BLACK, 0.5f));
				cell.setBorderRight(new SolidBorder(ColorConstants.BLACK, 0.5f));
				cell.setBold();
				cell.setPadding(5);
				cell.setHeight(15);
				Color backgroundColor = i % 2 == 0 ? new DeviceRgb(250, 250, 250) : ColorConstants.WHITE;

				cell.setBackgroundColor(backgroundColor);

				switch (j) {
				case 0:
					text = new Text(description);
					cell.setTextAlignment(TextAlignment.LEFT);
					break;
				case 1:
					text = new Text(totalKgString);
					break;
				case 2:
					text = new Text(purchaseUnitPriceString);
					cell.setFontColor(ColorConstants.RED);
					break;
				case 3:
					text = new Text(totalSalesEuroString);
					cell.setFontColor(new DeviceRgb(34, 139, 34));
					break;
				case 4:
					text = new Text("");
					break;
				}
				cell.add(new Paragraph(text));
				table.addCell(cell);
			}

		}

		// last line
		for (int j = 0; j < 5; j++) {
			cell = new Cell();
			cell.setTextAlignment(TextAlignment.CENTER);
			cell.addStyle(ReportTableContentCellStyle);
			cell.setBackgroundColor(headerRowBackgroundColor);
			cell.setBorder(Border.NO_BORDER);
			cell.setBorderLeft(new SolidBorder(ColorConstants.BLACK, 0.5f));
			cell.setBorderRight(new SolidBorder(ColorConstants.BLACK, 0.5f));
			cell.setPadding(5);
			cell.setHeight(15);
			Color backgroundColor = new DeviceRgb(250, 250, 0);
			cell.setBackgroundColor(backgroundColor);
			cell.setBold();

			switch (j) {
			case 0:
				text = new Text("TOTAL--Kg");
				cell.setTextAlignment(TextAlignment.LEFT);
				cell.setFontColor(ColorConstants.RED);
				// cell.setFontSize(12f);
				break;
			case 1:
				double totalConsommation = report.getTotalConsommation();
				String totalConsommationString = pdfUtil.formatMoney(String.valueOf(df.format(totalConsommation)));
				text = new Text(totalConsommationString);
				break;
			case 2:
				text = new Text("");
				break;
			case 3:
				double totalEuroToPay = report.getTotalEuroToPay();
				String totalEuroToPayString = pdfUtil.formatMoney(String.valueOf(df.format(totalEuroToPay)));
				text = new Text(totalEuroToPayString + " EURO");
				cell.setFontColor(new DeviceRgb(34, 139, 34));
				break;
			case 4:
				SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
				String strDate = formatter.format(report.getPaymentDate());
				pagamento = String.valueOf(strDate);
				text = new Text(strDate);
				cell.setFontColor(ColorConstants.RED);
				break;
			}
			cell.add(new Paragraph(text));
			cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
			cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
			table.addCell(cell);
		}

		document.add(table);
		return document;
	}

	// part 2 (table 2) : rest in stock

	public Document addContentStockStatusReportTable(Document document, Report report) {

		table = new Table(new float[] { 300F, 300F });
		table.setMarginLeft(70);
		table.setMarginRight(70);
		table.setMarginTop(10f);
		table.setBorder(Border.NO_BORDER);

		// table of products header

		// line 1 : cell 1
		cell = new Cell();
		text = new Text("Colorants");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(ReportTableHeaderCellStyle);
		cell.setFontColor(ColorConstants.RED);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// line 1 : cell 1
		cell = new Cell();
		text = new Text("TOTAL Kg");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(ReportTableHeaderCellStyle);
		cell.setFontColor(ColorConstants.RED);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// all products purchased from this supplier
		// extracted from allPurchaseInvoiceItems of PurchaseInvoices from this supplier
		
		// lines table of products : content
		int nbOfLines = report.getProductsSaled().size();

		double totalKgQteInStock = report.getTotalInStock();

		for (int i = 0; i < nbOfLines; i++) {
			// col 0
			String description = "";
			description = report.getProductsSaled().get(i).getName();
			description = description != null ? description.toUpperCase() : " ";

			// for each purchased product
			double qteInStock = report.getProductsSaled().get(i).getQteInStock();		
			String qteInStockString = String.valueOf(qteInStock);
			

			for (int j = 0; j < 2; j++) {
				cell = new Cell();
				cell.setTextAlignment(TextAlignment.CENTER);
				cell.addStyle(ReportTableContentCellStyle);
				cell.setBackgroundColor(headerRowBackgroundColor);
				cell.setBorder(Border.NO_BORDER);
				cell.setBorderLeft(new SolidBorder(ColorConstants.BLACK, 0.5f));
				cell.setBorderRight(new SolidBorder(ColorConstants.BLACK, 0.5f));
				cell.setBold();
				cell.setPadding(5);
				cell.setHeight(15);
				Color backgroundColor = i % 2 == 0 ? new DeviceRgb(250, 250, 250) : ColorConstants.WHITE;
				cell.setBackgroundColor(backgroundColor);

				switch (j) {
				case 0:
					text = new Text(description);
					cell.setTextAlignment(TextAlignment.LEFT);
					break;

				case 1:
					text = new Text(qteInStockString);
					cell.setTextAlignment(TextAlignment.CENTER);
					cell.setFontColor(new DeviceRgb(34, 139, 34));
					break;
				}
				cell.add(new Paragraph(text));
				table.addCell(cell);
			}

		}

		// last line
		String totalKgQteInStockString = String.valueOf(df.format(totalKgQteInStock));
		for (int j = 0; j < 2; j++) {
			cell = new Cell();
			cell.setTextAlignment(TextAlignment.CENTER);
			cell.addStyle(ReportTableContentCellStyle);
			cell.setBackgroundColor(headerRowBackgroundColor);
			cell.setBorder(Border.NO_BORDER);
			cell.setBorderLeft(new SolidBorder(ColorConstants.BLACK, 0.5f));
			cell.setBorderRight(new SolidBorder(ColorConstants.BLACK, 0.5f));
			cell.setPadding(5);
			cell.setHeight(15);
			Color backgroundColor = new DeviceRgb(250, 250, 0);
			cell.setBackgroundColor(backgroundColor);
			cell.setBold();

			switch (j) {
			case 0:
				text = new Text("TOTAL--Kg");
				cell.setTextAlignment(TextAlignment.LEFT);
				cell.setFontColor(ColorConstants.RED);
				// cell.setFontSize(12f);
				break;
			case 1:
				text = new Text(totalKgQteInStockString + " Kg");
				cell.setFontColor(ColorConstants.RED);
				break;
			}
			cell.add(new Paragraph(text));
			cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
			cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
			table.addCell(cell);
		}

		document.add(table);
		return document;
	}

}
