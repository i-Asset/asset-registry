package at.srfg.iot.aas.service.registry.event.object;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdType;
import at.srfg.iot.common.datamodel.asset.api.ISubmodel;

@Service
public class DescriptorService {
	
	@Autowired
	private ApplicationEventPublisher publisher;
	@Autowired
	private RegistryService registry;

	
	public Optional<AssetAdministrationShell> registerAdministrationShell(AssetAdministrationShellDescriptor dto) {
		Optional<AssetAdministrationShell> shell = registry.getAssetAdministrationShell(dto.getIdentification());
		if (! shell.isPresent()) {
			AssetAdministrationShell newShell = new AssetAdministrationShell(dto.getIdentification());
			AssetAdministrationShellDescriptorEventObject e = new AssetAdministrationShellDescriptorEventObject(this, newShell, dto);
			// event processes all contained elements of the descriptor (endpoints, submodel descriptors, referable, identifiable)
			publisher.publishEvent(e);
			// save before processing dependencies
			AssetAdministrationShell stored = registry.saveAssetAdministrationShell(e.getEntity());
			// process dependencies
			postProcessAssetAdministrationShellDescriptor(stored, dto);
			// 
			return Optional.of(stored);
		}
		return shell;
	}
	private void postProcessAssetAdministrationShellDescriptor(AssetAdministrationShell stored, AssetAdministrationShellDescriptor dto) {
		for ( SubmodelDescriptor sub : dto.getSubmodels()) {
			// the submodel might have an identifier "idShort", so need to find the model based on identifier
			if ( sub.getIdType().equals(IdType.IdShort)) {
				// retrieve the submodel via idShort
				Optional<ISubmodel> existing = stored.getSubmodel(sub.getIdShort());
				processSubmodel(stored,  existing.orElse(null),  sub);
			}
			else {
				// the identifier is global, read the model from the registry
				Optional<Submodel> existing = registry.getSubmodel(sub.getIdentification());
				processSubmodel(stored,  existing.orElse(null),  sub);
			}
		}
	}
	private void processSubmodel(AssetAdministrationShell shell, ISubmodel existing, SubmodelDescriptor dto) {
		if (existing != null) {
			throw new IllegalArgumentException(String.format("Submodel [%s] already exists and is not part of the the AssetAdministrationShell [%s]!",
					existing.getIdentification().toString(),
					shell.getIdentification().toString()
					));
			// update only when shell's are equal
//			if ( e.getAssetAdministrationShell().equals(shell)) {
//			}
//			else {
//				worker.registerSubmodel(existing.get(), dto);
//			}
		}
		else {
			registerSubmodel(shell, dto);
		}
		
	}
	private void registerSubmodel(AssetAdministrationShell shell, SubmodelDescriptor dto) {
		SubmodelDescriptorEventObject e = new SubmodelDescriptorEventObject(this, new Submodel(shell), dto);
		publisher.publishEvent(e);
		Submodel changed = e.getEntity();
		// save the submodel
		Submodel updated = registry.saveSubmodel(changed);
		// postprocess submodel
		
	}
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
}