package at.srfg.iot.aas.service.basys.event;

import at.srfg.iot.aas.common.Referable;

/**
 * Event interface for mapping basyx data to 
 * {@link Referable} entities.
 * @author dglachs
 *
 */
public interface ReferableEvent extends BasyxEvent {
	/**
	 * Obtain access to the local element implementing {@link Referable}
	 * @return
	 */
	public Referable getLocal();
	/**
	 * Provide access to the basyx object representing the corresponding {@link org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable}
	 * @return
	 */
	default org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable getBasyxReferable() {
		return org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable.createAsFacade(getBasyxMap(),null);
	}

}
