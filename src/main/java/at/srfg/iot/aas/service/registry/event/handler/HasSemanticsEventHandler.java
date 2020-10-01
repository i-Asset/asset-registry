package at.srfg.iot.aas.service.registry.event.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.common.HasSemantics;
import at.srfg.iot.aas.common.referencing.ReferableElement;
import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.aas.service.registry.event.HasSemanticsEvent;

@Component
public class HasSemanticsEventHandler {
	@Autowired
	RegistryService registry;
//	@Autowired 
//	private ReferableRepository<ReferableElement> referableRepo;
	@EventListener
	public void onHasSemanticsEvent(HasSemanticsEvent event) {
		HasSemantics entity = event.getEntity();
		HasSemantics dto = event.getDTO();
		
		if (! event.isSemanticIdResolved()) {
			// 
			Optional<ReferableElement> ref = registry.resolveReference(dto.getSemanticId());
			if ( ref.isPresent()) {
				if (event.isValidSemanticElement(ref.get())) {
					// entity may be Submodel, SubmodelElement, Qualifier each with different semanticId's
					entity.setSemanticElement(ref.get());
				}
			}
		}
		
		
		
	}
}