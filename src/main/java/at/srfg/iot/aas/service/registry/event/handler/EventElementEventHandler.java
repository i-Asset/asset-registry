package at.srfg.iot.aas.service.registry.event.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.aas.service.registry.event.EventElementEvent;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;

@Component
public class EventElementEventHandler {
	@Autowired
	RegistryService registry;

	@EventListener
	@Order(10)
	public void onEventElementEvent(EventElementEvent event) {
		event.getEntity().setActive(event.getDTO().isActive());
		event.getEntity().setDirection(event.getDTO().getDirection());
		event.getEntity().setMessageTopic(event.getDTO().getMessageTopic());
		// 
		if (! event.isObservedReferenceResolved()) {
			// try to resolve the element
			Optional<ReferableElement> ref = registry.resolveReference(event.getDTO().getObservableReference(), ReferableElement.class, true);
			if ( ref.isPresent()) {
				// when reference points to a valid element 
				if (event.isValidObservedElement(ref.get())) {
					event.getEntity().setObservableElement(ref.get());
				}
			}
		}
		if ( ! event.isMessageBrokerResolved()) {
			Optional<ReferableElement> ref = registry.resolveReference(event.getDTO().getMessageBroker(), ReferableElement.class, true);
			if ( ref.isPresent()) {
				// when reference points to a valid element 
				if (event.isValidMessageBroker(ref.get())) {
					event.getEntity().setMessageBrokerElement(ref.get());
				}
			}
		}
	}
}
