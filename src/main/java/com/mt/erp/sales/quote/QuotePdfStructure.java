package com.mt.erp.sales.quote;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

public class QuotePdfStructure extends PdfStructure {
	PDFUtil pdfUtil = new PDFUtil();
	float totalCommande = 0;
	DecimalFormat df = new DecimalFormat("####.###");
	int nbOfColumns = 3;

	public QuotePdfStructure(String fileName) {
		super("devis", fileName);
	}

	public Document addContentQuoteTable(Document document, Quote quote) {
		boolean existCertificat = false;
		boolean existProductAgainstType = false;
		boolean existDiscount = false;

		List<QuoteItem> items = new ArrayList<QuoteItem>(quote.getItems());
		for (QuoteItem quoteItem : items) {
			if (quoteItem.getCertificat() != null) {
				existCertificat = true;
			}
			if (quoteItem.getProductAgainstType() != null) {
				existProductAgainstType = true;
			}
			if (quoteItem.getDiscount() > 0) {
				existDiscount = true;
			}
		}

		// 000
		if (!existCertificat && !existProductAgainstType && !existDiscount) {
			table = new Table(new float[] { 380F, 100F, 130F });
		}
		// 001
		else if (!existCertificat && !existProductAgainstType && existDiscount) {
			table = new Table(new float[] { 420F, 100F, 150F, 100F, 150F });
			nbOfColumns += 2;
		}
		// 010
		else if (!existCertificat && existProductAgainstType && !existDiscount) {
			table = new Table(new float[] { 420F, 360F, 100F, 150F });
			nbOfColumns += 1;
		}
		// 011
		else if (!existCertificat && existProductAgainstType && existDiscount) {
			table = new Table(new float[] { 420F, 350F, 90F, 130F, 90F, 130F });
			nbOfColumns += 3;
		}
		// 100
		else if (existCertificat && !existProductAgainstType && !existDiscount) {
			table = new Table(new float[] { 420F, 350F, 100F, 150F });
			nbOfColumns += 1;
		}
		// 101
		else if (existCertificat && !existProductAgainstType && existDiscount) {
			table = new Table(new float[] { 420F, 360F, 90F, 130F, 90F, 130F });
			nbOfColumns += 3;
		}
		// 110
		else if (existCertificat && existProductAgainstType && !existDiscount) {
			table = new Table(new float[] { 420F, 200F, 300F, 100F, 130F });
			nbOfColumns += 2;
		}
		// 111
		else if (existCertificat && existProductAgainstType && existDiscount) {
			table = new Table(new float[] { 380F, 160F, 200F, 90F, 120F, 90F, 120F });
			nbOfColumns += 4;
		}

		// 000 : certificat=0, product_against_type=0, discount=0
		// 001 : certificat=0, product_against_type=0, discount=1
		// 010 : certificat=0, product_against_type=1, discount=0
		// 011 : certificat=0, product_against_type=1, discount=1
		// 100 : certificat=1, product_against_type=0, discount=0
		// 101 : certificat=1, product_against_type=0, discount=1
		// 110 : certificat=1, product_against_type=1, discount=0
		// 111 : certificat=1, product_against_type=1, discount=1

		/**
		 * content : table of products
		 */
		// table = new Table(new float[] { 380F, 160F, 200F, 90F, 120F, 90F, 120F });
		table.useAllAvailableWidth();
		table.setMarginTop(25f);
		table.setBorder(Border.NO_BORDER);

		// table of products header

		// line 1 : cell 1 : description
		cell = new Cell();
		text = new Text("Descrizione/description");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(headerRowBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		if (existCertificat) {
			// line 1 : cell 2: Certificats
			cell = new Cell();
			text = new Text("Certificats");
			cell.add(new Paragraph(text));
			cell.setTextAlignment(TextAlignment.LEFT);
			cell.addStyle(tableHeaderCellStyle);
			cell.setBackgroundColor(headerRowBackgroundColor);
			cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
			cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
			cell.setPadding(5);
			table.addCell(cell);
		}

		if (existProductAgainstType) {
			// line 1 : cell 3: Produits Equivalents
			cell = new Cell();
			text = new Text("Produits Equivalents");
			cell.add(new Paragraph(text));
			cell.setTextAlignment(TextAlignment.LEFT);
			cell.addStyle(tableHeaderCellStyle);
			cell.setBackgroundColor(headerRowBackgroundColor);
			cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
			cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
			cell.setPadding(5);
			table.addCell(cell);
		}

		// line 1 : cell 4: UM
		cell = new Cell();
		text = new Text("UM");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(headerRowBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		if (existDiscount) {
			// line 1 : cell 5: PU.HT (DT)
			cell = new Cell();
			text = new Text("PU.HT (DT)");
			cell.add(new Paragraph(text));
			cell.setTextAlignment(TextAlignment.CENTER);
			cell.addStyle(tableHeaderCellStyle);
			cell.setBackgroundColor(headerRowBackgroundColor);
			cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
			cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
			cell.setPadding(5);
			table.addCell(cell);

			// line 1 : cell 6: Discount
			cell = new Cell();
			text = new Text("Discount");
			cell.add(new Paragraph(text));
			cell.setTextAlignment(TextAlignment.CENTER);
			cell.addStyle(tableHeaderCellStyle);
			cell.setBackgroundColor(headerRowBackgroundColor);
			cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
			cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
			cell.setPadding(5);
			table.addCell(cell);
		}

		// line 1 : cell 7: PU.HT (DT)
		cell = new Cell();
		text = new Text("PU.HT (DT)");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(headerRowBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// lines table of products : content
		int nbOfLines = items.size() > 10 ? items.size() : 10;

		for (int i = 0; i < nbOfLines; i++) {

			String description = "";
			String CertificatName = "";
			String productAgainstType = "";
			String unit = "";
			
			float unitSellingPriceHT;
			String unitSellingPriceHTString = "";
			
			float discount;
			String discountString = "";
			
			float unitSellingPriceHTAfterDiscount;
			String unitSellingPriceHTAfterDiscountString = "";

			if (i + 1 <= items.size()) {

				description = items.get(i).getProduct().getDetails().getName();
				description = description != null ? description.toUpperCase() : " ";

				if (items.get(i).getCertificat() != null) {
					CertificatName = items.get(i).getCertificat().getDetails() != null
							? items.get(i).getCertificat().getDetails().getName().toUpperCase()
							: " ";
				}

				if (items.get(i).getProductAgainstType() != null) {
					productAgainstType = items.get(i).getProductAgainstType().getDetails() != null
							? items.get(i).getProductAgainstType().getDetails().getName().toUpperCase()
							: " ";
				}

				unit = items.get(i).getProduct().getStockSetting().getUnit();
				unit = unit != null ? unit.toUpperCase() : " ";

				unitSellingPriceHT = items.get(i).getUnitSellingPrice() ;
				unitSellingPriceHTString = unitSellingPriceHT > 0 ? String.valueOf(df.format(unitSellingPriceHT)) : "";
				unitSellingPriceHTString = pdfUtil.formatMoney(unitSellingPriceHTString);

				discount = items.get(i).getDiscount();
				discountString = discount > 0 ? String.valueOf(df.format(discount)) + " %" : "";

				unitSellingPriceHTAfterDiscount = items.get(i).getUnitSellingPrice()* (1 - (items.get(i).getDiscount() / 100));
				unitSellingPriceHTAfterDiscountString = unitSellingPriceHTAfterDiscount > 0
						? String.valueOf(df.format(unitSellingPriceHTAfterDiscount))
						: "0";
				unitSellingPriceHTAfterDiscountString = pdfUtil.formatMoney(unitSellingPriceHTAfterDiscountString);

			}

			for (int j = 0; j < nbOfColumns; j++) {
				cell = new Cell();
				cell.setTextAlignment(TextAlignment.CENTER);
				cell.addStyle(tableContentCellStyle);
				cell.setBackgroundColor(headerRowBackgroundColor);
				cell.setBorder(Border.NO_BORDER);
				cell.setBorderLeft(new SolidBorder(ColorConstants.BLACK, 0.5f));
				cell.setBorderRight(new SolidBorder(ColorConstants.BLACK, 0.5f));

				cell.setPadding(5);
				cell.setHeight(12);
				Color backgroundColor = i % 2 == 0 ? new DeviceRgb(250, 250, 250) : ColorConstants.WHITE;

				cell.setBackgroundColor(backgroundColor);
				// 000
				if (!existCertificat && !existProductAgainstType && !existDiscount) {
					switch (j) {
					case 0:
						text = new Text(description);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 1:
						text = new Text(unit);
						break;
					case 2:
						text = new Text(unitSellingPriceHTAfterDiscountString);
						break;
					}
				}
				// 001
				else if (!existCertificat && !existProductAgainstType && existDiscount) {
					switch (j) {
					case 0:
						text = new Text(description);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 1:
						text = new Text(unit);
						break;
					case 2:
						text = new Text(unitSellingPriceHTString);
						break;
					case 3:
						text = new Text(discountString);
						break;
					case 4:
						text = new Text(unitSellingPriceHTAfterDiscountString);
						break;
					}
				}
				// 010
				else if (!existCertificat && existProductAgainstType && !existDiscount) {
					switch (j) {
					case 0:
						text = new Text(description);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 1:
						text = new Text(productAgainstType);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 2:
						text = new Text(unit);
						break;
					case 3:
						text = new Text(unitSellingPriceHTAfterDiscountString);
						break;
					}
				}
				// 011
				else if (!existCertificat && existProductAgainstType && existDiscount) {
					switch (j) {
					case 0:
						text = new Text(description);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 1:
						text = new Text(productAgainstType);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 2:
						text = new Text(unit);
						break;
					case 3:
						text = new Text(unitSellingPriceHTString);
						break;
					case 4:
						text = new Text(discountString);
						break;
					case 5:
						text = new Text(unitSellingPriceHTAfterDiscountString);
						break;
					}
				}
				// 100
				else if (existCertificat && !existProductAgainstType && !existDiscount) {
					switch (j) {
					case 0:
						text = new Text(description);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 1:
						text = new Text(CertificatName);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 2:
						text = new Text(unit);
						break;
					case 3:
						text = new Text(unitSellingPriceHTAfterDiscountString);
						break;
					}
				}
				// 101
				else if (existCertificat && !existProductAgainstType && existDiscount) {
					switch (j) {
					case 0:
						text = new Text(description);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 1:
						text = new Text(CertificatName);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 2:
						text = new Text(unit);
						break;
					case 3:
						text = new Text(unitSellingPriceHTString);
						break;
					case 4:
						text = new Text(discountString);
						break;
					case 5:
						text = new Text(unitSellingPriceHTAfterDiscountString);
						break;
					}
				}
				// 110
				else if (existCertificat && existProductAgainstType && !existDiscount) {
					switch (j) {
					case 0:
						text = new Text(description);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 1:
						text = new Text(CertificatName);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 2:
						text = new Text(productAgainstType);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 3:
						text = new Text(unit);
						break;
					case 4:
						text = new Text(unitSellingPriceHTAfterDiscountString);
						break;
					}
				}
				// 111
				else if (existCertificat && existProductAgainstType && existDiscount) {
					switch (j) {
					case 0:
						text = new Text(description);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 1:
						text = new Text(CertificatName);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 2:
						text = new Text(productAgainstType);
						cell.setTextAlignment(TextAlignment.LEFT);
						break;
					case 3:
						text = new Text(unit);
						break;
					case 4:
						text = new Text(unitSellingPriceHTString);
						break;
					case 5:
						text = new Text(discountString);
						break;
					case 6:
						text = new Text(unitSellingPriceHTAfterDiscountString);
						break;
					}
				}
				
				cell.add(new Paragraph(text));

				if (i == nbOfLines - 1) {// last line
					cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 0.5f));
				}

				table.addCell(cell);
			}
		}

		document.add(table);
		return document;
	}

	public Document addPriceValidityDate(Document document, Quote quote) {
		table = new Table(new float[] { 100F, 400F });
		// table = new Table(2);
		table.useAllAvailableWidth();
		table.setBorder(Border.NO_BORDER);
		table.setMarginTop(20f);
		table.setHorizontalAlignment(HorizontalAlignment.LEFT);

		// label
		cell = new Cell();
		text = new Text("Date de validité des prix :");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(styleBoldBlack8);
		cell.setBorder(Border.NO_BORDER);
		table.addCell(cell);

		// content
		cell = new Cell();		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(quote.getPriceValidityDate());
		text = new Text(strDate);
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(styleBlack8);
		cell.setBorder(Border.NO_BORDER);
		table.addCell(cell);

		document.add(table);
		return document;
	}
}
