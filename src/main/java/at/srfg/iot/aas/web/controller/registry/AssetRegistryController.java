package at.srfg.iot.aas.web.controller.registry;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.dictionary.ConceptDescription;
import at.srfg.iot.aas.modeling.SubmodelElement;
import at.srfg.iot.aas.service.registry.AssetRegistryService;
import at.srfg.iot.api.AssetRegistryAPI;

@RestController
public class AssetRegistryController implements AssetRegistryAPI {
	@Autowired
	private AssetRegistryService registry;

	@Override
	public Optional<AssetAdministrationShell> getAssetAdministrationShell(String uri) throws Exception {
		return registry.getAssetAdministrationShell(uri);
	}

	@Override
	public boolean deleteAssetAdministrationShell(String uri) throws Exception {
		return registry.deleteAssetAdministrationShell(uri);
	}

	@Override
	public Optional<Submodel> getSubmodel(String uri) throws Exception {
		return registry.getSubmodel(uri);
	}

	@Override
	public Optional<Submodel> addSubmodel(String uri, Submodel submodel) throws Exception {
		return registry.addSubmodel(uri, submodel);
	}

	@Override
	public Optional<Submodel> setSubmodel(Submodel submodel) throws Exception {
		return registry.setSubmodel(submodel);
	}
	@Override
	public boolean deleteSubmodel(String uri) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<SubmodelElement> getSubmodelElement(String uri, String path) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SubmodelElement> addSubmodelElement(String uri, String path, SubmodelElement submodelElement)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SubmodelElement> setSubmodelElement(String uri, String path, SubmodelElement submodelElement)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteSubmodelElement(String uri, String path) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<ConceptDescription> getConceptDescription(String uri) throws Exception {
		return registry.getConceptDescription(uri);
	}

	@Override
	public Optional<ConceptDescription> addConceptDescription(String uri, ConceptDescription submodelElement)
			throws Exception {
		// TODO Auto-generated method stub
		return registry.addConceptDescription(submodelElement);
	}

	@Override
	public Optional<ConceptDescription> setConceptDescription(String uri, ConceptDescription conceptDescription)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteConceptDescription(String uri) throws Exception {
		return registry.deleteConceptDescription(uri);
	}

}
