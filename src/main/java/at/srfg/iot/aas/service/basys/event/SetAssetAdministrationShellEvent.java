package at.srfg.iot.aas.service.basys.event;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;

import at.srfg.iot.aas.basic.AssetAdministrationShell;

/**
 * Event for mapping {@link AssetAdministrationShell}
 * @author dglachs
 *
 */
public class SetAssetAdministrationShellEvent extends SetIdentifiableElement<AssetAdministrationShell> 
	
	implements SetAssetAdministrationShell, SetReferable, SetHasDataSpecification, SetIdentifiable {

	public SetAssetAdministrationShellEvent(Map<String, Object> map, AssetAdministrationShell referable) {
		super(map, referable);
	}
	public SetAssetAdministrationShellEvent(IAssetAdministrationShell shell, AssetAdministrationShell referable) {
		super((org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell)shell, referable);
	}
	
	
}
