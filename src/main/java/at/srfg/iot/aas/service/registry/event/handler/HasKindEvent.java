package at.srfg.iot.aas.service.registry.event.handler;

import at.srfg.iot.aas.common.HasKind;

public interface HasKindEvent extends ApiEvent {
	public HasKind getEntity();
	public HasKind getDTO();

}
