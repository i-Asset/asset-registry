package at.srfg.iot.aas.service.registry.event;

import at.srfg.iot.common.datamodel.asset.aas.common.HasDataSpecification;

public interface HasDataSpecificationEvent extends ApiEvent {
	HasDataSpecification getEntity();
	HasDataSpecification getDTO();

}
