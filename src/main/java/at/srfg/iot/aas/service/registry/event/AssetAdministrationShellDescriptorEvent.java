package at.srfg.iot.aas.service.registry.event;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;

public interface AssetAdministrationShellDescriptorEvent extends ApiEvent {
	AssetAdministrationShell getEntity();
	AssetAdministrationShellDescriptor getDTO();

}
