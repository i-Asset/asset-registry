package at.srfg.iot.aas.service.registry.event;

import at.srfg.iot.aas.common.Qualifiable;

public interface QualifiableEvent extends ApiEvent {
	Qualifiable getEntity();
	Qualifiable getDTO();
}
