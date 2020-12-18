package at.srfg.iot.aas.service.registry.event;

import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;

public interface IdentifiableEvent extends ApiEvent {
	
	Identifiable getEntity();
	Identifiable getDTO();

}
