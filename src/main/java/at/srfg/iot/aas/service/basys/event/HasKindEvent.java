package at.srfg.iot.aas.service.basys.event;

import at.srfg.iot.aas.common.HasKind;

/**
 * Event for processing the HasKind stereotype
 * @author dglachs
 *
 */
public interface HasKindEvent extends BasyxEvent {
	/**
	 * Obtain access to the model element implementing {@link HasKind}
	 * @return
	 */
	HasKind getLocal();
	/**
	 * Obtain access to the stereotype element
	 * @return
	 */
	default org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind getBasyxHasKind() {
		return org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind.createAsFacade(getBasyxMap());
	}
}
