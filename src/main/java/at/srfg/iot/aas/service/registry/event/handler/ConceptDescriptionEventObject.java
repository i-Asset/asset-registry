package at.srfg.iot.aas.service.registry.event.handler;

import at.srfg.iot.aas.dictionary.ConceptDescription;

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

	@Override
	protected ConceptDescription newEntity() {
		return new ConceptDescription();
	}
	

}
