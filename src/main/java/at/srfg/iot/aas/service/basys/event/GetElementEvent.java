package at.srfg.iot.aas.service.basys.event;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.parts.ConceptDictionary;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.RelationshipElement;

import at.srfg.iot.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.aas.modeling.submodelelement.Blob;
import at.srfg.iot.aas.modeling.submodelelement.File;

public abstract class GetElementEvent<T, M extends Map<String,Object>>  implements GetElement {
	
	private final T local;
	private final M mapElement;


	public GetElementEvent(M map, T localEntity) {
		this.mapElement = map;
		this.local = localEntity;
	}


	/**
	 * @return the local
	 */
	public T getLocal() {
		return local;
	}

	/**
	 * @return the mapElement
	 */
	public M getBasyxMap() {
		return mapElement;
	}
	protected static ConceptDictionary newConceptDictionary() {
		return new ConceptDictionary();
	}
	protected static Property newProperty() {
		return new Property();
	}
	protected static SubmodelElementCollection newSubmodelElementCollection() {
		return new SubmodelElementCollection();
	}
	protected static SubmodelElement newSubmodelElement(at.srfg.iot.aas.modeling.SubmodelElement entity) {
		if ( entity instanceof File) {
			return new org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File(((File) entity).getValue(), ((File) entity).getMimeType());
		}
		else if ( entity instanceof Blob) {
			return new org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob(((Blob) entity).getValue(), ((Blob) entity).getMimeType());
		}
		return newSubmodelElement(entity.getModelType());
	}
	private static SubmodelElement newSubmodelElement(KeyElementsEnum element) {
		switch(element) {
		case Property:
			return new Property();
		case SubmodelElementCollection:
			return new SubmodelElementCollection();
//		case Event:
//			return new Event();
		case Operation:
			return new Operation();
//		case OperationVariable:
//			return new OperationVariable();
		case ReferenceElement:
			return new ReferenceElement();
		case RelationshipElement:
			return new RelationshipElement();
		case File:
		case Blob:
		default:
			throw new IllegalArgumentException("Model type is currently not supported");
		}
	}
}
