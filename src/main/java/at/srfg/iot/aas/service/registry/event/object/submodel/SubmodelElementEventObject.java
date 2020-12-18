package at.srfg.iot.aas.service.registry.event.object.submodel;

import at.srfg.iot.aas.service.registry.event.HasDataSpecificationEvent;
import at.srfg.iot.aas.service.registry.event.HasKindEvent;
import at.srfg.iot.aas.service.registry.event.HasSemanticsEvent;
import at.srfg.iot.aas.service.registry.event.QualifiableEvent;
import at.srfg.iot.aas.service.registry.event.ReferableEvent;
import at.srfg.iot.aas.service.registry.event.object.RegistryEventObject;
import at.srfg.iot.common.datamodel.asset.aas.basic.GlobalReference;
import at.srfg.iot.common.datamodel.asset.aas.common.HasKind;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.dictionary.ConceptDescription;
import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Blob;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.File;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.OperationVariable;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.ReferenceElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.RelationshipElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.SubmodelElementCollection;
import at.srfg.iot.common.datamodel.semanticlookup.model.ConceptBase.ConceptType;

public abstract class SubmodelElementEventObject<E extends SubmodelElement, D extends SubmodelElement> extends RegistryEventObject<E, D> 
	implements ReferableEvent, HasSemanticsEvent, HasKindEvent, HasDataSpecificationEvent, QualifiableEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -864423526018683240L;
	private final SubmodelElementContainer parent;
	public SubmodelElementEventObject(Object source, SubmodelElementContainer container, E stored, D api) {
		super(source, stored, api);
		this.parent = container;
	}
	/**
	 * Create the corresponding SubmodelElementEventObject
	 * @param source
	 * @param container
	 * @param dto
	 * @return
	 */
	public static SubmodelElementEventObject<? extends SubmodelElement,?> fromSubmodelElement(Object source, SubmodelElementContainer container, SubmodelElement dto) {
		return fromSubmodelElement(source, container, container.newChildElement(dto.getIdShort(), dto.getClass()), dto);
		
	}
	public static SubmodelElementEventObject<? extends SubmodelElement,?> fromSubmodelElement(Object source, SubmodelElementContainer container, SubmodelElement entity, SubmodelElement dto) {
		switch(dto.getModelType()) {
		case Property:
			return new PropertyEventObject(source, container, (Property)entity, (Property)dto);
		case Blob:
			return new BlobEventObject(source, container, (Blob)entity, (Blob)dto);
		case File:
			return new FileEventObject(source, container, (File)entity, (File)dto);
		case ReferenceElement:
			return new ReferenceElementEventObject(source, container, (ReferenceElement)entity, (ReferenceElement)dto);
		case Operation:
			return new OperationEventObject(source, container, (Operation)entity, (Operation)dto);
		case OperationVariable:
			return new OperationVariableEventObject(source, container, (OperationVariable)entity, (OperationVariable)dto);
		case RelationshipElement:
			return new RelationshipElementEventObject(source, container, (RelationshipElement)entity, (RelationshipElement)dto);
		case SubmodelElementCollection:
			return new SubmodelElementCollectionEventObject(source, container, (SubmodelElementCollection)entity,(SubmodelElementCollection)dto);
		case EventElement:
			return new EventElementEventObject(source, container, (EventElement)entity,(EventElement)dto);
		default:
			throw new IllegalArgumentException("Provided submodel element is not supported!");
		}
	}
	protected SubmodelElementContainer getParent() {
		return parent;
	}
	@Override
	public boolean isValidSemanticElement(ReferableElement semantic) {
		// when current submodel is of Kind "Type", the reference may point  
		// (1) to another type model or
		// (2) to an external class definition (GlobalReference) from semantic lookup which specifies the properties to include with the submodel
		// (3) to an internal ConceptDescription which in turn points to a  
		// when current submodel is of kind "Instance", the reference must point to a submodel of kind "Type" 
		if (semantic != null && semantic instanceof SubmodelElementCollection) {
			// a submodel element collection may point to a "Type" collection (although not stated in the spec!)
			SubmodelElementCollection type = (SubmodelElementCollection) semantic;
			if ( getEntity().getModelType()==KeyElementsEnum.SubmodelElementCollection ) {
				if (type.getKind().equals(Kind.Type)) {
					return true;
				}
			}
		}
		else {
			// 
			if ( semantic !=null && semantic instanceof ConceptDescription) {
				ConceptDescription desc = (ConceptDescription) semantic;
				return desc.isCaseOfType(ConceptType.ConceptProperty);
			}
			// a Submodel may only point to a ConceptClass ... 
			if ( semantic != null && semantic instanceof GlobalReference) {
				GlobalReference ref = (GlobalReference)semantic;
				// the global reference must point to a ConceptClass (from semantic lookup!!!
				if (ref.getReferencedType().equals(ConceptType.ConceptProperty)) {
					return true;
				}
			}
			
		}
		if ( semantic != null) {
			if ( getEntity().isInstance()) {
				if ( getEntity().getModelType().equals(semantic.getModelType()) ) {
					// the referable element must be "HasKind"
					HasKind hasKind = HasKind.class.cast(semantic);
					if (! hasKind.isInstance()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
