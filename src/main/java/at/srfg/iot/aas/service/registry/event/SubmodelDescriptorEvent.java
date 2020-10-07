package at.srfg.iot.aas.service.registry.event;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.basic.directory.SubmodelDescriptor;

public interface SubmodelDescriptorEvent extends ApiEvent {
	Submodel getEntity();
	SubmodelDescriptor getDTO();
	AssetAdministrationShell getAssetAdministrationShell();
}
