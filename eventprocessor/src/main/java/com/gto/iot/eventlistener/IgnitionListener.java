package com.gto.iot.eventlistener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class IgnitionListener implements UpdateListener{

	public void update(EventBean[] count,EventBean[] empty) {
		// TODO Auto-generated method stub
		System.out.println(" vehicle breakdown: Ignition tried count -"+count);
	}

		
}
