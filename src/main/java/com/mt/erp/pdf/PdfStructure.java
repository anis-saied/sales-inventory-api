
package com.mt.erp.pdf;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.mt.erp.invoices.Invoice;
import com.mt.erp.sales.customer.Customer;
import com.mt.erp.settings.company.MyCompany;

public class PdfStructure {

	protected String fileName;
	protected String destination;

	protected PdfDocument pdfDoc;
	protected Document document;

	protected Text content;
	protected Text title;
	protected Cell cell;
	protected Table table;
	protected Text text;
	protected Cell innerCell;

	Style styleWhiteBold10;

	protected Color headerRowBackgroundColor = new DeviceRgb(135, 206, 235);// ColorConstants.WHITE;// LIGHT_GRAY;
	protected Style styleWhite10;
	protected Style styleBlack10;
	protected Style styleBoldBlack10;
	protected Style styleBlack11;
	protected Style styleBoldBlack11;
	protected Style styleBlack12;
	protected Style styleBoldBlack12;
	protected Style styleBoldBlack16;
	protected Style styleCellBgRed;
	protected Style styleCellBgRedDocumentTitle;
	protected Style infoCompanyLabelStyle;
	protected Style tableHeaderCellStyle;
	protected Style infoCompanyContentStyle;
	protected Style tableContentCellStyle;
	protected Style styleBlack8;
	protected Style styleWhite8;
	protected Style styleBoldBlack8;
	protected Style styleWhiteBold8;
	protected Style styleBlack10BlueFontGrayBackGround;
	protected Style styleRed12;
	protected Style ReportTableHeaderCellStyle;
	protected Style ReportTableContentCellStyle;

	private static final String GENERAL_PATH = "C:/Users/IDEAL/git/sarrachimie-frontend-angular-main/src/assets/pdf";

	public PdfStructure(String fileType, String fileName) {
		this.fileName = fileName;
		destination = GENERAL_PATH + "/" + fileType + "/" + fileName + ".pdf";

	}

	public Document createDocumentInstance() {
		try {
			pdfDoc = new PdfDocument(new PdfWriter(destination));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		document = new Document(pdfDoc, PageSize.A4);
		document.setMargins(15, 36, 36, 36);

		configuration(document);

		return document;
	}

	/*
	 * HEADER
	 */

	// header : COMPANT NAME
	public Document addHeaderTop(Document document, MyCompany company) {
		table = new Table(new float[] { 150, 200, 200 });
		table.useAllAvailableWidth();
		table.setBorder(Border.NO_BORDER);
		cell = new Cell(1, 3);
		//cell.setHeight(8f);
		//cell.addStyle(styleWhite10);
		cell.setBackgroundColor(ColorConstants.RED);
		cell.setFontSize(10f);
		cell.setFontColor(ColorConstants.WHITE);
		cell.setPadding(0);
		cell.setBold();

		cell.setBackgroundColor(ColorConstants.RED);
		title = new Text(company.getDetails().getName().toUpperCase());
		cell.setFontColor(ColorConstants.WHITE);
		cell.add(new Paragraph(title));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.setBorder(Border.NO_BORDER);
		table.addCell(cell);
		document.add(table);

		// **********Slogant*************************************************/

		table = new Table(new float[] { 1, 145F });
		table.useAllAvailableWidth();
		table.setBorder(Border.NO_BORDER);

		// empty Cell
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 0.3f));
		cell.setHeight(6);
		table.addCell(cell);

		// Slogant content Cell
		cell = new Cell(2, 1);
		content = new Text(company.getSlogan().toUpperCase());
		cell.add(new Paragraph(content));
		cell.setTextAlignment(TextAlignment.RIGHT);
		cell.setFontSize(7f);
		cell.setHeight(16);
		cell.setBorder(Border.NO_BORDER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		table.addCell(cell);

		// empty Cell
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		table.addCell(cell);

		document.add(table);

		return document;
	}

	// companies info: my company, customer
	public Document addHeaderBottom(Document document, String documentTitle, Invoice invoice, MyCompany myCompany,
			Customer customer) {

		table = new Table(new float[] { 300F, 100F, 300F });
		table.useAllAvailableWidth();
		table.setBorder(Border.NO_BORDER);
		table.setMarginTop(10f);
		table.setMarginBottom(10f);

		// line 1
		// MyCompany : name
		cell = new Cell();
		Table innerTable = new Table(1);
		innerTable.useAllAvailableWidth();
		innerTable.setBorder(Border.NO_BORDER);
		innerTable.setBorder(new SolidBorder(ColorConstants.BLACK, 0.3f));

		// inner Table : line 1
		// adresse : content
		innerCell = new Cell();
		text = new Text(myCompany.getDetails().getName().toUpperCase());
		innerCell.add(new Paragraph(text));
		innerCell.setTextAlignment(TextAlignment.CENTER);
		innerCell.addStyle(infoCompanyLabelStyle);
		innerCell.setPaddingBottom(10);
		innerCell.setBorder(Border.NO_BORDER);
		innerCell.setBold();
		innerTable.addCell(innerCell);

		// inner Table : line 2.1
		// siege : content
		innerCell = new Cell();
		String address = myCompany.getCompanyContact().getSiegeAddress();
		address = address != "" ? address : "";
		String city = myCompany.getCompanyContact().getSiegeCity();
		city = city != "" ? ", " + city : "";
		String postalCode = myCompany.getCompanyContact().getSiegePostalCode();
		postalCode = postalCode != "" ? ", " + postalCode : "";
		String country = myCompany.getCompanyContact().getCountry();
		// country = country != "" ? ", " + country : "";
		country = ", Tunisie";
		text = new Text("Siège: " + address + city + postalCode + country);
		innerCell.add(new Paragraph(text));
		innerCell.setTextAlignment(TextAlignment.LEFT);
		innerCell.addStyle(infoCompanyContentStyle);
		innerCell.setBorder(Border.NO_BORDER);
		innerCell.setPaddingLeft(5);
		innerTable.addCell(innerCell);

		// inner Table : line 2.2
		// adresse : content

		innerCell = new Cell();
		address = myCompany.getCompanyContact().getAddress();
		address = address != "" ? address : "";
		city = myCompany.getCompanyContact().getCity();
		city = city != "" ? ", " + city : "";
		postalCode = myCompany.getCompanyContact().getPostalCode();
		postalCode = postalCode != "" ? ", " + postalCode : "";
		country = myCompany.getCompanyContact().getCountry();
		// country = country != "" ? ", " + country : "";
		country = ", Tunisie";
		text = new Text("Adresse 2: " + address + city + postalCode + country);
		innerCell.add(new Paragraph(text));
		innerCell.setTextAlignment(TextAlignment.LEFT);
		innerCell.addStyle(infoCompanyContentStyle);
		innerCell.setBorder(Border.NO_BORDER);
		innerCell.setPaddingLeft(5);
		innerTable.addCell(innerCell);

		// inner Table : line 3
		// RC : content
		innerCell = new Cell();
		String tradeRegisterNo = myCompany.getTradeRegisterNo() != null ? myCompany.getTradeRegisterNo().toUpperCase()
				: "";
		text = new Text("RC : " + tradeRegisterNo);
		innerCell.add(new Paragraph(text));
		innerCell.setTextAlignment(TextAlignment.LEFT);
		innerCell.addStyle(infoCompanyContentStyle);
		innerCell.setBorder(Border.NO_BORDER);
		innerCell.setPaddingLeft(5);
		innerTable.addCell(innerCell);

		// inner Table : line 4
		// Code TVA : content
		innerCell = new Cell();
		String taxRegistrationNo = myCompany.getTaxRegistrationNo() != null
				? myCompany.getTaxRegistrationNo().toUpperCase()
				: "";
		text = new Text("Matricule Fiscale : " + taxRegistrationNo);
		innerCell.add(new Paragraph(text));
		innerCell.setTextAlignment(TextAlignment.LEFT);
		innerCell.addStyle(infoCompanyContentStyle);
		innerCell.setBorder(Border.NO_BORDER);
		innerCell.setPaddingLeft(5);
		innerTable.addCell(innerCell);

		// inner Table : line 5
		// Email : content
		innerCell = new Cell();
		text = new Text("Email : " + myCompany.getCompanyContact().getEmail());
		innerCell.add(new Paragraph(text));
		innerCell.setTextAlignment(TextAlignment.LEFT);
		innerCell.addStyle(infoCompanyContentStyle);
		innerCell.setBorder(Border.NO_BORDER);
		innerCell.setPaddingLeft(5);
		innerTable.addCell(innerCell);

		// inner Table : line 4
		// tél : content
		innerCell = new Cell();
		text = new Text("Tél : " + myCompany.getCompanyContact().getPhone());
		innerCell.add(new Paragraph(text));
		innerCell.setTextAlignment(TextAlignment.CENTER);
		innerCell.addStyle(infoCompanyContentStyle);
		innerCell.setPaddingTop(10);
		innerCell.setBorder(Border.NO_BORDER);
		innerTable.addCell(innerCell);

		cell.add(innerTable);
		cell.setBorder(Border.NO_BORDER);
		table.addCell(cell);

		// line 2 : cell empty
		cell = new Cell();
		table.addCell(cell);
		cell.setBorder(Border.NO_BORDER);

		// customer : details
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		innerTable = new Table(1);
		innerTable.useAllAvailableWidth();
		// innerTable.setBorder(new SolidBorder(ColorConstants.BLACK, 0.3f));

		// document infos
		innerCell = new Cell();
		innerCell.setBorder(Border.NO_BORDER);
		Table innerTableDucoment = new Table(new float[] { 100F, 100F });
		innerTableDucoment.useAllAvailableWidth();

		// document title & reference
		Cell innerCellDocumentInfo = new Cell(1, 2);
		String number = formatReference(invoice.getReference());
		text = new Text(documentTitle + " " + number);
		innerCellDocumentInfo.add(new Paragraph(text));
		innerCellDocumentInfo.setTextAlignment(TextAlignment.CENTER);
		innerCellDocumentInfo.addStyle(infoCompanyLabelStyle);
		innerCellDocumentInfo.setFontSize(12f);
		innerCellDocumentInfo.setBorder(Border.NO_BORDER);
		// innerCellDocumentInfo.setBold();
		innerTableDucoment.addCell(innerCellDocumentInfo);

		// date
		innerCellDocumentInfo = new Cell(1, 2);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = "Date : " + formatter.format(invoice.getCreationDate());
		text = new Text(strDate);
		innerCellDocumentInfo.add(new Paragraph(text));
		innerCellDocumentInfo.setTextAlignment(TextAlignment.CENTER);
		innerCellDocumentInfo.addStyle(infoCompanyLabelStyle);
		innerCellDocumentInfo.setFontSize(12f);
		innerCellDocumentInfo.setBorder(Border.NO_BORDER);
		// innerCellDocumentInfo.setBold();
		innerTableDucoment.addCell(innerCellDocumentInfo);

		// client label
		innerCellDocumentInfo = new Cell(1, 2);
		innerCellDocumentInfo.add(new Paragraph("CLIENT"));
		innerCellDocumentInfo.setTextAlignment(TextAlignment.CENTER);
		innerCellDocumentInfo.addStyle(infoCompanyLabelStyle);
		innerCellDocumentInfo.setBorder(Border.NO_BORDER);
		innerCellDocumentInfo.setPaddingTop(50);
		innerTableDucoment.addCell(innerCellDocumentInfo);

		innerCell.add(innerTableDucoment);
		innerTable.addCell(innerCell);

		// inner Table : line 1
		// adresse : label
		innerCell = new Cell();
		text = new Text(customer.getDetails().getName().toUpperCase());
		innerCell.add(new Paragraph(text));
		innerCell.setTextAlignment(TextAlignment.CENTER);
		innerCell.addStyle(infoCompanyLabelStyle);
		innerCell.setBold();
		innerCell.setBorder(Border.NO_BORDER);
		innerCell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 0.3f));
		innerCell.setBorderLeft(new SolidBorder(ColorConstants.BLACK, 0.3f));
		innerCell.setBorderRight(new SolidBorder(ColorConstants.BLACK, 0.3f));
		innerTable.addCell(innerCell);

		// adresse : content
		innerCell = new Cell();
		address = customer.getCompanyContact().getAddress();
		address = address != null ? address : "";
		city = customer.getCompanyContact().getCity();
		city = city != null ? ", " + city : "";
		postalCode = customer.getCompanyContact().getPostalCode();
		postalCode = postalCode != null ? ", " + postalCode : "";
		country = customer.getCompanyContact().getCountry();
		country = country != null ? ", " + country : "";
		text = new Text(address + city + postalCode + country);
		innerCell.add(new Paragraph(text));
		innerCell.setTextAlignment(TextAlignment.LEFT);
		innerCell.addStyle(infoCompanyContentStyle);
		innerCell.setBorder(Border.NO_BORDER);
		innerCell.setBorderLeft(new SolidBorder(ColorConstants.BLACK, 0.3f));
		innerCell.setBorderRight(new SolidBorder(ColorConstants.BLACK, 0.3f));
		innerCell.setPaddingLeft(5);
		innerTable.addCell(innerCell);

		// inner Table : line 2

		// Code TVA : content
		innerCell = new Cell();
		taxRegistrationNo = customer.getTaxRegistrationNo() != null ? myCompany.getTaxRegistrationNo().toUpperCase()
				: "";
		text = new Text("TVA : " + taxRegistrationNo);
		innerCell.add(new Paragraph(text));
		innerCell.setTextAlignment(TextAlignment.LEFT);
		innerCell.addStyle(infoCompanyContentStyle);
		innerCell.setPaddingLeft(5);
		innerCell.setBorder(Border.NO_BORDER);
		innerCell.setBorderLeft(new SolidBorder(ColorConstants.BLACK, 0.3f));
		innerCell.setBorderRight(new SolidBorder(ColorConstants.BLACK, 0.3f));
		innerCell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 0.3f));
		innerCell.setPaddingBottom(20);
		innerTable.addCell(innerCell);

		cell.add(innerTable);
		cell.setBorder(Border.NO_BORDER);
		table.addCell(cell);

		document.add(table);

		return document;
	}

	/*
	 * CONTENT
	 */

	// header : title document
	public Document addContentTop(Document document, String title) {

		table = new Table(new float[] { 33F, 600F });
		// table = new Table(2);
		table.useAllAvailableWidth();
		table.setBorder(Border.NO_BORDER);
		// table.setMarginTop(30f);
		table.setMarginBottom(15f);
		table.setHorizontalAlignment(HorizontalAlignment.LEFT);

		// title : label
		cell = new Cell();
		text = new Text("Titre :");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(styleBoldBlack8);
		cell.setBorder(Border.NO_BORDER);
		table.addCell(cell);

		// title : content
		cell = new Cell();
		text = new Text(title);
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(styleBlack8);
		cell.setBorder(Border.NO_BORDER);
		table.addCell(cell);

		document.add(table);
		return document;

	}

	/*
	 * CONFIGURATION
	 */
	public void configuration(Document document) {

		styleBlack8 = new Style();
		styleBlack8.setFontColor(ColorConstants.BLACK);
		styleBlack8.setFontSize(8f);

		styleBoldBlack8 = new Style();
		styleBoldBlack8.setFontColor(ColorConstants.BLACK);
		styleBoldBlack8.setFontSize(8f);
		styleBoldBlack8.setBold();

		styleWhiteBold8 = new Style();
		styleWhiteBold8.setFontColor(ColorConstants.WHITE);
		styleWhiteBold8.setFontSize(8f);
		styleWhiteBold8.setBold();

		styleWhite8 = new Style();
		styleWhite8.setFontColor(ColorConstants.WHITE);
		styleWhite8.setFontSize(8f);

		styleWhite10 = new Style();
		styleWhite10.setFontColor(ColorConstants.WHITE);
		styleWhite10.setFontSize(10f);
		

		styleWhiteBold10 = new Style();
		styleWhiteBold10.setFontColor(ColorConstants.WHITE);
		styleWhiteBold10.setFontSize(10f);

		styleBlack10 = new Style();
		styleBlack10.setFontColor(ColorConstants.BLACK);
		styleBlack10.setFontSize(10f);

		styleBoldBlack10 = new Style();
		styleBoldBlack10.setFontColor(ColorConstants.BLACK);
		styleBoldBlack10.setFontSize(10f);
		styleBoldBlack10.setBold();

		styleBlack11 = new Style();
		styleBlack11.setFontColor(ColorConstants.BLACK);
		styleBlack11.setFontSize(11f);

		styleBoldBlack11 = new Style();
		styleBoldBlack11.setFontColor(ColorConstants.BLACK);
		styleBoldBlack11.setFontSize(11f);
		styleBoldBlack11.setBold();

		styleBlack12 = new Style();
		styleBlack12.setFontColor(ColorConstants.BLACK);
		styleBlack12.setFontSize(12f);

		styleBoldBlack12 = new Style();
		styleBoldBlack12.setFontColor(ColorConstants.BLACK);
		styleBoldBlack12.setFontSize(12f);
		styleBoldBlack12.setBold();

		styleBoldBlack16 = new Style();
		styleBoldBlack16.setFontColor(ColorConstants.BLACK);
		styleBoldBlack16.setFontSize(16f);
		styleBoldBlack16.setBold();

		styleCellBgRed = new Style();
		styleCellBgRed.setBackgroundColor(ColorConstants.RED);
		styleCellBgRed.setBorder(Border.NO_BORDER);
		styleCellBgRed.setFontColor(ColorConstants.WHITE);
		styleCellBgRed.setFontSize(10f);

		styleCellBgRedDocumentTitle = new Style();
		styleCellBgRedDocumentTitle.setBackgroundColor(ColorConstants.RED);
		styleCellBgRedDocumentTitle.setBorder(Border.NO_BORDER);
		styleCellBgRedDocumentTitle.setFontColor(ColorConstants.WHITE);
		styleCellBgRedDocumentTitle.setFontSize(16f);

		infoCompanyLabelStyle = styleBoldBlack8;
		infoCompanyContentStyle = styleBlack8;

		tableHeaderCellStyle = styleBoldBlack8;
		tableContentCellStyle = styleBlack8;

		ReportTableHeaderCellStyle = styleBoldBlack10;
		ReportTableContentCellStyle = styleBlack10;

		styleBlack10BlueFontGrayBackGround = new Style();
		styleBlack10BlueFontGrayBackGround.setFontColor(ColorConstants.BLUE);
		styleBlack10BlueFontGrayBackGround.setBackgroundColor(ColorConstants.LIGHT_GRAY);
		styleBlack10BlueFontGrayBackGround.setFontSize(10f);

		styleRed12 = new Style();
		styleRed12.setFontColor(ColorConstants.RED);
		styleRed12.setFontSize(12f);

	}

	// util
	public String formatReference(int reference) {
		String refString = String.valueOf(reference);
		int len = 4 - refString.length();
		for (int i = 0; i < len; i++) {
			refString = "0" + refString;
		}
		return refString;
	}

}
