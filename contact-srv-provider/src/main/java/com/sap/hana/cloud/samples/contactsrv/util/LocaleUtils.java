package com.sap.hana.cloud.samples.contactsrv.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class LocaleUtils
{
	/**
	 * Map containing the country selection lists as values and the language as key.
	 */
	private static Map<String, SortedMap<String, String>> countryMap = new HashMap<String, SortedMap<String, String>>();
	
	
	/**
	 * Returns a {@link SortedMap} containing the ISO 3166 code of all countries as the key and the localized name 
	 * of the country as the value.
	 * 
	 * @param locale The {@link Locale} used to obtain the localized names of the countries
	 * @return {@link SortedMap} containing the ISO 3166 code of all countries as the key and the localized name 
	 * of the country as the value.
	 */
	public static SortedMap<String, String> getCountryList(Locale locale)
	{
		if (locale == null || locale.getLanguage() == null)
		{
			return new TreeMap<String, String>(); // return empty map
		}
		
		SortedMap<String, String> retVal = null;
		
		if (! countryMap.containsKey(locale.getLanguage()))
		{
			Map<String, String> map = new HashMap<String, String>(Locale.getISOCountries().length);
			
			for (String str : Locale.getISOCountries())
			{
				map.put(str, new Locale("", str).getDisplayCountry(locale));
			}
			
			retVal = sortByValues(map);
			countryMap.put(locale.getLanguage(), retVal);
		}
		
		retVal = countryMap.get(locale.getLanguage());
		
		return retVal;
		
	}
	
	/**
	 * Sorts the specified {@link Map} according to the natural ordering of its values.
	 * 
	 * @param map The {@link Map} to sort
	 * @return The {@link SortedMap} 
	 */
	public static <K, V extends Comparable<V>> SortedMap<K, V> sortByValues(final Map<K, V> map) 
	{
		Comparator<K> valueComparator =  new Comparator<K>() 
		{
		    public int compare(K k1, K k2) 
		    {
		        int compare = map.get(k1).compareTo(map.get(k2));
		        if (compare == 0) return 1;
		        else return compare;
		    }
		};
		
		SortedMap<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}

}
