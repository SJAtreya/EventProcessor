package com.gto.iot.eventlistener;

import java.io.IOException;
import java.util.Map;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.gto.iot.dto.Location;
import com.gto.iot.dto.SpeedStream;
import com.gto.iot.eventprocessor.NearestVehicleClassifier;
import com.gto.iot.main.TcpClient;

public class FuelWarningListener implements UpdateListener{
	
	public void update(EventBean[] fuel,EventBean[] empty) {
		// TODO Auto-generated method stub
		System.out.println(" Warning ! fuel low ");
		SpeedStream stream = (SpeedStream)  fuel[0].getUnderlying();
		System.out.println("Low Fuel identified in stream :" + (stream));
		Map.Entry<String, Location> entry = NearestVehicleClassifier
				.getNearestVehicle(stream.getVin(),
						new Location(stream.getLat(), stream.getLon()));

		StringBuffer buffer = new StringBuffer(stream.toString());
		buffer.append(",");
		buffer.append("Needs Refuelling");
		buffer.append(",");
		buffer.append("Low Fuel");
		buffer.append(",");
		buffer.append("Not Notified");
		buffer.append(",");
		buffer.append(entry != null ? entry.getKey() : "No vehicles nearby");
		System.out.println(buffer.toString());
		try {
			TcpClient.sendMessage(buffer.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
