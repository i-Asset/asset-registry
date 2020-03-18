package at.srfg.iot.aas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import at.srfg.iot.aas.api.Referable;


/**
 * Helper class holding references to model elements
 * 
 */

public class Reference implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Key> keys;
	
	public Reference() {
		
	}
	public Reference(Referable to) {
		keys = new ArrayList<Key>();
		do {
			keys.add(0, Key.of(to));
			// 
			to = to.getParentElement();
		} while (to != null);
	}

	/**
	 * @return the keys
	 */
	public List<Key> getKeys() {
		return keys;
	}

	/**
	 * @param keys the keys to set
	 */
	public void setKeys(List<Key> keys) {
		this.keys = keys;
	}


	

}