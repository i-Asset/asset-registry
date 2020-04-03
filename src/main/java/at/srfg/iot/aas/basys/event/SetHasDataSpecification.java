package at.srfg.iot.aas.basys.event;

import at.srfg.iot.aas.common.HasDataSpecification;
/**
 * Event for setting {@link HasDataSpecification} 
 * 
 * @author dglachs
 *
 */
public interface SetHasDataSpecification extends SetElement {
	public HasDataSpecification getLocal();
	
}
