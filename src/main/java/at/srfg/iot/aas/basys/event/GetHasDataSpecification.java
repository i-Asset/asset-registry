package at.srfg.iot.aas.basys.event;

import at.srfg.iot.aas.api.HasDataSpecification;
/**
 * Event for setting {@link HasDataSpecification} 
 * 
 * @author dglachs
 *
 */
public interface GetHasDataSpecification extends GetElement {
	public HasDataSpecification getLocal();
	
}
