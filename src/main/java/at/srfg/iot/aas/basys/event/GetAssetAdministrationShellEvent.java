package at.srfg.iot.aas.basys.event;

import at.srfg.iot.aas.model.AssetAdministrationShell;

/**
 * Event for mapping {@link AssetAdministrationShell}
 * from lo
 * @author dglachs
 *
 */
public class GetAssetAdministrationShellEvent 
	extends GetIdentifiableElementEvent<AssetAdministrationShell, org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell> 
	implements GetIdentifiable, GetReferable 	{

	private final boolean descriptorOnly;
	public GetAssetAdministrationShellEvent(AssetAdministrationShell referable) {
		// create the new map as 
		this(referable, false);
		
	}
	public GetAssetAdministrationShellEvent(AssetAdministrationShell referable, boolean descriptorOnly) {
		// create the new map as 
		super(newAAS(), referable);
		this.descriptorOnly = descriptorOnly; 
	}
	public boolean getDescriptorOnly() {
		return descriptorOnly;
	}
}
