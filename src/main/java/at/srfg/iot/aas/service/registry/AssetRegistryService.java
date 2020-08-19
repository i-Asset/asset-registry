package at.srfg.iot.aas.service.registry;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.dependency.SemanticLookup;
import at.srfg.iot.aas.dictionary.ConceptDescription;
import at.srfg.iot.aas.repository.registry.AssetAdministrationShellRepository;
import at.srfg.iot.aas.repository.registry.AssetRepository;
import at.srfg.iot.aas.repository.registry.ConceptDescriptionRepository;
import at.srfg.iot.aas.repository.registry.SubmodelRepository;
import at.srfg.iot.aas.service.basys.BoschRexRoth;
import at.srfg.iot.aas.service.registry.event.RegistryEvent;
import at.srfg.iot.classification.model.ConceptBase;
import at.srfg.iot.classification.model.Description;

@Service
public class AssetRegistryService {
	@Autowired
	private AssetAdministrationShellRepository aasRepo;
	@Autowired
	private AssetRepository assetRepo;
	@Autowired
	private SubmodelRepository submodelRepo;
	@Autowired
	private ConceptDescriptionRepository conceptDescriptionRepo;
	@Autowired
	private RegistryEvent registryEvent;
	@Autowired
	private BoschRexRoth sample;
	@Autowired
	private SemanticLookup lookup;
	
	public Optional<AssetAdministrationShell> getAssetAdministrationShell(String uri) {
		Optional<AssetAdministrationShell> aas = aasRepo.findByIdentification(new Identifier(uri));
		if ( ! aas.isPresent()) {
			return sample.createAsset(uri);
		}
		return aas;
	}
	public boolean deleteAssetAdministrationShell(String uri) {
		Optional<AssetAdministrationShell> aas = getAssetAdministrationShell(uri);
		if ( aas.isPresent()) {
			aasRepo.delete(aas.get());
			return true;
		}
		return false;
	}
	public Optional<Submodel> getSubmodel(String uri) {
		return getSubmodel(new Identifier(uri));
	}
	public Optional<Submodel> getSubmodel(Identifier identification) {
		return submodelRepo.findByIdentification(identification);
	}
	/**
	 * Add a new submodel to the {@link AssetAdministrationShell} indicated by the
	 * <code>aasUri</code>. The aasUri must point to a valid AAS.
	 * @param aasUri The identifier of the {@link AssetAdministrationShell}
	 * @param submodel The {@link Submodel} (including contained elements)
	 * @return
	 */
	public Optional<Submodel> addSubmodel(String aasUri, Submodel submodel) {
		
		Optional<AssetAdministrationShell> aas = getAssetAdministrationShell(aasUri);
		if ( aas.isPresent()) {
			AssetAdministrationShell theShell = aas.get();
			return Optional.of(registryEvent.createSubmodel(theShell, submodel));
		}
		return Optional.empty();
	}
	/**
	 * Update an existing submodel
	 * @param submodel
	 * @return
	 */
	public Optional<Submodel> setSubmodel(Submodel submodel) {
		return Optional.of(registryEvent.setSubmodel(submodel));
	}
	public Optional<Asset> getAsset(String uri) {
		return assetRepo.findByIdentification(new Identifier(uri));
	}
	public Optional<ConceptDescription> getConceptDescription(String uri) {
		Optional<ConceptDescription> cd = conceptDescriptionRepo.findByIdentification(new Identifier(uri));
		if (! cd.isPresent()) {
			// try to find the concept description in the semantic lookup
			Optional<ConceptBase> base = lookup.getConcept(uri);
			if ( base.isPresent()) {
				ConceptBase cb = base.get();
				ConceptDescription concept = new ConceptDescription(new Identifier(uri));
				for ( Description desc : cb.getDescription()) {
					concept.setDescription(desc.getLanguage(), desc.getPreferredName());
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
	public Optional<ConceptDescription> addConceptDescription(ConceptDescription submodelElement) {
		ConceptDescription c = registryEvent.createConceptDescription(submodelElement);
		ConceptDescription storedConcept = conceptDescriptionRepo.save(c);
		return Optional.of(storedConcept);
	}
	public boolean deleteConceptDescription(String uri) {
		Optional<ConceptDescription> cdOpt = conceptDescriptionRepo.findByIdentification(new Identifier(uri));
		if ( cdOpt.isPresent()) {
			conceptDescriptionRepo.delete(cdOpt.get());
			return true;
		}
		return false;
	}
	
	
}
