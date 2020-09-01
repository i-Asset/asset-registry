package at.srfg.iot.aas.service.registry.event;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;

public interface SubmodelEvent extends ApiEvent {
	Submodel getEntity();
	Submodel getDTO();
	AssetAdministrationShell getAssetAdministrationShell();
}
