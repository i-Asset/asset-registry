package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.modeling.submodelelement.File;
import at.srfg.iot.aas.service.registry.event.submodel.FileEvent;

public class FileEventObject extends SubmodelElementEventObject<File, File> implements FileEvent {

	public FileEventObject(Object source, SubmodelElementContainer container, File entity, File api) {
		super(source, container, entity, api);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
