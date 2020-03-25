package at.srfg.iot.aas.basys.event;

import at.srfg.iot.aas.api.Identifiable;

/**
 * Event for processing the Identifiable stereotype
 * @author dglachs
 *
 */
public interface GetIdentifiable extends GetElement {
	/**
	 * Obtain access to the model element implementing {@link Identifiable}
	 * @return
	 */
	Identifiable getLocal();
	
}
