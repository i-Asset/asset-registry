package at.srfg.iot.aas.basys.event;

import java.util.Map;

import at.srfg.iot.aas.basic.Asset;

/**
 * Event for mapping {@link Asset}
 * @author dglachs
 *
 */
public class SetAssetEvent extends SetIdentifiableElement<Asset> 
	
	implements SetReferable, SetHasKind, SetHasDataSpecification, SetIdentifiable {

	public SetAssetEvent(Map<String, Object> map, Asset referable) {
		super(map, referable);
	}



}
