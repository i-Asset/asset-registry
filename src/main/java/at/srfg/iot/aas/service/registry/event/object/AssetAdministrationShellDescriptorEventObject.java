package at.srfg.iot.aas.service.registry.event.object;


import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.aas.service.registry.event.AssetAdministrationShellDescriptorEvent;
import at.srfg.iot.aas.service.registry.event.IdentifiableEvent;
import at.srfg.iot.aas.service.registry.event.ReferableEvent;

public class AssetAdministrationShellDescriptorEventObject extends RegistryEventObject<AssetAdministrationShell, AssetAdministrationShellDescriptor> 
	// all the handlers for the implemented interfaces are visited on every event 
	implements AssetAdministrationShellDescriptorEvent, IdentifiableEvent, ReferableEvent {
	/**
	 * default serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;

	public AssetAdministrationShellDescriptorEventObject(Object source, AssetAdministrationShell entity, AssetAdministrationShellDescriptor dto) {
		super(source, entity, dto);
	}


}
