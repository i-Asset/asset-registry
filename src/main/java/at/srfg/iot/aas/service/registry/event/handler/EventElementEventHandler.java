package at.srfg.iot.aas.service.registry.event.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.common.referencing.ReferableElement;
import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.aas.service.registry.event.EventElementEvent;

@Component
public class EventElementEventHandler {
	@Autowired
	RegistryService registry;

	@EventListener
	public void onEventElementEvent(EventElementEvent event) {
		event.getEntity().setActive(event.getDTO().isActive());
		event.getEntity().setDirection(event.getDTO().getDirection());
		event.getEntity().setMessageTopic(event.getDTO().getMessageTopic());
		// 
		if (! event.isObservedReferenceResolved()) {
			// 
			Optional<ReferableElement> ref = registry.resolveReference(event.getDTO().getObservableReference());
			if ( ref.isPresent()) {
				// when reference points to a valid element 
				if (event.isValidObservedElement(ref.get())) {
					event.getEntity().setObservableElement(ref.get());
				}
			}
		}
	}
}