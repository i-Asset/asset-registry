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
	 * 
	 * @return
	 */
	Kind getKind();
	void setKind(Kind kind);
}
