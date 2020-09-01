package at.srfg.iot.aas.service.registry.event;

import at.srfg.iot.aas.common.SubmodelElementContainer;

public interface SubmodelElementContainerEvent extends ApiEvent {

	SubmodelElementContainer getEntity();
	SubmodelElementContainer getDTO();
}
