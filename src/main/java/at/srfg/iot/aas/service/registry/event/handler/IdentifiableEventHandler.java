package at.srfg.iot.aas.service.registry.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.registry.event.IdentifiableEvent;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;

@Component
public class IdentifiableEventHandler {

	@EventListener
	@Order(1)
	public void onIdentifiableEvent(IdentifiableEvent event) {
		Identifiable entity = event.getEntity();
		Identifiable dto = event.getDTO();
		// handle the respective data
		entity.setAdministration(dto.getAdministration());
		entity.setIdentification(dto.getIdentification());
	}
}
