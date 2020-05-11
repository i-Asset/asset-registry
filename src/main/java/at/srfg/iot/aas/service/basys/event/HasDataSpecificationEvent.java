package at.srfg.iot.aas.service.basys.event;

import at.srfg.iot.aas.common.HasDataSpecification;
/**
 * Event for setting {@link HasDataSpecification} 
 * 
 * @author dglachs
 *
 */
public interface HasDataSpecificationEvent extends BasyxEvent {
	public HasDataSpecification getLocal();
	
	default org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification getBasyxHasDataSpecification() {
		return org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification.createAsFacade(getBasyxMap());
	}
	
}
