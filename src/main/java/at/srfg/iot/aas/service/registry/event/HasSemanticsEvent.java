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
		// 
		
		if ( getDTO().getSemanticId() != null) {
			if ( getDTO().getSemanticElement() != null  ) {
				return getDTO().getSemanticElement().asReference().equals(getDTO().getSemanticId());		
			}
			return false;
		}
		return (getEntity().getSemanticElement() == null || 
				!(getEntity().getSemanticElement() instanceof Reference));
	}

}
