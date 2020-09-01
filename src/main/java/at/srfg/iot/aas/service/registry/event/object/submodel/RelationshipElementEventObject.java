package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.modeling.submodelelement.RelationshipElement;

public class RelationshipElementEventObject extends SubmodelElementEventObject<RelationshipElement, RelationshipElement> {

	public RelationshipElementEventObject(Object source, SubmodelElementContainer container, RelationshipElement entity, RelationshipElement api) {
		super(source, container, entity, api);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
