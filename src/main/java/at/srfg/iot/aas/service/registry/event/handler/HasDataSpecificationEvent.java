package at.srfg.iot.aas.service.registry.event.handler;

import at.srfg.iot.aas.common.HasDataSpecification;

public interface HasDataSpecificationEvent extends ApiEvent {
	HasDataSpecification getEntity();
	HasDataSpecification getDTO();

}
