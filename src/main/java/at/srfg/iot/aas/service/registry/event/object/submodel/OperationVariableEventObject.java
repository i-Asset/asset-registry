package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.service.registry.event.submodel.OperationVariableEvent;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.OperationVariable;

public class OperationVariableEventObject extends SubmodelElementEventObject<OperationVariable, OperationVariable> implements OperationVariableEvent {

	public OperationVariableEventObject(Object source, SubmodelElementContainer container, OperationVariable entity, OperationVariable api) {
		super(source, container, entity, api);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
