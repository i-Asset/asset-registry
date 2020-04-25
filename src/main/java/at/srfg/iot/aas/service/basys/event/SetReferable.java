package at.srfg.iot.aas.service.basys.event;

import at.srfg.iot.aas.common.Referable;

/**
 * Event interface for mapping basyx data to 
 * {@link Referable} entities.
 * @author dglachs
 *
 */
public interface SetReferable extends SetElement {
	/**
	 * Obtain access to the local element implementing {@link Referable}
	 * @return
	 */
	public Referable getLocal();

}
