package at.srfg.iot.aas.service.registry.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.common.Referable;
import at.srfg.iot.aas.common.referencing.ReferableDescription;
import at.srfg.iot.aas.service.registry.event.ReferableEvent;

@Component
public class ReferableEventHandler {

	@EventListener
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
