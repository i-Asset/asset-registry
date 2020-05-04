package at.srfg.iot.aas.service.basys.event;

import at.srfg.iot.aas.common.Referable;

/**
 * Event for reading the data of
 * {@link Referable} elements.
 * 
 * @author dglachs
 *
 */
public interface GetReferable extends GetElement {
	/**
	 * Obtain access to the model element implementing {@link Referable}
	 * @return
	 */
	Referable getLocal();
	
}