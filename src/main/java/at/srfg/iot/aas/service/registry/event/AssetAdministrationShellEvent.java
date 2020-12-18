package at.srfg.iot.aas.service.registry.event;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;

public interface AssetAdministrationShellEvent extends ApiEvent {
	AssetAdministrationShell getEntity();
	AssetAdministrationShell getDTO();
	/**
	 * Check whether a provided parent AAS has been resolved or not
	 * @return
	 */
	@JsonIgnore
	default boolean isDerivedFromResolved() {
		// the reference is resolved when 
		// the DTO does NOT have a reference OR
		return (getDTO().getDerivedFrom() == null || ( 
				// the actual entity has a derived element AND 
				getEntity().getDerivedFromElement() != null && 
				// the actual derived from IS the same than the reference provided
				Reference.isSameIdentifiable(getEntity().getDerivedFromElement(), getDTO().getDerivedFrom())
			));
	}

}
