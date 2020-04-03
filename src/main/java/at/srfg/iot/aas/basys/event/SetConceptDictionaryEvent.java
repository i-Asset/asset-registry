package at.srfg.iot.aas.basys.event;

import java.util.Map;

import at.srfg.iot.aas.dictionary.ConceptDictionary;

public class SetConceptDictionaryEvent extends SetElementEvent<ConceptDictionary>

		implements SetReferable {

	public SetConceptDictionaryEvent(Map<String, Object> map, ConceptDictionary referable) {
		super(map, referable);
	}

}