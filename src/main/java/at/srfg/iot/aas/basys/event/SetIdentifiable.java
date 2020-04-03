package at.srfg.iot.aas.basys.event;

import at.srfg.iot.aas.common.Identifiable;

/**
 * Event for processing the Identifiable stereotype
 * @author dglachs
 *
 */
public interface SetIdentifiable extends SetElement {
	/**
	 * Obtain access to the model element implementing {@link Identifiable}
	 * @return
	 */
	Identifiable getLocal();
}
