package at.srfg.iot.aas.service.registry.event.object;

import at.srfg.iot.aas.service.registry.event.HasDataSpecificationEvent;
import at.srfg.iot.aas.service.registry.event.ReferableEvent;
import at.srfg.iot.common.datamodel.asset.aas.dictionary.ConceptDescription;

public class ConceptDescriptionEventObject extends RegistryEventObject<ConceptDescription, ConceptDescription> 
	implements ReferableEvent, HasDataSpecificationEvent
	{

	public ConceptDescriptionEventObject(Object source, ConceptDescription api) {
		super(source, api);
	}

	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	

}
