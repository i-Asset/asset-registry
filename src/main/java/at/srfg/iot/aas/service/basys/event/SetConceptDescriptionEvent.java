package at.srfg.iot.aas.service.basys.event;

import java.util.Map;

import at.srfg.iot.aas.dictionary.ConceptDescription;

public class SetConceptDescriptionEvent extends SetIdentifiableElement<ConceptDescription> 
	implements SetReferable, SetIdentifiable {

	public SetConceptDescriptionEvent(Map<String, Object> map, ConceptDescription referable) {
		super(map, referable);
	}

}
