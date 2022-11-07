package org.arqum.open.prayertimes.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * A set of utilities for date/time manipulations.
 */
public class DateTimeUtils {
	
	private static final String INSTANTIATION_CLASS_ERROR_MESSAGE_FORMATTER = "Cannot instantiate statis class: %s";
	
	/**
	 * Private constructor to prevent accidental instantiations.
	 */
	private DateTimeUtils() {
		String errorMessage = String.format(INSTANTIATION_CLASS_ERROR_MESSAGE_FORMATTER, DateTimeUtils.class.getName());
		throw new IllegalAccessError(errorMessage);
	}
	
	/**
	 * Parses the given time as a {@link String} with format (hh:mm a) to an instance of {@link LocalTime}.
	 * 
	 * @param timeAsString Time encoded as a {@link String}.
	 * @return An instance of {@link LocalTime}.
	 */
	public static LocalTime parse(String timeAsString) {
		return LocalTime.parse(timeAsString, DateTimeFormatter.ofPattern("hh:mm a", Locale.US));
	}

	/**
	 * Rounds the minutes of the given <code>time</code> to the next minute that is multiple of .
	 * 
	 * @param time An instance of {@link LocalTime}.
	 * @return An instance of {@link LocalTime}.
	 */
	public static LocalTime roundMinutesToNextMultipleOf5(LocalTime time) {
		int minutes = time.getMinute();
		int nextMultipleOfTen = (int) (5 * Math.ceil(minutes / 5.0));
		int difference = nextMultipleOfTen - minutes;
		LocalTime newTime = time.plusMinutes(difference);
		return newTime;
	}
	
	/**
	 * Rounds the minutes of the given <code>time</code> to the next minute that is multiple of 15.
	 * 
	 * @param time An instance of {@link LocalTime}.
	 * @return An instance of {@link LocalTime}.
	 */
	public static LocalTime roundMinutesToNexMultipleOf15(LocalTime time) {
		int minutes = time.getMinute();
		int nextMultipleOfFifteen = (int) (15 * Math.ceil(minutes / 15.0));
		int difference = nextMultipleOfFifteen - minutes;
		LocalTime newTime = time.plusMinutes(difference);
		return newTime;
	}
	
	/**
	 * Retrieves the {@link LocalDate} that corresponds to the start of daylight saving date.
	 * <p>
	 * The start of daylight saving time occurs at the second Sunday of March.
	 * 
	 * @param year An {@link Integer representing the year}.
	 * @return An instance of {@link LocalDate}.
	 */
	public static LocalDate getDaylightSavingStartDate(int year) {
		LocalDate date = LocalDate.of(year, Month.MARCH.getValue(), 1);
		while (!DayOfWeek.SUNDAY.equals(date.getDayOfWeek())) {
			date = date.plusDays(1);
		}
		LocalDate nextSundayDate = date.plusDays(7);
		return nextSundayDate;
	}
	
	/**
	 * Retrieves the {@link LocalDate} that corresponds to the end of daylight saving date.
	 * <p>
	 * The end of daylight saving time occurs at the first Sunday of November.
	 * 
	 * @param year An {@link Integer representing the year}.
	 * @return An instance of {@link LocalDate}.
	 */
	public static LocalDate getDaylightSavingEndDate(int year) {
		LocalDate date = LocalDate.of(year, Month.NOVEMBER.getValue(), 1);
		while (!DayOfWeek.SUNDAY.equals(date.getDayOfWeek())) {
			date = date.plusDays(1);
		}
		return date;
	}
	
}
