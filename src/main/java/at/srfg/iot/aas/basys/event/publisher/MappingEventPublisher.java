package at.srfg.iot.aas.basys.event.publisher;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basys.event.GetAssetAdministrationShellEvent;
import at.srfg.iot.aas.basys.event.GetAssetEvent;
import at.srfg.iot.aas.basys.event.GetConceptDescriptionEvent;
import at.srfg.iot.aas.basys.event.GetConceptDictionaryEvent;
import at.srfg.iot.aas.basys.event.GetSubmodelElementEvent;
import at.srfg.iot.aas.basys.event.GetSubmodelEvent;
import at.srfg.iot.aas.basys.event.SetAssetAdministrationShellEvent;
import at.srfg.iot.aas.basys.event.SetAssetEvent;
import at.srfg.iot.aas.basys.event.SetConceptDescriptionEvent;
import at.srfg.iot.aas.basys.event.SetConceptDictionaryEvent;
import at.srfg.iot.aas.basys.event.SetSubmodelElementEvent;
import at.srfg.iot.aas.basys.event.SetSubmodelEvent;
import at.srfg.iot.aas.basys.event.handler.util.MappingHelper;
import at.srfg.iot.aas.model.Asset;
import at.srfg.iot.aas.model.AssetAdministrationShell;
import at.srfg.iot.aas.model.IdType;
import at.srfg.iot.aas.model.dictionary.ConceptDescription;
import at.srfg.iot.aas.model.dictionary.ConceptDictionary;
import at.srfg.iot.aas.model.submodel.Submodel;
import at.srfg.iot.aas.model.submodel.elements.SubmodelElement;

@Component
public class MappingEventPublisher {
	@Autowired
	private ApplicationEventPublisher publisher;
	/**
	 * Process the provided {@link Submodel} including all contained {@link SubmodelElement}
	 * @param map The map
	 * @param model
	 */
	public void handleSubmodel(Map<String,Object> map, Submodel model) {
		System.out.println("Wanna handle the submodel");
		SetSubmodelEvent sme = new SetSubmodelEvent(map, model);
		publisher.publishEvent(sme);
	}
	public void handleSubmodelElement(Map<String,Object> map, SubmodelElement model) {
		System.out.println("Wanna handle the submodel element");
		SetSubmodelElementEvent sme = new SetSubmodelElementEvent(map, model);
		publisher.publishEvent(sme);
	}
	public void handleAsset(Map<String,Object> map, Asset model) {
		System.out.println("Wanna handle the asset");
		SetAssetEvent assetEvent = new SetAssetEvent(map, model);
		publisher.publishEvent(assetEvent);
	}
	public void handleAssetAdministrationShell(Map<String,Object> map, AssetAdministrationShell model) {
		System.out.println("Wanna handle the submodel");
		SetAssetAdministrationShellEvent sme = new SetAssetAdministrationShellEvent(map, model);
		publisher.publishEvent(sme);
	}
	public void handleConceptDictionary(Map<String,Object> map, ConceptDictionary dictionary) {
		System.out.println("Wanna handle the concept dictionary");
		SetConceptDictionaryEvent sme = new SetConceptDictionaryEvent(map, dictionary);
		publisher.publishEvent(sme);
		
	}
	public void handleConceptDescription(Map<String,Object> map, ConceptDescription description) {
		System.out.println("Wanna handle the concept dictionary");
		SetConceptDescriptionEvent sme = new SetConceptDescriptionEvent(map, description);
		publisher.publishEvent(sme);
		
	}
	
	public AASDescriptor getDescriptorFromAssetAdministrationShell(AssetAdministrationShell shell) {
		GetAssetAdministrationShellEvent event = new GetAssetAdministrationShellEvent(shell, true);
		publisher.publishEvent(event);
		org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell map = event.getBasyxMap();
		String endpoint = shell.getFirstEndpoint().getAddress();
		// the endpoint
//		if (! endpoint.endsWith(shell.getId())) {
//			endpoint = String.format("%s/%s", endpoint, shell.getId());
//		}
		map.setEndpoint(endpoint, shell.getFirstEndpoint().getType());
		return new AASDescriptor(event.getBasyxMap());
	}
	
	public org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell getFromAssetAdministrationShell(AssetAdministrationShell shell) {
		GetAssetAdministrationShellEvent event = new GetAssetAdministrationShellEvent(shell);
		publisher.publishEvent(event);
		return event.getBasyxMap();
	}
	public org.eclipse.basyx.aas.metamodel.map.parts.Asset getFromAsset(Asset asset) {
		GetAssetEvent event = new GetAssetEvent(asset);
		publisher.publishEvent(event);
		return event.getBasyxMap();
		
	}
	public org.eclipse.basyx.aas.metamodel.map.parts.ConceptDictionary getFromConceptDictionary(ConceptDictionary conceptDictionary) {
		GetConceptDictionaryEvent event = new GetConceptDictionaryEvent(conceptDictionary);
		publisher.publishEvent(event);
		return event.getBasyxMap();
	}
	public org.eclipse.basyx.submodel.metamodel.map.parts.ConceptDescription getFromConceptDescription(ConceptDescription conceptDescription) {
		GetConceptDescriptionEvent event = new GetConceptDescriptionEvent(conceptDescription);
		publisher.publishEvent(event);
		return event.getBasyxMap();
	}
	public SubmodelDescriptor getDescriptorFromSubmodel(Submodel submodel) {
		Identifier id = new Identifier();
		id.setId(submodel.getId());
		return new SubmodelDescriptor(submodel.getIdShort(), id, submodel.getParentElement().getFirstEndpoint().getAddress());
//		GetSubmodelEvent event = new GetSubmodelEvent(submodel, true);
//		publisher.publishEvent(event);
//		SubModel map = event.getBasyxMap();
//		String endpoint = submodel.getParentElement().getFirstEndpoint().getAddress();
//		// the endpoint
////		if (! endpoint.endsWith(shell.getId())) {
////			endpoint = String.format("%s/%s", endpoint, shell.getId());
////		}
//		SubmodelDescriptor desriptor = new SubmodelDescriptor(map);
//		desriptor.setEndpoint(endpoint, submodel.getParentElement().getFirstEndpoint().getType());
//		return descriptor);
	}
	public SubModel getFromSubmodel(Submodel model) {
		GetSubmodelEvent event = new GetSubmodelEvent(model);
		publisher.publishEvent(event);
		return event.getBasyxMap();
	}
	public org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement getFromSubmodelElement(SubmodelElement model) {
		GetSubmodelElementEvent event = new GetSubmodelElementEvent(model);
		publisher.publishEvent(event);
		return event.getBasyxMap();
	}
}