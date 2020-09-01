package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.modeling.submodelelement.File;

public class FileEventObject extends SubmodelElementEventObject<File, File> {

	public FileEventObject(Object source, SubmodelElementContainer container, File entity, File api) {
		super(source, container, entity, api);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
