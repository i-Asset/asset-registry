package at.srfg.iot.aas.service.registry.event;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.SubmodelDescriptor;

public interface SubmodelDescriptorEvent extends ApiEvent {
	Submodel getEntity();
	SubmodelDescriptor getDTO();
	AssetAdministrationShell getAssetAdministrationShell();
}
