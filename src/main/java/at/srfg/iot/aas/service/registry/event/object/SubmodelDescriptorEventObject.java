package at.srfg.iot.aas.service.registry.event.object;


import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.aas.service.registry.event.IdentifiableEvent;
import at.srfg.iot.aas.service.registry.event.ReferableEvent;
import at.srfg.iot.aas.service.registry.event.SubmodelDescriptorEvent;

public class SubmodelDescriptorEventObject extends RegistryEventObject<Submodel, SubmodelDescriptor> 
	// all the handlers for the implemented interfaces are visited on every event 
	implements SubmodelDescriptorEvent, IdentifiableEvent, ReferableEvent {
	private final AssetAdministrationShell aasIdentifier;
	/**
	 * default serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;

	public SubmodelDescriptorEventObject(Object source, AssetAdministrationShell aas, Submodel entity, SubmodelDescriptor dto) {
		super(source, entity, dto);
		// keep the identifier (when required)
		this.aasIdentifier = aas;
	}
	public AssetAdministrationShell getAssetAdministrationShell() {
		return aasIdentifier;
	}
}
