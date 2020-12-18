package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.service.registry.event.EventElementEvent;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;

public class EventElementEventObject extends SubmodelElementEventObject<EventElement, EventElement> 
	implements EventElementEvent {

	public EventElementEventObject(Object source, SubmodelElementContainer container, EventElement entity, EventElement api) {
		super(source, container, entity, api);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

}
