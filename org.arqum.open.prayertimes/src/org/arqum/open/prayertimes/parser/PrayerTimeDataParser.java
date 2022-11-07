package org.arqum.open.prayertimes.parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.arqum.open.prayertimes.model.DayPrayerTimes;
import org.arqum.open.prayertimes.model.Prayer;
import org.arqum.open.prayertimes.model.PrayerTime;
import org.arqum.open.prayertimes.util.DateTimeUtils;

public class PrayerTimeDataParser {

	public static List<DayPrayerTimes> parse(int year, List<String> rawPrayerTimesData) {
		List<DayPrayerTimes> result = new ArrayList<DayPrayerTimes>();
		
		Month currentMonth = null;
		for(String row: rawPrayerTimesData) {
			char firstChar = row.charAt(0);
			if(firstChar >= '1' && firstChar <= '9') {
				String[] contentArray = row.split("\\t");
				int dayOfTheMonth = Integer.parseInt(contentArray[0]);
				LocalDate date = LocalDate.of(year, currentMonth.getValue(), dayOfTheMonth);
				
				DayPrayerTimes dayPrayerTimes = new DayPrayerTimes(date);
				
				LocalTime fajrAthanTime = DateTimeUtils.parse(contentArray[1]);
				PrayerTime fajrPrayerTime = new PrayerTime(fajrAthanTime);
				dayPrayerTimes.setPrayerTime(Prayer.FAJR, fajrPrayerTime);
				
				LocalTime sunriseTime = DateTimeUtils.parse(contentArray[2]);
				dayPrayerTimes.setSunriseTime(sunriseTime);
				
				LocalTime dhuhrAthanTime = DateTimeUtils.parse(contentArray[3]);
				PrayerTime duhurPrayerTime = new PrayerTime(dhuhrAthanTime);
				dayPrayerTimes.setPrayerTime(Prayer.DHUHR, duhurPrayerTime);
				
				LocalTime asrAthanTime = DateTimeUtils.parse(contentArray[4]);
				PrayerTime asrPrayerTime = new PrayerTime(asrAthanTime);
				dayPrayerTimes.setPrayerTime(Prayer.ASR, asrPrayerTime);
				
				LocalTime maghribAthanTime = DateTimeUtils.parse(contentArray[5]);
				PrayerTime maghribPrayerTime = new PrayerTime(maghribAthanTime);
				dayPrayerTimes.setPrayerTime(Prayer.MAGHRIB, maghribPrayerTime);
				
				LocalTime ishaAthanTime = DateTimeUtils.parse(contentArray[6]);
				PrayerTime ishaPrayerTime = new PrayerTime(ishaAthanTime);
				dayPrayerTimes.setPrayerTime(Prayer.ISHA, ishaPrayerTime);
				
				result.add(dayPrayerTimes);
				
			} else {
				if(currentMonth == null) {
					currentMonth = Month.JANUARY;
				} else {
					currentMonth = currentMonth.plus(1);
				}
			}
			
		}
		return result;
	}

}
