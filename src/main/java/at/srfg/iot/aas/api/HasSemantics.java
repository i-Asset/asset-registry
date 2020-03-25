package at.srfg.iot.aas.api;

import at.srfg.iot.aas.model.Identifier;
import at.srfg.iot.aas.model.Kind;
import at.srfg.iot.aas.model.ReferableElement;
import at.srfg.iot.aas.model.Reference;

/**
 * Stereotype for an element, that can have
 * a semantic definition.
 * 
 * The semantic definition is either stored
 * <ul>
 * <li>in the {@link Identifier} obtained via {@link #getSemanticIdentifier()} 
 * pointing to an external global element such as eCl@ss, or
 * <li>in the {@link ReferableElement} obtained via {@link #getSemanticElement()}
 * pointing to a local element of {@link Kind#Type} that defines the semantics
 * </ul>
 * @author dglachs
 *
 */
public interface HasSemantics {
	/** 
	 * Obtain the (local) semantic definition of
	 * the current element.
	 * 
	 * @return
	 */
	ReferableElement getSemanticElement();
	/**
	 * Set the semantic element
	 * @param semanticId
	 */
	void setSemanticElement(ReferableElement semanticId);
	/**
	 * Get the external global semantic identifier
	 * @return
	 */
	Identifier getSemanticIdentifier();
	/**
	 * Store the external global semantic identifier
	 * @param identifier
	 */
	void setSemanticIdentifier(Identifier identifier);
	/**
	 * provide a {@link Reference} to the (local) semantic definition
	 * @return
	 */
	default Reference getSemanticId() {
		if ( getSemanticElement()== null) {
			return null;
		}
		return new Reference(getSemanticElement());
	}

	
}