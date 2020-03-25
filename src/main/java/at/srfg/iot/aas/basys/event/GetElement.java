package at.srfg.iot.aas.basys.event;

import java.util.Map;

/**
 * Root interface for all mapping events
 * producing the basyx representation of the
 * local entities.
 * 
 * @author dglachs
 *
 */
public interface GetElement {
	/**
	 * Obtain access to the basyx map used
	 * to map from the local entities.
	 * @return the basyx map
	 */
	public Map<String,Object> getBasyxMap();

}
