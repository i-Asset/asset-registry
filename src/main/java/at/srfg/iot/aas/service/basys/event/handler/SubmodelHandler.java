package at.srfg.iot.aas.service.basys.event.handler;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.facade.submodelelement.SubmodelElementFacadeFactory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.aas.modeling.SubmodelElement;
import at.srfg.iot.aas.modeling.submodelelement.Blob;
import at.srfg.iot.aas.modeling.submodelelement.File;
import at.srfg.iot.aas.modeling.submodelelement.Operation;
import at.srfg.iot.aas.modeling.submodelelement.OperationVariable;
import at.srfg.iot.aas.modeling.submodelelement.Property;
import at.srfg.iot.aas.modeling.submodelelement.ReferenceElement;
import at.srfg.iot.aas.modeling.submodelelement.RelationshipElement;
import at.srfg.iot.aas.modeling.submodelelement.SubmodelElementCollection;
import at.srfg.iot.aas.repository.registry.ReferableRepository;
import at.srfg.iot.aas.service.basys.event.GetSubmodelEvent;
import at.srfg.iot.aas.service.basys.event.SetSubmodelEvent;
import at.srfg.iot.aas.service.basys.event.handler.util.MappingHelper;
import at.srfg.iot.aas.service.basys.event.publisher.MappingEventPublisher;
import at.srfg.iot.api.ISubmodel;

@Component
public class SubmodelHandler {
	@Autowired
	private ReferableRepository<SubmodelElement> submodelElementRepo;
	
	@Autowired
	private MappingEventPublisher publisher;

	@EventListener
	public void onSubmodelSet(SetSubmodelEvent event) {
		SubModel subModel = event.getBasyxSubModel();
		
		System.out.println("Submodel handling: " + subModel.getIdShort());
		
//		for (ISubmodelElement iSubModelElement : subModel.getSubmodelElements().values() ) {
//			// 
//			Optional<SubmodelElement> local = event.getLocal().getSubmodelElement(iSubModelElement.getIdShort());
//			
//			SubmodelElement submodelElement = local.orElse(create(event.getLocal(), (org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement) iSubModelElement));
//
//			if ( submodelElement != null) {
//				// process the submodel element
//				publisher.handleSubmodelElement((org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement) iSubModelElement, submodelElement);
//				
////				new SubmodelElementData(property, submodelElement).accept(new SubmodelElementVisitor<>());
//				submodelElementRepo.save(submodelElement);
//			}
//		}
		Collection<Map<String,Object>> properties = MappingHelper.getAsCollection(subModel, SubModel.SUBMODELELEMENT);
		
		for ( Map<String,Object> property : properties ) {
			
			String idShort = MappingHelper.getIdShort(property);
			// find the 
			if ( idShort != null && idShort.length() > 0) {
				Optional<at.srfg.iot.api.ISubmodelElement> local = event.getLocal().getSubmodelElement(idShort);
				at.srfg.iot.api.ISubmodelElement submodelElement = local.orElse(create(event.getLocal(), property));
				
				if ( submodelElement != null) {
					// process the submodel element
					publisher.handleSubmodelElement(property, submodelElement);
					// TODO: ensure proper types
					submodelElementRepo.save((SubmodelElement)submodelElement);
				}
				
			}
			
		}

		
	}
	
	@EventListener
	public void onSubmodelGet(GetSubmodelEvent event) {
		System.out.println("Submodel handling");
		
		for ( at.srfg.iot.api.ISubmodelElement submodelElement : event.getLocal().getSubmodelElements() ) {
			org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement s = publisher.getFromSubmodelElement(submodelElement);
			event.getBasyxMap().addSubModelElement(s);
		}
		
	}	
//	private SubmodelElement create(Submodel model, ISubmodelElement map) {
//		String modelType = map.getModelType();
//		KeyElementsEnum elementType = KeyElementsEnum.valueOf(modelType);
//		switch(elementType) {
//		}
//		return null;
//	}
	private SubmodelElement create(ISubmodel model, Map<String,Object> map) {
		//
		ISubmodelElement e = SubmodelElementFacadeFactory.createSubmodelElement(map);
		String idShort = MappingHelper.getIdShort(map);
		KeyElementsEnum elementType = MappingHelper.getModelType(map);
		switch(elementType) {
		// Data elements
		case DataElement:
		case Property:
			Property property = new Property(idShort, model);
			Map<String,Object> valueType = MappingHelper.getElementAsMap(map, "valueType");
			// use String as default
			PropertyValueTypeDef def = PropertyValueTypeDef.String;
			if (! valueType.isEmpty() ) {
				String defName = MappingHelper.getElementValue(valueType, String.class, "dataObjectType", "name");
				if ( defName != null && defName.length()>0) {
					def = PropertyValueTypeDefHelper.fromName(defName);
				}
			}
			property.setValueQualifier(def.name().toUpperCase());
			return property;
		case Blob:
			return new Blob(idShort, model);
		case File:
			return new File(idShort, model);
		case ReferenceElement:
			return new ReferenceElement(idShort, model);
		// Other Elements
		case Event:
			return null;
		case Operation:
			return new Operation(idShort, model);
//		case OperationVariable:
//			return new OperationVariable(idShort, model);
		case RelationshipElement:
			return new RelationshipElement(idShort, model);
		case SubmodelElementCollection:
			return new SubmodelElementCollection(idShort, model);
			
		default:
			return null;
		}	
	}

}
