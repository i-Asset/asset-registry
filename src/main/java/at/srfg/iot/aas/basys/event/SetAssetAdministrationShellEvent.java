package at.srfg.iot.aas.basys.event;

import java.util.Map;

import at.srfg.iot.aas.model.AssetAdministrationShell;

/**
 * Event for mapping {@link AssetAdministrationShell}
 * @author dglachs
 *
 */
public class SetAssetAdministrationShellEvent extends SetIdentifiableElement<AssetAdministrationShell> 
	
	implements SetReferable, SetHasDataSpecification, SetIdentifiable {

	public SetAssetAdministrationShellEvent(Map<String, Object> map, AssetAdministrationShell referable) {
		super(map, referable);
	}
}
