package at.srfg.iot.aas.basys.event;

import at.srfg.iot.aas.api.HasKind;

/**
 * Event for processing the HasKind stereotype
 * @author dglachs
 *
 */
public interface GetHasKind extends GetElement {
	/**
	 * Obtain access to the model element implementing {@link HasKind}
	 * @return
	 */
	HasKind getLocal();
}
