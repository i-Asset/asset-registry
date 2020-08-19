package at.srfg.iot.aas.service.registry.event.handler;

import at.srfg.iot.aas.common.HasSemantics;

public interface HasSemanticsEvent extends ApiEvent {
	HasSemantics getEntity();
	HasSemantics getDTO();

}
