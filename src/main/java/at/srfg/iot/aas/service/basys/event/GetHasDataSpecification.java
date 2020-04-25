package at.srfg.iot.aas.service.basys.event;

import at.srfg.iot.aas.common.HasDataSpecification;
/**
 * Event for setting {@link HasDataSpecification} 
 * 
 * @author dglachs
 *
 */
public interface GetHasDataSpecification extends GetElement {
	public HasDataSpecification getLocal();
	
}
