package at.srfg.iot.aas.service.basys.event.handler;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.service.basys.event.GetConceptDictionaryEvent;
import at.srfg.iot.aas.service.basys.event.SetConceptDictionaryEvent;
import at.srfg.iot.aas.service.basys.event.handler.util.MappingHelper;
import at.srfg.iot.aas.service.basys.event.publisher.MappingEventPublisher;
import at.srfg.iot.aas.dictionary.ConceptDescription;
import at.srfg.iot.aas.repository.basys.IdentifiableRepository;

@Component
public class ConceptDictionaryHandler {
	@Autowired
	private IdentifiableRepository<ConceptDescription> cdRepo;
	
	@Autowired
	private MappingEventPublisher publisher;

	@EventListener
	public void onConceptDescriptionSet(SetConceptDictionaryEvent event) {
		System.out.println("Concept Dictionary handling: " + event.getLocal().getIdShort());
		
		Collection<Map<String,Object>> conceptDescriptions = MappingHelper.getAsCollection(event.getBasyxMap(), "conceptDescriptions");
		
		for (Map<String,Object> conceptDescriptionMap : conceptDescriptions ) {
			Optional<Identifier> optId = MappingHelper.getIdentifier(conceptDescriptionMap);
			if ( optId.isPresent()) {
				Identifier id = optId.get();
				Optional<ConceptDescription> cd = cdRepo.findByIdentification(id);
				
				ConceptDescription conceptDescription = cd.orElse(new ConceptDescription(id));
//
				publisher.handleConceptDescription(conceptDescriptionMap, conceptDescription);
				//
				event.getLocal().addConceptDescription(conceptDescription);
				cdRepo.save(conceptDescription);
			}
			
		}
		
		
	}

	@EventListener
	public void onConceptDescriptionGet(GetConceptDictionaryEvent event) {
		System.out.println("Concept Dictionary handling: " + event.getLocal().getIdShort());
		org.eclipse.basyx.aas.metamodel.map.parts.ConceptDictionary cd = event.getBasyxMap();
		
		for ( ConceptDescription desc : event.getLocal().getConceptDictionary() ) {
			// trigger the concept description read
			org.eclipse.basyx.submodel.metamodel.map.parts.ConceptDescription b = publisher.getFromConceptDescription(desc);
			// add the description to the dictionary
			cd.addConceptDescription(b);
		}
		
	}

}
