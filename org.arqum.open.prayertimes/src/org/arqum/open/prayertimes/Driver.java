package org.arqum.open.prayertimes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.arqum.open.prayertimes.model.DayPrayerTimes;
import org.arqum.open.prayertimes.model.Prayer;
import org.arqum.open.prayertimes.parser.PrayerTimeDataParser;
import org.arqum.open.prayertimes.util.IqamaTimeCalculator;

public class Driver {

	private static final String DATA_DIRECTORY_PATH = "./resources";
	
	/**
	 * The main driver for the prayer time generation.
	 * 
	 * @param args An array of files
	 */
	public static void main(String[] args) {
		File dataDirectoryFile = new File(DATA_DIRECTORY_PATH);
		File[] dataFiles = dataDirectoryFile.listFiles();
		
		// Sort based on file name to make sure correct processing order.
		Arrays.sort(dataFiles, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		List<DayPrayerTimes> prayerTimes = new ArrayList<DayPrayerTimes>();
		for(File dataFile: dataFiles) {
			List<DayPrayerTimes> subPrayerTimes = parse(dataFile);
			prayerTimes.addAll(subPrayerTimes);
		}
		
		IqamaTimeCalculator.calculate(prayerTimes);
		
		
		for(DayPrayerTimes dayPrayerTimes: prayerTimes) {
//			if(DayOfWeek.SUNDAY.equals(dayPrayerTimes.getDay())) {
//				System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
//			}
//			if(dayPrayerTimes.getDate().getYear() != 2023) {
//				continue;
//			}
//			String date = dayPrayerTimes.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yy"));
//			//System.out.println(dayPrayerTimes.getSunriseTime());
//			//System.out.println(dayPrayerTimes.gePrayerTime(Prayer.ISHA).getAthanTime().format(DateTimeFormatter.ofPattern("HH:mm")));
//			System.out.println(dayPrayerTimes.gePrayerTime(Prayer.ISHA).getIqamaTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		}
	}

	private static List<DayPrayerTimes> parse(File dataFile) {
		String fileName = dataFile.getName();
		String yearAsString = fileName.substring(0, 4);
		int year = Integer.parseInt(yearAsString);
		List<String> rawPrayerTimesData = null;
		try {
			rawPrayerTimesData = Files.readAllLines(dataFile.toPath());
		} catch (IOException e) {
			System.err.println("Error while reading contents of: " + dataFile.getAbsolutePath());
			return new ArrayList<DayPrayerTimes>();
		}
		
		List<DayPrayerTimes> dayPrayerTimesList = PrayerTimeDataParser.parse(year, rawPrayerTimesData);
		return dayPrayerTimesList;
	}
	
}
