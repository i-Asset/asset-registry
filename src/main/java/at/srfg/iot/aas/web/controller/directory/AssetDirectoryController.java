package at.srfg.iot.aas.web.controller.directory;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.aas.service.registry.RegistryWorker;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdType;
import at.srfg.iot.common.datamodel.asset.api.AssetDirectoryAPI;

@RestController
public class AssetDirectoryController implements AssetDirectoryAPI {
	@Value("${iAsset.platformHost:}")
	private String platformHost;
	@Value("${server.port:}")
	private int port;

	@Autowired
	private RegistryService registry;
	
	@Autowired
	private RegistryWorker worker;

	@Override
	public Optional<AssetAdministrationShellDescriptor> lookup(String aasIdentifier) {
		Identifier id = new Identifier(aasIdentifier);
		Optional<AssetAdministrationShell> aas = registry.getAssetAdministrationShell(id);
		if (aas.isPresent()) {
			AssetAdministrationShellDescriptor desc = new AssetAdministrationShellDescriptor(aas.get());
			desc.withDefaultEndpoint(platformHost);
			return Optional.of(desc);
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<SubmodelDescriptor> lookup(String aasIdentifier, String submodelIdentifier) {
		Identifier id = new Identifier(submodelIdentifier);
		if ( id.getIdType().equals(IdType.IdShort)) {
			Optional<AssetAdministrationShell> aas = registry.getAssetAdministrationShell(id);
			if (aas.isPresent()) {
				Optional<Submodel> sub = aas.get().getSubmodel(id);
				if ( sub.isPresent()) {
					return Optional.of(new SubmodelDescriptor(sub.get()));
				}
			}
			return Optional.empty();
			
		}
		Optional<Submodel> submodel = registry.getSubmodel(id);
		if ( submodel.isPresent()) {
			return Optional.of(new SubmodelDescriptor(submodel.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<AssetAdministrationShell> register(AssetAdministrationShellDescriptor shell) {
		return worker.registerAdministrationShell(shell);
	}

	@Override
	public Optional<Submodel> register(String aasIdentifier, SubmodelDescriptor model) {
		return worker.registerSubmodel(aasIdentifier, model);		
	}

	@Override
	public void unregister(String aasIdentifier) {
		registry.deleteAssetAdministrationShell(aasIdentifier);
		
		
	}

	@Override
	public void unregister(String aasIdentifier, List<String> submodelIdentifier) {
		// TODO Auto-generated method stub
		
	}

}
