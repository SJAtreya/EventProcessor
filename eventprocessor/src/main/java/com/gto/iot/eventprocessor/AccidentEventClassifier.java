package com.gto.iot.eventprocessor;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.gto.iot.dto.SpeedStream;
import com.gto.iot.eventlistener.AccidentListener;

public class AccidentEventClassifier {

	private static final String accidentContext = "create context accident partition by vin from SpeedStream";

	private static final String brakingIdentification = "context accident select first(*), last(*) from SpeedStream.win:time(2)  group by vin having (last(speed)-first(speed)<-40)";

	private static EPRuntime epRuntime;

	private static AccidentEventClassifier classifier = new AccidentEventClassifier();

	private static EPAdministrator cepAdm;

	private AccidentEventClassifier() {
	}

	public void initialize() {
		Configuration configuration = new Configuration();
		configuration.addEventType("SpeedStream", SpeedStream.class.getName());
		EPServiceProvider cep = EPServiceProviderManager.getProvider(
				"myCEPEngine", configuration);
		epRuntime = cep.getEPRuntime();
		cepAdm = cep.getEPAdministrator();
		cepAdm.createEPL(accidentContext);
		cepAdm.createEPL(brakingIdentification).addListener(new AccidentListener());
	}

	public static AccidentEventClassifier getInstance() {
		return classifier;
	}

	public void identifyEvent(SpeedStream streamData) {
		epRuntime.sendEvent(streamData);
	}
}
