package at.srfg.iot.aas.service.registry;

import java.util.Optional;
import java.util.function.Supplier;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Endpoint;
import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.aas.common.Identifiable;
import at.srfg.iot.aas.common.Referable;
import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.common.referencing.IdentifiableElement;
import at.srfg.iot.aas.common.referencing.Reference;
import at.srfg.iot.aas.dictionary.ConceptDescription;
import at.srfg.iot.aas.modeling.SubmodelElement;
import at.srfg.iot.aas.service.registry.event.object.AssetAdministrationShellDescriptorEventObject;
import at.srfg.iot.aas.service.registry.event.object.AssetAdministrationShellEventObject;
import at.srfg.iot.aas.service.registry.event.object.AssetEventObject;
import at.srfg.iot.aas.service.registry.event.object.ConceptDescriptionEventObject;
import at.srfg.iot.aas.service.registry.event.object.SubmodelDescriptorEventObject;
import at.srfg.iot.aas.service.registry.event.object.SubmodelEventObject;
import at.srfg.iot.aas.service.registry.event.object.submodel.SubmodelElementEventObject;
import at.srfg.iot.api.ISubmodelElement;

@Component
public class RegistryWorker {
//	@Autowired
//	private SubmodelRepository submodelRepo;
//	@Autowired
//	private SubmodelElementRepository submodelElementRepo;

	@Autowired
	private ApplicationEventPublisher publisher;
	@Autowired
	private RegistryService registry;
	public void setElement(String identifable, Referable element) {
		Optional<IdentifiableElement> root = registry.getIdentifiable(new Identifier(identifable));
		if ( root.isPresent()) {
			// 
			setElement(root.get(), element);
		}
	}
	public void setElement(Identifiable shellId, Referable element) {
		// perform the checks
		//
		// is there a parent reference in the element?
		Reference parent = element.getParent();
		// parent might be empty
		String path = "";
		if (parent != null) {
			path = parent.getPath();
			
		}
		// store the element
		setElement(shellId, path, element);
		
	}
	public void setElement(Identifiable root, @NotNull String path, Referable element) {
		// perform the checks
//		// is the root the same 
//		boolean isSame = parent.hasRoot(shellId.getId()) || parent.isRoot(shellId);
		// obtain the path

		String[] pathToken = path.split("/");
		Optional<Referable> parent = registry.resolveReference(root, pathToken);
		if ( parent.isPresent()) {
			switch (element.getModelType()) {
			case Asset:
				createAsset(Asset.class.cast(element));
				break;
			case Submodel:
				createSubmodel(parent.get(), Submodel.class.cast(element));
				break;
			case SubmodelElement:
			case ConceptDictionary:
			case ConceptDescription:
				
			default:
				
			}
		}
		
	}
	
	private Asset createAsset(Asset dto) {
		AssetEventObject e = new AssetEventObject(this, dto);
		publisher.publishEvent(e);
		//
		return e.getEntity();
	}
	/**
	 * Register a new AssetAdministrationShell with the iAsset Registry. 
	 * <p>
	 * Registration only provides the descriptor element of the {@link AssetAdministrationShell} including the
	 * descriptor element(s) of the linked {@link Submodel} elements. 
	 * 
	 * A descriptor holds the {@link Endpoint} element specifying the device where the shell is finally located 
	 * 
	 * </p>
	 * @param dto The descriptor element
	 * @return
	 */
	@Transactional
	public Optional<AssetAdministrationShell> registerAdministrationShell(AssetAdministrationShellDescriptor dto) {
		Optional<AssetAdministrationShell> shell = registry.getAssetAdministrationShell(dto.getIdentification());
		if (! shell.isPresent()) {
			AssetAdministrationShell newShell = new AssetAdministrationShell(dto.getIdentification());
			AssetAdministrationShellDescriptorEventObject e = new AssetAdministrationShellDescriptorEventObject(this, newShell, dto);
			// event processes all contained elements of the descriptor (endpoints, submodel descriptors, referable, identifiable)
			publisher.publishEvent(e);
			
			// save & return
			return Optional.of(registry.saveAssetAdministrationShell(e.getEntity()));
		}
		return shell;
	}
	/**
	 * Update a complete {@link AssetAdministrationShell}
	 * @param dto The element defining the asset administration shell
	 * @return
	 */
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
	/**
	 * Add a new {@link Submodel} to an existing {@link AssetAdministrationShell}
	 * @param shellId
	 * @param dto
	 * @return
	 */
	private Optional<Submodel> createSubmodel(String shellId, Submodel dto) {
		// create an event - the result submodel will be created/filled on the fly
		Optional<AssetAdministrationShell> shell = registry.getAssetAdministrationShell(shellId);
		if ( shell.isPresent()) {
			SubmodelEventObject e = new SubmodelEventObject(this, new Submodel(shell.get()), dto);
			publisher.publishEvent(e);
			Submodel changed = e.getEntity();
			// save the submodel
			Submodel updated = registry.saveSubmodel(changed);
			return Optional.of(updated);
		}
		return Optional.empty();
	}
	private Optional<Submodel> createSubmodel(Referable parent, Submodel dto) {
		if ( AssetAdministrationShell.class.isInstance(parent)) {
			AssetAdministrationShell shell = AssetAdministrationShell.class.cast(parent);
			Optional<Submodel> entity = shell.getSubmodel(dto.getIdentification());
			Submodel sub = entity.orElseGet(new Supplier<Submodel>() {

				@Override
				public Submodel get() {
					return new Submodel(shell);
				}
			});
			SubmodelEventObject e = new SubmodelEventObject(this, sub, dto);
			publisher.publishEvent(e);
			Submodel changed = e.getEntity();
			// save the submodel
			Submodel updated = registry.saveSubmodel(changed);
			return Optional.of(updated);
		}
		return Optional.empty();
	}
	public Optional<Submodel> updateSubmodel(Submodel dto) {
		Optional<Submodel> entity = registry.getSubmodel(dto.getIdentification());
		if (entity.isPresent()) {
			SubmodelEventObject e = new SubmodelEventObject(this, entity.get(), dto);
			publisher.publishEvent(e);
			Submodel changed = e.getEntity();
			// save the submodel
			Submodel updated = registry.saveSubmodel(changed);
			return Optional.of(updated);
			
		}
		return entity;
		
	}

	private Submodel createSubmodel(AssetAdministrationShell theShell, Submodel dto) {
		Optional<Submodel> entity = registry.getSubmodel(dto.getIdentification());
		if (entity.isPresent()) {
			throw new IllegalArgumentException("A submodel with the provided identifier exists - cannot create!");
		}
		else {
			// create a new Submodel
			Submodel newModel = new Submodel(dto.getIdentification(), theShell);
			// pass the new model as entity to the event object
			SubmodelEventObject e = new SubmodelEventObject(this, newModel, dto);
			publisher.publishEvent(e);
			// the event object holds the changed element
			Submodel changed = e.getEntity();
			// save the changed submodel
			Submodel updated = registry.saveSubmodel(changed);
			return updated;
			
		}
	}
	@Transactional
	public Optional<Submodel> registerSubmodel(String shellId, SubmodelDescriptor dto) {
		// create an event - the result submodel will be created/filled on the fly
		Optional<AssetAdministrationShell> shell = registry.getAssetAdministrationShell(shellId);
		if ( shell.isPresent()) {
			SubmodelDescriptorEventObject e = new SubmodelDescriptorEventObject(this, new Submodel(shell.get()), dto);
			publisher.publishEvent(e);
			Submodel changed = e.getEntity();
			// save the submodel
			Submodel updated = registry.saveSubmodel(changed);
			return Optional.of(updated);
		}
		return Optional.empty();		
	}
	/**
	 * Register the provided descriptor (as event)
	 * @param existing
	 * @param dto
	 * @return
	 */
	public Optional<Submodel> registerSubmodel(Submodel existing, SubmodelDescriptor dto) {
		// the existing must have the containing shell assigned
		SubmodelDescriptorEventObject e = new SubmodelDescriptorEventObject(this, existing, dto);
		publisher.publishEvent(e);
		return Optional.of(registry.saveSubmodel(e.getEntity()));

	}
	
	public Optional<Submodel> setSubmodel(Submodel existing, Submodel dto) {
		// the existing must have the containing shell assigned
		SubmodelEventObject e = new SubmodelEventObject(this, existing, dto);
		publisher.publishEvent(e);
		return Optional.of(registry.saveSubmodel(e.getEntity()));

	}
	public Optional<Submodel> setSubmodel(Submodel dto) {
		Optional<Submodel> entity = registry.getSubmodel(dto.getIdentification());
		
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
		SubmodelEventObject e = new SubmodelEventObject(this, existing, dto);
		publisher.publishEvent(e);
		// the event holds the changed object
		Submodel changed = e.getEntity();
		// save the submodel
		Submodel updated = registry.saveSubmodel(changed);
		return Optional.of(updated);
		//
	}
	private ConceptDescription createConceptDescription(ConceptDescription dto) {
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
			parent = registry.resolvePath(uri, path, SubmodelElementContainer.class);
		}
		if (parent.isPresent()) {
			// the parent element must have a contained element with the provided idShort
			SubmodelElementContainer container = parent.get();
			Optional<ISubmodelElement> entity = container.getSubmodelElement(submodelElement.getIdShort());
			if (! entity.isPresent()) {
				SubmodelElementEventObject<? extends SubmodelElement,? extends SubmodelElement> event = SubmodelElementEventObject.fromSubmodelElement(this, parent.get(), null, submodelElement);
				publisher.publishEvent(event);
				SubmodelElement stored = registry.saveSubmodelElement(event.getEntity());
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
		return registry.saveSubmodelElement(event.getEntity());
	}
	public SubmodelElement setSubmodelElement(SubmodelElement existing, SubmodelElement updated) {
		SubmodelElementEventObject<? extends SubmodelElement,? extends SubmodelElement> event = SubmodelElementEventObject.fromSubmodelElement(this, existing.getSubmodel(), existing, updated);
		// process all the submodel-element event handler
		publisher.publishEvent(event);
		// 
		
		return registry.saveSubmodelElement(event.getEntity());
	}
	public Optional<SubmodelElement> setSubmodelElement(String uri, String path, SubmodelElement submodelElement) {
		Optional<SubmodelElementContainer> parent = Optional.empty();
		// try to find via parent reference
		if ( submodelElement.hasParentReference()) {
			parent = registry.resolveReference(submodelElement.getParent(), SubmodelElementContainer.class);
		}
		// use the path
		else {
			parent = registry.resolvePath(uri, path, SubmodelElementContainer.class);
		}
		if (parent.isPresent()) {
			// the parent element must have a contained element with the provided idShort
			SubmodelElementContainer container = parent.get();
			Optional<SubmodelElement> entity = container.getChildElement(submodelElement.getIdShort(), SubmodelElement.class);
//			Optional<ISubmodelElement> entity = container.getSubmodelElement(submodelElement.getIdShort());
			if ( entity.isPresent()) {
				SubmodelElementEventObject<? extends SubmodelElement,? extends SubmodelElement> event = SubmodelElementEventObject.fromSubmodelElement(this, parent.get(), entity.get(), submodelElement);
				publisher.publishEvent(event);
				SubmodelElement stored = registry.saveSubmodelElement(event.getEntity());
				return Optional.of(stored);
			}
		}
		return Optional.empty();
	}


}
