package at.srfg.iot.aas.service.indexing;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;

public interface AssetTypeIndexingEvent {
	AssetAdministrationShell getEventElement();

}
