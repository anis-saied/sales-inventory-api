package com.mt.erp.pdf;

import java.util.Stack;

public class PDFUtil {

	public String formatReference(int reference) {
		String refString = String.valueOf(reference);
		int len = 4 - refString.length();
		for (int i = 0; i < len; i++) {
			refString = "0" + refString;
		}
		return refString;
	}

	// to do
	public String formatMoney(String amount) {
		String amountString = amount;
		int indexOfDot = amountString.indexOf(",");
		if (indexOfDot >= 0) {
			int decimalLen = amountString.substring(indexOfDot + 1).length();
			int len = 3 - decimalLen;
			for (int i = 0; i < len; i++) {
				amountString = amountString + "0";
			}
		} else {
			amountString = amountString + ",000";
		}

		return addThousandSeparator(amountString);
	}

	private String addThousandSeparator(String numberAstring) {
				//add thousand separator
		Stack<Character> numbers = new Stack<Character>();
		
		for (int i = 0; i < numberAstring.indexOf(","); i++) {
			numbers.push(numberAstring.charAt(i));			
		}
		
		numberAstring = numberAstring.substring(numberAstring.indexOf(","));
		
		int i=0;
		while(!numbers.isEmpty()) {
			numberAstring = numbers.lastElement() +numberAstring;
			numbers.pop();
			i++;
			numberAstring = i%3==0 ? "."+numberAstring : numberAstring;			
		}
		
		if (numberAstring.startsWith(".")) {
			numberAstring = numberAstring.substring(1);
		}
		return numberAstring;
	}

	// to do
	public String formatQuantity(String quantity) {
		String quantityString = quantity;
		int indexOfDot = quantityString.indexOf(",");
		if (indexOfDot >= 0) {
			int decimalLen = quantityString.substring(indexOfDot + 1).length();
			int len = 3 - decimalLen;
			for (int i = 0; i < len; i++) {
				quantityString = quantityString + "0";
			}
		}
		return quantityString;
	}

	public static void main(String[] args) {
		PDFUtil pdfutil = new PDFUtil();
		/*
		 * DecimalFormat df = new DecimalFormat("####.###"); float quantity = 1.0f;
		 * System.out.println("quantity=" + quantity); String quantityString =
		 * String.valueOf(df.format(quantity)); System.out.println("quantityString=" +
		 * quantityString); System.out.println("formatMoney(quantityString)=" +
		 * pdfutil.formatMoney(quantityString));
		 */
		String amount = "123456,78";
		System.out.println(amount);
		System.out.println(pdfutil.formatMoney(amount));
	}
}
