package at.srfg.iot.aas.service.registry.event;

import at.srfg.iot.common.datamodel.asset.aas.common.Qualifiable;

public interface QualifiableEvent extends ApiEvent {
	Qualifiable getEntity();
	Qualifiable getDTO();
}
