package at.srfg.iot.aas.service.registry.event.object;


import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.service.registry.event.AssetAdministrationShellEvent;
import at.srfg.iot.aas.service.registry.event.IdentifiableEvent;
import at.srfg.iot.aas.service.registry.event.ReferableEvent;

public class AssetAdministrationShellEventObject extends RegistryEventObject<AssetAdministrationShell, AssetAdministrationShell> 
	// all the handlers for the implemented interfaces are visited on every event 
	implements AssetAdministrationShellEvent, IdentifiableEvent, ReferableEvent {
	/**
	 * default serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;

	public AssetAdministrationShellEventObject(Object source, AssetAdministrationShell entity, AssetAdministrationShell dto) {
		super(source, entity, dto);
	}


}
