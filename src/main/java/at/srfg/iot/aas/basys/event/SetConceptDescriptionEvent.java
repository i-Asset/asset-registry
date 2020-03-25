package at.srfg.iot.aas.basys.event;

import java.util.Map;

import at.srfg.iot.aas.model.dictionary.ConceptDescription;

public class SetConceptDescriptionEvent extends SetIdentifiableElement<ConceptDescription> 
	implements SetReferable, SetIdentifiable {

	public SetConceptDescriptionEvent(Map<String, Object> map, ConceptDescription referable) {
		super(map, referable);
	}

}
