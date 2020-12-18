package at.srfg.iot.aas.service.indexing;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;

public class AssetTypeIndexingEventObject extends IndexingEvent<AssetAdministrationShell> 
	implements AssetTypeIndexingEvent  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AssetTypeIndexingEventObject(Object source, AssetAdministrationShell identifiable) {
		super(source, identifiable);
	}

}
