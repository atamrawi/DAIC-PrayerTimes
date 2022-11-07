package org.arqum.open.prayertimes.model;

/**
 * Represents the five available prayer.
 */
public enum Prayer {
	

	FAJR("Fajr"),

	DHUHR("Dhuhr"),

	ASR("Asr"),
	
	MAGHRIB("Maghrib"),
	
	ISHA("Isha");
	
	/**
	 * The associated label with each enumeration.
	 */
	private String label;
	
	/**
	 * Constructs a new instance of {@link Prayer}.
	 * @param label
	 */
	private Prayer(String label) {
		this.label = label;
	}
	
	/**
	 * Gets the label associated with the enumeration item.
	 * 
	 * @return A {@link String}.
	 */
	public String getLabel() {
		return this.label;
	}
	
}
