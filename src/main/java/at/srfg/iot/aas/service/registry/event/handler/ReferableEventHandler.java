package at.srfg.iot.aas.service.registry.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.registry.event.ReferableEvent;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableDescription;

@Component
public class ReferableEventHandler {

	@EventListener
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public void onReferableEvent(ReferableEvent event) {
		Referable entity = event.getEntity();
		Referable dto = event.getDTO();
		// handle the respective data
		entity.setCategory(dto.getCategory());
		entity.setIdShort(dto.getIdShort());
		
		for (ReferableDescription c : dto.getDescription()) {
			entity.setDescription(c.getLanguage(), c.getDescription());
		}
		
	}
}
