package at.srfg.iot.aas.web.controller.registry;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.referencing.Reference;
import at.srfg.iot.aas.dictionary.ConceptDescription;
import at.srfg.iot.aas.modeling.SubmodelElement;
import at.srfg.iot.aas.service.basys.BoschRexRoth;
import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.aas.service.registry.RegistryWorker;
import at.srfg.iot.api.AssetRepositoryAPI;

@RestController
public class AssetRegistryController implements AssetRepositoryAPI {
//	@Autowired
//	private AssetRegistryService registry;
	@Autowired
	private RegistryWorker worker;
	
	@Autowired
	private RegistryService registry;
	@Autowired
	private BoschRexRoth sample;

	@Override
	public Optional<AssetAdministrationShell> getAssetAdministrationShell(String uri, boolean complete) throws Exception {
		Optional<AssetAdministrationShell> opt = registry.getAssetAdministrationShell(uri, complete);
		// FIXME: remove this, for development only
		if (! opt.isPresent()) {
			return sample.createAsset(uri);
		}
		return opt;
	}
	@Override
	public Optional<AssetAdministrationShell> setAssetAdministrationShell(AssetAdministrationShell dto) throws Exception {
		// pass the dto element to the worker
		return worker.saveAssetAdministrationShell(dto);
	}
	@Override
	public Optional<Asset> getAsset(String uri) throws Exception {
		return registry.getAsset(uri);
	}
	@Override
	public boolean deleteAssetAdministrationShell(String uri) throws Exception {
		return registry.deleteAssetAdministrationShell(uri);
	}

//	@Override
//	public Optional<Submodel> getSubmodel(String uri) throws Exception {
//		
//		return registry.getSubmodel(uri);
//	}

	@Override
	public Optional<Submodel> addSubmodel(String uri, Submodel submodel) throws Exception {
		worker.setElement(uri, submodel);
		// TODO: handle return types
		return Optional.of(submodel);
	}

	@Override
	public Optional<Submodel> setSubmodel(String uri, Submodel submodel) throws Exception {
		// 
		
		return worker.setSubmodel(submodel);
	}
	@Override
	public boolean deleteSubmodel(String uri) throws Exception {
		return registry.deleteSubmodel(uri);
	}

	@Override
	public Optional<SubmodelElement> getSubmodelElement(String uri, String path) throws Exception {
		return registry.resolvePath(uri, path, SubmodelElement.class);
	}

	@Override
	public Optional<AssetAdministrationShell> getAssetAdministrationShellByReference(Reference reference)
			throws Exception {
		return registry.resolveReference(reference, AssetAdministrationShell.class);
	}

	@Override
	public Optional<Asset> getAssetByReference(Reference reference) throws Exception {
		return registry.resolveReference(reference, Asset.class);
	}

	@Override
	public Optional<Submodel> getSubmodelByReference(Reference reference) throws Exception {
		return registry.resolveReference(reference, Submodel.class);
	}

	@Override
	public Optional<SubmodelElement> getSubmodelElementByReference(Reference reference) throws Exception {
		return registry.resolveReference(reference, SubmodelElement.class);
	}

	@Override
	public Optional<ConceptDescription> getConceptDescriptionByReference(Reference reference) throws Exception {
		return registry.resolveReference(reference, ConceptDescription.class);
	}
	
	@Override
	public Optional<SubmodelElement> addSubmodelElement(String uri, String path, SubmodelElement submodelElement)
			throws Exception {
		return worker.addSubmodelElement(uri, path, submodelElement);
	}

	@Override
	public Optional<SubmodelElement> setSubmodelElement(String uri, String path, SubmodelElement submodelElement)
			throws Exception {
		return worker.setSubmodelElement(uri, path, submodelElement);
	}

	@Override
	public boolean deleteSubmodelElement(String uri, String path) throws Exception {
		return registry.deleteSubmodelElement(uri, path);
	}

	@Override
	public Optional<ConceptDescription> getConceptDescription(String uri) throws Exception {
		return registry.getConceptDescription(uri);
	}

	@Override
	public Optional<ConceptDescription> addConceptDescription(String uri, ConceptDescription submodelElement)
			throws Exception {
		throw new UnsupportedOperationException("Adding concept description is currently not supported!");

	}

	@Override
	public Optional<ConceptDescription> setConceptDescription(String uri, ConceptDescription conceptDescription)
			throws Exception {
		throw new UnsupportedOperationException("Updateing concept description is currently not supported!");
	}

	@Override
	public boolean deleteConceptDescription(String uri) throws Exception {
		return registry.deleteConceptDescription(uri);
	}

}
