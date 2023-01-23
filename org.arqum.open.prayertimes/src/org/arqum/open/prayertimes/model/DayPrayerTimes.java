package org.arqum.open.prayertimes.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.StringJoiner;

public class DayPrayerTimes {

	private LocalDate date;
	
	private LocalTime sunriseTime;
	
	private EnumMap<Prayer, PrayerTime> prayerToPrayerTimeMap;
	
	public DayPrayerTimes(LocalDate date) {
		this.date = date;
		this.sunriseTime = null;
		this.prayerToPrayerTimeMap = new EnumMap<Prayer, PrayerTime>(Prayer.class);
	}
	
	public void setPrayerTime(Prayer prayer, PrayerTime prayerTime) {
		this.prayerToPrayerTimeMap.put(prayer, prayerTime);
	}
	
	public void setSunriseTime(LocalTime sunriseTime) {
		this.sunriseTime = sunriseTime;
	}
	
	public LocalTime getSunriseTime() {
		return this.sunriseTime;
	}
	
	public DayOfWeek getDay() {
		return this.date.getDayOfWeek();
	}

	public PrayerTime gePrayerTime(Prayer prayer) {
		return this.prayerToPrayerTimeMap.get(prayer);
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	@Override
	public String toString() {
		StringJoiner stringJoiner = new StringJoiner("\t");
		stringJoiner.add(this.date.format(DateTimeFormatter.ofPattern("EEE MM-dd-yy")));
		stringJoiner.add(this.gePrayerTime(Prayer.FAJR).toString());
		stringJoiner.add(this.sunriseTime.format(DateTimeFormatter.ofPattern("hh:mm a")));
		stringJoiner.add(this.gePrayerTime(Prayer.DHUHR).toString());
		stringJoiner.add(this.gePrayerTime(Prayer.ASR).toString());
		stringJoiner.add(this.gePrayerTime(Prayer.MAGHRIB).toString());
		stringJoiner.add(this.gePrayerTime(Prayer.ISHA).toString());
		return stringJoiner.toString();
	}
	
}
