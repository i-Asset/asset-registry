package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.service.registry.event.submodel.RelationshipElementEvent;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.RelationshipElement;

public class RelationshipElementEventObject extends SubmodelElementEventObject<RelationshipElement, RelationshipElement> implements RelationshipElementEvent {

	public RelationshipElementEventObject(Object source, SubmodelElementContainer container, RelationshipElement entity, RelationshipElement api) {
		super(source, container, entity, api);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
