package at.srfg.iot.aas.service.basys.event;

import java.util.Map;

/**
 * Root interface for all mapping events used
 * to create or update local data from Basyx Map
 * 
 * @author dglachs
 *
 */
public interface BasyxEvent {
	/**
	 * Obtain access to the raw basyx map used
	 * for mapping to the local entities.
	 * @return
	 */
	public Map<String,Object> getBasyxMap();
}
