package com.gto.iot.eventprocessor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.gto.iot.dto.Location;


public class LastKnownLocation {

	private HashMap<String, Location> locationCache = new HashMap<String, Location>();

	private static LastKnownLocation self = new LastKnownLocation();

	private LastKnownLocation() {

	}

	public static LastKnownLocation getInstance() {
		return self;
	}

	public void put(String vin, Location location) {
		location.setTs(System.currentTimeMillis());
		locationCache.put(vin, location);
	}

	public Map<String, Location> getAllVehicleLocationExcept(String vin) {
		return locationCache
				.entrySet()
				.stream()
				.filter(locationEntry -> (System.currentTimeMillis() - locationEntry
						.getValue().getTs()) < 1200000 && !locationEntry.getKey().equalsIgnoreCase(vin))
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
	}
}
