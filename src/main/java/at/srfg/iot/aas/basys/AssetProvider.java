package at.srfg.iot.aas.basys;

import java.util.Map;
import java.util.Optional;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.model.Asset;
import at.srfg.iot.aas.model.AssetAdministrationShell;
import at.srfg.iot.aas.model.Identifier;
import at.srfg.iot.aas.repository.AssetAdministrationShellRepository;
import at.srfg.iot.aas.repository.IdentifiableRepository;

@Service
public class AssetProvider implements IModelProvider {

	
	@Autowired
	private AssetAdministrationShellRepository aasRepo;

	
	@Autowired
	private BasysService basys;

	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		Optional<AssetAdministrationShell> aas = aasRepo.findByIdentification(new Identifier(path));
		if ( aas.isPresent()) {
			return basys.processAssetAdministrationShell(aas.get());
		}
//		Optional<Registration> reg = registry.findById(path);
//		if (reg.isPresent()) {
//			Registration entry = reg.get();
//			ModelUrn urn = new ModelUrn(entry.getIdentifier());
//			AASDescriptor descriptor = new AASDescriptor(urn, entry.getFirstEndpoint().getAddress());
//			return descriptor;
//		}
		return null;
	}
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		// 
		Optional<AssetAdministrationShell> aas = aasRepo.findByIdentification(new Identifier(path));
		// TODO Auto-generated method stub

	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		// check for the basyx map
		if (newEntity instanceof Map<?,?>) {
			// check the identifiable ...
			if ( path==null) {
				// when no path - the asset is provided
				@SuppressWarnings("unchecked")
				AASDescriptor aasDescriptor = new AASDescriptor((Map<String, Object>) newEntity);

				basys.processAssetAdministrationShell(aasDescriptor);
				//
				//
			}
			else if ("submodels".equals(path) && newEntity instanceof Map ) {
				// handle submodel & properties ...
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String,Object>)newEntity;
				basys.processSubmodel(map);
			}
			
			//

		}

	}

	@Override
	public void deleteValue(String path) throws Exception {
		Optional<AssetAdministrationShell> reg = aasRepo.findByIdentification(new Identifier(path));
		if ( reg.isPresent() ) {
			aasRepo.delete(reg.get());
		}
		

	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		throw new Exception("wrong usage");

	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws Exception {
		throw new Exception("wrong usage");
	}

}
