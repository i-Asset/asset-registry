package at.srfg.iot.aas.basys.event.handler;

import org.eclipse.basyx.submodel.metamodel.map.parts.ConceptDescription;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basys.event.GetConceptDescriptionEvent;
import at.srfg.iot.aas.basys.event.SetConceptDescriptionEvent;

@Component
public class ConceptDescriptionHandler {

	@EventListener
	public void onConceptDescriptionSet(SetConceptDescriptionEvent event) {
		System.out.println("Concept Description handling: " + event.getLocal().getId());
		// 
		// connect to eClass??
		
		
	}
	@EventListener
	public void onConceptDescriptionGet(GetConceptDescriptionEvent event) {
		System.out.println("Concept Description handling: " + event.getLocal().getId());
		// 
		// connect to eClass??
		ConceptDescription facade = ConceptDescription.createAsFacade(event.getBasyxMap());
		
	}

}
