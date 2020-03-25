package at.srfg.iot.aas.basys.event.handler;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basys.event.GetSubmodelEvent;
import at.srfg.iot.aas.basys.event.SetSubmodelEvent;
import at.srfg.iot.aas.basys.event.handler.util.MappingHelper;
import at.srfg.iot.aas.basys.event.publisher.MappingEventPublisher;
import at.srfg.iot.aas.model.KeyElementsEnum;
import at.srfg.iot.aas.model.submodel.Submodel;
import at.srfg.iot.aas.model.submodel.elements.Blob;
import at.srfg.iot.aas.model.submodel.elements.File;
import at.srfg.iot.aas.model.submodel.elements.Operation;
import at.srfg.iot.aas.model.submodel.elements.OperationVariable;
import at.srfg.iot.aas.model.submodel.elements.Property;
import at.srfg.iot.aas.model.submodel.elements.ReferenceElement;
import at.srfg.iot.aas.model.submodel.elements.RelationshipElement;
import at.srfg.iot.aas.model.submodel.elements.SubmodelElement;
import at.srfg.iot.aas.model.submodel.elements.SubmodelElementCollection;
import at.srfg.iot.aas.repository.ReferableRepository;

@Component
public class SubmodelHandler {
	@Autowired
	private ReferableRepository<SubmodelElement> submodelElementRepo;
	
	@Autowired
	private MappingEventPublisher publisher;

	@EventListener
	public void onSubmodelSet(SetSubmodelEvent event) {
		System.out.println("Submodel handling: " + MappingHelper.getIdShort(event.getBasyxMap()));
		
		Collection<Map<String,Object>> properties = MappingHelper.getAsCollection(event.getBasyxMap(), SubModel.SUBMODELELEMENT);
		
		for ( Map<String,Object> property : properties ) {
			
			String idShort = MappingHelper.getIdShort(property);
			// find the 
			
			
			Optional<SubmodelElement> local = event.getLocal().getSubmodelElement(idShort);
			SubmodelElement submodelElement = local.orElse(create(event.getLocal(), property));

			if ( submodelElement != null) {
				// process the submodel element
				publisher.handleSubmodelElement(property, submodelElement);
				
//				new SubmodelElementData(property, submodelElement).accept(new SubmodelElementVisitor<>());
				submodelElementRepo.save(submodelElement);
			}
		}

		
	}
	
	@EventListener
	public void onSubmodelGet(GetSubmodelEvent event) {
		System.out.println("Submodel handling");
		
		for ( SubmodelElement submodelElement : event.getLocal().getSubmodelElements() ) {
			org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement s = publisher.getFromSubmodelElement(submodelElement);
			event.getBasyxMap().addSubModelElement(s);
		}
		
	}	
	private SubmodelElement create(Submodel model, Map<String,Object> map) {
		//
		String idShort = MappingHelper.getIdShort(map);
		KeyElementsEnum elementType = MappingHelper.getModelType(map);
		switch(elementType) {
		// Data elements
		case DataElement:
			Map<String,Object> valueType = MappingHelper.getElementAsMap(map, "valueType");
			PropertyValueTypeDef def = PropertyValueTypeDefHelper.readTypeDef(valueType);
			Property property = new Property(idShort, model);
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
		case OperationVariable:
			return new OperationVariable(idShort, model);
		case RelationshipElement:
			return new RelationshipElement(idShort, model);
		case SubmodelElementCollection:
			return new SubmodelElementCollection(idShort, model);
			
		default:
			return null;
		}	
	}

}
