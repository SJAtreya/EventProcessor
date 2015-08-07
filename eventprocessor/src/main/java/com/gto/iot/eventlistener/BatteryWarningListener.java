package com.gto.iot.eventlistener;

import java.io.IOException;
import java.util.Map;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.gto.iot.dto.Location;
import com.gto.iot.dto.SpeedStream;
import com.gto.iot.eventprocessor.NearestVehicleClassifier;
import com.gto.iot.main.TcpClient;

public class BatteryWarningListener implements UpdateListener{

	public void update(EventBean[] battery,EventBean[] empty) {
		// TODO Auto-generated method stub
		SpeedStream stream = (SpeedStream) battery[0].getUnderlying();
		System.out.println("Low battery identified in stream :" + (stream));
		Map.Entry<String, Location> entry = NearestVehicleClassifier
				.getNearestVehicle(stream.getVin(),
						new Location(stream.getLat(), stream.getLon()));

		StringBuffer buffer = new StringBuffer(stream.toString());
		buffer.append(",");
		buffer.append("Warning!");
		buffer.append(",");
		buffer.append("Low Battery");
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
