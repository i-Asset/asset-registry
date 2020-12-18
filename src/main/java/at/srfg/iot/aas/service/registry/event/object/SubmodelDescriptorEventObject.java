package at.srfg.iot.aas.service.registry.event.object;


import at.srfg.iot.aas.service.registry.event.HasKindEvent;
import at.srfg.iot.aas.service.registry.event.HasSemanticsEvent;
import at.srfg.iot.aas.service.registry.event.IdentifiableEvent;
import at.srfg.iot.aas.service.registry.event.ReferableEvent;
import at.srfg.iot.aas.service.registry.event.SubmodelDescriptorEvent;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.GlobalReference;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.dictionary.ConceptDescription;
import at.srfg.iot.common.datamodel.semanticlookup.model.ConceptBase.ConceptType;

public class SubmodelDescriptorEventObject extends RegistryEventObject<Submodel, SubmodelDescriptor> 
	// all the handlers for the implemented interfaces are visited on every event 
	implements SubmodelDescriptorEvent, HasSemanticsEvent, HasKindEvent, IdentifiableEvent, ReferableEvent {
	
	private final AssetAdministrationShell aasIdentifier;
	/**
	 * default serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;

	public SubmodelDescriptorEventObject(Object source, Submodel entity, SubmodelDescriptor dto) {
		super(source, entity, dto);
		// keep the identifier (when required)
		this.aasIdentifier = entity.getAssetAdministrationShell();
	}
	public AssetAdministrationShell getAssetAdministrationShell() {
		return aasIdentifier;
	}
	@Override
	public boolean isValidSemanticElement(ReferableElement semantic) {
		// when current submodel is of Kind "Type", the reference may point  
		// (1) to another type model or
		// (2) to an external class definition (GlobalReference) from semantic lookup which specifies the properties to include with the submodel
		// (3) to an internal ConceptDescription which in turn points to a  

		// when current submodel is of kind "Instance", the reference must point to a submodel of kind "Type" 
		if (semantic != null && semantic instanceof Submodel) {
			Submodel type = (Submodel) semantic;
			if (type.getKind().equals(Kind.Type)) {
				return true;
			}
		}
		// a submodel may point to a concept description where the description is caseOf ConceptType.ConceptClass
		if ( semantic !=null && semantic instanceof ConceptDescription) {
			ConceptDescription desc = (ConceptDescription) semantic;
			return desc.isCaseOfType(ConceptType.ConceptClass);
		}
		// a Submodel may only point to a ConceptClass ... 
		if ( semantic != null && semantic instanceof GlobalReference) {
			GlobalReference ref = (GlobalReference)semantic;
			// the global reference must point to a ConceptClass (from semantic lookup!!!
			if (ref.getReferencedType().equals(ConceptType.ConceptClass)) {
				return true;
			}
		}
		
		return false;
	}
	
}
