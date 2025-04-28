package com.mt.erp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DateUtil {

	public static boolean isBisextilYear(int year) {
		if ((year % 4 == 0 && year % 100 > 0) || year % 400 == 0)
			return true;
		return false;
	}

	public static int lastDayOfTheMonth(int month, int year) {
		int day = 0;
		Integer months31[] = { 1, 3, 5, 7, 8, 10, 12 };
		List<Integer> m31 = Arrays.asList(months31);

		Integer months30[] = { 4, 6, 9, 11 };
		List<Integer> m30 = Arrays.asList(months30);

		if (month == 2) {
			day = 28;
			if (isBisextilYear(year))
				day = 29;
		} else if (m31.contains(month)) {
			day = 31;
		} else if (m30.contains(month)) {
			day = 30;
		}
		return day;
	}

	public static List<List<String>> prepareTabOfPeriodes(String date1, String date2) {
		List<List<String>> periodes = new ArrayList<List<String>>(); // of Dates
		List<String> periode;

		DateTimeFormatter mmddyyyyFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate localDateBegin;
		LocalDate localDateEnd;

		String periodeDate1;
		String periodeDate2;

		// start
		localDateBegin = LocalDate.parse(date1);
		localDateBegin.format(mmddyyyyFormatter);
		int dateStartDay = localDateBegin.getDayOfMonth();
		int dateStartMonth = localDateBegin.getMonthValue();
		int dateStartYear = localDateBegin.getYear();

		// end
		localDateEnd = LocalDate.parse(date2);
		localDateEnd.format(mmddyyyyFormatter);
		int dateEndDay = localDateEnd.getDayOfMonth();
		int dateEndMonth = localDateEnd.getMonthValue();
		int dateEndYear = localDateEnd.getYear();

		int day;
		int month = dateStartMonth;
		
		// rest of first month
		periode = new ArrayList<String>();
		
		periodeDate1 = createPeriodeDate1(dateStartDay, dateStartMonth, dateStartYear);
		periode.add(periodeDate1);

		periodeDate2 = createPeriodeDate2(dateStartMonth, dateStartYear);
		periode.add(periodeDate2);
		
		periodes.add(periode);

		if ((dateStartYear < dateEndYear) || (dateStartYear == dateEndYear && dateStartMonth < dateEndMonth)) {			
			// rest of months of first year
			for (month = dateStartMonth + 1; month <= 12; month++) {
				periode = new ArrayList<String>();
				periodeDate1 = createPeriodeDate1(1, month, dateStartYear);
				periode.add(periodeDate1);
				periodeDate2 = createPeriodeDate2(month, dateStartYear);
				periode.add(periodeDate2);
				periodes.add(periode);
			}
		}

		// intermediate years
		for (int year = dateStartYear + 1; year < dateEndYear; year++) {
			for (month = 1; month <= 12; month++) {
				periode = new ArrayList<String>();
				periodeDate1 = createPeriodeDate1(1, month, year);
				periode.add(periodeDate1);
				day = lastDayOfTheMonth(month, year);
				periodeDate2 = createPeriodeDate2(month, year);
				periode.add(periodeDate2);
				periodes.add(periode);
			}
		}

		if (dateStartYear < dateEndYear) {
			// last year
			for (month = 1; month < dateEndMonth; month++) {
				periode = new ArrayList<String>();
				periodeDate1 = createPeriodeDate1(1, month, dateEndYear);
				periode.add(periodeDate1);
				day = lastDayOfTheMonth(month, dateEndYear);
				periodeDate2 = createPeriodeDate2(month, dateEndYear);
				periode.add(periodeDate2);
				periodes.add(periode);
			}

			// last month of last year
			month = dateEndMonth;
			periode = new ArrayList<String>();
			periodeDate1 = createPeriodeDate1(1, month, dateEndYear);
			periode.add(periodeDate1);
			periodeDate2 = dateEndYear + "-" + month + "-" + dateEndDay;
			periodeDate2 = createPeriodeDate1(dateEndDay, month, dateEndYear);
			periode.add(periodeDate2);
			periodes.add(periode);
		}
		
		//System.out.println("periodes.size()-1 :"+periodes.get(periodes.size()-1));
		return periodes;
	}

	private static String createPeriodeDate2(int dateStartMonth, int dateStartYear) {
		String periodeDate2;
		int day = lastDayOfTheMonth(dateStartMonth, dateStartYear);
		periodeDate2 = dateStartYear + "-";
		if (dateStartMonth < 10)
			periodeDate2 += "0";
		periodeDate2 += dateStartMonth + "-" + day;
		return periodeDate2;
	}

	private static String createPeriodeDate1(int dateStartDay, int dateStartMonth, int dateStartYear) {
		String periodeDate1;
		periodeDate1 = dateStartYear + "-";
		if (dateStartMonth < 10)
			periodeDate1 += "0";
		periodeDate1 += dateStartMonth + "-";
		if (dateStartDay < 10)
			periodeDate1 += "0";
		periodeDate1 += dateStartDay;
		return periodeDate1;
	}
}
