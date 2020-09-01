package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.modeling.submodelelement.SubmodelElementCollection;
import at.srfg.iot.aas.service.registry.event.SubmodelElementContainerEvent;

public class SubmodelElementCollectionEventObject extends SubmodelElementEventObject<SubmodelElementCollection, SubmodelElementCollection>
	implements SubmodelElementContainerEvent
	{

	public SubmodelElementCollectionEventObject(Object source, SubmodelElementContainer container, SubmodelElementCollection entity, SubmodelElementCollection api) {
		super(source, container, entity, api);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
