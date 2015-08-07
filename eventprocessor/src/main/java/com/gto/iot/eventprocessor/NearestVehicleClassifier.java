package com.gto.iot.eventprocessor;

import java.util.Map;

import com.gto.iot.dto.Location;

public class NearestVehicleClassifier {

    public static final double R = 6372.8; // In kilometers
    
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
 
        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
	
	public static Map.Entry<String, Location> getNearestVehicle(String vin, Location inputLocation) {
		double distance = 999999;
		double tempDistance = 0;
		Map.Entry<String, Location> nearestVehicle = null;
		Map<String,Location> allVehicleLocation = LastKnownLocation.getInstance().getAllVehicleLocationExcept(vin);
		for (Map.Entry<String, Location> locationEntry: allVehicleLocation.entrySet()) {
			tempDistance = haversine(inputLocation.getLat(), inputLocation.getLon(), locationEntry.getValue().getLat(), locationEntry.getValue().getLon());
			if (tempDistance < distance) {
				nearestVehicle = locationEntry;
				distance = tempDistance;
			}
		}
		return nearestVehicle;
	}
}

