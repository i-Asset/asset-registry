package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.modeling.submodelelement.ReferenceElement;

public class ReferenceElementEventObject extends SubmodelElementEventObject<ReferenceElement, ReferenceElement> {

	public ReferenceElementEventObject(Object source, SubmodelElementContainer container, ReferenceElement entity, ReferenceElement api) {
		super(source, container, entity, api);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
