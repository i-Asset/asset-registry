package at.srfg.iot.aas.service.basys.event;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDictionary;

import at.srfg.iot.aas.dictionary.ConceptDictionary;

public class SetConceptDictionaryEvent extends SetElementEvent<ConceptDictionary>

		implements SetReferable {

	public SetConceptDictionaryEvent(Map<String, Object> map, ConceptDictionary referable) {
		super(map, referable);
	}
	public SetConceptDictionaryEvent(IConceptDictionary map, ConceptDictionary dict) {
		// IConceptDictionary must be a basyx ConceptDictionary, we can cast
		super((org.eclipse.basyx.aas.metamodel.map.parts.ConceptDictionary)map, dict);
	}

}