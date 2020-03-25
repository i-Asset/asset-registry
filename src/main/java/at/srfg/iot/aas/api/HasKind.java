package at.srfg.iot.aas.api;

import at.srfg.iot.aas.model.Kind;
/**
 * Defines the {@link Kind} of the 
 * element, one of {@link Kind#Type} or {@link Kind#Instance}
 * @author dglachs
 *
 */
public interface HasKind {
	/**
	 * Get the {@link Kind} of the element
	 * @return
	 */
	Kind getKind();
	/**
	 * Set the {@link Kind} of the element, either {@link Kind#Type}
	 * or {@link Kind#Instance}
	 * 
	 * @param kind The kind
	 */
	void setKind(Kind kind);
}
