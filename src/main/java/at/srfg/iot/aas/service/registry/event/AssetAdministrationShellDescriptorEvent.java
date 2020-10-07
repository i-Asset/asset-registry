package at.srfg.iot.aas.service.registry.event;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.directory.AssetAdministrationShellDescriptor;

public interface AssetAdministrationShellDescriptorEvent extends ApiEvent {
	AssetAdministrationShell getEntity();
	AssetAdministrationShellDescriptor getDTO();

}
