package at.srfg.iot.aas.service.basys.event;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.parts.IConceptDescription;

import at.srfg.iot.aas.dictionary.ConceptDescription;

public class SetConceptDescriptionEvent extends SetIdentifiableElement<ConceptDescription> 
	implements SetReferable, SetIdentifiable {

	public SetConceptDescriptionEvent(Map<String, Object> map, ConceptDescription referable) {
		super(map, referable);
	}
	public SetConceptDescriptionEvent(IConceptDescription map, ConceptDescription referable) {
		super((org.eclipse.basyx.submodel.metamodel.map.parts.ConceptDescription) map, referable);
	}

}
