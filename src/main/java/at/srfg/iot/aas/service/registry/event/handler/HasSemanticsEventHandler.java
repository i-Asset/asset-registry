package at.srfg.iot.aas.service.registry.event.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.aas.service.registry.event.HasSemanticsEvent;
import at.srfg.iot.common.datamodel.asset.aas.common.HasSemantics;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;

@Component
public class HasSemanticsEventHandler {
	@Autowired
	RegistryService registry;
//	@Autowired 
//	private ReferableRepository<ReferableElement> referableRepo;
	@EventListener
	@Order(1)
	public void onHasSemanticsEvent(HasSemanticsEvent event) {
		HasSemantics entity = event.getEntity();
		HasSemantics dto = event.getDTO();
		
		if (! event.isSemanticIdResolved()) {
			// 
			Optional<ReferableElement> ref = registry.resolveReference(dto.getSemanticId(), ReferableElement.class);
			if ( ref.isPresent()) {
				if (event.isValidSemanticElement(ref.get())) {
					// entity may be Submodel, SubmodelElement, Qualifier each with different semanticId's
					entity.setSemanticElement(ref.get());
				}
			}
		}
		
		
		
	}
}
