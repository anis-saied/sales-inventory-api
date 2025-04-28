package com.mt.erp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mt.erp.sales.invoice.SalesInvoiceRepository;

public class Test {

	
	public static void main(String[] args) throws ParseException {
		

		DateTimeFormatter mmddyyyyFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

		String date1 = "2020-01-06";		
		String date2 = "2021-01-25";
		
		LocalDate localDate1 = LocalDate.parse(date1);
		localDate1.format(mmddyyyyFormatter);		
		System.out.println(localDate1);
		
		LocalDate localDate2 = LocalDate.parse(date2 );
		localDate2.format(mmddyyyyFormatter);
		System.out.println(localDate2);
		
		
		List<List<String>> r = DateUtil.prepareTabOfPeriodes(localDate1.toString(), localDate2.toString());
		Date dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(r.get(0).get(0));
		System.out.println(dateStart);
		

		

	}

}
