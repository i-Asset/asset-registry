package at.srfg.iot.aas.service.registry.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.common.HasSemantics;
import at.srfg.iot.aas.common.referencing.ReferableElement;

@Component
public class HasSemanticsEventHandler {
//	@Autowired 
//	private ReferableRepository<ReferableElement> referableRepo;
	@EventListener
	public void onHasSemanticsEvent(HasSemanticsEvent event) {
		HasSemantics entity = event.getEntity();
		HasSemantics dto = event.getDTO();
		
		// handle the data
		ReferableElement elem = dto.getSemanticElement();
		entity.setSemanticIdentifier(dto.getSemanticIdentifier());
	}
}
