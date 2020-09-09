package at.srfg.iot.aas.service.registry.event;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;

import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.SubmodelElementContainer;

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
