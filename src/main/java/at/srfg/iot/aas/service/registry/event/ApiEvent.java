package at.srfg.iot.aas.service.registry.event;

import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;

public interface ApiEvent {
	/**
	 * Provide access to the persisted entity.
	 * The entity must be already assigned to it's parent elements, e.g.
	 * <ul>
	 * <li>{@link Submodel#getAssetAdministrationShell()} must provide the {@link AssetAdministrationShellEvent}
	 * <li>{@link SubmodelElement#getSubmodel()} must point to the {@link Submodel} and
	 * <li>{@link SubmodelElement#getParent()} must point to a {@link SubmodelElementContainer}
	 * </ul>
	 * @return
	 */
	Object getEntity();
	/**
	 * Provide access to the provided Data Transfer object (DTO) 
	 * @return
	 */
	Object getDTO();

}
