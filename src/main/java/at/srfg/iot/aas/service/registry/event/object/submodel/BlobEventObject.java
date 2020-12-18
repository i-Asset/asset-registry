package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.service.registry.event.submodel.BlobEvent;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Blob;

public class BlobEventObject extends SubmodelElementEventObject<Blob, Blob> implements BlobEvent {

	public BlobEventObject(Object source, SubmodelElementContainer container, Blob entity, Blob api) {
		super(source, container, entity, api);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
