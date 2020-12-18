package at.srfg.iot.aas.service.registry.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.registry.event.HasKindEvent;
import at.srfg.iot.common.datamodel.asset.aas.common.HasKind;

@Component
public class HasKindEventHandler {
	@EventListener
	@Order(1)
	public void onHasKindEvent(HasKindEvent event) {
		HasKind entity = event.getEntity();
		HasKind dto = event.getDTO();
		// map
		entity.setKind(dto.getKind());
	}
}
