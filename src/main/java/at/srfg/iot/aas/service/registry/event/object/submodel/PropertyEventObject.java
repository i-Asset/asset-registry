package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.modeling.submodelelement.Property;

public class PropertyEventObject extends SubmodelElementEventObject<Property, Property> {

	public PropertyEventObject(Object source, SubmodelElementContainer container, Property entity, Property api) {
		super(source, container, entity, api);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
