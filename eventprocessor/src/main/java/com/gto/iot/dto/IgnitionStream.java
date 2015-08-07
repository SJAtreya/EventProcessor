package com.gto.iot.dto;

public class IgnitionStream extends Stream{

	private String type;
	
	private String vin;
	
	private Double speed;
	
	private int ignition;
	
	private Double battery;
	
	private Double fuel;

	public IgnitionStream(String msg) {
		// TODO Auto-generated constructor stub
		String[] array = msg.split(",");
		type = array[0];
		vin = array[1];
		speed = Double.valueOf(array[2]);
		ignition = Integer.valueOf(array[3]);
		battery = Double.valueOf(array[4]);
		fuel = Double.valueOf(array[5]);
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

	

}
