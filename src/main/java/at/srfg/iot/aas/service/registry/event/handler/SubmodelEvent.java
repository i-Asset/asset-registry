package at.srfg.iot.aas.service.registry.event.handler;

import at.srfg.iot.aas.basic.Submodel;

public interface SubmodelEvent extends ApiEvent {
	Submodel getEntity();
	Submodel getDTO();
}
