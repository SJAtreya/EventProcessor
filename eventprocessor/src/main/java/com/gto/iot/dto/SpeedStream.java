package com.gto.iot.dto;

public class SpeedStream extends Stream {

	private String type;

	private String vin;

	private Double speed;

	private int ignition;

	private Double battery;

	private Double fuel;

	private double lat;

	private double lon;
	
	private long ts;

	public SpeedStream(String msg) {
		// TODO Auto-generated constructor stub
		String[] array = msg.split(",");
		try {
			type = array[0];
			vin = array[1];
			lat = Double.valueOf(array[2]);
			lon = Double.valueOf(array[3]);
			ts = Long.valueOf(array[4]);
			speed = Double.valueOf(array[5]);
			ignition = Integer.valueOf(array[8]);
			battery = Double.valueOf(array[6]);
			fuel = Double.valueOf(array[7]);			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public int getIgnition() {
		return ignition;
	}

	public void setIgnition(int ignition) {
		this.ignition = ignition;
	}

	public Double getBattery() {
		return battery;
	}

	public void setBattery(Double battery) {
		this.battery = battery;
	}

	public Double getFuel() {
		return fuel;
	}

	public void setFuel(Double fuel) {
		this.fuel = fuel;
	}

	
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
	
	public double getTs() {
		return ts;
	}

	public void setTs(long ts) {
		this.ts = ts;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer tcpData = new StringBuffer();
		tcpData.append("E");
		tcpData.append(",");
		tcpData.append(this.getVin());
		tcpData.append(",");
		tcpData.append(this.getLat());
		tcpData.append(",");
		tcpData.append(this.getLon());
		tcpData.append(",");
		tcpData.append(this.getTs());
		tcpData.append(",");
		tcpData.append(this.getSpeed());
		tcpData.append(",");
		tcpData.append(this.getBattery());
		tcpData.append(",");
		tcpData.append(this.getFuel());
		return tcpData.toString();
	}
}
