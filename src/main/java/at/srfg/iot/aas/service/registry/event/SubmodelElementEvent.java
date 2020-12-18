package at.srfg.iot.aas.service.registry.event;

import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;

public interface SubmodelElementEvent<E extends SubmodelElement, D extends SubmodelElement> extends ApiEvent {
	E getEntity();
	D getDTO();
}
