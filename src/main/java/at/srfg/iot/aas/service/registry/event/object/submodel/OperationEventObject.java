package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.modeling.submodelelement.Operation;

public class OperationEventObject extends SubmodelElementEventObject<Operation, Operation> {

	public OperationEventObject(Object source, SubmodelElementContainer container, Operation entity, Operation api) {
		super(source, container, entity, api);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
