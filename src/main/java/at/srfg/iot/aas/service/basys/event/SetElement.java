package at.srfg.iot.aas.service.basys.event;

import java.util.Map;

/**
 * Root interface for all mapping events used
 * to create or update local data
 * 
 * @author dglachs
 *
 */
public interface SetElement {
	/**
	 * Obtain access to the basyx map used
	 * for mapping to the local entities.
	 * @return
	 */
	public Map<String,Object> getBasyxMap();

}
