package at.srfg.iot.aas.service.registry.event.handler;

import at.srfg.iot.aas.basic.Asset;

public class AssetEventObject extends RegistryEventObject<Asset, Asset> {
	/**
	 * default serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;

	public AssetEventObject(Object source, Asset api) {
		super(source, api);
	}

	@Override
	protected Asset newEntity() {
		return new Asset();
	}


}
