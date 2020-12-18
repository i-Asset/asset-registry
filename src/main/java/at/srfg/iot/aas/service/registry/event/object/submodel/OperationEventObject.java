package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.service.registry.event.submodel.OperationEvent;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;

public class OperationEventObject extends SubmodelElementEventObject<Operation, Operation> implements OperationEvent {

	public OperationEventObject(Object source, SubmodelElementContainer container, Operation entity, Operation api) {
		super(source, container, entity, api);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
