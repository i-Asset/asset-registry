package at.srfg.iot.aas.service.registry.event;

public interface ApiEvent {
	/**
	 * Provide access to the persisted entity.
	 * The entity must be already assigned to it's parents 
	 * @return
	 */
	Object getEntity();
	/**
	 * Provide access to the provided Data Transfer object (DTO) 
	 * @return
	 */
	Object getDTO();

}
