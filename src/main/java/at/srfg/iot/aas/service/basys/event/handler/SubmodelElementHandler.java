package at.srfg.iot.aas.service.basys.event.handler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.service.basys.event.GetSubmodelElementEvent;
import at.srfg.iot.aas.service.basys.event.SetSubmodelElementEvent;
import at.srfg.iot.aas.service.basys.event.handler.util.MappingHelper;
import at.srfg.iot.aas.service.basys.event.publisher.MappingEventPublisher;
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

@Component
public class SubmodelElementHandler {
	@Autowired
	private MappingEventPublisher mappingEventPublisher;

	@EventListener
	public void onSubmodelElementSet(SetSubmodelElementEvent event) {
		System.out.println("Store SubmodelElement: " + event.getLocal().getIdShort() );
		if ( event.getLocal() instanceof Property ) {
			visitProperty(event.getBasyxMap(),(Property)event.getLocal());
		}
		if ( event.getLocal() instanceof Blob ) {
			visitBlob(event.getBasyxMap(),(Blob)event.getLocal());
		}
		if ( event.getLocal() instanceof File) {
			visitFile(event.getBasyxMap(),(File)event.getLocal());
		}
		if ( event.getLocal() instanceof SubmodelElementCollection) {
			visitCollection(event.getBasyxMap(),(SubmodelElementCollection)event.getLocal());
		}
	}
	
	@EventListener
	public void onSubmodelElementGet(GetSubmodelElementEvent event) {
		System.out.println("Read SubmodelElement: " + event.getLocal().getIdShort());
		
		
		if ( event.getLocal() instanceof Property ) {
			readProperty(event.getBasyxMap(), (Property)event.getLocal());
		}
//		if ( event.getLocal() instanceof Blob ) {
//			visitBlob(event.getBasyxMap(),(Blob)event.getLocal());
//		}
//		if ( event.getLocal() instanceof File) {
//			visitFile(event.getBasyxMap(),(File)event.getLocal());
//		}
		if ( event.getLocal() instanceof SubmodelElementCollection) {
			readCollection(event.getBasyxMap(), (SubmodelElementCollection)event.getLocal());
		}
	}	
	private void visitCollection(Map<String,Object> map, SubmodelElementCollection collection) {
		Collection<Map<String,Object>> collectionElements =MappingHelper.getAsCollection(map, SubModel.SUBMODELELEMENT);
		for ( Map<String,Object> element : collectionElements ) {
			String idShort = MappingHelper.getIdShort(map);
			// find the 
			
			
			Optional<SubmodelElement> local = collection.getSubmodelElement(idShort);
			SubmodelElement submodelElement = local.orElse(create(collection, element));

			if ( submodelElement != null) {
				// process the submodel element
				mappingEventPublisher.handleSubmodelElement(element, submodelElement);
			}
			
			
		}

	}
	private SubmodelElement create(SubmodelElementCollection collection, Map<String,Object> map) {
		//
		org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement elem = org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement.createAsFacade(map);
		String idShort = MappingHelper.getIdShort(map);
		KeyElementsEnum elementType = MappingHelper.getModelType(map);
		switch(elementType) {
		// Data elements
		case DataElement:
			Map<String,Object> valueType = MappingHelper.getElementAsMap(map, "valueType");
			
			PropertyValueTypeDef def = PropertyValueTypeDefHelper.readTypeDef(valueType);
			Property property = new Property(idShort, collection);
			property.setValueQualifier(def.name().toUpperCase());
			return property;
		case Blob:
			return new Blob(idShort, collection);
		case File:
			return new File(idShort, collection);
		case ReferenceElement:
			return new ReferenceElement(idShort, collection);
		// Other Elements
		case Event:
			return null;
		case Operation:
			return new Operation(idShort, collection);
		case OperationVariable:
			return new OperationVariable(idShort, collection);
		case RelationshipElement:
			return new RelationshipElement(idShort, collection);
		case SubmodelElementCollection:
			return new SubmodelElementCollection(idShort, collection);
			
		default:
			return null;
		}
	}

	private void visitFile(Map<String, Object> map, File value) {
		value.setValue(MappingHelper.getElementValue(map, String.class, "value"));
		value.setMimeType(MappingHelper.getElementValue(map, String.class, "mimeType"));
	}
	private void visitProperty(Map<String,Object> map, Property value) {
		Map<String,Object> valueType = MappingHelper.getElementAsMap(map, "valueType");
		PropertyValueTypeDef def = PropertyValueTypeDefHelper.readTypeDef(valueType);
		switch (def) {
		case String:
			value.setValue(MappingHelper.getElementValue(map, String.class, "value"));
			value.setValueQualifier("STRING");
			break;
		case Double:
			Double doubleValue = MappingHelper.getElementValue(map, Double.class, "value");
			value.setValue(String.valueOf(doubleValue));
			value.setValueQualifier("DOUBLE");
			break;
		case Integer:
			Integer intValue = MappingHelper.getElementValue(map, Integer.class, "value");
			value.setValue(String.valueOf(intValue));
			value.setValueQualifier("INTEGER");
			break;
		default:
		}
	}
	private void visitBlob(Map<String,Object> element, Blob value) {
		byte[] bytes = MappingHelper.getElementValue(element, byte[].class, "value");
		value.setValue(bytes);
		value.setMimeType(MappingHelper.getElementValue(element, String.class, "mimeType"));
	}
	private void readCollection(org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement map, SubmodelElementCollection element) {
		if ( map instanceof org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection) {
			org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection collection = (org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection) map;
			Map<String,ISubmodelElement> elementMap = new HashMap<>();
			
			for (SubmodelElement subElement : element.getSubmodelElements() ) {
				
				ISubmodelElement sub = mappingEventPublisher.getFromSubmodelElement(subElement);
				elementMap.put(sub.getIdShort(),sub);
			}
			collection.setElements(elementMap);
		}
	}
	private void readProperty(org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement map, Property element) {
		// 
		if ( map instanceof org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property) {
			org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property property = (org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property) map;
			String value = element.getValue();
			String vQualifier = element.getValueQualifier();
			switch(vQualifier) {
			case "STRING":
				property.set(value);
				break;
			case "INTEGER":
				property.set(Integer.valueOf(value));
				break;
			case "DOUBLE":
				property.set(Double.valueOf(value));
				break;
			}
		}
	}
	
	

}
