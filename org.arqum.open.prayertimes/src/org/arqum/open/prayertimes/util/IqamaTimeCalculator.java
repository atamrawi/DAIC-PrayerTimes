package org.arqum.open.prayertimes.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.arqum.open.prayertimes.model.DayPrayerTimes;
import org.arqum.open.prayertimes.model.Prayer;
import org.arqum.open.prayertimes.model.PrayerTime;

public class IqamaTimeCalculator {

	public static void calculate(List<DayPrayerTimes> dayPrayerTimesList) {
		for(int index = 0; index < dayPrayerTimesList.size(); index++) {
			DayPrayerTimes dayPrayerTimes = dayPrayerTimesList.get(index);
			boolean daylightSavingTime = occursInDaylightSavingPeriod(dayPrayerTimes.getDate());
			int rangeStartIndex = getCalculationRangeStartIndex(index, dayPrayerTimes.getDay());
			int rangeEndIndex = rangeStartIndex + 6;
			
			if(rangeStartIndex >= 0 && rangeEndIndex < dayPrayerTimesList.size()) {
				List<DayPrayerTimes> rangeSubList = dayPrayerTimesList.subList(rangeStartIndex, rangeEndIndex + 1);
				
				LocalTime maxFajrPrayerTime = LocalTime.MIN;
				LocalTime maxAsrPrayerTime = LocalTime.MIN;
				LocalTime maxMaghribPrayerTime = LocalTime.MIN;
				LocalTime maxIshaPrayerTime = LocalTime.MIN;
				for(DayPrayerTimes subDayPrayerTime: rangeSubList) {
					PrayerTime fajrPrayerTime = subDayPrayerTime.gePrayerTime(Prayer.FAJR);
					if(maxFajrPrayerTime.isBefore(fajrPrayerTime.getAthanTime())) {
						maxFajrPrayerTime = fajrPrayerTime.getAthanTime();
					}
					
					PrayerTime asrPrayerTime = subDayPrayerTime.gePrayerTime(Prayer.ASR);
					if(maxAsrPrayerTime.isBefore(asrPrayerTime.getAthanTime())) {
						maxAsrPrayerTime = asrPrayerTime.getAthanTime();
					}
					
					PrayerTime maghribPrayerTime = subDayPrayerTime.gePrayerTime(Prayer.MAGHRIB);
					if(maxMaghribPrayerTime.isBefore(maghribPrayerTime.getAthanTime())) {
						maxMaghribPrayerTime = maghribPrayerTime.getAthanTime();
					}
					
					PrayerTime ishaPrayerTime = subDayPrayerTime.gePrayerTime(Prayer.ISHA);
					if(maxIshaPrayerTime.isBefore(ishaPrayerTime.getAthanTime())) {
						maxIshaPrayerTime = ishaPrayerTime.getAthanTime();
					}
				}
				
				
				maxFajrPrayerTime = maxFajrPrayerTime.plusMinutes(20);
				maxFajrPrayerTime = DateTimeUtils.roundMinutesToNexMultipleOf15(maxFajrPrayerTime);
				dayPrayerTimes.gePrayerTime(Prayer.FAJR).setIqamaTime(maxFajrPrayerTime);
				
				
				if(daylightSavingTime) {
					dayPrayerTimes.gePrayerTime(Prayer.DHUHR).setIqamaTime(LocalTime.of(13, 30));
				} else {
					dayPrayerTimes.gePrayerTime(Prayer.DHUHR).setIqamaTime(LocalTime.of(12, 30));
				}
				
				
				maxAsrPrayerTime = maxAsrPrayerTime.plusMinutes(20);
				maxAsrPrayerTime = DateTimeUtils.roundMinutesToNexMultipleOf15(maxAsrPrayerTime);
				dayPrayerTimes.gePrayerTime(Prayer.ASR).setIqamaTime(maxAsrPrayerTime);
				
				maxMaghribPrayerTime = maxMaghribPrayerTime.plusMinutes(5);
				maxMaghribPrayerTime = DateTimeUtils.roundMinutesToNextMultipleOf5(maxMaghribPrayerTime);
				dayPrayerTimes.gePrayerTime(Prayer.MAGHRIB).setIqamaTime(maxMaghribPrayerTime);
				
				maxIshaPrayerTime = maxIshaPrayerTime.plusMinutes(20);
				maxIshaPrayerTime = DateTimeUtils.roundMinutesToNexMultipleOf15(maxIshaPrayerTime);
				if(daylightSavingTime) {
					if(maxIshaPrayerTime.isBefore(LocalTime.of(20, 0))) {
						maxIshaPrayerTime = LocalTime.of(20, 0);
					}
				} else {
					if(maxIshaPrayerTime.isBefore(LocalTime.of(19, 0))) {
						maxIshaPrayerTime = LocalTime.of(19, 0);
					}
				}
				
				dayPrayerTimes.gePrayerTime(Prayer.ISHA).setIqamaTime(maxIshaPrayerTime);
			}
		}
	}
	
	private static int getCalculationRangeStartIndex(int currentDateIndex, DayOfWeek day) {
		switch(day) {
		case FRIDAY:
			return currentDateIndex - 5;
		case MONDAY:
			return currentDateIndex - 1;
		case SATURDAY:
			return currentDateIndex - 6;
		case SUNDAY:
			return currentDateIndex;
		case THURSDAY:
			return currentDateIndex - 4;
		case TUESDAY:
			return currentDateIndex - 2;
		case WEDNESDAY:
			return currentDateIndex - 3;
		default:
			return currentDateIndex;
		}
	}
	
	private static boolean occursInDaylightSavingPeriod(LocalDate date) {
		LocalDate daylightSavingStartDate = DateTimeUtils.getDaylightSavingStartDate(date.getYear());
		if(date.equals(daylightSavingStartDate)) {
			return true;
		}
		
		LocalDate daylightSavingEndDate = DateTimeUtils.getDaylightSavingEndDate(date.getYear());
		return date.isAfter(daylightSavingStartDate) && date.isBefore(daylightSavingEndDate);
	}

}
