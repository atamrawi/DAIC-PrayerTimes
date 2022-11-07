package org.arqum.open.prayertimes.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an entity corresponding to a {@link Prayer}
 */
public class PrayerTime {
	
	private static final String ATHAN_IQAMA_TIME_STRING_FORMATTER = "%s(%s)";

	private LocalTime athanTime;
	private LocalTime iqamaTime;
	
	public PrayerTime(LocalTime athanTime) {
		this.athanTime = athanTime;
		this.iqamaTime = null;
	}
	
	public void setIqamaTime(LocalTime iqamaTime) {
		this.iqamaTime = iqamaTime;
	}

	public LocalTime getAthanTime() {
		return this.athanTime;
	}
	
	public LocalTime getIqamaTime() {
		return this.iqamaTime;
	}
	
	@Override
	public String toString() {
		String athanTime = this.athanTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
		if(this.iqamaTime == null) {
			return athanTime;
		} else {
			String iqamaTime = this.iqamaTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
			return String.format(ATHAN_IQAMA_TIME_STRING_FORMATTER, athanTime, iqamaTime);
		}
	}
	
}
