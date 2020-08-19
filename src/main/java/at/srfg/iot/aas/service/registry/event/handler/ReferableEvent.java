package at.srfg.iot.aas.service.registry.event.handler;

import at.srfg.iot.aas.common.Referable;

/**
 * Event for handling {@link Referable}s 
 * @author dglachs
 *
 */
public interface ReferableEvent extends ApiEvent {
	/**
	 * Return the already stored and existing element, may be <code>null</code>
	 * @return
	 */
	Referable getEntity();
	/**
	 * Return the corresponding object provided via API
	 * @return
	 */
	Referable getDTO();
}
