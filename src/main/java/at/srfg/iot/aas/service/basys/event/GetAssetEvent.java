package at.srfg.iot.aas.service.basys.event;

import at.srfg.iot.aas.basic.Asset;

public class GetAssetEvent extends GetIdentifiableElementEvent<Asset, org.eclipse.basyx.aas.metamodel.map.parts.Asset>
	implements GetReferable, GetIdentifiable, GetHasDataSpecification {

	public GetAssetEvent(Asset asset) {
		super(newAsset(), asset);
	}

}
