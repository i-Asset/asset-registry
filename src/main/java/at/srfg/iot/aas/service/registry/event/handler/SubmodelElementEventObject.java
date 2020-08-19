package at.srfg.iot.aas.service.registry.event.handler;

import at.srfg.iot.aas.modeling.SubmodelElement;
import at.srfg.iot.aas.modeling.submodelelement.Blob;
import at.srfg.iot.aas.modeling.submodelelement.File;
import at.srfg.iot.aas.modeling.submodelelement.Operation;
import at.srfg.iot.aas.modeling.submodelelement.OperationVariable;
import at.srfg.iot.aas.modeling.submodelelement.Property;
import at.srfg.iot.aas.modeling.submodelelement.ReferenceElement;

public class SubmodelElementEventObject extends RegistryEventObject<SubmodelElement, SubmodelElement> 
	implements ReferableEvent, HasSemanticsEvent, HasKindEvent, HasDataSpecificationEvent, QualifiableEvent {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -864423526018683240L;
	public SubmodelElementEventObject(Object source, SubmodelElement stored, SubmodelElement api) {
		super(source, stored, api);
	}
	public SubmodelElementEventObject(Object source, SubmodelElement api) {
		super(source, api);
	}
	@Override
	protected SubmodelElement newEntity() {
		// TODO Auto-generated method stub
		SubmodelElement dto = getDTO();
		switch( dto.getModelType() ) {
		case Property:
			return new Property();
		case File:
			return new File();
		case ReferenceElement:
			return new ReferenceElement();
		case Blob:
			return new Blob();
//		case Event:
//			return new Event();
		case Operation:
			return new Operation();
		case OperationVariable:
			return new OperationVariable();
		default:
			throw new IllegalArgumentException("The requested model type cannot be instantiated!");
		}
		
	}


}
