package at.srfg.iot.aas.service.registry.event;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.common.referencing.ReferableElement;
import at.srfg.iot.aas.common.referencing.Reference;
import at.srfg.iot.aas.modeling.SubmodelElement;
import at.srfg.iot.aas.modeling.submodelelement.EventElement;

public interface EventElementEvent extends ApiEvent {
	EventElement getEntity();
	EventElement getDTO();
	
	/**
	* determine whether whether the semantic {@link ReferableElement} 
	* has been resolved, e.g. does not point to a (non persistable) {@link Reference}
	* @return <code>true</code> when {@link #getSemanticElement()} is <code>null</code> or not a {@link Reference}
	*/
	@JsonIgnore
	default boolean isObservedReferenceResolved() {
		if ( getDTO().getObservableReference() != null) {
			if ( getEntity().getObservableElement() != null  ) {
				return getEntity().getObservableElement().asReference().equals(getDTO().getObservableReference());		
			}
			return false;
		}
		return true;
	}
	@JsonIgnore
	default boolean isMessageBrokerResolved() {
		if ( getDTO().getMessageBroker() != null) {
			if ( getEntity().getMessageBrokerElement() != null  ) {
				return getEntity().getMessageBrokerElement().asReference().equals(getDTO().getMessageBroker());		
			}
			return false;
		}
		return true;
	}
	default boolean isValidObservedElement(ReferableElement observedElement) {
		if ( observedElement instanceof AssetAdministrationShell) {
			return true;
		}
		else if ( observedElement instanceof SubmodelElementContainer) {
			return true;
			
		}
		else if ( observedElement instanceof SubmodelElement) {
			return true;
		}
		else {
			return false;
		}
	}
	default boolean isValidMessageBroker(ReferableElement observedElement) {
		if ( observedElement instanceof SubmodelElementContainer) {
			return true;
		}
		else {
			return false;
		} 
	}
}
