package at.srfg.iot.aas.service.basys.event;

import at.srfg.iot.aas.common.Identifiable;

/**
 * Event for processing the Identifiable stereotype
 * @author dglachs
 *
 */
public interface IdentifiableEvent extends BasyxEvent {
	/**
	 * Obtain access to the model element implementing {@link Identifiable}
	 * @return
	 */
	Identifiable getLocal();
	default org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable getBasyxIdentifiable() {
		return org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable.createAsFacade(getBasyxMap());
	}
}
