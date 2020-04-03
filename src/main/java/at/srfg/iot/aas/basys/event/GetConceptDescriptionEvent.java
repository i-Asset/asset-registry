package at.srfg.iot.aas.basys.event;

import at.srfg.iot.aas.dictionary.ConceptDescription;

public class GetConceptDescriptionEvent extends GetIdentifiableElementEvent<ConceptDescription, org.eclipse.basyx.submodel.metamodel.map.parts.ConceptDescription> 
	implements GetReferable, GetIdentifiable {

	public GetConceptDescriptionEvent(ConceptDescription referable) {
		super(newConceptDescription(), referable);
	}

}
