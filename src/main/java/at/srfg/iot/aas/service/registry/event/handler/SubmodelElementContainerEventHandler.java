package at.srfg.iot.aas.service.registry.event.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.modeling.SubmodelElement;
import at.srfg.iot.aas.service.registry.RegistryWorker;
import at.srfg.iot.aas.service.registry.event.SubmodelElementContainerEvent;

@Component
public class SubmodelElementContainerEventHandler {
	@Autowired
	ApplicationEventPublisher publisher;
	@Autowired
	RegistryWorker worker;
	
	@EventListener
	public void onSubmodelElementContainerEvent(SubmodelElementContainerEvent event) {
		//
		SubmodelElementContainer entity = event.getEntity();
		SubmodelElementContainer dto = event.getDTO();
		// 
		for (SubmodelElement element : dto.getSubmodelElements()) {
			// SubmodelElement is Referable only - identification only via idShort! 
			Optional<SubmodelElement> existing = entity.getSubmodelElement(element.getIdShort());
			if ( existing.isPresent() ) {
				SubmodelElement submodelElement = existing.get();
				if ( submodelElement.getSubmodel() != null && submodelElement.getParentElement()!= null) {
					worker.setSubmodelElement(existing.get(), element);
				}
								
			}
			else {
				// when not derived, create the element
				if (! Boolean.TRUE.equals(element.getDerived())) {
					// no corresponding entity present 
					worker.setSubmodelElement(entity, element);
				}
			}
		}
	}
}