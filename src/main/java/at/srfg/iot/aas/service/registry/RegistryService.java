package at.srfg.iot.aas.service.registry;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.Referable;
import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.common.referencing.IdentifiableElement;
import at.srfg.iot.aas.common.referencing.Key;
import at.srfg.iot.aas.common.referencing.ReferableElement;
import at.srfg.iot.aas.common.referencing.Reference;
import at.srfg.iot.aas.dependency.SemanticLookup;
import at.srfg.iot.aas.dictionary.ConceptDescription;
import at.srfg.iot.aas.modeling.SubmodelElement;
import at.srfg.iot.aas.repository.registry.AssetAdministrationShellRepository;
import at.srfg.iot.aas.repository.registry.AssetRepository;
import at.srfg.iot.aas.repository.registry.ConceptDescriptionRepository;
import at.srfg.iot.aas.repository.registry.IdentifiableRepository;
import at.srfg.iot.aas.repository.registry.ReferableRepository;
import at.srfg.iot.aas.repository.registry.SubmodelElementRepository;
import at.srfg.iot.aas.repository.registry.SubmodelRepository;
import at.srfg.iot.api.IAssetAdministrationShell;
import at.srfg.iot.api.ISubmodel;
import at.srfg.iot.api.ISubmodelElement;
import at.srfg.iot.classification.model.ConceptBase;
import at.srfg.iot.provider.IAssetProvider;

@Service
public class RegistryService {
	@Autowired
	private IdentifiableRepository<IdentifiableElement> identifiableRepo;
	@Autowired
	private AssetAdministrationShellRepository aasRepo;
	@Autowired
	private ReferableRepository<ReferableElement> referableRepo;
	@Autowired
	private AssetRepository assetRepo;
	@Autowired
	private SubmodelRepository submodelRepo;
	@Autowired
	private SubmodelElementRepository submodelElementRepo;
	@Autowired
	private ConceptDescriptionRepository conceptDescriptionRepo;
	@Autowired
	private SemanticLookup lookup;
	
	
	public AssetAdministrationShell saveAssetAdministrationShell(AssetAdministrationShell elem) {
		return aasRepo.save(elem);
	}
	public Submodel saveSubmodel(Submodel elem) {
		return submodelRepo.save(elem);
	}
	public SubmodelElement saveSubmodelElement(SubmodelElement elem) {
		return submodelElementRepo.save(elem);
	}
	public Optional<AssetAdministrationShell> getAssetAdministrationShell(String uri, boolean complete) {
		return getAssetAdministrationShell(new Identifier(uri),complete);
	}
	public Optional<AssetAdministrationShell> getAssetAdministrationShell(Reference referenceToShell) {
		// Reference must point to an AssetAdministrationShell
		if ( referenceToShell.getModelType().getElementClass().equals(AssetAdministrationShell.class)) {
			return aasRepo.findByIdentification(referenceToShell.getFirstIdentifier());
		}
		else {
			
			return Optional.empty();
		}
	}
	public Optional<AssetAdministrationShell> getAssetAdministrationShell(String uri) {
		return getAssetAdministrationShell(new Identifier(uri), false);
	}
	public Optional<AssetAdministrationShell> getAssetAdministrationShell(Identifier identifier, boolean complete) {
		Optional<AssetAdministrationShell> theShell = aasRepo.findByIdentification(identifier);
		if (theShell.isPresent()) {
			// is complete true
			if (complete) {
				for(Submodel sub : theShell.get().getChildElements(Submodel.class)) {
					enhanceSubmodel(sub);
				}
			}
			else {
				// omit the submodels from the parent (derivedFrom AAS)  
				theShell.get().setSubModel(new ArrayList<>());
			}
		}
		
		return theShell;
	}
	public Optional<AssetAdministrationShell> getAssetAdministrationShell(Identifier identifier) {
		return getAssetAdministrationShell(identifier, false);
	}
	public Optional<AssetAdministrationShell> getAssetAdministrationShell(Key key) {
		return aasRepo.findByIdentification(new Identifier(key.getIdType(), key.getValue()));
	}
	public boolean deleteAssetAdministrationShell(Identifier identifier) {
		Optional<AssetAdministrationShell> aas = aasRepo.findByIdentification(identifier);
		if ( aas.isPresent() ) {
			aasRepo.delete(aas.get());
			return true;
		}
		return false;
	}
	public boolean deleteAssetAdministrationShell(String uri) {
		return deleteAssetAdministrationShell(new Identifier(uri));
	}
	public Optional<Submodel> getSubmodel(String uri) {
		return getSubmodel(new Identifier(uri));
	}
	public Optional<Submodel> getSubmodel(Identifier identification) { 
		return getSubmodel(identification, false);
	}
	public Optional<Submodel> getSubmodel(Identifier identification, boolean includeDerived) {
		Optional<Submodel> model = submodelRepo.findByIdentification(identification);
		if (model.isPresent()) {
			Submodel sub = model.get();
			if( includeDerived ) {
				enhanceSubmodel(sub);
			}
		}
		return model;
	}
	/**
	 * Helper method which checks for derived elements
	 * @param sub
	 */
	private void enhanceSubmodel(Submodel sub) {
		if (sub.getSemanticElement() != null && sub.getSemanticElement() instanceof Submodel) {
			Submodel parent = (Submodel)sub.getSemanticElement();
			// merge elements ... 
			
			mergeSubmodelElements(sub, parent);
		}
	}
	private String[] checkPath(String path) {
		// remove leading and trailing slashes
		try {
			path = URLDecoder.decode(path,"UTF-8");
			while( path.startsWith("/")) {
				path = path.substring(1);
			}
			while (path.endsWith("/")) {
				path = path.substring(0, path.length()-1);
			}
			while (path.contains("//") ) {
				path = path.replaceAll("//", "/");
			}
			return path.split("/");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return path.split("/");
		}
	}

	/**
	 * 
	 * @param child
	 * @param parent
	 * @return
	 */
	private void mergeSubmodelElements(SubmodelElementContainer child, SubmodelElementContainer parent) {
		for (ISubmodelElement parentElement : parent.getSubmodelElements() ) {
			Optional<ISubmodelElement> childElement = child.getSubmodelElement(parentElement.getIdShort());
			if (childElement.isPresent()) {
				ISubmodelElement c = childElement.get();
				
				
				if ( parentElement instanceof SubmodelElementContainer && 
					 c instanceof SubmodelElementContainer	) {
					mergeSubmodelElements((SubmodelElementContainer)c, (SubmodelElementContainer)parent);
				}
				else {
					// TODO: "decide whether to merge" or not 
				}
			}
			else {
				// merge only in memory - do not persist (e.g. save) afterwards
				// mark the element as derived
				parentElement.setDerived(true);
				child.addSubmodelElement(parentElement);
			}
		}
	}
	
	public boolean deleteSubmodel(Identifier identifier) {
		Optional<Submodel> aas = submodelRepo.findByIdentification(identifier);
		if ( aas.isPresent() ) {
			submodelRepo.delete(aas.get());
			return true;
		}
		return false;
	}
	public boolean deleteSubmodel(String uri) {
		return deleteSubmodel(new Identifier(uri));
	}
	public Optional<IdentifiableElement> getIdentifiable(Identifier identifier) {
		return identifiableRepo.findByIdentification(identifier);
	}
	public boolean deleteIdentifiable(Identifier identifier) {
		Optional<IdentifiableElement> aas = identifiableRepo.findByIdentification(identifier);
		if ( aas.isPresent() ) {
			identifiableRepo.delete(aas.get());
			return true;
		}
		return false;
	}
	public boolean deleteReferable(Reference reference) {
		Optional<ReferableElement> toDelete = resolveReference(reference, ReferableElement.class);
		if ( toDelete.isPresent() ) {
			referableRepo.delete(toDelete.get());
			return true;
		}
		return false;
	}
	public boolean deleteReferable(Referable referable) {
		Optional<ReferableElement> toDelete = resolveReference(referable.asReference(), ReferableElement.class);
		if ( toDelete.isPresent() ) {
			referableRepo.delete(toDelete.get());
			return true;
		}
		return false;
	}
	public boolean deleteIdentfiable(String uri) {
		return deleteIdentifiable(new Identifier(uri));
	}
	public Optional<Asset> getAsset(String uri) {
		return getAsset(new Identifier(uri));
	}
	public Optional<Asset> getAsset(Identifier identifier) {
		return assetRepo.findByIdentification(identifier);
	}
	/**
	 * Resolve the {@link ReferableElement} and indicated in the provided <code>identifier</code>
	 * and <code>path</code>
	 * @param identifier The IRDI, IRD or UUID of an {@link IdentifiableElement}
	 * @param path The concatenated <code>idShort</code> values with slash "/" as delimiter
	 * @param clazz The desired class of the resulting {@link ReferableElement} 
	 * @return The {@link ReferableElement} or empty
	 */
	public <T> Optional<T> resolveReference(String uri, String path, Class<T> clazz) {
		Optional<Referable> ref = resolveReference(uri,path);
		if ( ref.isPresent()) {
			if ( clazz.isInstance(ref.get())) {
				return Optional.of(clazz.cast(ref.get()));
			}
		}
		return Optional.empty();
		
	}
	public <T> Optional<T> resolveReference(Referable root, String path, Class<T> clazz) {
		Optional<Referable> ref = resolveReference(root,path);
		if ( ref.isPresent()) {
			if ( clazz.isInstance(ref.get())) {
				return Optional.of(clazz.cast(ref.get()));
			}
		}
		return Optional.empty();
		
	}
	/**
	 * Resolve the {@link ReferableElement} as indicated in the provided {@link Reference}
	 * @param reference The Reference with the {@link Key} elements pointing to the 
	 * 					requested element
	 * @param clazz The desired class of the resulting {@link ReferableElement} 
	 * @return The Referable
	 */
	public <T> Optional<T> resolveReference(Reference reference, Class<T> clazz) {
		Optional<Referable> ref = resolveReference(reference);
		if ( ref.isPresent()) {
			if ( clazz.isInstance(ref.get())) {
				return Optional.of(clazz.cast(ref.get()));
			}
		}
		return Optional.empty();
	}
	public <T> Optional<T> resolveReference(Reference reference, Class<T> clazz, boolean create) {
		Optional<Referable> ref = resolveReference(reference, create);
		if ( ref.isPresent()) {
			if ( clazz.isInstance(ref.get())) {
				return Optional.of(clazz.cast(ref.get()));
			}
		}
		return Optional.empty();
	}
	/**
	 * Resolve the {@link ReferableElement} and indicated in the provided <code>identifier</code>
	 * and <code>path</code>
	 * @param identifier The IRDI, IRD or UUID of an {@link IdentifiableElement}
	 * @param path The concatenated <code>idShort</code> values with slash "/" as delimiter 
	 * @return The {@link ReferableElement} or empty
	 */
	public Optional<Referable> resolveReference(String identifier, String path) {
		
		return resolveReference(identifier,checkPath(path));
	}
	/**
	 * Resolve the {@link ReferableElement} as indicated in the provided <code>identifier</code> 
	 * pointing to an {@link IdentifiableElement} and (optional) to the {@link ReferableElement}s
	 * with their idShort<code>idShort</code>
	 * @param identifier The IRDI, IRD or UUID of an {@link IdentifiableElement}
	 * @param idShort The idShort references 
	 * @return The {@link ReferableElement} or empty
	 */
	
	public Optional<Referable> resolveReference(String identifier, String ... idShort) {
		Optional<IdentifiableElement> identfiable = identifiableRepo.findByIdentification(new Identifier(identifier));
		if ( identfiable.isPresent()) {
			return resolveReference(identfiable.get(), idShort);
		}
		return Optional.empty();
	}
	public Optional<Referable> resolveReference(Referable root, String ... path ) {
		Optional<Referable> referable = Optional.of(root);
		for ( String s : path) {
			// 
			Referable element = referable.get();
			if ( element instanceof AssetAdministrationShell) {
				Optional<ISubmodel> submodel  = ((AssetAdministrationShell) element).getSubmodel(s);
				if ( submodel.isPresent()) {
					referable = Optional.of(submodel.get());
					continue;
				}
			}
			else if ( element instanceof SubmodelElementContainer ) {
				Optional<ISubmodelElement> submodelElement = ((SubmodelElementContainer)element).getSubmodelElement(s);
				if ( submodelElement.isPresent()) {
					referable = Optional.of(submodelElement.get());
					continue;
				}
			}

		}
		return referable;
	}
	/**
	 * Resolve the {@link ReferableElement} as indicated in the provided {@link Reference}
	 * @param reference The Reference with the {@link Key} elements pointing to the 
	 * 					requested element
	 * @return The Referable
	 */
	public Optional<Referable> resolveReference(Reference reference) {
		return resolveReference(reference, false);
	}
	public Optional<Referable> resolveReference(Reference reference, boolean createMissing) {	
		Optional<Referable> referable = Optional.empty();
		Referable parent = null;
		for (Key key : reference.getKeys()) {
			// 
			if (! referable.isPresent() ) {
				switch(key.getType()) {
				case AssetAdministrationShell:
				case Asset:
				case Submodel:
				case ConceptDescription:
					Optional<IdentifiableElement> shell = identifiableRepo.findByIdentification(key.asIdentifier());
					if ( shell.isPresent() ) {
						parent = shell.get();
						referable = Optional.of(parent);
						continue;
					}
				default: 
					throw new IllegalArgumentException("Key element cannot be resolved: "+ reference);
				}
			}
			else {
				switch(key.getIdType()) {
				case IdShort:
					// 
					
					parent = referable.get();
					referable = parent.getChildElement(key.getValue());
					// if it is not 
					if ( createMissing  && ! referable.isPresent()) {
						referable = fromKey(key, parent);
					}

					continue;

				default: 
					throw new IllegalArgumentException("Key element cannot be resolved: "+ key.getValue());
				}
			}
		}
		return referable;
	}
	private Optional<Referable> fromKey(Key theKey, Referable parent) {
		try {
			Referable referable = theKey.getType().getElementClass().newInstance();
			switch(theKey.getIdType()) {
			case IdShort:
				referable.setIdShort(theKey.getValue());
				break;
			default:
				if ( referable instanceof IdentifiableElement) {
					IdentifiableElement identifiable = (IdentifiableElement)referable;
					identifiable.setIdentification(theKey.asIdentifier());
				}
				else {
					throw new IllegalArgumentException("Key element cannot be created - must be of type idShort: " + theKey.getValue()) ;
				}
			}
			if ( parent != null) {
				parent.addChildElement(referable);
			}
			ReferableElement element = (ReferableElement) referable;
			return Optional.of(referableRepo.save(element));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("Key element cannot be created: " + theKey) ;
	}
	public Optional<ConceptDescription> getConceptDescription(String uri) {
		Optional<ConceptDescription> cd = conceptDescriptionRepo.findByIdentification(new Identifier(uri));
		if (! cd.isPresent()) {
			// try to find the concept description in the semantic lookup
			Optional<ConceptBase> base = lookup.getConcept(uri);
			if ( base.isPresent()) {
				ConceptBase cb = base.get();
				ConceptDescription concept = new ConceptDescription(new Identifier(uri));
				Map<Locale, String> pref = cb.getPreferredLabel();
				for ( Locale locale : pref.keySet()) {
					String label = pref.get(locale);
					concept.setDescription(locale.getLanguage(), label);
				}
				concept.setIdShort(cb.getShortName());
				
				concept.setVersion(cb.getVersionNumber());
				concept.setRevision(cb.getRevisionNumber());
				ConceptDescription saved = conceptDescriptionRepo.save(concept);
				return Optional.of(saved);
			}
		}
		return cd;
	}

	public boolean deleteConceptDescription(String uri) {
		Optional<ConceptDescription> cdOpt = conceptDescriptionRepo.findByIdentification(new Identifier(uri));
		if ( cdOpt.isPresent()) {
			conceptDescriptionRepo.delete(cdOpt.get());
			return true;
		}
		return false;
	}
	public boolean deleteSubmodelElement(String uri, String path) {
		Optional<SubmodelElement> element = resolveReference(uri, path, SubmodelElement.class);
		if ( element.isPresent() ) {
			submodelElementRepo.delete(element.get());
			return true;
		}
		return false;
	}
	

}
