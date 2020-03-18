package at.srfg.iot.aas.api;

import at.srfg.iot.aas.model.Identifier;
import at.srfg.iot.aas.model.ReferableElement;
import at.srfg.iot.aas.model.Reference;

public interface HasSemantics {
	ReferableElement getSemanticElement();
	void setSemanticElement(ReferableElement semanticId);
	Identifier getSemanticIdentifier();
	/**
	 * provide a {@link Reference} to the parent element
	 * @return
	 */
	default Reference getSemanticId() {
		if ( getSemanticElement()== null) {
			return null;
		}
		return new Reference(getSemanticElement());
	}

	
}