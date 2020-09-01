package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.modeling.submodelelement.Blob;

public class BlobEventObject extends SubmodelElementEventObject<Blob, Blob> {

	public BlobEventObject(Object source, SubmodelElementContainer container, Blob entity, Blob api) {
		super(source, container, entity, api);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
