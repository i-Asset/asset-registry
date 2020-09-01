package at.srfg.iot.aas.service.registry.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.common.Identifiable;
import at.srfg.iot.aas.service.registry.event.IdentifiableEvent;

@Component
public class IdentifiableEventHandler {

	@EventListener
	public void onIdentifiableEvent(IdentifiableEvent event) {
		Identifiable entity = event.getEntity();
		Identifiable dto = event.getDTO();
		// handle the respective data
		entity.setAdministration(dto.getAdministration());
		entity.setIdentification(dto.getIdentification());
	}
}
