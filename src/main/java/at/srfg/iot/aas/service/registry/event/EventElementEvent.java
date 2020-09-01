package at.srfg.iot.aas.service.registry.event;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.referencing.ReferableElement;
import at.srfg.iot.aas.common.referencing.Reference;
import at.srfg.iot.aas.modeling.SubmodelElement;
import at.srfg.iot.aas.modeling.submodelelement.Event;

public interface EventElementEvent extends ApiEvent {
	Event getEntity();
	Event getDTO();
	
	/**
	* determine whether whether the semantic {@link ReferableElement} 
	* has been resolved, e.g. does not point to a (non persistable) {@link Reference}
	* @return <code>true</code> when {@link #getSemanticElement()} is <code>null</code> or not a {@link Reference}
	*/
	@JsonIgnore
	default boolean isObservedReferenceResolved() {
		// 
		return (getEntity().getObservableElement() == null || 
				!(getEntity().getObservableElement() instanceof Reference));
	}
	@JsonIgnore
	default boolean isSemanticElementResolved() {
		return (getEntity().getSemanticElement() == null || 
				!(getEntity().getSemanticElement() instanceof Reference));
	}
	@JsonIgnore
	default boolean isMessageBrokerResolved() {
		return (getEntity().getMessageBroker() == null || 
				!(getEntity().getMessageBroker() instanceof Reference));
	}
	default boolean isValidObservedElement(ReferableElement observedElement) {
		if ( observedElement instanceof AssetAdministrationShell) {
			return true;
		}
		else if ( observedElement instanceof Submodel) {
			return true;
			
		}
		else if ( observedElement instanceof SubmodelElement) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
