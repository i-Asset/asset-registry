package at.srfg.iot.aas.service.registry;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.dictionary.ConceptDescription;
import at.srfg.iot.aas.modeling.SubmodelElement;
import at.srfg.iot.aas.repository.registry.SubmodelElementRepository;
import at.srfg.iot.aas.repository.registry.SubmodelRepository;
import at.srfg.iot.aas.service.registry.event.object.AssetAdministrationShellEventObject;
import at.srfg.iot.aas.service.registry.event.object.AssetEventObject;
import at.srfg.iot.aas.service.registry.event.object.ConceptDescriptionEventObject;
import at.srfg.iot.aas.service.registry.event.object.SubmodelEventObject;
import at.srfg.iot.aas.service.registry.event.object.submodel.SubmodelElementEventObject;

@Component
public class RegistryWorker {
	@Autowired
	private SubmodelRepository submodelRepo;
	@Autowired
	private SubmodelElementRepository submodelElementRepo;

	@Autowired
	ApplicationEventPublisher publisher;
	@Autowired
	RegistryService registry;
	
	public Asset createAsset(Asset dto) {
		AssetEventObject e = new AssetEventObject(this, dto);
		publisher.publishEvent(e);
		//
		return e.getEntity();
	}
	public Optional<AssetAdministrationShell> saveAssetAdministrationShell(AssetAdministrationShell dto) {
		Optional<AssetAdministrationShell> shell = registry.getAssetAdministrationShell(dto.getIdentification());
		if ( shell.isPresent()) {
			AssetAdministrationShellEventObject e = new AssetAdministrationShellEventObject(this, shell.get(), dto);
			publisher.publishEvent(e);
			// save & return
			return Optional.of(registry.saveAssetAdministrationShell(e.getEntity()));
		}
		else {
			AssetAdministrationShellEventObject e = new AssetAdministrationShellEventObject(this, new AssetAdministrationShell(dto.getIdentification()), dto);
			publisher.publishEvent(e);
			// save & return
			return Optional.of(registry.saveAssetAdministrationShell(e.getEntity()));
		}
	}
	public Optional<Submodel> createSubmodel(String shellId, Submodel dto) {
		// create an event - the result submodel will be created/filled on the fly
		Optional<AssetAdministrationShell> shell = registry.getAssetAdministrationShell(shellId);
		if ( shell.isPresent()) {
			SubmodelEventObject e = new SubmodelEventObject(this, shell.get(), null, dto);
			publisher.publishEvent(e);
			Submodel changed = e.getEntity();
			// save the submodel
			Submodel updated = submodelRepo.save(changed);
			return Optional.of(updated);
		}
		return Optional.empty();
	}
	public Optional<Submodel> updateSubmodel(Submodel dto) {
		Optional<Submodel> entity = submodelRepo.findByIdentification(dto.getIdentification());
		if (entity.isPresent()) {
			SubmodelEventObject e = new SubmodelEventObject(this, entity.get().getAssetAdministrationShell(), entity.get(), dto);
			publisher.publishEvent(e);
			Submodel changed = e.getEntity();
			// save the submodel
			Submodel updated = submodelRepo.save(changed);
			return Optional.of(updated);
			
		}
		return entity;
		
	}
	public Submodel createSubmodel(Submodel dto, AssetAdministrationShell theShell) {
		// verify dto when theShell is null
		if ( theShell == null ) {
			// the dto must contain a Reference to the shell
		}
		return null;
	}
	public Submodel createSubmodel(AssetAdministrationShell theShell, Submodel dto) {
		Optional<Submodel> entity = submodelRepo.findByIdentification(dto.getIdentification());
		if (entity.isPresent()) {
			throw new IllegalArgumentException("A submodel with the provided identifier exists - cannot create!");
		}
		else {
			// create a new Submodel
			Submodel newModel = new Submodel(dto.getIdentification(), theShell);
			// pass the new model as entity to the event object
			SubmodelEventObject e = new SubmodelEventObject(this, theShell, newModel, dto);
			publisher.publishEvent(e);
			// the event object holds the changed element
			Submodel changed = e.getEntity();
			// save the changed submodel
			Submodel updated = submodelRepo.save(changed);
			return updated;
			
		}
	}
	public Optional<Submodel> setSubmodel(Submodel existing, Submodel dto) {
		// the existing must have the containing shell assigned
		SubmodelEventObject e = new SubmodelEventObject(this, existing.getAssetAdministrationShell(), existing, dto);
		publisher.publishEvent(e);
		return Optional.of(registry.saveSubmodel(e.getEntity()));

	}
	public Optional<Submodel> setSubmodel(Submodel dto) {
		Optional<Submodel> entity = submodelRepo.findByIdentification(dto.getIdentification());
		
		Submodel existing = entity.orElseGet(new Supplier<Submodel>() {

			@Override
			public Submodel get() {
				// resolve reference to the parent, e.g. the shell
				Optional<AssetAdministrationShell> aasReferenced = registry.resolveReference(dto.getParent(), AssetAdministrationShell.class);
				if (aasReferenced.isPresent()) {
					return new Submodel(dto.getIdentification(), aasReferenced.get());
				}
				throw new IllegalArgumentException("Provided AssetAdministrationShell not found!");
			}
		});
		// 
		SubmodelEventObject e = new SubmodelEventObject(this, existing.getAssetAdministrationShell(), existing, dto);
		publisher.publishEvent(e);
		// the event holds the changed object
		Submodel changed = e.getEntity();
		// save the submodel
		Submodel updated = submodelRepo.save(changed);
		return Optional.of(updated);
		//
	}
	public ConceptDescription createConceptDescription(ConceptDescription dto) {
		ConceptDescriptionEventObject e = new ConceptDescriptionEventObject(this, dto);
		publisher.publishEvent(e);
		return e.getEntity();
	}
	public Optional<SubmodelElement> addSubmodelElement(String uri, String path, SubmodelElement submodelElement) {
		Optional<SubmodelElementContainer> parent = Optional.empty();
		// try to find via parent reference
		if ( submodelElement.hasParentReference()) {
			parent = registry.resolveReference(submodelElement.getParent(), SubmodelElementContainer.class);
		}
		// use the path
		else {
			parent = registry.resolveReference(uri, path, SubmodelElementContainer.class);
		}
		if (parent.isPresent()) {
			// the parent element must have a contained element with the provided idShort
			SubmodelElementContainer container = parent.get();
			Optional<SubmodelElement> entity = container.getSubmodelElement(submodelElement.getIdShort());
			if (! entity.isPresent()) {
				SubmodelElementEventObject<? extends SubmodelElement,? extends SubmodelElement> event = SubmodelElementEventObject.fromSubmodelElement(this, parent.get(), null, submodelElement);
				publisher.publishEvent(event);
				SubmodelElement changed = event.getEntity();
				SubmodelElement stored = submodelElementRepo.save(changed);
				return Optional.of(stored);
			}
			else {
				// the parent already has an element with the idShort  
				throw new IllegalArgumentException(String.format("SubmodelElement with idShort (%s) already present - try updating!", submodelElement.getIdShort()));
			}
		}
		return Optional.empty();
	}
	public SubmodelElement setSubmodelElement(SubmodelElementContainer container, SubmodelElement updated) {
		SubmodelElementEventObject<? extends SubmodelElement,? extends SubmodelElement> event = SubmodelElementEventObject.fromSubmodelElement(this, container, updated);
		// process all the submodel-element event handler
		publisher.publishEvent(event);
		// 
		return submodelElementRepo.save(event.getEntity());
	}
	public SubmodelElement setSubmodelElement(SubmodelElement existing, SubmodelElement updated) {
		SubmodelElementEventObject<? extends SubmodelElement,? extends SubmodelElement> event = SubmodelElementEventObject.fromSubmodelElement(this, existing.getSubmodel(), existing, updated);
		// process all the submodel-element event handler
		publisher.publishEvent(event);
		// 
		
		return submodelElementRepo.save(event.getEntity());
	}
	public Optional<SubmodelElement> setSubmodelElement(String uri, String path, SubmodelElement submodelElement) {
		Optional<SubmodelElementContainer> parent = Optional.empty();
		// try to find via parent reference
		if ( submodelElement.hasParentReference()) {
			parent = registry.resolveReference(submodelElement.getParent(), SubmodelElementContainer.class);
		}
		// use the path
		else {
			parent = registry.resolveReference(uri, path, SubmodelElementContainer.class);
		}
		if (parent.isPresent()) {
			// the parent element must have a contained element with the provided idShort
			SubmodelElementContainer container = parent.get();
			Optional<SubmodelElement> entity = container.getSubmodelElement(submodelElement.getIdShort());
			if ( entity.isPresent()) {
				SubmodelElementEventObject<? extends SubmodelElement,? extends SubmodelElement> event = SubmodelElementEventObject.fromSubmodelElement(this, parent.get(), entity.get(), submodelElement);
				publisher.publishEvent(event);
				SubmodelElement changed = event.getEntity();
				SubmodelElement stored = submodelElementRepo.save(changed);
				return Optional.of(stored);
			}
		}
		return Optional.empty();
	}


}
