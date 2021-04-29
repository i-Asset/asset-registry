package at.srfg.iot.aas.service.registry.event;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.common.datamodel.asset.aas.common.HasSemantics;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;

public interface HasSemanticsEvent extends ApiEvent {
	HasSemantics getEntity();
	HasSemantics getDTO();
	/**
	 * Check whether the {@link ReferableElement} referenced 
	 * with {@link HasSemantics} is valid in the respective context
	 * @param semantic The resolved {@link Reference}
	 * @return
	 */
	public boolean isValidSemanticElement(ReferableElement semantic);
	/**
	* determine whether whether the semantic {@link ReferableElement} 
	* has been resolved, e.g. does not point to a (non persistable) {@link Reference}
	* @return <code>true</code> when {@link #getSemanticElement()} is <code>null</code> or not a {@link Reference}
	*/
	@JsonIgnore
	default boolean isSemanticIdResolved() {
		// DTO provides semantic reference and the 
		
		if ( getDTO().getSemanticId() != null) {
			if ( getDTO().getSemanticElement().isPresent()) {
				// true when semanticElement  
				return getDTO().getSemanticElement().get().asReference().equals(getDTO().getSemanticId());
			}
			return false;
		}
		// true when entity is empty OR  entity semantic elemnt is present and not a reference!
		return (!	getEntity().getSemanticElement().isPresent() 
				|| 	!(Reference.class.isInstance(getEntity().getSemanticElement().get())));
	}

}
