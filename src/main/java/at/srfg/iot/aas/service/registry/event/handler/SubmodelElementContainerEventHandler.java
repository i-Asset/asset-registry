package at.srfg.iot.aas.service.registry.event.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.registry.RegistryWorker;
import at.srfg.iot.aas.service.registry.event.SubmodelElementContainerEvent;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;

@Component
public class SubmodelElementContainerEventHandler {
	@Autowired
	RegistryWorker worker;
	
	@EventListener
	public void onSubmodelElementContainerEvent(SubmodelElementContainerEvent event) {
		//
		SubmodelElementContainer entity = event.getEntity();
		SubmodelElementContainer dto = event.getDTO();
		// 
		for (SubmodelElement element : dto.getSubmodelElements(SubmodelElement.class)) {
			// SubmodelElement is Referable only - identification only via idShort! 
			Optional<SubmodelElement> existing = entity.getChildElement(element.getIdShort(), SubmodelElement.class);
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
