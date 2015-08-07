package com.gto.iot.eventprocessor;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.gto.iot.dto.SpeedStream;
import com.gto.iot.dto.IgnitionStream;
import com.gto.iot.eventlistener.IgnitionListener;


public class EventClassifier extends AbstractClassifier{

	private static final String breakDownContext = "create context breakdown partition by vin from IgnitionStream";

	private static final String ignitionFailureIdentification = "context breakdown select * from IgnitionStream.win:time_batch(60) where speed = 0 and ignition != 0 and type = 'E' group by vin having (count(ignition) > 15)";
	
	private static EPRuntime epRuntime; 

	private static EventClassifier classifier = new EventClassifier();
	
	private static EPAdministrator cepAdm;

	private EventClassifier() {
	}

	
	public static EventClassifier getInstance() {
		return classifier;
	}

	public void addStatement(EPAdministrator cepAdminS) {
		// TODO Auto-generated method stub
		cepAdminS.createEPL(breakDownContext);
		cepAdminS.createEPL(ignitionFailureIdentification).addListener(new IgnitionListener());
	}
}
