package at.srfg.iot.aas.basys.event;

import at.srfg.iot.aas.model.dictionary.ConceptDictionary;

public class GetConceptDictionaryEvent extends GetElementEvent<ConceptDictionary, org.eclipse.basyx.aas.metamodel.map.parts.ConceptDictionary>

		implements GetReferable {

	public GetConceptDictionaryEvent(ConceptDictionary referable) {
		super(newConceptDictionary(), referable);
	}
}