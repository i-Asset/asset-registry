package at.srfg.iot.aas.service.registry.event.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.aas.service.registry.RegistryWorker;
import at.srfg.iot.aas.service.registry.event.submodel.BlobEvent;
import at.srfg.iot.aas.service.registry.event.submodel.FileEvent;
import at.srfg.iot.aas.service.registry.event.submodel.OperationEvent;
import at.srfg.iot.aas.service.registry.event.submodel.OperationVariableEvent;
import at.srfg.iot.aas.service.registry.event.submodel.PropertyEvent;
import at.srfg.iot.aas.service.registry.event.submodel.ReferenceElementEvent;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.OperationVariable;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;

@Component
public class SubmodelElementEventHandler {
	@Autowired
	RegistryService registry;
	@Autowired
	ApplicationEventPublisher publisher;
	@Autowired
	RegistryWorker worker;
	
	@EventListener
	@Order(10)
	public void onPropertyEvent(PropertyEvent event) {
		Property entity = event.getEntity();
		Property dto = event.getDTO();
		// TODO: add validation logic
		entity.setValueQualifier(dto.getValueQualifier());
		entity.setValue(dto.getValue());
	}
	@EventListener
	@Order(10)
	public void onBlobEvent(BlobEvent event) {
		// store the byte array
		event.getEntity().setValue(event.getDTO().getValue());
	}
	@EventListener
	@Order(10)
	public void onFileEvent(FileEvent event) {
		// FILE specifies a mime type and the file name
		event.getEntity().setMimeType(event.getDTO().getMimeType());
		event.getEntity().setValue(event.getDTO().getValue());
	}
	
	@EventListener
	@Order(10)
	public void onReferenceElementEvent(ReferenceElementEvent event) {
		// need to resolve the reference
		Optional<Reference> valueRef = event.getDTO().getValueeAsReference();
		if ( valueRef.isPresent() ) {
			Optional<ReferableElement> ref = registry.resolveReference(valueRef.get(), ReferableElement.class);
			if ( ref.isPresent()) {
				event.getEntity().setValue(ref.get());
			}
		}
		else {
			// TODO: need to send event for the ReferableElement ...
		}
	}
	@EventListener
	@Order(10)
	public void onOperationEvent(OperationEvent event) {
		// need to resolve the reference
		Operation operation = event.getEntity();
		Operation dto = event.getDTO();
		// 
		for (OperationVariable variable : dto.getChildElements(OperationVariable.class)) {
			// SubmodelElement is Referable only - identification only via idShort! 
			Optional<OperationVariable> existing = operation.getChildElement(variable.getIdShort(), OperationVariable.class);
			if ( existing.isPresent() ) {
				OperationVariable submodelElement = existing.get();
				if ( submodelElement.getSubmodel() != null && submodelElement.getParentElement()!= null) {
					worker.setSubmodelElement(existing.get(), variable);
				}
								
			}
			else {
				// when not derived, create the element
				if (! Boolean.TRUE.equals(variable.getDerived())) {
					// no corresponding entity present 
					OperationVariable vEntity = new OperationVariable(variable.getIdShort(), operation, variable.getDirection());
					worker.setSubmodelElement(vEntity, variable);
				}
			}
		}
		
	}
	@EventListener
	@Order(10)
	public void onOperationVariableEvent(OperationVariableEvent event) {
		OperationVariable entity = event.getEntity();
		OperationVariable dto = event.getDTO();
		// 
		SubmodelElement sub =  dto.getValue();
		// value is "aggregated"
		Optional<SubmodelElement> submodelElement = registry.resolveReference(sub.asReference(), SubmodelElement.class);
		
		if ( submodelElement.isPresent()) {
			SubmodelElement elem = submodelElement.get();
			if (! elem.isInstance()) {
				entity.setValue(elem);
			}
		}
		else {
			// TODO: submodel element of Kind "Type" not present - will have to create it by sending "asynchronous" event
		}
		
	}

}
