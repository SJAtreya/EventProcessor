package com.gto.iot.eventprocessor;

import com.espertech.esper.client.EPAdministrator;
import com.gto.iot.eventlistener.AccidentListener;
import com.gto.iot.eventlistener.BatteryListener;
import com.gto.iot.eventlistener.BatteryWarningListener;
import com.gto.iot.eventlistener.FuelListener;
import com.gto.iot.eventlistener.FuelWarningListener;

public class TimeEventClassifier extends AbstractClassifier {

	private static final String accidentContext = "create context accident partition by vin from SpeedStream";

	private static final String brakingIdentification = "context accident select first(*), last(*) from SpeedStream.win:time(10)  group by vin having (last(speed)-first(speed)<-40)";
	
	// private static final String breakDownContext =
	// "create context breakdown partition by vin from SpeedStream";

	private static final String batteryFailureIdentification = "context accident select * from SpeedStream.win:time_batch(60) where speed > 0 and battery < 5 and type = 'T' group by vin having (count(battery) > 15)";
	private static final String batteryWarningIdentification = "context accident select * from SpeedStream.win:time_batch(60) where speed > 0 and battery < 15 and type = 'T' group by vin having (count(battery) > 15)";
	// battery low warning
	// battery low critical

	private static final String fuelEmptyIdentification = "context accident select * from SpeedStream.win:time_batch(60) where fuel <10 and speed > 0 and type = 'T' group by vin having (count(fuel) > 15)";
	private static final String fuelEmptyWarningIdentification = "context accident select * from SpeedStream.win:time_batch(60) where fuel <20 and speed > 0 and type = 'T' group by vin having (count(fuel) > 15)";
	private static TimeEventClassifier classifier = new TimeEventClassifier();

	private TimeEventClassifier() {
	}

	public static TimeEventClassifier getInstance() {
		return classifier;
	}

	@Override
	public void addStatement(EPAdministrator cepAdminS) {
		// TODO Auto-generated method stub
		System.out.println("timeeventclassifier");
		cepAdminS.createEPL(accidentContext);
		cepAdminS.createEPL(brakingIdentification).addListener(
				new AccidentListener());
		// cepAdminS.createEPL(breakDownContext);
		cepAdminS.createEPL(batteryFailureIdentification).addListener(
				new BatteryListener());
		cepAdminS.createEPL(batteryWarningIdentification).addListener(
				new BatteryWarningListener());
		cepAdminS.createEPL(fuelEmptyIdentification).addListener(
				new FuelListener());
		cepAdminS.createEPL(fuelEmptyWarningIdentification).addListener(
				new FuelWarningListener());

	}

}
