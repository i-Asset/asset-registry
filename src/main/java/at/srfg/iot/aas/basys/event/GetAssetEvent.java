package at.srfg.iot.aas.basys.event;

import at.srfg.iot.aas.model.Asset;

public class GetAssetEvent extends GetIdentifiableElementEvent<Asset, org.eclipse.basyx.aas.metamodel.map.parts.Asset>
	implements GetReferable, GetIdentifiable, GetHasKind, GetHasDataSpecification {

	public GetAssetEvent(Asset asset) {
		super(newAsset(), asset);
	}

}
