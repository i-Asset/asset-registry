package at.srfg.iot.aas.service.registry.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basic.Submodel;

@Component
public class SubmodelEventHandler {
	@EventListener
	public void onSubmodelEvent(SubmodelEvent event) {
		
		Submodel entity = event.getEntity();
		Submodel dto = event.getDTO();
	}
}
