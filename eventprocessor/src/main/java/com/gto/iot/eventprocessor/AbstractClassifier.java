package com.gto.iot.eventprocessor;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.gto.iot.dto.IStream;
import com.gto.iot.dto.IgnitionStream;
import com.gto.iot.dto.SpeedStream;
import com.gto.iot.eventlistener.AccidentListener;
import com.gto.iot.eventlistener.BatteryListener;
import com.gto.iot.eventlistener.FuelListener;
import com.gto.iot.eventlistener.IgnitionListener;
import com.gto.iot.eventprocessor.TimeEventClassifier;
import com.gto.iot.eventprocessor.EventClassifier;

public abstract class AbstractClassifier {

	private static EPRuntime epRuntime; 
	
	private static EPAdministrator cepAdm;
	public void initialize() {
		if (cepAdm != null) {
			addStatement(cepAdm);
			return;
		}
		Configuration configuration = new Configuration();
		configuration.addEventType("SpeedStream", SpeedStream.class.getName());
		configuration.addEventType("IgnitionStream", IgnitionStream.class.getName());
		EPServiceProvider cep = EPServiceProviderManager.getProvider(
				"myCEPEngine", configuration);
		epRuntime = cep.getEPRuntime();
		cepAdm = cep.getEPAdministrator();
		addStatement(cepAdm);
	}

	public abstract void addStatement(EPAdministrator cepAdm);
		
	
	public void identifyEvent(SpeedStream istreamData) {
		epRuntime.sendEvent(istreamData);
	}
}
