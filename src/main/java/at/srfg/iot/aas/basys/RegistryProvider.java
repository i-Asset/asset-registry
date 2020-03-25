package at.srfg.iot.aas.basys;

import java.util.Map;
import java.util.Optional;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.basys.event.handler.util.MappingHelper;
import at.srfg.iot.aas.basys.event.publisher.MappingEventPublisher;
import at.srfg.iot.aas.model.AssetAdministrationShell;
import at.srfg.iot.aas.model.Identifier;
import at.srfg.iot.aas.model.submodel.Submodel;
import at.srfg.iot.aas.repository.AssetAdministrationShellRepository;
import at.srfg.iot.aas.repository.IdentifiableRepository;

@Service
public class RegistryProvider implements IModelProvider {
	@Autowired
	private AssetAdministrationShellRepository aasRepo;
	@Autowired
	private IdentifiableRepository<Submodel> submodelRepo;

	@Autowired
	private MappingEventPublisher mappingEvent;
	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		// 
		at.srfg.iot.aas.model.Identifier id = new at.srfg.iot.aas.model.Identifier(path);
		Optional<AssetAdministrationShell> reg = aasRepo.findByIdentification(id);
		if ( reg.isPresent()) {
			AssetAdministrationShell theShell = reg.get();
			AASDescriptor descriptor = mappingEvent.getDescriptorFromAssetAdministrationShell(theShell);
			
//			org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell bShell = mappingEvent.getFromAssetAdministrationShell(reg.get());
//			bShell.setIdentification(new ModelUrn(theShell.getId()));
//			bShell.setEndpoint(theShell.getFirstEndpoint().getAddress() + "/"+path, theShell.getFirstEndpoint().getType());
//			
			return descriptor;
		}
		return null;
	}
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		// check for the basyx map
		if (newEntity instanceof Map<?,?>) {
			// path must be null ... 
			// check the identifiable ...
			if ( path==null) {
				// when no path - the asset is provided
				@SuppressWarnings("unchecked")
				AASDescriptor aasDescriptor = new AASDescriptor((Map<String, Object>) newEntity);
				AssetAdministrationShell shell = new AssetAdministrationShell(aasDescriptor.getIdentifier().getId());
				for ( Map<String, Object> ep : aasDescriptor.getEndpoints()) {
					int index = ep.get("index")!=null ? Integer.valueOf(ep.get("index").toString()) : 0;
					String address = ep.get("address").toString(); // + "/" + shell.getId();
					String type = ep.get("type").toString();
					shell.setEndpoint(index, address, type);
				}
				aasRepo.save(shell);
				// process arbitrary submodel descriptors
				for (SubmodelDescriptor desc : aasDescriptor.getSubModelDescriptors() ) {
					Identifier id = MappingHelper.getIdentifier(desc);
					Submodel submodel = new Submodel(id, shell);
					submodel.setIdShort(MappingHelper.getIdShort(desc));
					submodelRepo.save(submodel);
				}
			}
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