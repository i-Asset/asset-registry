package at.srfg.iot.aas.service.basys.event;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
/**
 * Event for setting {@link AssetAdministrationShell} 
 * 
 * @author dglachs
 *
 */
public interface AssetAdministrationShellEvent extends BasyxEvent {
	public AssetAdministrationShell getLocal();
	
	default org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell getBasyxAssetAdministrationShell() {
		return org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell.createAsFacade(getBasyxMap());
	}
	
}
